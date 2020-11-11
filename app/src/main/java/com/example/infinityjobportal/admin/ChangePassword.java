package com.example.infinityjobportal.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.infinityjobportal.ClientChangePassword;
import com.example.infinityjobportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
    EditText confirmPassword, newPassword, currentPassword;
    ProgressBar progressBar;

    Button submit;
    ImageView back;
    TextView  errorView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_change_password);

        currentPassword = findViewById(R.id.currentPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        newPassword = findViewById(R.id.newPassword);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);
        errorView = findViewById(R.id.errorView);
        progressBar = findViewById(R.id.progressBar);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth = FirebaseAuth.getInstance();
                user= mAuth.getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(), currentPassword.getText().toString());

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                Log.d("password", "Password updated");
                                                errorView.setText("");
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                                                builder.setMessage("Password Updated Successfully.")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                finish();
                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();

                                            } else {
                                                progressBar.setVisibility(View.GONE);
                                                Log.d("password", "Error password not updated");
                                                errorView.setText("Password is not updated. Try again...");
                                            }
                                        }
                                    });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    errorView.setText("Current password is incorrect.");
                                    Log.d("Auth", "Error auth failed");

                                }
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
}