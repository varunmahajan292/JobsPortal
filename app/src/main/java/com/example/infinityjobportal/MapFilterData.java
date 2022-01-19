package com.example.infinityjobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infinityjobportal.model.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MapFilterData extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int i;
    ImageView back;
    private MapFilterDataAdapter adapter;
    RecyclerView recyclerViewMapFilteredJobs;
    private ArrayList<PostJobPojo> MapJobsList;
    ArrayList<String> saveIdList = new ArrayList<>();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_filter_data);
        recyclerViewMapFilteredJobs = findViewById(R.id.rec_mapfilteredjobs);
        back = findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        MapJobsList = new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        db.collection("MyJobs").whereEqualTo("uid", mAuth.getCurrentUser().getEmail())//.whereEqualTo("type","application")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                // PostJobPojo p = d.toObject(PostJobPojo.class);
                                //  p.setJobTitle(d.getString("jobTitle"));
                                // p.setCompanyName(d.getString("companyName"));
                                //p.setCityAddress(d.getString("cityAddress"));
                                //p.setId(d.getId());

                                saveIdList.add(d.getString("jobId"));
                                // saveIdList.add(d.getId());
                                // Toast.makeText(getContext(),d.getString("jobId"),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(),saveIdList,Toast.LENGTH_SHORT).show();
                            }

                            // showToast();
                            //adapter.notifyDataSetChanged();


                        }
                        loadNotAplyedList();

                    }
                });

    }

    private void loadNotAplyedList() {

        for (int i = 0; i < GlobalStorage.S.size(); i++) {


            DocumentReference docRef = db.collection("Jobs").document(GlobalStorage.S.get(i));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {


                            PostJobPojo postJobPOJO = document.toObject(PostJobPojo.class);
                            postJobPOJO.setId(document.getId());

                            int count=0;

                                if(!saveIdList.isEmpty()) {
                                    for (int i = 0; i < saveIdList.size(); i++) {
                                        if (postJobPOJO.getId().equals(String.valueOf(saveIdList.get(i)))) {
                                            count = 1;
                                        }
                                    }
                                }

                                if(count==0)
                                    MapJobsList.add(postJobPOJO);

                            adapter.notifyDataSetChanged();


                        } else {

                        }


                    } else {

                    }

                }
            });

        }



        for (int i = 0; i < GlobalStorage.T.size(); i++) {


            DocumentReference docRef = db.collection("Jobs").document(GlobalStorage.T.get(i));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {


                            PostJobPojo postJobPOJO = document.toObject(PostJobPojo.class);
                            postJobPOJO.setId(document.getId());

                            int count=0;

                            if(!saveIdList.isEmpty()) {
                                for (int i = 0; i < saveIdList.size(); i++) {
                                    if (postJobPOJO.getId().equals(String.valueOf(saveIdList.get(i)))) {
                                        count = 1;
                                    }
                                }
                            }

                            if(count==0)
                                MapJobsList.add(postJobPOJO);

                            adapter.notifyDataSetChanged();


                        } else {

                        }


                    } else {

                    }

                }
            });

        }


        adapter = new MapFilterDataAdapter(this, MapJobsList);
        recyclerViewMapFilteredJobs.setHasFixedSize(true);
        recyclerViewMapFilteredJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMapFilteredJobs.setAdapter(adapter);

    }

}

