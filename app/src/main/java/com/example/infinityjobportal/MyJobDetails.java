package com.example.infinityjobportal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MyJobDetails extends AppCompatActivity {
    ImageView  back;
    TextView designation, company, location, salary, language, applicationDeadline, joiningDate, description, skiils, qualification, experience;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Button save, apply;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_details);
        back = findViewById(R.id.back);

        designation = findViewById(R.id.Designation);
        company  = findViewById(R.id.company_name);
        salary = findViewById(R.id.salary);
        location  = findViewById(R.id.location);
        language = findViewById(R.id.language);
        applicationDeadline = findViewById(R.id.application_deadline);
        joiningDate = findViewById(R.id.joining_date);
        description = findViewById(R.id.description);
        skiils = findViewById(R.id.skill_needed);
        qualification = findViewById(R.id.qualification);
        experience = findViewById(R.id.industry);
        apply  = findViewById(R.id.apply);
        save  = findViewById(R.id.savejob);


        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        id = getIntent().getStringExtra("id");
        String s =getIntent().getStringExtra("status");

        if (s.equals("save")){
            apply.setVisibility(View.VISIBLE);
            save.setVisibility(View.INVISIBLE);
        }
        else if(s.equals("application")){
            apply.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);

        }
        else if(s.equals("khatam")){
            apply.setVisibility(View.INVISIBLE);
            save.setVisibility(View.INVISIBLE);

        }
        else {
            apply.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
        }







//        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        loadInfo();





        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap apllication = new HashMap();
                apllication.put("uid",mAuth.getCurrentUser().getEmail());
                apllication.put("jobId",id);
                apllication.put("type","application");

                db.collection("MyJobs").add(apllication)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyJobDetails.this);
                                builder.setMessage("Application Submitted.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap apllication = new HashMap();
                apllication.put("uid",mAuth.getCurrentUser().getEmail());
                apllication.put("jobId",id);
                apllication.put("type","save");

                db.collection("MyJobs").add(apllication)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MyJobDetails.this);
                                builder.setMessage("Job saved to my jobs section.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

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
        DocumentReference docRef = db.collection("Jobs").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        PostJobPojo job = document.toObject(PostJobPojo.class);

                        designation.setText(job.getJobTitle());
                       company.setText(job.getCompanyName());
                       location.setText(job.getStreetAddress()+", "+job.getCityAddress()+", "+job.getProvinceAddress());
                       salary.setText("$"+job.getMinSalary()+ " - $" +job.getMaxSalary());
                       language.setText(job.getLanguage());
                        applicationDeadline.setText(job.getApplicationDeadline());
                       joiningDate.setText(job.getJoiningDate());
                       description.setText(job.getJobDescription());
                       skiils.setText(job.getSkillsRequired());
                       qualification.setText(job.getQualificationRequired());
                       //experience.setText("");



                    } else {

                    }
                } else {

                }
            }
        });

    }
}