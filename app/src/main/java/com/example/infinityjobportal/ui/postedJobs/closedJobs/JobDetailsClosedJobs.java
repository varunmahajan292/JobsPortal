package com.example.infinityjobportal.ui.postedJobs.closedJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class JobDetailsClosedJobs extends AppCompatActivity {
    private static final String TAG = "JobDetailsClosedJobs";

    TextView jobTitleClosed, companyNameClosed, locationClosed, salaryClosed, languageClosed, applicationDeadlineClosed, joiningDateClosed, jobDescriptionClosed, skillsRequiredClosed, qualificationClosed, industryClosed;
    Button markActive;

    FirebaseFirestore db;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_closed_jobs);

        jobTitleClosed = findViewById(R.id.jobTitleTextViewClosedJob);
        companyNameClosed = findViewById(R.id.companyNameTextViewClosedJob);
        locationClosed = findViewById(R.id.locationTextViewClosedJob);
        salaryClosed = findViewById(R.id.salaryTextViewClosedJob);
        languageClosed = findViewById(R.id.languageTextViewClosedJobs);
        applicationDeadlineClosed = findViewById(R.id.applicationDeadlineTextViewClosedJob);
        joiningDateClosed = findViewById(R.id.joiningDateTextViewClosedJob);
        jobDescriptionClosed = findViewById(R.id.descriptionTextViewClosedJob);
        skillsRequiredClosed = findViewById(R.id.skillNeededTextViewClosedJob);
        qualificationClosed = findViewById(R.id.qualificationTextViewClosedJob);
        industryClosed = findViewById(R.id.industryTextViewClosedJob);
        markActive = findViewById(R.id.markActiveButton);

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

                        jobTitleClosed.setText(postJobPojo.getJobTitle());
                        companyNameClosed.setText(postJobPojo.getCompanyName());
                        locationClosed.setText(postJobPojo.getStreetAddress() + ", " + postJobPojo.getCityAddress() + ", " + postJobPojo.getProvinceAddress());
                        salaryClosed.setText("$" + postJobPojo.getMinSalary() + " - $" + postJobPojo.getMaxSalary());
                        languageClosed.setText(postJobPojo.getLanguage());
                        applicationDeadlineClosed.setText(postJobPojo.getApplicationDeadline());
                        joiningDateClosed.setText(postJobPojo.getJoiningDate());
                        jobDescriptionClosed.setText(postJobPojo.getJobDescription());
                        skillsRequiredClosed.setText(postJobPojo.getSkillsRequired());
                        qualificationClosed.setText(postJobPojo.getQualificationRequired());
                    }
                }
            }
        });

        markActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("Jobs").document(id);


                Map<String,Object> updates = new HashMap<>();
                updates.put("status", "active");


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


        Log.d(TAG, "onCreate: ended");
    }
}