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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.infinityjobportal.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientLogin extends AppCompatActivity {
    private static final String TAG = "ClientLogin";
    public EditText email, pass;
    public Button login, signup, forgotpassword, admin;
    private ProgressBar progressBar;

    private TextView errorView;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: has started.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);


        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgotpassword = findViewById(R.id.bt_forgotpassword);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        errorView = findViewById(R.id.errorView);
        progressBar = findViewById(R.id.progressBar);
        admin = findViewById(R.id.admin);


        mAuth = FirebaseAuth.getInstance();


       // autoLogin();

        //SIGNUP onClick Listener.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: signUp.");
                startActivity(new Intent(getApplicationContext(), ClientSignUp.class));
            }
        });

        //ADMIN onClick Listener.
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: admin.");
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

        //FORGOT PASSWORD onClick Listener
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: forgot password.");
                Intent i = new Intent(getApplicationContext(), forget_password.class);
                startActivity(i);
            }
        });


        Log.d(TAG, "onCreate: has ended.");

    }

    private void autoLogin() {
        Log.d(TAG, "autoLogin: has started");

        mAuth.signInWithEmailAndPassword("sardars270@gmail.com", "barry123")

                .addOnCompleteListener(ClientLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                if (user.isEmailVerified()) {

                                    progressBar.setVisibility(View.GONE);
                                    //errorView.setText("");
                                    // errorView.setVisibility(View.GONE);
                                    Intent HomeActivity = new Intent(getApplicationContext(), MainActivity.class);
                                    //   setResult(RESULT_OK, null);
                                    startActivity(HomeActivity);



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

            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(2)
                    .playOn(email);

            errorView.setText("Email cant be empty");

        } else if (pass.getText().toString().contentEquals("")) {
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .repeat(2)
                    .playOn(pass);

            errorView.setText("Password cant be empty");

        } else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    // mAuth.signInWithEmailAndPassword("sardars270@gmail.com", "barry123")
           // mAuth.signInWithEmailAndPassword("sardars270@gmail.com", "barry123")

                    .addOnCompleteListener(ClientLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user != null) {
                                    if (user.isEmailVerified()) {

                                        progressBar.setVisibility(View.GONE);
                                        //errorView.setText("");
                                        // errorView.setVisibility(View.GONE);
                                        Intent HomeActivity = new Intent(getApplicationContext(), MainActivity.class);
                                        //   setResult(RESULT_OK, null);
                                        startActivity(HomeActivity);



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