package com.example.infinityjobportal.ui.postJob;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.infinityjobportal.EditAvailability;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostJobFragment extends Fragment {
    private static final String TAG = "PostJobFragment";
    CheckBox checkBoxMon, checkBoxTue, checkBoxWed, checkBoxThurs, checkBoxFri, checkBoxSat, checkBoxSun, ckbxMonMor, ckbxMonEve, ckbxMonN9t, ckbxTueMor, ckbxTueEve, ckbxTueN9t, ckbxWedMor, ckbxWedEve, ckbxWedN9t, ckbxThursMor, ckbxThursEve, ckbxThursN9t,
            ckbxFriMor, ckbxFriEve, ckbxFriN9t, ckbxSatMor, ckbxSatEve, ckbxSatN9t, ckbxSunMor, ckbxSunEve, ckbxSunN9t;
    String Mondayvalue, Tuesdayvalue, Wednessdayvalue, Thursdayvalue, Fridayvalue, Saturdayvalue, Sundayvalue;
    String day;
    DatePickerDialog datePickerDialog;
    double latitude;
    double longitude;

    private EditText mCompanyNameEditText, mJobTitleEditText, mStreetAddressEditText, mCityAddressEditText, mStartSalaryRangeEditText, mSalaryEndRangeEditText,
            mJoiningEditTextDate, mApplicationDeadlineEditTextDate, mJobDescriptionEditText, mSkillsRequiredEditText,
            mQualificationRequiredEditText;
    private Spinner mJobCategorySpinner,mProvinceAddressEditText,mCompanyNameSpinner;
    private CheckBox mEnglishCheckBox, mFrenchCheckBox;
    private Button mPostJobSubmitButton, mPostJobDraftButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance(); //Initialize an instance of Cloud Firestore.
FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: has Started");
        View root = inflater.inflate(R.layout.fragment_post_job, container, false);

        mCompanyNameSpinner = root.findViewById(R.id.companyNameSpinner);
        mJobCategorySpinner = root.findViewById(R.id.jobCategorySpinner);
        mJobTitleEditText = root.findViewById(R.id.jobTitleEditText);
        mStreetAddressEditText = root.findViewById(R.id.streetAddressEditText);
        mCityAddressEditText = root.findViewById(R.id.cityAddressEditText);
        mProvinceAddressEditText = root.findViewById(R.id.provinceSpinner);
        mEnglishCheckBox = root.findViewById(R.id.radioEnglish);
        mFrenchCheckBox = root.findViewById(R.id.radioFrench);
        mStartSalaryRangeEditText = root.findViewById(R.id.salaryStartRangeEditText);
        mSalaryEndRangeEditText = root.findViewById(R.id.salaryEndRangeEditText);
        mApplicationDeadlineEditTextDate = root.findViewById(R.id.applicationDeadlineEditTextDate);
        mJoiningEditTextDate = root.findViewById(R.id.joiningEditTextDate);
        mJobDescriptionEditText = root.findViewById(R.id.jobDescriptionEditText);
        mSkillsRequiredEditText = root.findViewById(R.id.skillsRequiredEditText);
        mQualificationRequiredEditText = root.findViewById(R.id.qualificationRequiredEditText);

        checkBoxMon = root.findViewById(R.id.checkBoxMon);
        checkBoxTue = root.findViewById(R.id.checkboxTue);
        checkBoxWed = root.findViewById(R.id.checkboxWed);
        checkBoxThurs = root.findViewById(R.id.checkBoxThurs);
        checkBoxFri = root.findViewById(R.id.checkboxFri);
        checkBoxSat = root.findViewById(R.id.checkboxSat);
        checkBoxSun = root.findViewById(R.id.checkboxSun);
        ckbxMonMor = root.findViewById(R.id.ckbxMonMor);
        ckbxMonEve = root.findViewById(R.id.ckbxMonEve);
        ckbxMonN9t = root.findViewById(R.id.chbxMonN9t);
        ckbxTueMor = root.findViewById(R.id.chbxTueMor);
        ckbxTueEve = root.findViewById(R.id.chbxTueEve);
        ckbxTueN9t = root.findViewById(R.id.chbxTueN9t);
        ckbxWedMor = root.findViewById(R.id.chbxWedMor);
        ckbxWedEve = root.findViewById(R.id.chbxWedEve);
        ckbxWedN9t = root.findViewById(R.id.chbxWedN9t);
        ckbxThursMor = root.findViewById(R.id.ckbxThursMOr);
        ckbxThursEve = root.findViewById(R.id.ckbxThursEve);
        ckbxThursN9t = root.findViewById(R.id.chbxThursN9t);
        ckbxFriMor = root.findViewById(R.id.chbxFriMor);
        ckbxFriEve = root.findViewById(R.id.chbxFriEve);
        ckbxFriN9t = root.findViewById(R.id.chbxFriN9t);
        ckbxSatMor = root.findViewById(R.id.chbxSatMor);
        ckbxSatEve = root.findViewById(R.id.chbxSatEve);
        ckbxSatN9t = root.findViewById(R.id.chbxSatN9t);
        ckbxSunMor = root.findViewById(R.id.chbxSunMor);
        ckbxSunEve = root.findViewById(R.id.chbxSunEve);
        ckbxSunN9t = root.findViewById(R.id.chbxSunN9t);




        //Submit Button.
        mPostJobSubmitButton = root.findViewById(R.id.postJobSubmitButton);
        mPostJobDraftButton = root.findViewById(R.id.postJobDraftButton);

        mAuth = FirebaseAuth.getInstance();

        //Application Deadline calendar
        mApplicationDeadlineEditTextDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mApplicationDeadlineEditTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        //joining date calender
        mJoiningEditTextDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mJoiningEditTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });



//Spinner for company names
        CollectionReference myCompaniesCollectionRef = db.collection("mycompanies");
        final List<String> companyNames = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, companyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCompanyNameSpinner.setAdapter(adapter);

        myCompaniesCollectionRef.whereEqualTo("userId",mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String companyName = document.getString("name");
                        companyNames.add(companyName);
                    }
                    adapter.notifyDataSetChanged();
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

        // OnClick Listener for post a job submit button.
        mPostJobSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: for job submit started");

                //Firestore values
                String companyName = mCompanyNameSpinner.getSelectedItem().toString();
                String jobCategory = mJobCategorySpinner.getSelectedItem().toString();
                String jobTitle = mJobTitleEditText.getText().toString();
                String streetAddress = mStreetAddressEditText.getText().toString();
                String city = mCityAddressEditText.getText().toString();
                String province = mProvinceAddressEditText.getSelectedItem().toString();

                String language = "";
                if (mEnglishCheckBox.isChecked()) {
                    language = "English";
                } else if (mFrenchCheckBox.isChecked()) {
                    language = "French";
                } else if (mFrenchCheckBox.isChecked() && mEnglishCheckBox.isChecked()) {
                    language = "English & French";
                }

                Double minSalary = Double.parseDouble(mStartSalaryRangeEditText.getText().toString());
                Double maxSalary = Double.parseDouble(mSalaryEndRangeEditText.getText().toString());


                String joiningDate = mJoiningEditTextDate.getText().toString();
                String applicationDeadline = mApplicationDeadlineEditTextDate.getText().toString();
                String jobDescription = mJobDescriptionEditText.getText().toString();
                String skillsRequired = mSkillsRequiredEditText.getText().toString();
                String qualificationRequired = mQualificationRequiredEditText.getText().toString();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String date = currentDate.format(todayDate);

                String Add= mStreetAddressEditText.getText().toString()+","+ mCityAddressEditText.getText().toString();
                //Toast.makeText(getContext(),"location"+Add,Toast.LENGTH_LONG).show();
                List<Address> addressList = null;
                if (Add != null || !Add.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(Add, 1);
                        final Address address = addressList.get(0);
                        final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        latitude=latLng.latitude;
                        longitude=latLng.longitude;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                final Address address = addressList.get(0);
                final LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                latitude=address.getLatitude();
                longitude=address.getLongitude();




                //availabilty
                if (checkBoxMon.isChecked()) {
                    // Toast.makeText(getContext(), "Monday", Toast.LENGTH_SHORT).show();
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Tueday";
                    if (ckbxTueMor.isChecked() && !ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                        //    Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning";
                    } else if (ckbxTueEve.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening";
                    } else if (ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening";
                    } else if (ckbxTueMor.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Night";
                    } else if (ckbxTueEve.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening/Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && ckbxTueN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening/Night";

                    }


                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Wednessday";
                    if (ckbxWedMor.isChecked() && !ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning";
                    } else if (ckbxWedEve.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedN9t.isChecked()) {
                        //   Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening";
                    } else if (ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening";
                    } else if (ckbxWedMor.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Night";
                    } else if (ckbxWedEve.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening/Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && ckbxWedN9t.isChecked()) {
                        //   Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening/Night";
                    }


                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Thursday";
                    if (ckbxThursMor.isChecked() && !ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning";
                    } else if (ckbxThursEve.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening";
                    } else if (ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening";
                    } else if (ckbxThursMor.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Night";
                    } else if (ckbxThursEve.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening/Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening/Night";
                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //   Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Friday";
                    if (ckbxFriMor.isChecked() && !ckbxMonEve.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning";
                    } else if (ckbxFriEve.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening";
                    } else if (ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening";
                    } else if (ckbxFriMor.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Night";
                    } else if (ckbxFriEve.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked()) {
                        //  Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening/Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening/Night";

                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //   Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Saturday";
                    if (ckbxSatMor.isChecked() && !ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning";
                    } else if (ckbxSatEve.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening";
                    } else if (ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening";
                    } else if (ckbxSatMor.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Night";
                    } else if (ckbxSatEve.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked()) {
                        //  Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening/Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && ckbxSatN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening/Night";

                    }
                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //  Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Sunday";
                    if (ckbxSunMor.isChecked() && !ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning";
                    } else if (ckbxSunEve.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening";
                    } else if (ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening";
                    } else if (ckbxSunMor.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Night";
                    } else if (ckbxSunEve.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening/Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening/Night";
                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                //availabilty end



                if (!hasValidationErrors(companyName, jobCategory, jobTitle, streetAddress, city, province, language, minSalary,
                        maxSalary, joiningDate, applicationDeadline, jobDescription,
                        skillsRequired, qualificationRequired)) {

                    PostJobPojo postJobPOJO = new PostJobPojo(companyName, jobCategory, jobTitle, streetAddress, city, province, language,
                            minSalary, maxSalary, joiningDate, applicationDeadline, jobDescription, skillsRequired, qualificationRequired,"active", date, latitude, longitude,Mondayvalue, Tuesdayvalue, Wednessdayvalue, Thursdayvalue, Fridayvalue, Saturdayvalue, Sundayvalue,mAuth.getCurrentUser().getEmail());

                    db.collection("Jobs")
                            .add(postJobPOJO)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });


                    Navigation.findNavController(view).navigate(R.id.postedJobsFragment);

                    Log.d(TAG, "onClick: for job post has ended");

                }


            }


        });

        mPostJobDraftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyName = mCompanyNameSpinner.getSelectedItem().toString();
                String jobCategory = mJobCategorySpinner.getSelectedItem().toString();
                String jobTitle = mJobTitleEditText.getText().toString();
                String streetAddress = mStreetAddressEditText.getText().toString();
                String city = mCityAddressEditText.getText().toString();
                String province = mProvinceAddressEditText.getSelectedItem().toString();

                String language = "";
                if (mEnglishCheckBox.isChecked()) {
                    language = "English";
                } else if (mFrenchCheckBox.isChecked()) {
                    language = "French";
                } else if (mFrenchCheckBox.isChecked() && mEnglishCheckBox.isChecked()) {
                    language = "English & French";
                }

                Double minSalary = Double.parseDouble(mStartSalaryRangeEditText.getText().toString());
                Double maxSalary = Double.parseDouble(mSalaryEndRangeEditText.getText().toString());


                String joiningDate = mJoiningEditTextDate.getText().toString();
                String applicationDeadline = mApplicationDeadlineEditTextDate.getText().toString();
                String jobDescription = mJobDescriptionEditText.getText().toString();
                String skillsRequired = mSkillsRequiredEditText.getText().toString();
                String qualificationRequired = mQualificationRequiredEditText.getText().toString();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String date = currentDate.format(todayDate);

                //availabilty
                if (checkBoxMon.isChecked()) {
                    // Toast.makeText(getContext(), "Monday", Toast.LENGTH_SHORT).show();
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Tueday";
                    if (ckbxTueMor.isChecked() && !ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                        //    Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning";
                    } else if (ckbxTueEve.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening";
                    } else if (ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked() && !ckbxTueEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && !ckbxTueN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening";
                    } else if (ckbxTueMor.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Night";
                    } else if (ckbxTueEve.isChecked() && ckbxTueN9t.isChecked() && !ckbxTueMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Evening/Night";
                    } else if (ckbxTueMor.isChecked() && ckbxTueEve.isChecked() && ckbxTueN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Tuesdayvalue = "Morning/Evening/Night";

                    }


                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Wednessday";
                    if (ckbxWedMor.isChecked() && !ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning";
                    } else if (ckbxWedEve.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedN9t.isChecked()) {
                        //   Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening";
                    } else if (ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked() && !ckbxWedEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && !ckbxWedN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening";
                    } else if (ckbxWedMor.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Night";
                    } else if (ckbxWedEve.isChecked() && ckbxWedN9t.isChecked() && !ckbxWedMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Evening/Night";
                    } else if (ckbxWedMor.isChecked() && ckbxWedEve.isChecked() && ckbxWedN9t.isChecked()) {
                        //   Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Wednessdayvalue = "Morning/Evening/Night";
                    }


                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    // Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Thursday";
                    if (ckbxThursMor.isChecked() && !ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning";
                    } else if (ckbxThursEve.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening";
                    } else if (ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked() && !ckbxThursEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && !ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening";
                    } else if (ckbxThursMor.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Night";
                    } else if (ckbxThursEve.isChecked() && ckbxThursN9t.isChecked() && !ckbxThursMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Evening/Night";
                    } else if (ckbxThursMor.isChecked() && ckbxThursEve.isChecked() && ckbxThursN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Thursdayvalue = "Morning/Evening/Night";
                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //   Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Friday";
                    if (ckbxFriMor.isChecked() && !ckbxMonEve.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning";
                    } else if (ckbxFriEve.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening";
                    } else if (ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked() && !ckbxFriEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && !ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening";
                    } else if (ckbxFriMor.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Night";
                    } else if (ckbxFriEve.isChecked() && ckbxFriN9t.isChecked() && !ckbxFriMor.isChecked()) {
                        //  Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Evening/Night";
                    } else if (ckbxFriMor.isChecked() && ckbxFriEve.isChecked() && ckbxFriN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Fridayvalue = "Morning/Evening/Night";

                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //   Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Saturday";
                    if (ckbxSatMor.isChecked() && !ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning";
                    } else if (ckbxSatEve.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening";
                    } else if (ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked() && !ckbxSatEve.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && !ckbxSatN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening";
                    } else if (ckbxSatMor.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Night";
                    } else if (ckbxSatEve.isChecked() && ckbxSatN9t.isChecked() && !ckbxSatMor.isChecked()) {
                        //  Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Evening/Night";
                    } else if (ckbxSatMor.isChecked() && ckbxSatEve.isChecked() && ckbxSatN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Saturdayvalue = "Morning/Evening/Night";

                    }
                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                    //  Toast.makeText(getContext(), "Tuesday", Toast.LENGTH_SHORT).show();
                    day = "Sunday";
                    if (ckbxSunMor.isChecked() && !ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "Morning is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning";
                    } else if (ckbxSunEve.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening";
                    } else if (ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked() && !ckbxSunEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && !ckbxSunN9t.isChecked()) {
                        //  Toast.makeText(getContext(), "monday Morning and Evening is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening";
                    } else if (ckbxSunMor.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunEve.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Night";
                    } else if (ckbxSunEve.isChecked() && ckbxSunN9t.isChecked() && !ckbxSunMor.isChecked()) {
                        // Toast.makeText(getContext(), "monday evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Evening/Night";
                    } else if (ckbxSunMor.isChecked() && ckbxSunEve.isChecked() && ckbxSunN9t.isChecked()) {
                        // Toast.makeText(getContext(), "monday Morning,evening and night is selected", Toast.LENGTH_SHORT).show();
                        Sundayvalue = "Morning/Evening/Night";
                    }

                    else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

                //availabilty end



                if (!hasValidationErrors(companyName, jobCategory, jobTitle, streetAddress, city, province, language, minSalary,
                        maxSalary,joiningDate, applicationDeadline, jobDescription,
                        skillsRequired, qualificationRequired)) {

                    PostJobPojo postJobPOJO = new PostJobPojo(companyName, jobCategory, jobTitle, streetAddress, city, province, language,
                            minSalary, maxSalary, joiningDate, applicationDeadline, jobDescription, skillsRequired, qualificationRequired, "draft", date, latitude, longitude, Mondayvalue, Tuesdayvalue, Wednessdayvalue, Thursdayvalue, Fridayvalue, Saturdayvalue, Sundayvalue,mAuth.getCurrentUser().getEmail());


                    db.collection("Jobs")
                            .add(postJobPOJO)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                  //  Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }


                Navigation.findNavController(view).navigate(R.id.postedJobsFragment);

            }
        });






        return root;


    }

    private boolean hasValidationErrors(String companyName, String jobCategory, String jobTitle, String streetAddress, String city, String province, String language, Double minSalary, Double maxSalary, String joiningDate, String applicationDeadline, String jobDescription, String skillsRequired, String qualificationRequired) {


//        if (companyName.isEmpty()) {
//            mCompanyNameEditText.setError("Company Name is required");
//            mCompanyNameEditText.requestFocus();
//            return true;
//        }
//        if (jobCategory.equals("Select the job category")) {
//            mCompanyNameEditText.setError("Job category is required");
//            mCompanyNameEditText.requestFocus();
//        }
        if (jobTitle.isEmpty()) {
            mJobTitleEditText.setError("Title is required");
            mJobTitleEditText.requestFocus();
            return true;
        }
        if (streetAddress.isEmpty()) {
            mStreetAddressEditText.setError("Street address is required");
            mStreetAddressEditText.requestFocus();
            return true;
        }

        if (city.isEmpty()) {
            mCityAddressEditText.setError("City is required");
            mCityAddressEditText.requestFocus();
            return true;
        }

        if (language.isEmpty()) {
            return true;
        }

        if (minSalary.equals(null)) {
            mStartSalaryRangeEditText.setError("Minimum salary is required");
            mStartSalaryRangeEditText.requestFocus();
            return true;
        }
        if (maxSalary.equals(null)) {
            mSalaryEndRangeEditText.setError("Maximum salary is required");
            mSalaryEndRangeEditText.requestFocus();
            return true;
        }

        if (joiningDate.isEmpty()) {
            mJoiningEditTextDate.setError("joining date is required");
            mJoiningEditTextDate.requestFocus();
            return true;
        }
        if (applicationDeadline.isEmpty()) {
            mApplicationDeadlineEditTextDate.setError("Application deadline is required");
            mApplicationDeadlineEditTextDate.requestFocus();
            return true;
        }
        if (jobDescription.isEmpty()) {
            mSkillsRequiredEditText.setError("Description is required");
            mSkillsRequiredEditText.requestFocus();
            return true;
        }
        if (skillsRequired.isEmpty()) {
            mSkillsRequiredEditText.setError("Skills are required");
            mSkillsRequiredEditText.requestFocus();
            return true;
        }
        if (qualificationRequired.isEmpty()) {
            mQualificationRequiredEditText.setError("Qualification is required");
            mQualificationRequiredEditText.requestFocus();
            return true;
        }
        return false;
    }


}