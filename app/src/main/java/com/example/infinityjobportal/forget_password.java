package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {
    EditText edit_email;
    TextView errorView;
    Button recoverybtn, login;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        edit_email=(EditText)findViewById(R.id.edit_email);
        recoverybtn=(Button)findViewById(R.id.recoverybtn);
        login = findViewById(R.id.login);
        errorView = findViewById(R.id.errorView);
        mAuth = FirebaseAuth.getInstance();

        recoverybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_email.getText().toString().contentEquals("")) {


                    errorView.setText("Email can`t be empty");


                }
                else
                {
                mAuth.sendPasswordResetEmail(edit_email.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                                AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                                builder.setTitle("Reset Password");
                                builder.setMessage("A Reset Password Link Is Sent To Your Registered EmailID")
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
                        })
                        /*
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                                    AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                                    builder.setTitle("Reset Password");
                                    builder.setMessage("A Reset Password Link Is Sent To Your Registered EmailID")
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
                                else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                                    // set title
                                    alertDialogBuilder.setTitle("Error");

                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("A Reset Password Link Is Not Sent. Please Retry.")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                }

                            }
                        })*/
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorView.setText(e.getMessage());
                    }
                });
                }

            }
        });





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ClientLogin.class);
                startActivity(i);

            }
        });



    }
}