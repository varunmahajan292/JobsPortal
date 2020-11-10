package com.example.infinityjobportal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class EditEducation extends AppCompatActivity implements OnClickListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textstart,textend;
    private Button back, update, delete;

    private EditText school1, degree1, fieldOfStudy1, startdate, enddate, grades, extraAct, discription1;
    CollectionReference reference = db.collection("Education");
    private com.example.infinityjobportal.pojoAddNewEducation pojoAddNewEducation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_education);
        pojoAddNewEducation = (com.example.infinityjobportal.pojoAddNewEducation) getIntent().getSerializableExtra("Education");
        back = findViewById(R.id.button_back);
        school1=findViewById(R.id.school);
        degree1 = findViewById(R.id.Degree);
        fieldOfStudy1 = findViewById(R.id.studyField);
        startdate = findViewById(R.id.start_date);
        enddate = findViewById(R.id.end_date);
        grades = findViewById(R.id.grade);
        extraAct = findViewById(R.id.activities_societies);
        discription1 = findViewById(R.id.description);
        textstart=findViewById(R.id.textstart);
        textend=findViewById(R.id.textend);

        school1.setText(pojoAddNewEducation.getSchool());
        degree1.setText(pojoAddNewEducation.getDegree());
        fieldOfStudy1.setText(pojoAddNewEducation.getFieldOfStudy());
        startdate.setText(pojoAddNewEducation.getStartDate());
        enddate.setText(pojoAddNewEducation.getEndDate());
        grades.setText(pojoAddNewEducation.getGrade());
        extraAct.setText(pojoAddNewEducation.getExtraActs());
        discription1.setText(pojoAddNewEducation.getdescription());
        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);

        textstart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEducation.this, new DatePickerDialog.OnDateSetListener() {
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



        textend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH = calendar.get(Calendar.MONTH);
                int DATE = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditEducation.this, new DatePickerDialog.OnDateSetListener() {
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








back.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(EditEducation.this, MainEducation.class));

    }
});

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
                    .playOn(discription1);
            discription1.setError("Description of Study is required");
            discription1.requestFocus();
            return true;
        }
        return false;
    }

    private void UpdateEducation() {

        String school = school1.getText().toString();
        String degree = degree1.getText().toString();
        String fieldOfStudy = fieldOfStudy1.getText().toString();
        String startDate = startdate.getText().toString();
        String endDate = enddate.getText().toString();
        String grade = grades.getText().toString();
        String extraActs = extraAct.getText().toString();
        String discription = discription1.getText().toString();

        if (!hasValidationErrors(school, degree, fieldOfStudy, startDate, endDate, grade, extraActs, discription)) {

           // CollectionReference reference = db.collection("Education");

            com.example.infinityjobportal.pojoAddNewEducation pojoaddNewEducation = new pojoAddNewEducation(school, degree, fieldOfStudy, startDate, endDate, grade, extraActs, discription);
            db.collection("Education").document(pojoAddNewEducation.getId()).update("school",pojoaddNewEducation.getSchool(), "degree",pojoaddNewEducation.getDegree(),
                    "fieldOfStudy", pojoaddNewEducation.getFieldOfStudy(), "startDate", pojoaddNewEducation.getStartDate(), "endDate",pojoaddNewEducation.getEndDate(), "grade",
                    pojoaddNewEducation.getGrade(), "extraActs", pojoaddNewEducation.getExtraActs(), "description", pojoaddNewEducation.getdescription())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void oid) {
                            Toast.makeText(EditEducation.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(EditEducation.this, MainEducation.class));
                        }
                    });

        }

    }


    private void deleteEducation() {
        db.collection("Education").document(pojoAddNewEducation.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditEducation.this, "Education deleted", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(EditEducation.this, MainEducation.class));
                        }
                    }
                });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update:
                UpdateEducation();
                break;
            case R.id.delete:


                AlertDialog.Builder builder = new AlertDialog.Builder(EditEducation.this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteEducation();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
                break;


        }
    }
}







