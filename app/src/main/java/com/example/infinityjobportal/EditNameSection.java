package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditNameSection extends AppCompatActivity {
EditText firstName, lastName, tagLine;
TextView save;
ImageView userPic, back;
ProgressBar progressBar;

FirebaseAuth mAuth;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_section);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        tagLine = findViewById(R.id.tagLine);

        save = findViewById(R.id.save);
        userPic = findViewById(R.id.userPic);
        back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);

        mAuth= FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();



        loadInfo();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                progressBar.setVisibility(View.VISIBLE);

                //Toast.makeText(getApplicationContext(),"save called",Toast.LENGTH_SHORT).show();
                DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());


                Map<String,Object> updates = new HashMap<>();
                updates.put("firstName", firstName.getText().toString());
                updates.put("lastName", lastName.getText().toString());
                updates.put("tagLine", tagLine.getText().toString());
                String firstname = firstName.getText().toString();
                String lastname = lastName.getText().toString();

                if (TextUtils.isEmpty(firstname)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(firstName);
                    firstName.setError("Invalid");
                    return;
                }
                if (TextUtils.isEmpty(lastname)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(lastName);
                    lastName.setError("Invalid");
                    return;
                }

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

        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdateUserPic.class));
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
                        firstName.setText(user.getFirstName());
                        lastName.setText(user.getLastName());



                        if(user.getTagLine().equals(""))
                        {
                            tagLine.setHint("Add your tag line.");
                        }
                        else
                        {
                            tagLine.setText(user.getTagLine());
                        }






                        FirebaseStorage firebaseStorage;
                        StorageReference storageReference;

                        firebaseStorage = FirebaseStorage.getInstance();
                        storageReference = firebaseStorage.getReference();

//        StorageReference imageRef = storageReference.child("Images/my.png");
                        StorageReference imageRef = storageReference.child("user/"+user.getUserProfilePic());

                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Glide.with(getApplicationContext())
                                        .load(uri)
                                        .circleCrop()
                                        .into(userPic);

                                //Picasso.get().load(uri).into(limg);

                                // Toast.makeText(getContext(),"Success.",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Profile Pic is not available",Toast.LENGTH_SHORT).show();
                            }
                        });





                    } else {

                    }
                } else {

                }
            }
        });

    }
}