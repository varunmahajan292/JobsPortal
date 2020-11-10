package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import   com.example.infinityjobportal.model.*;

public class ClientSignUp extends AppCompatActivity {
    EditText firstName,lastName,email,password,mobile;
    Button login,signup;
    ProgressBar progressBar;
    String  patterntomatch ="[0-9]{10}";
    public FirebaseAuth mAuth;
    FirebaseFirestore db;

    TextView errorView;
    String emailString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);


        firstName=findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        mobile=(EditText)findViewById(R.id.mobile);
        login=(Button) findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        emailString= email.getText().toString();
        progressBar = findViewById(R.id.progressBar);

        errorView = findViewById(R.id.errorView);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ClientLogin.class);
                startActivity(i);
            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Bounce)
                        .duration(700)
                        .repeat(2)
                        .playOn(signup);

                if (email.getText().toString().contentEquals("")) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(3)
                            .playOn(email);

                    errorView.setText("Email cannot be empty");


                } else if (password.getText().toString().contentEquals("")) {

                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(3)
                            .playOn(password);
                    errorView.setText("Password cannot be empty");


                }
               else if (firstName.getText().toString().contentEquals("")) {

                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(3)
                            .playOn(firstName);
                    errorView.setText("Firstname cannot be empty");
                }
                else if (lastName.getText().toString().contentEquals("")) {

                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(3)
                            .playOn(lastName);
                    errorView.setText("Lastname cannot be empty");
                }
                else if (!mobile.getText().toString().matches(patterntomatch)) {

                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(3)
                            .playOn(mobile);
                    errorView.setText("Please enter valid number");
                }else {


                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    loadOtherDetails();


                                    try {
                                        if (user != null)
                                            user.sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                   // loadOtherDetails();
                                                                progressBar.setVisibility(View.GONE);
                                                                //Toast.makeText(getApplicationContext(),"Verfy Email",Toast.LENGTH_LONG).show();
                                                                //  loadOtherDetails();
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(ClientSignUp.this);
                                                                builder.setMessage("A verification Email Is Sent To Your Registered EmailID, please click on the link and Sign in again!")
                                                                        .setCancelable(false)
                                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int id) {

                                                                                Intent i = new Intent(getApplicationContext(),ClientLogin.class);
                                                                                startActivity(i);
                                                                            }
                                                                        });
                                                                AlertDialog alert = builder.create();
                                                                alert.show();

                                                            }
                                                        }


                                                    });

                                    } catch (Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                        errorView.setText(e.getMessage());
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    errorView.setText(e.getMessage());
                                }
                            });


                }

            }
        });





    }

    private void loadOtherDetails() {
        User user = new User();
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setNumber(mobile.getText().toString());
        user.setEmail(email.getText().toString());
        user.setTagLine("");
        user.setAbout("");
        user.setWebsite("");
        user.setUserProfilePic("user.png");
        user.setCity("");
        user.setProvince("");
        user.setCountry("");
        user.setStreet("");
        user.setBuilding("");
        user.setApartment("");
        user.setZipCode("");


      //  db.collection("users").document(user.getEmail()).set(user)
        db.collection("users").document(mAuth.getCurrentUser().getEmail()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}