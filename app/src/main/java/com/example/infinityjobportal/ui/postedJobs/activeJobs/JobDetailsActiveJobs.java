package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class JobDetailsActiveJobs extends AppCompatActivity {
    private static final String TAG = "JobDetailsActiveJobs";

    TextView jobTitle, companyName, location, salary, language, applicationDeadline, joiningDate, jobDescription, skillsRequired, qualification, industry;
    Button markClosed;
    ImageView back, editActiveJobs;

    FirebaseFirestore db;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_active_jobs);

        jobTitle = findViewById(R.id.jobTitleTextViewActiveJob);
        companyName = findViewById(R.id.companyNameTextViewActiveJob);
        location = findViewById(R.id.locationTextViewActiveJob);
        salary = findViewById(R.id.salaryTextViewActiveJob);
        language = findViewById(R.id.languageTextViewActiveJobs);
        applicationDeadline = findViewById(R.id.applicationDeadlineTextViewActiveJob);
        joiningDate = findViewById(R.id.joiningDateTextViewActiveJob);
        jobDescription = findViewById(R.id.descriptionTextViewActiveJob);
        skillsRequired = findViewById(R.id.skillNeededTextViewActiveJob);
        qualification = findViewById(R.id.qualificationTextViewActiveJob);
        industry = findViewById(R.id.industryTextViewActiveJob);
        markClosed = findViewById(R.id.markClosedButton);
        back = findViewById(R.id.backActiveJobsDetails);
        editActiveJobs = findViewById(R.id.editJobImageViewActiveJob);

        db = FirebaseFirestore.getInstance();

        id = getIntent().getStringExtra("activeJobID");


        DocumentReference docRef = db.collection("Jobs").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Log.d(TAG, "onComplete: addOnCompleteListener");

                        PostJobPojo postJobPojo = document.toObject(PostJobPojo.class);

                        jobTitle.setText(postJobPojo.getJobTitle());
                        companyName.setText(postJobPojo.getCompanyName());
                        location.setText(postJobPojo.getStreetAddress() + ", " + postJobPojo.getCityAddress() + ", " + postJobPojo.getProvinceAddress());
                        salary.setText("$" + postJobPojo.getMinSalary() + " - $" + postJobPojo.getMaxSalary());
                        language.setText(postJobPojo.getLanguage());
                        applicationDeadline.setText(postJobPojo.getApplicationDeadline());
                        joiningDate.setText(postJobPojo.getJoiningDate());
                        jobDescription.setText(postJobPojo.getJobDescription());
                        skillsRequired.setText(postJobPojo.getSkillsRequired());
                        qualification.setText(postJobPojo.getQualificationRequired());
                    }
                }
            }
        });

        markClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("Jobs").document(id);


                Map<String,Object> updates = new HashMap<>();
                updates.put("status", "closed");


                docRef.update((Map<String, Object>) updates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "onComplete: called");

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: called");
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

        editActiveJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        Log.d(TAG, "onCreate: ended");
    }
}