package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateAbout extends AppCompatActivity {
    EditText about;
    TextView save;
    ImageView  back;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_about);

        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);
        about = findViewById(R.id.about);

        mAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        loadInfo();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),"save called",Toast.LENGTH_SHORT).show();
                DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());

                Map<String,Object> updates = new HashMap<>();
                updates.put("about", about.getText().toString());

                docRef.update((Map<String, Object>) updates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"Data Updated...",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Data Not Updated. Try Again..,",Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }





    private void loadInfo() {
        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        User user = document.toObject(User.class);

                        if(user.getAbout().equals(""))
                        {
                            about.setHint("Write here about your self.");
                        }
                        else
                        {
                            about.setText(user.getAbout());
                        }
                    } else {

                    }
                } else {

                }
            }
        });

    }
}