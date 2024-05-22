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

public class alpha_set1 extends AppCompatActivity {

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
        setContentView(R.layout.activity_alpha_set1);

        fUsername = findViewById(R.id.fUsername);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        video1 = findViewById(R.id.video1);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        question = findViewById(R.id.questions);
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fUsername.setText(value.getString("UserName"));
            }
        });

        next.setOnClickListener((View.OnClickListener) new ClickListener());
        back.setOnClickListener((View.OnClickListener) new ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for 'A'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FA.mp4?alt=media&token=30e30566-7f1b-42bb-9ee5-bdb2441cb76a");
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
                    Intent intent = new Intent(getApplicationContext(), Alphabets.class);
                    startActivity(intent);
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'A'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FA.mp4?alt=media&token=30e30566-7f1b-42bb-9ee5-bdb2441cb76a");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for 'B'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FB.mp4?alt=media&token=6c794020-49de-406c-9f34-a9a1733fcb61"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                } else if (vs == 3) {
                    question.setText("Learn a sign for 'C'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FC.mp4?alt=media&token=a6a9b9cb-ea40-403e-a4da-c5aa105f49ab"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -= 1;
                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for 'B'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FB.mp4?alt=media&token=6c794020-49de-406c-9f34-a9a1733fcb61");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'C'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FC.mp4?alt=media&token=a6a9b9cb-ea40-403e-a4da-c5aa105f49ab");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    question.setText("Learn a sign for 'D'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FD.mp4?alt=media&token=de3ce51a-7c10-48fc-8f1b-11dee9abe860");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 3){
                    Intent i = new Intent(getApplicationContext(), AlphaMcq1.class);
                    startActivity(i);
                }
            }
        }
    }
}
