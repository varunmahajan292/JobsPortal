package com.example.infinityjobportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.infinityjobportal.adapter.LOEAdapter;
import com.example.infinityjobportal.model.LOEModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListOfExperienceActiviy extends AppCompatActivity {
 TextView plus;
 RecyclerView rec;
 LOEAdapter loeAdapter;
 faltu_context context;
FirebaseAuth mAuth;
   ArrayList<LOEModel> list=new ArrayList<>();
 FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_experience_activiy);
        plus=findViewById(R.id.plus);
        rec=findViewById(R.id.rec);
        db = FirebaseFirestore.getInstance();
         mAuth=FirebaseAuth.getInstance();
        db.collection("LOE").whereEqualTo("a","extra").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                LOEModel p = d.toObject(LOEModel.class);
                                p.setDesignation(d.getString("designation"));
                                p.setInstitute(d.getString("institute"));
                                p.setStartdate(d.getString("startdate"));
                                p.setEnddate(d.getString("enddate"));
                                p.setId(d.getString("id"));
                                p.setUserId(d.getString("userId"));
                                list.add(p);
                            }
                            loeAdapter.notifyDataSetChanged();
                        }
                    }
                });
    /*   db.collection("LOE").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                LOEModel p = d.toObject(LOEModel.class);
                                p.setDesignation(d.getString("designation"));
                                p.setInstitute(d.getString("institute"));
                                p.setStartdate(d.getString("startdate"));
                                p.setEnddate(d.getString("enddate"));
                                 p.setId(d.getString("id"));
                                 p.setUserId(d.getString("userId"));
                                list.add(p);
                            }
                            loeAdapter.notifyDataSetChanged();
                        }
                    }
                });*/




        loeAdapter =new LOEAdapter(list, this, "af");

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        rec.setAdapter(loeAdapter);

    }


    public void AddExp(View view) {
        Intent i = new Intent(getApplicationContext(),add_exp.class);
        startActivity(i);
    }




}