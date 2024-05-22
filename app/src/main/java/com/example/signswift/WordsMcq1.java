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

public class WordsMcq1 extends AppCompatActivity {
    TextView fUsername, question;
    String userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Button check,next,op1,op2,op3;
    VideoView video1,video2;
    ImageView back;
    FrameLayout option1,option2;
    int vs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_mcq1);

        fUsername = findViewById(R.id.fUsername);
        check = findViewById(R.id.check);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        question = findViewById(R.id.questions);
        op1 = findViewById(R.id.button2);
        op2 = findViewById(R.id.button3);
        op3 = findViewById(R.id.button4);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        userID = fAuth.getCurrentUser().getUid();
        op1.setOnClickListener(new WordsMcq1.ClickListener());
        op2.setOnClickListener(new WordsMcq1.ClickListener());
        op3.setOnClickListener(new WordsMcq1.ClickListener());
        DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fUsername.setText(value.getString("UserName"));
            }
        });

        MediaController mediaController = new MediaController(this);
        video2.setVisibility(View.GONE);
        op1.setVisibility(View.VISIBLE);
        op2.setVisibility(View.VISIBLE);
        op3.setVisibility(View.VISIBLE);
        Q1();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),words_set1.class));

            }
        });
    }

    public void Q1(){
        video1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video1.start(); //need to make transition seamless.
            }
        });

        question.setText("What's the sign for?");//How are you?
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FWords%20Rework%2Fhow%20are%20you.mp4?alt=media&token=5298069b-34af-4830-9aff-f4d4e9b2a512");
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vs += 1;
                if (!op2.isSelected() && !op1.isSelected() && !op3.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (op1.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                if (op2.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (op3.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Q2();
                op1.setText("What's up");
                op2.setText("Why are you sad?");
                op3.setText("Who are you?");
                video2.setVisibility(View.GONE);
                op1.setVisibility(View.VISIBLE);
                op2.setVisibility(View.VISIBLE);
                op3.setVisibility(View.VISIBLE);
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

        question.setText("What's the sign for?");//What's up
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FWords%20Rework%2Fwhat's%20up.mp4?alt=media&token=be3b9e9b-aef6-4548-8d6a-b147b6fb8101");
        video1.setVideoURI(uri);
        video1.requestFocus();
        video1.start();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vs += 1;
                if (!op2.isSelected() && !op1.isSelected() && !op3.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (op1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (op2.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                if (op3.isSelected()) {
                    op1.setSelected(false);
                    op2.setSelected(false);
                    op3.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Q3();
                video2.setVisibility(View.VISIBLE);
                op1.setVisibility(View.GONE);
                op2.setVisibility(View.GONE);
                op3.setVisibility(View.GONE);
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

        question.setText("What is the sign for 'Hello'?");
        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FWords%20Rework%2Fhello.mp4?alt=media&token=2390ac76-202d-4dd4-b91b-448525a98812");
        video1.setVideoURI(uri);//hello
        video1.requestFocus();
        video1.start();

        video2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                video2.start(); //need to make transition seamless.
            }
        });
        Uri uri1 = Uri.parse("https://firebasestorage.googleapis.com/v0/b/signswift-7c7cc.appspot.com/o/Words%2FWords%20Rework%2Fgoodbye.mp4?alt=media&token=c8f7529d-95e0-47ec-a45e-9f370fc364ee");
        video2.setVideoURI(uri1);//goodbye
        video2.requestFocus();
        video2.start();


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!option2.isSelected() && !option1.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Please!! select atleast 1 option", Toast.LENGTH_SHORT).show();
                }
                if (option1.isSelected()) {
                    check.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                }
                if (option2.isSelected()) {
                    option1.setSelected(false);
                    option2.setSelected(false);
                    Toast.makeText(WordsMcq1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), Words.class));
            }
        });
    }

    public class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button2) {
                op1.setSelected(true);
                op2.setSelected(false);
                op3.setSelected(false);
                if (vs==0){
                    Q1();
                } else if (vs==1) {
                    Q2();
                }

            } else if (v.getId() == R.id.button3) {
                op1.setSelected(false);
                op2.setSelected(true);
                op3.setSelected(false);
                if (vs==0){
                    Q1();
                } else if (vs==1) {
                    Q2();
                }
            } else if (v.getId() == R.id.button4) {
                op1.setSelected(false);
                op2.setSelected(false);
                op3.setSelected(true);
                if (vs==0){
                    Q1();
                } else if (vs==1) {
                    Q2();
                }
            }
        }
    }
}
