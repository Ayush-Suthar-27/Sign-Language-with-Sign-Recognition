package com.example.signswift;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Practice extends CameraActivity {

    CameraBridgeViewBase cameraBridgeViewBase;
    Mat rgba;
    Mat gray;
    public int mCamera = 0;
    TextView fUsername;
    ImageView profileimage;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    private objectDetectorClass objectDetectorClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_practice);
        fUsername = findViewById(R.id.fUsername);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        profileimage = findViewById(R.id.profileImage);
        userID = fAuth.getCurrentUser().getUid();

        try {
            objectDetectorClass = new objectDetectorClass(getAssets(),"ssd_mobilenet.tflite","labelmap.txt",300,"Sign_language_model.tflite",96);
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }

        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);
        cameraBridgeViewBase.setCameraIndex(mCamera);

        cameraBridgeViewBase.setCvCameraViewListener(new CameraBridgeViewBase.CvCameraViewListener2() {
            @Override
            public void onCameraViewStarted(int width, int height) {
                rgba = new Mat(width,height,CvType.CV_8UC4);
                gray = new Mat(width,height,CvType.CV_8UC1);
            }

            @Override
            public void onCameraViewStopped() {
                rgba.release();
                gray.release();
            }

            @Override
            public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
                rgba = inputFrame.rgba();
                gray = inputFrame.gray();
                if(mCamera == 1){
                    Core.flip(rgba,rgba,-1);
                    Core.flip(gray,gray,-1);
                }
                Mat out = new Mat();
                out = objectDetectorClass.recognizeImage(rgba);
                return out;
            }
        });

        if(OpenCVLoader.initDebug()){
            Log.d("Loaded","Success");
            cameraBridgeViewBase.enableView();
        }else {
            Log.d("Loaded","err");
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_home){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            }
            if (item.getItemId() == R.id.bottom_practice){
                return true;
            }
            if (item.getItemId() == R.id.bottom_profile){
                startActivity(new Intent(getApplicationContext(),Profile.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fUsername.setText(value.getString("UserName"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraBridgeViewBase.enableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraBridgeViewBase.disableView();
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    private void getPermission(){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
            getPermission();
        }
    }


}