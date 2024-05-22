package com.example.signswift;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class AlphaMcq2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_alpha_mcq2);

        question = findViewById(R.id.questions);
        fUsername = findViewById(R.id.fUsername);
        check = findViewById(R.id.check);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
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
                startActivity(new Intent(getApplicationContext(),alpha_set2.class));

            }
        });
    }

    public void Q1(){
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });
        question.setText("The sign for 'E' is?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FE.mp4?alt=media&token=b131523f-c995-4943-8f9c-9a064ff677fd");
        video1.setVideoURI(uri);//E
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });

        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FH.mp4?alt=media&token=015cc8be-2764-44fc-bf19-84689728cd59");
        video2.setVideoURI(uri1);//H
        video2.requestFocus();
        video2.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
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
        question.setText("Which is the sign for 'G'?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FF.mp4?alt=media&token=280720ef-c390-4e30-bf4e-1b281086dccd");
        video1.setVideoURI(uri);//F
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });

        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FG.mp4?alt=media&token=61a9c140-48e1-43e2-bc7f-4e5107e0d618");
        video2.setVideoURI(uri1);//G
        video2.requestFocus();
        video2.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
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
        question.setText("What is the sign for 'H'?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FH.mp4?alt=media&token=015cc8be-2764-44fc-bf19-84689728cd59");
        video1.setVideoURI(uri);//H
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });
        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Alphabets%2FAlphabets%20Rework%2FF.mp4?alt=media&token=280720ef-c390-4e30-bf4e-1b281086dccd");
        video2.setVideoURI(uri1);//F
        video2.requestFocus();
        video2.start();


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(AlphaMcq2.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), Alphabets.class));
            }
        });
    }
}
