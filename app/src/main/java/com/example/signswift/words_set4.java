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

public class words_set4 extends AppCompatActivity {
    TextView fUsername, question;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Button next;
    VideoView video1;
    ImageView back;
    int vs = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_set4);

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

        next.setOnClickListener((View.OnClickListener) new words_set4.ClickListener());
        back.setOnClickListener((View.OnClickListener) new words_set4.ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for 'Yes'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FYes.mp4?alt=media&token=390549cd-4b1b-43d9-8dd3-1a114999b843");
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();
    }

    public class ClickListener implements View.OnClickListener{


        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.back) {
                if (vs == 0) {
                    Intent intent = new Intent(getApplicationContext(), Words.class);
                    startActivity(intent);
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'Yes'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FYes.mp4?alt=media&token=390549cd-4b1b-43d9-8dd3-1a114999b843");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for 'No'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FNo.mp4?alt=media&token=efb62ac7-9e9e-4b6b-b348-7eb8a29c0227"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                } else if (vs == 3) {
                    question.setText("Learn a sign for 'Please'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FPlease.mp4?alt=media&token=9e6b0d26-e69a-4d08-b595-ee6162aef684"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -= 1;
                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for 'No'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FNo.mp4?alt=media&token=efb62ac7-9e9e-4b6b-b348-7eb8a29c0227");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'Please'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FPlease.mp4?alt=media&token=9e6b0d26-e69a-4d08-b595-ee6162aef684");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    question.setText("Learn a sign for 'Sorry'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FSorry.mp4?alt=media&token=760f68e8-e767-4ce1-9db4-28c303fa06ba");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 3){
                    Intent i = new Intent(getApplicationContext(), WordsMcq4.class);
                    startActivity(i);
                }
            }
        }
    }
}