package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.Adapterjoblist;
import com.example.infinityjobportal.adapter.adapterAppliedJobs;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class ViewApplicationActiveJobs extends AppCompatActivity {
    String id,b;
    ArrayList<PostJobPojo> list = new ArrayList<>();
    ArrayList<User> list2 = new ArrayList<>();
    ArrayList<PostJobPojo> list3 = new ArrayList<>();
    ArrayList<String > l = new ArrayList<String>();


    private FirebaseFirestore db;
    ArrayList<String> candidateList = new ArrayList<>();
    ArrayList<User> candidateListData = new ArrayList<>();

    ViewApplicationAdapter viewApplicationAdapter;
    RecyclerView viewApplicationsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_application_active_jobs);
        viewApplicationsRecyclerView = findViewById(R.id.viewApplicationsRecyclerView);
        id = getIntent().getStringExtra("activeJobID");

        setTitle("Applications");

        db = FirebaseFirestore.getInstance();
        //Toast.makeText(ViewApplicationActiveJobs.this, id, Toast.LENGTH_SHORT).show();






        db.collection("MyJobs").whereEqualTo("jobId", id).whereEqualTo("type","application").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {


                                l.add(d.getString("uid"));
                                //Toast.makeText(getApplicationContext(),d.getString("uid"),Toast.LENGTH_SHORT).show();
                            }

                            showdata();


                        }
                    }



                    public void showdata() {
                        for (int i = 0; i < l.size(); i++) {
//Toast.makeText(getContext(),l.get(i),Toast.LENGTH_SHORT).show();

                            b=String.valueOf(l.get(i));

                            DocumentReference docRef = db.collection("users").document(b);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {

                                            User user = document.toObject(User.class);
                                            //user.setFirstName(document.getString("firstName"));
                                            list2.add(user);
                                           // user.setJobTitle(document.getString("jobTitle"));
                                            //user.setJobCategory(document.getString("jobCategory"));
                                            //user.setCityAddress(String.valueOf(document.getString("cityAddress")+", "+document.getString("provinceAddress")));
                                            //user.setId(String.valueOf(b));
                                            //user.setProvinceAddress("application");

                                           // Toast.makeText(ViewApplicationActiveJobs.this, "toast", Toast.LENGTH_SHORT).show();


                                            //  Toast.makeText(getContext(),user.getJobTitle(),Toast.LENGTH_SHORT).show();
                                            //InteAdapter.notifyDataSetChanged();
                                            viewApplicationAdapter = new ViewApplicationAdapter( getApplicationContext(),list2);

                                            viewApplicationsRecyclerView.setHasFixedSize(true);
                                            viewApplicationsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                                            viewApplicationsRecyclerView.setAdapter(viewApplicationAdapter);

                                        } else {

                                        }
                                    } else {

                                    }
                                }
                            });





                            // Toast.makeText(getApplicationContext(),l.get(i),Toast.LENGTH_SHORT).show();


                        }
                    }
                });



    }

}
/*
        db.collection("MyJobs").whereEqualTo("jobId", id).whereEqualTo("type", "application")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {

                    List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list1) {
                        candidateList.add(d.getString("uid"));
                    }

                    //viewApplicationData();
                }
            }
        });

        viewApplicationData();

       // viewApplicationAdapter = new ViewApplicationAdapter(getApplicationContext(), candidateList);

        //viewApplicationsRecyclerView.setHasFixedSize(true);
        //viewApplicationsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //viewApplicationsRecyclerView.setAdapter(viewApplicationAdapter);


    }

    private void viewApplicationData() {
        for (int i = 0; i < candidateList.size(); i++) {
            db.collection("users").document(candidateList.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        User user = documentSnapshot.toObject(User.class);
                        candidateListData.add(user);
                        Toast.makeText(ViewApplicationActiveJobs.this, user.getFirstName(), Toast.LENGTH_SHORT).show();
                        //viewApplicationAdapter.notifyDataSetChanged();

                    }
                }
            });
            viewApplicationAdapter.notifyDataSetChanged();

        }

       // User u = new User();

        //candidateListData.add(new User());
        viewApplicationAdapter = new ViewApplicationAdapter(getApplicationContext(), candidateListData);

        viewApplicationsRecyclerView.setHasFixedSize(true);
        viewApplicationsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewApplicationsRecyclerView.setAdapter(viewApplicationAdapter);

    }


    }

 */
