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

public class words_set5 extends AppCompatActivity {
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
        setContentView(R.layout.activity_words_set5);

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

        next.setOnClickListener((View.OnClickListener) new words_set5.ClickListener());
        back.setOnClickListener((View.OnClickListener) new words_set5.ClickListener());
        MediaController mediaController = new MediaController(this);
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("Learn a sign for 'Thank you'");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FThank%20you.mp4?alt=media&token=257ce25b-8783-4b19-b367-661bb09ce879");
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
                    question.setText("Learn a sign for 'Thank you'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FThank%20you.mp4?alt=media&token=257ce25b-8783-4b19-b367-661bb09ce879");
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;
                } else if (vs == 2) {
                    question.setText("Learn a sign for 'See you later'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FSee%20you%20later.mp4?alt=media&token=5eb3e96c-2ad4-4cde-a70e-b69b4603438b"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -=1;

                } else if (vs == 3) {
                    question.setText("Learn a sign for 'Nice to meet you'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FNice%20to%20meet%20you.mp4?alt=media&token=b0e99d7c-b61a-4506-9d7d-13eac63b1643"); ///put Url
                    video1.setVideoURI(uri);
                    video1.requestFocus();
                    video1.start();
                    vs -= 1;
                }else if (vs == 4){
                    question.setText("Learn a sign for 'Take care'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FTake%20care.mp4?alt=media&token=92da2501-b60c-4e89-9be4-c86cfa41b2ce");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs -= 1;
                }
            }else if (v.getId() == R.id.next) {
                if (vs == 0) {
                    question.setText("Learn a sign for 'See you later'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FSee%20you%20later.mp4?alt=media&token=5eb3e96c-2ad4-4cde-a70e-b69b4603438b");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 1) {
                    question.setText("Learn a sign for 'Nice to meet you'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FNice%20to%20meet%20you.mp4?alt=media&token=b0e99d7c-b61a-4506-9d7d-13eac63b1643");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 2){
                    question.setText("Learn a sign for 'Take care'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FTake%20care.mp4?alt=media&token=92da2501-b60c-4e89-9be4-c86cfa41b2ce");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 3){
                    question.setText("Learn a sign for 'Good bye'");
                    Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FGoodbye.mp4?alt=media&token=0bb04cd7-31bd-4e2a-b102-7d1acad875a9");
                    video1.setVideoURI(uri);
                    video1.start();
                    vs += 1;
                }else if (vs == 4){
                    Intent i = new Intent(getApplicationContext(), WordsMcq5.class);
                    startActivity(i);
                }
            }
        }
    }
}