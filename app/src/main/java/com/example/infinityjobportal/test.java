package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {
Button button;

FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<String> saveIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mAuth = FirebaseAuth.getInstance();
         db = FirebaseFirestore.getInstance();
button = findViewById(R.id.button);



        db.collection("MyJobs").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                //PostJobPojo p = d.toObject(PostJobPojo.class);
                                //  p.setJobTitle(d.getString("jobTitle"));
                                // p.setCompanyName(d.getString("companyName"));
                                //p.setCityAddress(d.getString("cityAddress"));
                                //p.setId(d.getId());

                                saveIdList.add(d.getString("jobId"));
                                // Toast.makeText(getContext(),d.getString("jobId"),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(),saveIdList,Toast.LENGTH_SHORT).show();
                            }
                            //adapter.notifyDataSetChanged();
                        }
                    }
                });



        for(int i=0; i<saveIdList.size(); i++) {
            //  text.setText(saveIdList.get(i));
            Toast.makeText(getApplicationContext(), saveIdList.get(i), Toast.LENGTH_SHORT).show();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                HashMap apllication = new HashMap();
                apllication.put("uid",mAuth.getCurrentUser().getEmail());
                apllication.put("jobId","sample");
                apllication.put("date", FieldValue.serverTimestamp());

                db.collection("MyJobs").add(apllication)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(test.this);
                                builder.setMessage("Job saved to my jobs section.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // finish();
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
    }
}

