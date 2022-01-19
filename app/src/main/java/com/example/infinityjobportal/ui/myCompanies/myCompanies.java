package com.example.infinityjobportal.ui.myCompanies;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinityjobportal.AddCompanyActivity;
import com.example.infinityjobportal.*;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.*;
import com.example.infinityjobportal.adapter.CompanyAdapter;
import com.example.infinityjobportal.model.Company;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myCompanies extends Fragment {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;

    private List<Company> companyList;
    CompanyAdapter adapter;
    RecyclerView recyclerView;
    String TAG = "MyCompaniesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_companies, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab);

        mAuth  = FirebaseAuth.getInstance();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddCompanyActivity.class);
                startActivity(i);
            }
        });



        firebaseFirestore = FirebaseFirestore.getInstance();

        companyList = new ArrayList<>();

        // setup recycler view
        recyclerView = root.findViewById(R.id.recyclerView);



      //  getCompanies();
        return root;
    }



    @Override
    public void onResume() {
        //other stuff
        super.onResume();
        getCompanies();
    }

    private void getCompanies() {

        recyclerView.setHasFixedSize(true);
        adapter = new CompanyAdapter(getContext(), companyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        companyList.clear();
        firebaseFirestore.collection("mycompanies").whereEqualTo("userId",mAuth.getCurrentUser().getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<Company> companies = documentSnapshots.toObjects(Company.class);

                            List<DocumentSnapshot> list1 = documentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                Company p = d.toObject(Company.class);
                                p.setId(d.getId());

                                companyList.add(p);
                                }


                            }


                            // Add all to your list

                            Log.d(TAG, "onSuccess: " + companyList);
                            adapter.notifyDataSetChanged();
                        }



                });

    }


}
