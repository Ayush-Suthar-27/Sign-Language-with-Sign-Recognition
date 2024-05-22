package com.example.signswift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    EditText fUsername,fEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    Button Save;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fEmail = findViewById(R.id.fEmail);
        fUsername = findViewById(R.id.fUsername);
        Save = findViewById(R.id.save);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        Intent data = getIntent();
        String Username = data.getStringExtra("UserName");
        String Email = data.getStringExtra("Email");

        fUsername.setText(Username);
        fEmail.setText(Email);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fUsername.getText().toString().isEmpty() || fEmail.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this, "One or Many fields are Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = fEmail.getText().toString();
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(email)
                        .build();

                user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = fstore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Email",fEmail.getText().toString());
                        edited.put("UserName",fUsername.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}