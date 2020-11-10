package com.example.infinityjobportal.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinityjobportal.AddCompanyActivity;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.CompanyAdapter;
import com.example.infinityjobportal.model.Company;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCompaniesFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;

    private List<Company> companyList;
    CompanyAdapter adapter;
    RecyclerView recyclerView;
    String TAG = "MyCompaniesFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_companies, container, false);
        FloatingActionButton fab = root.findViewById(R.id.fab);
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
        recyclerView.setHasFixedSize(true);
        adapter = new CompanyAdapter(getContext(), companyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        getCompanies();
        return root;
    }

    private void getCompanies() {
        companyList.clear();
        firebaseFirestore.collection("mycompanies").get()
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

                            // Add all to your list
                            companyList.addAll(companies);
                            Log.d(TAG, "onSuccess: " + companyList);
                            adapter.notifyDataSetChanged();
                        }
                    }


                });

    }


}
