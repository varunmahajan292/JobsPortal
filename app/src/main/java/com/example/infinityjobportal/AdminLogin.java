package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinityjobportal.admin.AdminMainActivity;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class AdminLogin extends AppCompatActivity {
    private static final String TAG = "AdminLogin";
    public EditText email, pass;
    public Button login, user, forgotpassword;
    ProgressBar progressBar;

    TextView errorView;
    public FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        login = findViewById(R.id.login);
        user = findViewById(R.id.user);
        forgotpassword = findViewById(R.id.bt_forgotpassword);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        errorView = findViewById(R.id.errorView);
        progressBar = findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // autoLogin();

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ClientLogin.class));
            }
        });


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), forget_password.class);
                startActivity(i);
            }
        });





    }
    private void autoLogin() {






        mAuth.signInWithEmailAndPassword("varunmahajan292@gmail.com", "varun123")
                .addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                if (user.isEmailVerified()) {

                                    DocumentReference docRef = db.collection("users").document("varunmahajan292@gmail.com");
                                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            User user = documentSnapshot.toObject(User.class);

                                            //user.setAdmin(documentSnapshot);


                                              if(user.isAdmin())
                                               {
                                                   progressBar.setVisibility(View.GONE);
                                                   //errorView.setText("");
                                                   // errorView.setVisibility(View.GONE);
                                                   Intent HomeActivity = new Intent(getApplicationContext(), AdminMainActivity.class);
                                                   //   setResult(RESULT_OK, null);
                                                   startActivity(HomeActivity);
                                               }
                                               else {
                                                   progressBar.setVisibility(View.GONE);
                                                   errorView.setText("This Email Is Not SetUp For Admin Account.");
                                               }


                                          //  Intent HomeActivity = new Intent(getApplicationContext(), AdminMainActivity.class);
                                            //   setResult(RESULT_OK, null);
                                            //startActivity(HomeActivity);
                                        }
                                    });

                                } else {

                                    progressBar.setVisibility(View.GONE);
                                    errorView.setText("Please Verify your EmailID and SignIn");

                                }
                            }

                        } else {
                            // If sign in fails, display a message to the user.

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            if (task.getException() != null) {
                                errorView.setText(task.getException().getMessage());

                            }

                        }

                    }
                });


    }



    public void logInFunction(View view) {
        Log.d(TAG, "logInFunction: has started.");
        if (email.getText().toString().contentEquals("")) {


            errorView.setText("Email cant be empty");


        } else if (pass.getText().toString().contentEquals("")) {

            errorView.setText("Password cant be empty");

        } else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    // mAuth.signInWithEmailAndPassword("sardars270@gmail.com", "barry123")
                    // mAuth.signInWithEmailAndPassword("sardars270@gmail.com", "barry123")

                    .addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    if (user.isEmailVerified()) {

                                        progressBar.setVisibility(View.GONE);

                                        db.collection("users").document(email.getText().toString().trim())
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                 User user = documentSnapshot.toObject(User.class);

                                                if(user.isAdmin())
                                                {
                                                    progressBar.setVisibility(View.GONE);
                                                    //errorView.setText("");
                                                    // errorView.setVisibility(View.GONE);
                                                    Intent HomeActivity = new Intent(getApplicationContext(), AdminMainActivity.class);
                                                    //   setResult(RESULT_OK, null);
                                                    startActivity(HomeActivity);
                                                }
                                                else {
                                                    progressBar.setVisibility(View.GONE);
                                                    errorView.setText("This Email Is Not SetUp For Admin Account.");
                                                }



                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                                        //errorView.setText("");
                                        // errorView.setVisibility(View.GONE)
                                    } else {

                                        progressBar.setVisibility(View.GONE);
                                        errorView.setText("Please Verify your EmailID and SignIn");

                                    }
                                }

                            } else {
                                // If sign in fails, display a message to the user.

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                if (task.getException() != null) {
                                    errorView.setText(task.getException().getMessage());

                                }

                            }

                        }
                    });


        }

    }
}

