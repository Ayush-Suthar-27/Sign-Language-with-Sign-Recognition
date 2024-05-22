package com.example.signswift;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class alpha_set3 extends AppCompatActivity {

    TextView fUsername, question;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Button next;
    VideoView video1;
    ImageView back;
    int vs = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_set3);

        fUsername = findViewById(R.id.fUsername);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        video1 = findViewById(R.id.video1);
        question = findViewById(R.id.questions);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fUsername.setText(value.getString("UserName"));
            }
        });

        next.setOnClickListener((View.OnClickListener) new alpha_set3.ClickListener());
        back.setOnClickListener((View.OnClickListener) new alpha_set3.ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for 'I'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FI.mp4?alt=media&token=5b6d8be3-391b-4aac-b53d-c65029ec0991");
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();
    }

    public class ClickListener implements View.OnClickListener{


        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.back) {
                if (vs == 0){
                    Intent intent = new Intent(getApplicationContext(), Alphabets.class);
                    startActivity(intent);
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'I'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FI.mp4?alt=media&token=5b6d8be3-391b-4aac-b53d-c65029ec0991");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for 'J'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FJ.mp4?alt=media&token=febe070a-8f1b-4d3b-a498-380ff2c9e025"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                } else if (vs == 3) {
                    question.setText("Learn a sign for 'K'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FK.mp4?alt=media&token=e1da588f-9166-402a-b8ab-dca3bc32a885"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -= 1;
                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for 'J'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FJ.mp4?alt=media&token=febe070a-8f1b-4d3b-a498-380ff2c9e025");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'K'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FK.mp4?alt=media&token=e1da588f-9166-402a-b8ab-dca3bc32a885");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    question.setText("Learn a sign for 'L'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FL.mp4?alt=media&token=865f94fa-9faf-4d99-b600-f3ce0a4ba861");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 3){
                    Intent i = new Intent(getApplicationContext(), AlphaMcq3.class);
                    startActivity(i);
                }
            }
        }
    }
}
