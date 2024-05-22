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

public class alpha_set2 extends AppCompatActivity {

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
            setContentView(R.layout.activity_alpha_set2);

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

            next.setOnClickListener((View.OnClickListener) new alpha_set2.ClickListener());
            back.setOnClickListener((View.OnClickListener) new alpha_set2.ClickListener());
            MediaController mediaController = new MediaController(this);
            video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    video1.start(); //need to make transition seamless.
                }
            });
            question.setText("Learn a sign for 'E'");
            Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FE.mp4?alt=media&token=cb506dcb-7a34-4847-9e97-45f5552daf67");
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
                        question.setText("Learn a sign for 'E'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FE.mp4?alt=media&token=cb506dcb-7a34-4847-9e97-45f5552daf67");
                        video1.setVideoURI(uri);
                        video1.requestFocus();
                        video1.start();
                        vs -=1;
                    } else if (vs == 2) {
                        question.setText("Learn a sign for 'F'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FF.mp4?alt=media&token=57a0c561-93e7-4b29-a49c-f686f648cec0"); ///put Url
                        video1.setVideoURI(uri);
                        video1.requestFocus();
                        video1.start();
                        vs -=1;

                    } else if (vs == 3) {
                        question.setText("Learn a sign for 'G'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FG.mp4?alt=media&token=5f9fe49e-5b88-42ad-b2fb-b6e61f917a2c"); ///put Url
                        video1.setVideoURI(uri);
                        video1.requestFocus();
                        video1.start();
                        vs -= 1;
                    }
                }else if (v.getId() == R.id.next) {
                    if (vs == 0) {
                        question.setText("Learn a sign for 'F'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FF.mp4?alt=media&token=57a0c561-93e7-4b29-a49c-f686f648cec0");
                        video1.setVideoURI(uri);
                        video1.start();
                        vs += 1;
                    }else if (vs == 1) {
                        question.setText("Learn a sign for 'G'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FG.mp4?alt=media&token=5f9fe49e-5b88-42ad-b2fb-b6e61f917a2c");
                        video1.setVideoURI(uri);
                        video1.start();
                        vs += 1;
                    }else if (vs == 2){
                        question.setText("Learn a sign for 'H'");
                        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FH.mp4?alt=media&token=5e2ec53d-50f3-4606-a353-70593588a050");
                        video1.setVideoURI(uri);
                        video1.start();
                        vs += 1;
                    }else if (vs == 3){
                        Intent i = new Intent(getApplicationContext(), AlphaMcq2.class);
                        startActivity(i);
                    }
                }
            }
        }
    }
