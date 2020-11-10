package com.example.infinityjobportal.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infinityjobportal.ClientLogin;
import com.example.infinityjobportal.GlobalStorage;
import com.example.infinityjobportal.Jobs_search;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.Adapterjoblist;
import com.example.infinityjobportal.model.*;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView recjoblist;
    TextView search, text;
    int count;
    FirebaseAuth mAuth;
    private ArrayList<PostJobPojo> list=new ArrayList<PostJobPojo>();
    private ArrayList<PostJobPojo> notAppliedList=new ArrayList<PostJobPojo>();

    ArrayList<String> saveIdList = new ArrayList<>();
    Adapterjoblist adapter;
    FirebaseFirestore db;
    CollectionReference collectionReference;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recjoblist=root.findViewById(R.id.recJobList);
        text=root.findViewById(R.id.text);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        collectionReference = db.collection("Jobs");


      //  loadList();
        loadMyJobsList();








        return root;
    }

    private void loadList() {

        collectionReference//.whereLessThan("minSalary",20).whereGreaterThan("minSalary",10)
                //.whereEqualTo("jobCategory", "Technology")//
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                PostJobPojo p = d.toObject(PostJobPojo.class);
                                p.setJobTitle(d.getString("jobTitle"));
                                p.setCompanyName(d.getString("companyName"));
                                p.setCityAddress(d.getString("cityAddress"));
                                //   p.setMinSalary(Double.valueOf(d.getString("minSalary")));
                                p.setJobCategory(d.getString("jobCategory"));
                                p.setLanguage(d.getString("language"));
                                p.setId(d.getId());

                                list.add(p);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



        adapter =new Adapterjoblist(getContext(), list);

        recjoblist.setHasFixedSize(true);
        recjoblist.setLayoutManager(new LinearLayoutManager(getContext()));
        recjoblist.setAdapter(adapter);

    }



    private void loadMyJobsList() {
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

                            loadNotAplyedList();
                        }

                    }

                    private void loadNotAplyedList() {

                        notAppliedList.clear();
                        collectionReference.whereEqualTo("status","active").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                if (!queryDocumentSnapshots.isEmpty()) {

                                    List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                                    count = list1.size();
                                    for (DocumentSnapshot d : list1) {
                                        int count=0;
                                        PostJobPojo p = d.toObject(PostJobPojo.class);
                                        p.setId(d.getId());

                                        for(int i=0; i<saveIdList.size(); i++) {
                                            if(d.getId().equals(String.valueOf(saveIdList.get(i)))) {
                                                count=1;
                                            }
                                        }
                                        if(count==0)
                                            notAppliedList.add(p);
                                    }
                                    adapter.notifyDataSetChanged();
                                    text.setText("Total Result : "+String.valueOf(notAppliedList.size()));
                                }
                            }


                        });

                        adapter =new Adapterjoblist(getContext(), notAppliedList);

                        recjoblist.setHasFixedSize(true);
                        recjoblist.setLayoutManager(new LinearLayoutManager(getContext()));
                        recjoblist.setAdapter(adapter);

                    }


                    private void showToast() {

                        for(int i=0; i<saveIdList.size(); i++) {
                            // text.setText(saveIdList.get(i));
                            Toast.makeText(getContext(), saveIdList.get(i), Toast.LENGTH_SHORT).show();
                        }

                    }


                });

    }
}
