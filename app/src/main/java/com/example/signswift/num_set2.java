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

public class num_set2 extends AppCompatActivity {
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
        setContentView(R.layout.activity_num_set2);

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

        next.setOnClickListener((View.OnClickListener) new num_set2.ClickListener());
        back.setOnClickListener((View.OnClickListener) new num_set2.ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for '6'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F6.mp4?alt=media&token=085a69e6-8a73-41dc-82f9-56d6ce026d48");
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
                    Intent intent = new Intent(getApplicationContext(), Numbers.class);
                    startActivity(intent);
                }else if (vs == 1) {
                    question.setText("Learn a sign for '6'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F6.mp4?alt=media&token=085a69e6-8a73-41dc-82f9-56d6ce026d48");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for '7'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F7.mp4?alt=media&token=9f26bba0-0b30-4891-bb4b-0b355d598803"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                } else if (vs == 3) {
                    question.setText("Learn a sign for '8'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F8.mp4?alt=media&token=23b35422-b173-4d7e-a497-6fc94365b9a7"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -= 1;
                }else if (vs == 4){
                    question.setText("Learn a sign for '9'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F9.mp4?alt=media&token=eafda155-1330-4488-a744-e4c586e6ec8f");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs -= 1;
                }else if (vs == 5){
                    question.setText("Learn a sign for '10'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F10.mp4?alt=media&token=3e16b7d5-5f7a-4907-8868-7beb5e6f8a49");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs -= 1;
                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for '7'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F7.mp4?alt=media&token=9f26bba0-0b30-4891-bb4b-0b355d598803");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for '8'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F8.mp4?alt=media&token=23b35422-b173-4d7e-a497-6fc94365b9a7");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    question.setText("Learn a sign for '9'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F9.mp4?alt=media&token=eafda155-1330-4488-a744-e4c586e6ec8f");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 3){
                    question.setText("Learn a sign for '10'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F10.mp4?alt=media&token=3e16b7d5-5f7a-4907-8868-7beb5e6f8a49");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 4){
                    question.setText("Learn a sign for '11'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F11.mp4?alt=media&token=7827e4a3-ec18-49f5-b90e-b5ac515b88ae");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 5){
                    Intent i = new Intent(getApplicationContext(), NumMcq2.class);
                    startActivity(i);
                }
            }
        }
    }
}