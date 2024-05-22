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

public class words_set1 extends AppCompatActivity {
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
        setContentView(R.layout.activity_words_set1);

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

        next.setOnClickListener((View.OnClickListener) new words_set1.ClickListener());
        back.setOnClickListener((View.OnClickListener) new words_set1.ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for 'Hello'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FHello.mp4?alt=media&token=85bacfbe-a8e5-4e89-b07c-258ecab9b24f");
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
                    question.setText("Learn a sign for 'Hello'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FHello.mp4?alt=media&token=85bacfbe-a8e5-4e89-b07c-258ecab9b24f");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for 'How are you?'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FHow%20are%20you.mp4?alt=media&token=12f46b04-ca88-49bc-96f9-572aa5bbb16b"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for 'How are you?'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FHow%20are%20you.mp4?alt=media&token=12f46b04-ca88-49bc-96f9-572aa5bbb16b");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'What's up'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FWhats%20up.mp4?alt=media&token=30e878ee-e864-452f-840b-329d072914cb");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    Intent i = new Intent(getApplicationContext(), WordsMcq1.class);
                    startActivity(i);
                }
            }
        }
    }
}
