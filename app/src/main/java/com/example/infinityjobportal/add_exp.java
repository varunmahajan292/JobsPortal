package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.infinityjobportal.model.LOEModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add_exp extends AppCompatActivity {
 EditText et_designation,et_institute,start_date,end_date;
 ImageView img;

 Button pickdate,spickdate;
    int mYear, mMonth, mDay;
 TextView faltu;
 Button submit;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 71;
    FirebaseFirestore db;
    StorageReference mstorageRef;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageinbtye;
    String encodedImage;
    //String encodedImage;
    private Bitmap imagetostore;
    String a;
    String b;
    String userpic;
    public Uri imguri;
    FirebaseAuth mAuth;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exp);
        et_designation = findViewById(R.id.et_designation);
        et_institute = findViewById(R.id.et_institute);
        start_date = findViewById(R.id.et_start_date);
        end_date = findViewById(R.id.et_end_date);

        faltu = findViewById(R.id.faltu);
        submit = findViewById(R.id.submit);
        img = findViewById(R.id.img);
        db = FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

      //  final global_vars globalVariable = (global_vars) faltu_context.context;
        start_date.addTextChangedListener(new TextWatcher() {
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
                    start_date.setText(current);
                    start_date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        end_date.addTextChangedListener(new TextWatcher() {
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
                    end_date.setText(current);
                    end_date.setSelection(sel < current.length() ? sel : current.length());



                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
      /* spickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(add_exp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                et_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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


                DatePickerDialog datePickerDialog = new DatePickerDialog(add_exp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {



                Map<String, Object> data = new HashMap<>();
                data.put("a", "extra");
                data.put("designation", et_designation.getText().toString());
                data.put("institute", et_institute.getText().toString());
                data.put("startdate", start_date.getText().toString());
                data.put("enddate", end_date.getText().toString());
                data.put("userId", String.valueOf(mAuth.getCurrentUser().getEmail()));
                DocumentReference documentReference = db.collection("LOE").document();
                data.put("id", String.valueOf(documentReference.getId()));
                documentReference.set(data);
                String designation =et_designation.getText().toString();
                String insitute =et_institute.getText().toString();
                String startdate =start_date.getText().toString();
                String enddate =end_date.getText().toString();
                if (TextUtils.isEmpty(designation)) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(et_designation);
                    et_designation.setError("Invalid");

                }else if (TextUtils.isEmpty(insitute)){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(et_institute);
                    et_institute.setError("Invalid");
                }
                else if (TextUtils.isEmpty(startdate)){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(start_date);
                    start_date.setError("Invalid");
                }
                else if (TextUtils.isEmpty(enddate)){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(end_date);
                    end_date.setError("Invalid");
                }
                else {
                    Toast.makeText(getApplicationContext(), "LOE added ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), ListOfExperienceActiviy.class);
                    startActivity(i);
                }


            }
        });




        }






}