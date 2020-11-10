package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.infinityjobportal.adapter.LOEAdapter;
import com.example.infinityjobportal.model.LOEModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Update_Exp extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText designation, institute, startdate, enddate;
    TextView faltu;
    LOEModel loeModel;
    String loeid;
    LOEAdapter adapter;
    int mYear, mMonth, mDay;
    LOEModel pro;
    ArrayList<LOEModel> list = new ArrayList<>();
    Button update, spickdate, pickdate;
    FirebaseFirestore db;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__exp);
        designation = findViewById(R.id.et_designation);
        institute = findViewById(R.id.et_institute);
        startdate = findViewById(R.id.et_start_date);
        enddate = findViewById(R.id.et_end_date);
        // recyclerView = findViewById(R.id.recycleview);
        update = findViewById(R.id.update);
        faltu = findViewById(R.id.faltu);

        loeid =  getIntent().getStringExtra("loeid");
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        startdate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    startdate.setText(current);
                    startdate.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        enddate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    enddate.setText(current);
                    enddate.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

      /*  spickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Update_Exp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Update_Exp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });*/



      DocumentReference docRef = db.collection("LOE").document(loeid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        LOEModel pro = document.toObject(LOEModel.class);
                        designation.setText(pro.getDesignation());
                        institute.setText(pro.getInstitute());
                        startdate.setText(pro.getStartdate());
                        enddate.setText(pro.getEnddate());
                        faltu.setText(pro.getId());
                        FirebaseStorage firebaseStorage;
                        //StorageReference storageReference;

                        firebaseStorage = FirebaseStorage.getInstance();
                        //storageReference = firebaseStorage.getReference();

                    } else {
                        // Log.d(TAG, "No such document");
                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });




       update.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){

        DocumentReference docRef = db.collection("LOE").document(loeid);

// Update the timestamp field with the value from the server
        Map<String, Object> updates = new HashMap<>();
        updates.put("designation", designation.getText().toString());
        updates.put("institute", institute.getText().toString());
        updates.put("startdate", startdate.getText().toString());
        updates.put("enddate", enddate.getText().toString());
            String desig = designation.getText().toString();
            String insti = institute.getText().toString();
            String sdate = startdate.getText().toString();
            String edate = enddate.getText().toString();
            if (TextUtils.isEmpty(desig)) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(designation);
                designation.setError("Invalid");
                return;
            }
            if (TextUtils.isEmpty(insti)) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(institute);
                institute.setError("Invalid");
                return;
            }
            if (TextUtils.isEmpty(sdate)) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(startdate);
                startdate.setError("Invalid");
                return;
            }
            if (TextUtils.isEmpty(edate)) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(2)
                        .playOn(enddate);
                enddate.setError("Invalid");
                return;
            }
        docRef.update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(getApplicationContext(), "Data Updated...", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Update_Exp.this, ListOfExperienceActiviy.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Data Not Updated. Try Again..,", Toast.LENGTH_SHORT).show();

                    }
                });


    }
    });
}

    }
