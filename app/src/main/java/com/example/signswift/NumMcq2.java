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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class NumMcq2 extends AppCompatActivity {

    TextView fUsername, question;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Button check,next;
    VideoView video1,video2;
    ImageView back;
    FrameLayout option1,option2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_mcq2);

        fUsername = findViewById(R.id.fUsername);
        check = findViewById(R.id.check);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        question =findViewById(R.id.questions);
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

        MediaController mediaController = new MediaController(this);
        Q1();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),num_set2.class));

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void Q1(){
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("The sign for '7' is?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F2.mp4?alt=media&token=9240a366-1df1-428b-8fe8-30757e82e7f9");
        video1.setVideoURI(uri);//2
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });

        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F7.mp4?alt=media&token=9f26bba0-0b30-4891-bb4b-0b355d598803");
        video2.setVideoURI(uri1);//7
        video2.requestFocus();
        video2.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(true);
                option2.setSelected(false);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(false);
                option2.setSelected(true);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Q2();
                check.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
            }
        });
    }

    public void Q2() {
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("What is the sign for '10'?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F10.mp4?alt=media&token=3e16b7d5-5f7a-4907-8868-7beb5e6f8a49");
        video1.setVideoURI(uri);//10
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });

        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F0.mp4?alt=media&token=8bb2ac91-cfc1-4d45-aa5c-f74d09e126d0");
        video2.setVideoURI(uri1);//0
        video2.requestFocus();
        video2.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(true);
                option2.setSelected(false);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(false);
                option2.setSelected(true);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Q3();
                check.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
            }
        });
    }

    public void Q3() {
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("The sign for '8' is?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F8.mp4?alt=media&token=23b35422-b173-4d7e-a497-6fc94365b9a7");
        video1.setVideoURI(uri);//8
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });
        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Numbers%2F2.mp4?alt=media&token=9240a366-1df1-428b-8fe8-30757e82e7f9");
        video2.setVideoURI(uri1);//2
        video2.requestFocus();
        video2.start();


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(NumMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(true);
                option2.setSelected(false);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option1.setSelected(false);
                option2.setSelected(true);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Numbers.class));
            }
        });
    }
}