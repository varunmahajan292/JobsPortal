package com.example.infinityjobportal.ui.postedJobs.draftJobs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.ui.postedJobs.activeJobs.ActiveJobsAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class DraftJobsFragment extends Fragment {


    private static final String TAG = "DraftJobsFragment";

    RecyclerView recyclerView;
    View view;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference jobsReference = db.collection("Jobs");

    private DraftJobsAdapter draftJobsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_draft_jobs, container, false);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        Log.d(TAG, "setUpRecyclerView: called");

        //Query
        Query query = jobsReference.whereEqualTo("status","draft");

        //Recycler Options
        FirestoreRecyclerOptions<PostJobPojo> options = new FirestoreRecyclerOptions.Builder<PostJobPojo>()
                .setQuery(query, PostJobPojo.class)
                .build();
        draftJobsAdapter = new DraftJobsAdapter(options);

        recyclerView = view.findViewById(R.id.draft_jobs_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(draftJobsAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        draftJobsAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        draftJobsAdapter.stopListening();
    }
}