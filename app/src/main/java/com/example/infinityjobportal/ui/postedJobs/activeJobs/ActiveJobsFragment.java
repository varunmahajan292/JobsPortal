package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActiveJobsFragment extends Fragment {

    private static final String TAG = "ActiveJobsFragment";

    RecyclerView recyclerView;
    View view;

    private FirebaseFirestore db;
    private CollectionReference jobsReference;
    private CollectionReference collectionReference;
    FirebaseAuth mAuth;

    private ActiveJobsAdapter activeJobsAdapter;
    private ArrayList<PostJobPojo> documentList = new ArrayList<PostJobPojo>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_active_jobs, container, false);
        recyclerView = view.findViewById(R.id.active_posted_jobs_recyclerview);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        Log.d(TAG, "setUpRecyclerView: called");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        jobsReference = db.collection("Jobs");

        //Query
        Query query = jobsReference.whereEqualTo("status", "active").whereEqualTo("uid", mAuth.getCurrentUser().getEmail());

        // QuerySnapshot - A QuerySnapshot contains the results of a query. It can contain zero or more DocumentSnapshot objects.
        // DocumentSnapshot - A DocumentSnapshot contains data read from a document in your Cloud Firestore database. The data can be extracted with the getData() or get(String) methods.
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot documentSnapshot : list1) {

                                PostJobPojo postJobPojo = documentSnapshot.toObject(PostJobPojo.class);
                                postJobPojo.setId(documentSnapshot.getId());


                                documentList.add(postJobPojo);
                            }

                            activeJobsAdapter.notifyDataSetChanged();
                        }
                    }



                });




        activeJobsAdapter = new ActiveJobsAdapter(getContext(), documentList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(activeJobsAdapter);

    }

}