package com.example.infinityjobportal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class AddNewEducation extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView back;
    TextView textStart, textEnd ,save;
    FirebaseAuth mAuth;
    private EditText school1, degree1, fieldOfStudy1, startdate, enddate, grades, extraAct, description1;
    CollectionReference reference = db.collection("Education");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_education);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        school1 = findViewById(R.id.school);
        degree1 = findViewById(R.id.Degree);
        fieldOfStudy1 = findViewById(R.id.studyField);
        startdate = findViewById(R.id.start_date);
        textStart = findViewById(R.id.textstart);
        textEnd = findViewById(R.id.textend);
        enddate = findViewById(R.id.end_date);
        grades = findViewById(R.id.grade);
        extraAct = findViewById(R.id.activities_societies);
        description1 = findViewById(R.id.description);

        mAuth = FirebaseAuth.getInstance();
        final String userId = mAuth.getCurrentUser().getEmail();
        textStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewEducation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        Calendar calendar1 = getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, date);
                        String dateText = DateFormat.format("E, MM, d, yyyy", calendar1).toString();

                        startdate.setText(dateText);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewEducation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        Calendar calendar1 = getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, date);
                        String dateText = DateFormat.format("E, MM, d, yyyy", calendar1).toString();

                        enddate.setText(dateText);
                    }
                }, YEAR, MONTH, DATE);

                datePickerDialog.show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String school = school1.getText().toString();
                String degree = degree1.getText().toString();
                String fieldOfStudy = fieldOfStudy1.getText().toString();
                String startDate = startdate.getText().toString();
                String endDate = enddate.getText().toString();
                String grade = grades.getText().toString();
                String extraActs = extraAct.getText().toString();
                String discription = description1.getText().toString();

                if (!hasValidationErrors(school, degree, fieldOfStudy, startDate, endDate, grade, extraActs, discription)) {

                    CollectionReference reference = db.collection("Education");

                    pojoAddNewEducation pojoaddNewEducation = new pojoAddNewEducation(school, degree, fieldOfStudy, startDate, endDate, grade, extraActs, discription, userId);

                    reference.add(pojoaddNewEducation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddNewEducation.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewEducation.this, "e.toString()", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();

                }
            }

            private boolean hasValidationErrors(String school, String degree, String fieldOfStudy, String startDate, String endDate, String grade, String extraActs, String discription) {
                if (school.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(school1);
                    school1.setError("School Name is required");
                    school1.requestFocus();
                    return true;
                }

                if (degree.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(degree1);
                    degree1.setError("Degree is required");
                    degree1.requestFocus();
                    return true;
                }

                if (fieldOfStudy.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(fieldOfStudy1);
                    fieldOfStudy1.setError("Field of study is required");
                    fieldOfStudy1.requestFocus();
                    return true;
                }

                if (startDate.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(startdate);
                    startdate.setError("Start date is required");
                    startdate.requestFocus();
                    return true;
                }

                if (endDate.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(enddate);
                    enddate.setError("End date is required");
                    enddate.requestFocus();
                    return true;
                }
                if (grade.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(grades);
                    grades.setError("Grades are required");
                    grades.requestFocus();
                    return true;
                }
                if (extraActs.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(extraAct);
                    extraAct.setError("Activities and societies information is required");
                    extraAct.requestFocus();
                    return true;
                }
                if (discription.isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(description1);
                    description1.setError("Description of Study is required");
                    description1.requestFocus();
                    return true;
                }
                return false;
            }
        });

    }
}