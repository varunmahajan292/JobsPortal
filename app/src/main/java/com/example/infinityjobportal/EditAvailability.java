package com.example.infinityjobportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infinityjobportal.model.Availability;
import com.example.infinityjobportal.ui.myProfile.MyProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAvailability extends AppCompatActivity
      //  implements View.OnClickListener
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CheckBox checkBoxMon, checkBoxTue, checkBoxWed, checkBoxThurs, checkBoxFri, checkBoxSat, checkBoxSun, ckbxMonMor, ckbxMonEve, ckbxMonN9t, ckbxTueMor, ckbxTueEve, ckbxTueN9t, ckbxWedMor, ckbxWedEve, ckbxWedN9t, ckbxThursMor, ckbxThursEve, ckbxThursN9t,
            ckbxFriMor, ckbxFriEve, ckbxFriN9t, ckbxSatMor, ckbxSatEve, ckbxSatN9t, ckbxSunMor, ckbxSunEve, ckbxSunN9t;
    Button btnback, btnsave;
    TextView  save;
    ImageView back;
    String Mondayvalue, Tuesdayvalue, Wednessdayvalue, Thursdayvalue, Fridayvalue, Saturdayvalue, Sundayvalue;
    String day;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_availability);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        checkBoxMon = findViewById(R.id.checkBoxMon);
        checkBoxTue = findViewById(R.id.checkboxTue);
        checkBoxWed = findViewById(R.id.checkboxWed);
        checkBoxThurs = findViewById(R.id.checkBoxThurs);
        checkBoxFri = findViewById(R.id.checkboxFri);
        checkBoxSat = findViewById(R.id.checkboxSat);
        checkBoxSun = findViewById(R.id.checkboxSun);
        ckbxMonMor = findViewById(R.id.ckbxMonMor);
        ckbxMonEve = findViewById(R.id.ckbxMonEve);
        ckbxMonN9t = findViewById(R.id.chbxMonN9t);
        ckbxTueMor = findViewById(R.id.chbxTueMor);
        ckbxTueEve = findViewById(R.id.chbxTueEve);
        ckbxTueN9t = findViewById(R.id.chbxTueN9t);
        ckbxWedMor = findViewById(R.id.chbxWedMor);
        ckbxWedEve = findViewById(R.id.chbxWedEve);
        ckbxWedN9t = findViewById(R.id.chbxWedN9t);
        ckbxThursMor = findViewById(R.id.ckbxThursMOr);
        ckbxThursEve = findViewById(R.id.ckbxThursEve);
        ckbxThursN9t = findViewById(R.id.chbxThursN9t);
        ckbxFriMor = findViewById(R.id.chbxFriMor);
        ckbxFriEve = findViewById(R.id.chbxFriEve);
        ckbxFriN9t = findViewById(R.id.chbxFriN9t);
        ckbxSatMor = findViewById(R.id.chbxSatMor);
        ckbxSatEve = findViewById(R.id.chbxSatEve);
        ckbxSatN9t = findViewById(R.id.chbxSatN9t);
        ckbxSunMor = findViewById(R.id.chbxSunMor);
        ckbxSunEve = findViewById(R.id.chbxSunEve);
        ckbxSunN9t = findViewById(R.id.chbxSunN9t);



        mAuth= FirebaseAuth.getInstance();
        final String userId=  mAuth.getCurrentUser().getEmail();

         DocumentReference dref=db.collection("Availability").document(mAuth.getCurrentUser().getEmail());



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String Mon = documentSnapshot.getString("monday");
                    String Tue = documentSnapshot.getString("tuesday");
                    String Wed = documentSnapshot.getString("wednessday");
                    String Thurs = documentSnapshot.getString("thursday");
                    String Fri = documentSnapshot.getString("friday");
                    String Sat = documentSnapshot.getString("saturday");
                    String Sun = documentSnapshot.getString("sunday");

                        if (Mon.equals("Morning/Evening/Night")) {
                            checkBoxMon.setChecked(true);
                            ckbxMonMor.setChecked(true);
                            ckbxMonEve.setChecked(true);
                            ckbxMonN9t.setChecked(true);
                        } else if (Mon.equals("Morning")){
                            checkBoxMon.setChecked(true);
                            ckbxMonMor.setChecked(true);

                        } else if (Mon.equals("Evening")) {
                            checkBoxMon.setChecked(true);
                            ckbxMonEve.setChecked(true);
                        } else if (Mon.equals("Night")) {
                            ckbxMonN9t.setChecked(true);
                            checkBoxMon.setChecked(true);
                        } else if (Mon.equals("Morning/Evening")) {
                            ckbxMonMor.setChecked(true);
                            ckbxMonEve.setChecked(true);
                            checkBoxMon.setChecked(true);
                        } else if (Mon.equals("Evening/Night")) {
                            ckbxMonEve.setChecked(true);
                            ckbxMonN9t.setChecked(true);
                            checkBoxMon.setChecked(true);
                        } else if (Mon.equals("Morning/Night")) {
                            ckbxMonMor.setChecked(true);
                            ckbxMonN9t.setChecked(true);
                            checkBoxMon.setChecked(true);
                        } else {
                            ckbxTueMor.setChecked(false);
                            ckbxTueEve.setChecked(false);
                            ckbxTueN9t.setChecked(false);

                        }
                        if(Tue.equals("Morning/Evening/Night")) {
                            checkBoxTue.setChecked(true);
                            ckbxTueMor.setChecked(true);
                            ckbxTueEve.setChecked(true);
                            ckbxTueN9t.setChecked(true);

                        } else if (Tue.equals("Morning") ){
                              ckbxTueMor.setChecked(true);
                            checkBoxTue.setChecked(true);

                        } else if (Tue.equals("Evening")) {
                            checkBoxTue.setChecked(true);
                            ckbxTueEve.setChecked(true);
                            checkBoxTue.setChecked(true);
                        } else if (Tue.equals("Night")) {
                            ckbxTueN9t.setChecked(true);
                            checkBoxTue.setChecked(true);
                        } else if (Tue.equals("Morning/Evening")) {
                            ckbxTueMor.setChecked(true);
                            ckbxTueEve.setChecked(true);
                            checkBoxTue.setChecked(true);
                        } else if (Tue.equals("Evening/Night")) {
                            ckbxTueEve.setChecked(true);
                            ckbxTueN9t.setChecked(true);
                            checkBoxTue.setChecked(true);
                        } else if (Tue.equals("Morning/Night")) {
                            ckbxTueMor.setChecked(true);
                            ckbxTueN9t.setChecked(true);
                            checkBoxTue.setChecked(true);
                        } else {
                            ckbxTueMor.setChecked(false);
                            ckbxTueEve.setChecked(false);
                            ckbxTueN9t.setChecked(false);

                        }
                    if(Wed.equals("Morning/Evening/Night")) {
                        checkBoxWed.setChecked(true);
                        ckbxWedMor.setChecked(true);
                        ckbxWedEve.setChecked(true);
                        ckbxWedN9t.setChecked(true);

                    } else if (Wed.equals("Morning") ){
                        ckbxWedMor.setChecked(true);
                        checkBoxWed.setChecked(true);
                        checkBoxWed.setChecked(true);

                    } else if (Tue.equals("Evening")) {
                        checkBoxWed.setChecked(true);
                        ckbxWedEve.setChecked(true);
                        checkBoxWed.setChecked(true);
                    } else if (Wed.equals("Night")) {
                        ckbxWedN9t.setChecked(true);
                        checkBoxWed.setChecked(true);
                        checkBoxWed.setChecked(true);
                    } else if (Wed.equals("Morning/Evening")) {
                        ckbxWedMor.setChecked(true);
                        ckbxWedEve.setChecked(true);
                        checkBoxWed.setChecked(true);

                    } else if (Wed.equals("Evening/Night")) {
                        ckbxWedEve.setChecked(true);
                        ckbxWedN9t.setChecked(true);
                        checkBoxWed.setChecked(true);
                    } else if (Wed.equals("Morning/Night")) {
                        ckbxWedMor.setChecked(true);
                        ckbxWedN9t.setChecked(true);
                        checkBoxWed.setChecked(true);
                    } else {
                        ckbxWedMor.setChecked(false);
                        ckbxWedEve.setChecked(false);
                        ckbxWedN9t.setChecked(false);


                    }
                    if(Thurs.equals("Morning/Evening/Night")) {
                        checkBoxThurs.setChecked(true);
                        ckbxThursMor.setChecked(true);
                        ckbxThursEve.setChecked(true);
                        ckbxThursN9t.setChecked(true);

                    } else if (Thurs.equals("Morning") ){
                        ckbxThursMor.setChecked(true);
                        checkBoxThurs.setChecked(true);

                    } else if (Thurs.equals("Evening")) {
                        checkBoxThurs.setChecked(true);
                        ckbxThursEve.setChecked(true);
                    } else if (Thurs.equals("Night")) {
                        ckbxThursN9t.setChecked(true);
                        checkBoxThurs.setChecked(true);
                    } else if (Thurs.equals("Morning/Evening")) {
                        ckbxThursMor.setChecked(true);
                        ckbxThursEve.setChecked(true);
                        checkBoxThurs.setChecked(true);
                    } else if (Thurs.equals("Evening/Night")) {
                        ckbxThursEve.setChecked(true);
                        ckbxThursN9t.setChecked(true);
                        checkBoxThurs.setChecked(true);
                    } else if (Thurs.equals("Morning/Night")) {
                        ckbxThursMor.setChecked(true);
                        ckbxThursN9t.setChecked(true);
                        checkBoxThurs.setChecked(true);
                    } else {
                        ckbxThursMor.setChecked(false);
                        ckbxThursEve.setChecked(false);
                        ckbxThursN9t.setChecked(false);

                    }

                    if(Fri.equals("Morning/Evening/Night")) {
                        checkBoxFri.setChecked(true);
                        ckbxFriMor.setChecked(true);
                        ckbxFriEve.setChecked(true);
                        ckbxFriN9t.setChecked(true);

                    } else if (Fri.equals("Morning") ){
                        ckbxFriMor.setChecked(true);
                        checkBoxFri.setChecked(true);

                    } else if (Fri.equals("Evening")) {
                        checkBoxFri.setChecked(true);
                        ckbxFriEve.setChecked(true);
                    } else if (Fri.equals("Night")) {
                        ckbxFriN9t.setChecked(true);
                        checkBoxFri.setChecked(true);
                    } else if (Fri.equals("Morning/Evening")) {
                        ckbxFriMor.setChecked(true);
                        ckbxFriEve.setChecked(true);
                        checkBoxFri.setChecked(true);
                    } else if (Fri.equals("Evening/Night")) {
                        ckbxFriEve.setChecked(true);
                        ckbxFriN9t.setChecked(true);
                        checkBoxFri.setChecked(true);
                    } else if (Fri.equals("Morning/Night")) {
                        ckbxFriMor.setChecked(true);
                        ckbxFriN9t.setChecked(true);
                        checkBoxFri.setChecked(true);
                    } else {
                        ckbxFriMor.setChecked(false);
                        ckbxFriEve.setChecked(false);
                        ckbxFriN9t.setChecked(false);

                    }

                    if(Sat.equals("Morning/Evening/Night")) {
                        checkBoxSat.setChecked(true);
                        ckbxSatMor.setChecked(true);
                        ckbxSatEve.setChecked(true);
                        ckbxSatN9t.setChecked(true);

                    } else if (Sat.equals("Morning") ){
                        ckbxSatMor.setChecked(true);
                        checkBoxSat.setChecked(true);

                    } else if (Sat.equals("Evening")) {
                        checkBoxSat.setChecked(true);
                        ckbxSatEve.setChecked(true);
                    } else if (Sat.equals("Night")) {
                        ckbxSatN9t.setChecked(true);
                        checkBoxSat.setChecked(true);
                    } else if (Sat.equals("Morning/Evening")) {
                        ckbxSatMor.setChecked(true);
                        ckbxSatEve.setChecked(true);
                        checkBoxSat.setChecked(true);
                    } else if (Sat.equals("Evening/Night")) {
                        ckbxSatEve.setChecked(true);
                        ckbxSatN9t.setChecked(true);
                        checkBoxSat.setChecked(true);
                    } else if (Sat.equals("Morning/Night")) {
                        ckbxSatMor.setChecked(true);
                        ckbxSatN9t.setChecked(true);
                        checkBoxSat.setChecked(true);
                    } else {
                        ckbxSatMor.setChecked(false);
                        ckbxSatEve.setChecked(false);
                        ckbxSatN9t.setChecked(false);

                    }

                    if(Sun.equals("Morning/Evening/Night")) {
                        checkBoxSun.setChecked(true);
                        ckbxSunMor.setChecked(true);
                        ckbxSunEve.setChecked(true);
                        ckbxSunN9t.setChecked(true);

                    } else if (Sun.equals("Morning") ){
                        ckbxSunMor.setChecked(true);
                        checkBoxSun.setChecked(true);

                    } else if (Sun.equals("Evening")) {
                        checkBoxSun.setChecked(true);
                        ckbxSunEve.setChecked(true);
                    } else if (Sun.equals("Night")) {
                        ckbxSunN9t.setChecked(true);
                        checkBoxSun.setChecked(true);
                    } else if (Sun.equals("Morning/Evening")) {
                        ckbxSunMor.setChecked(true);
                        ckbxSunEve.setChecked(true);
                        checkBoxSun.setChecked(true);
                    } else if (Sun.equals("Evening/Night")) {
                        ckbxSunEve.setChecked(true);
                        ckbxSunN9t.setChecked(true);
                        checkBoxSun.setChecked(true);
                    } else if (Sun.equals("Morning/Night")) {
                        ckbxSunMor.setChecked(true);
                        ckbxSunN9t.setChecked(true);
                        checkBoxSun.setChecked(true);
                    } else {
                        ckbxSunMor.setChecked(false);
                        ckbxSunEve.setChecked(false);
                        ckbxSunN9t.setChecked(false);

                    }
                }
            }


        });














        checkBoxMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxMonMor.setChecked(true);
                ckbxMonEve.setChecked(true);
                ckbxMonN9t.setChecked(true);
                Mondayvalue = "Morning/Evening/Night";
                if (!checkBoxMon.isChecked()) {
                    ckbxMonMor.setChecked(false);
                    ckbxMonEve.setChecked(false);
                    ckbxMonN9t.setChecked(false);

                }
            }
        });
        checkBoxTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxTueMor.setChecked(true);
                ckbxTueEve.setChecked(true);
                ckbxTueN9t.setChecked(true);
                Tuesdayvalue = "Morning/Evening/Night";
                if (!checkBoxTue.isChecked()) {
                    ckbxTueMor.setChecked(false);
                    ckbxTueEve.setChecked(false);
                    ckbxTueN9t.setChecked(false);

                }
            }
        });

        checkBoxWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxWedMor.setChecked(true);
                ckbxWedEve.setChecked(true);
                ckbxWedN9t.setChecked(true);
                Wednessdayvalue = "Morning/Evening/Night";
                if (!checkBoxWed.isChecked()) {
                    ckbxWedMor.setChecked(false);
                    ckbxWedEve.setChecked(false);
                    ckbxWedN9t.setChecked(false);

                }
            }
        });
        checkBoxThurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxThursMor.setChecked(true);
                ckbxThursEve.setChecked(true);
                ckbxThursN9t.setChecked(true);
                Thursdayvalue = "Morning/Evening/Night";
                if (!checkBoxThurs.isChecked()) {
                    ckbxThursMor.setChecked(false);
                    ckbxThursEve.setChecked(false);
                    ckbxThursN9t.setChecked(false);

                }
            }
        });

        checkBoxFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxFriMor.setChecked(true);
                ckbxFriEve.setChecked(true);
                ckbxFriN9t.setChecked(true);
                Fridayvalue = "Morning/Evening/Night";
                if (!checkBoxFri.isChecked()) {
                    ckbxFriMor.setChecked(false);
                    ckbxFriEve.setChecked(false);
                    ckbxFriN9t.setChecked(false);

                }
            }
        });

        checkBoxSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxSatMor.setChecked(true);
                ckbxSatEve.setChecked(true);
                ckbxSatN9t.setChecked(true);
                Saturdayvalue = "Morning/Evening/Night";
                if (!checkBoxSat.isChecked()) {
                    ckbxSatMor.setChecked(false);
                    ckbxSatEve.setChecked(false);
                    ckbxSatN9t.setChecked(false);

                }
            }
        });

        checkBoxSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ckbxSunMor.setChecked(true);
                ckbxSunEve.setChecked(true);
                ckbxSunN9t.setChecked(true);
                Sundayvalue = "Morning/Evening/Night";

                if (!checkBoxSun.isChecked()) {
                    ckbxSunMor.setChecked(false);
                    ckbxSunEve.setChecked(false);
                    ckbxSunN9t.setChecked(false);

                }
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxMon.isChecked()) {
                   // Toast.makeText(EditAvailability.this, "Monday", Toast.LENGTH_SHORT).show();
                    day = "Monday";


                     if (ckbxMonMor.isChecked() && !ckbxMonEve.isChecked() && !ckbxMonN9t.isChecked()) {

                        Mondayvalue = "Morning";
                    } else if (ckbxMonEve.isChecked() && !ckbxMonMor.isChecked() && !ckbxMonN9t.isChecked()) {
                        Mondayvalue = "Evening";
                    } else if (ckbxMonN9t.isChecked() && !ckbxMonMor.isChecked() && !ckbxMonEve.isChecked()) {
                        Mondayvalue = "Night";
                    } else if (ckbxMonMor.isChecked() && ckbxMonEve.isChecked() && !ckbxMonN9t.isChecked()) {
                        Mondayvalue = "Morning/Evening";
                    } else if (ckbxMonMor.isChecked() && ckbxMonN9t.isChecked() && !ckbxMonEve.isChecked()) {

                        Mondayvalue = "Morning/Night";
                    } else if (ckbxMonEve.isChecked() && ckbxMonN9t.isChecked() && !ckbxMonMor.isChecked()) {

                        Mondayvalue = "Evening/Night";
                    } else if (ckbxMonMor.isChecked() && ckbxMonEve.isChecked() && ckbxMonN9t.isChecked()) {

                        Mondayvalue = "Morning/Evening/Night";

                    }

                    else {

                         AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                         builder.setMessage("Please select check box for Monday.")
                                 .setCancelable(false)
                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {

                                     }
                                 });
                         AlertDialog alert = builder.create();
                         alert.show();

                        return;


                    }
                }


                else {
                   // if(!checkBoxMon.isChecked())
                    {
                        ckbxMonMor.setChecked(false);
                        ckbxMonEve.setChecked(false);
                        ckbxMonN9t.setChecked(false);
                        Mondayvalue = "Not Available";
                    }
                }


                if (checkBoxTue.isChecked()) {

                   // Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Tueday";
                    if (ckbxTueMor.isChecked() && !ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                    //    Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning";
                    } else if (ckbxTueEve.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening";
                    } else if (ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueEve.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening";
                    } else if (ckbxTueMor.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Night";
                    } else if (ckbxTueEve.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening/Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && ckbxTueN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening/Night";

                    }


                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Tuesday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else {
                        ckbxTueMor.setChecked(false);
                        ckbxTueEve.setChecked(false);
                        ckbxTueN9t.setChecked(false);
                        Tuesdayvalue = "Not Available";

                }


                if (checkBoxWed.isChecked()) {

                   // Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Wednessday";
                    if (ckbxWedMor.isChecked() && !ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning";
                    } else if (ckbxWedEve.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedN9t.isChecked()) {
                     //   Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening";
                    } else if (ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening";
                    } else if (ckbxWedMor.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedEve.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Night";
                    } else if (ckbxWedEve.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening/Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && ckbxWedN9t.isChecked()) {
                     //   Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening/Night";
                    }


                        else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Wednessday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else  {
                    ckbxWedMor.setChecked(false);
                    ckbxWedEve.setChecked(false);
                    ckbxWedN9t.setChecked(false);
                    Wednessdayvalue = "Not Available";


                }
                if (checkBoxThurs.isChecked()) {

                   // Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Thursday";
                    if (ckbxThursMor.isChecked() && !ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning";
                    } else if (ckbxThursEve.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening";
                    } else if (ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursEve.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening";
                    } else if (ckbxThursMor.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Night";
                    } else if (ckbxThursEve.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening/Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && ckbxThursN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening/Night";
                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Thursday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else  {
                    ckbxThursMor.setChecked(false);
                    ckbxThursEve.setChecked(false);
                    ckbxThursN9t.setChecked(false);
                    Thursdayvalue = "Not Available";
                }

                if (checkBoxFri.isChecked()) {

                 //   Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Friday";
                    if (ckbxFriMor.isChecked() && !ckbxMonEve.isChecked() && !ckbxFriN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning";
                    } else if (ckbxFriEve.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening";
                    } else if (ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && !ckbxFriN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening";
                    } else if (ckbxFriMor.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriEve.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Night";
                    } else if (ckbxFriEve.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening/Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && ckbxFriN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening/Night";

                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Friday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else  {
                    ckbxFriMor.setChecked(false);
                    ckbxFriEve.setChecked(false);
                    ckbxFriN9t.setChecked(false);
                    Fridayvalue = "Not Available";
                }

                if (checkBoxSat.isChecked()) {

                 //   Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Saturday";
                    if (ckbxSatMor.isChecked() && !ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning";
                    } else if (ckbxSatEve.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening";
                    } else if (ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatEve.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening";
                    } else if (ckbxSatMor.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Night";
                    } else if (ckbxSatEve.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening/Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && ckbxSatN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening/Night";

                    }
                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Saturday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else   {
                    ckbxSatMor.setChecked(false);
                    ckbxSatEve.setChecked(false);
                    ckbxSatN9t.setChecked(false);
                    Saturdayvalue = "Not Available";
                }
                if (checkBoxSun.isChecked()) {

                  //  Toast.makeText(EditAvailability.this, "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Sunday";
                    if (ckbxSunMor.isChecked() && !ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "Morning is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning";
                    } else if (ckbxSunEve.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening";
                    } else if (ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                      //  Toast.makeText(EditAvailability.this, "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening";
                    } else if (ckbxSunMor.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunEve.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Night";
                    } else if (ckbxSunEve.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening/Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && ckbxSunN9t.isChecked()) {
                       // Toast.makeText(EditAvailability.this, "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening/Night";
                    }

                        else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAvailability.this);
                        builder.setMessage("Please select check box for Sunday.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return;

                    }
                }
                else {
                    ckbxSunMor.setChecked(false);
                    ckbxSunEve.setChecked(false);
                    ckbxSunN9t.setChecked(false);
                    Sundayvalue = "Not Available";
                }
                Availability Availability = new Availability(Mondayvalue, Tuesdayvalue, Wednessdayvalue, Thursdayvalue, Fridayvalue, Saturdayvalue, Sundayvalue);
                DocumentReference documentReference=db.collection("Availability").document(mAuth.getCurrentUser().getEmail());
                documentReference.set(Availability).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                 @Override
                                                                                 public void onSuccess(Void aVoid) {
                                                                                     Toast.makeText(EditAvailability.this, " Availability Added Successfully", Toast.LENGTH_SHORT).show();
                                                                                     finish();

                                                                                 }
                                                                             }
                );
               /* CollectionReference reference = db.collection("Availability");
                reference.add(pojoAvailability).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(EditAvailability.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(EditAvailability.this, MainAvailability.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditAvailability.this, "e.toString()", Toast.LENGTH_SHORT).show();

                    }
                });

                */

            }
        });


    }


    }

