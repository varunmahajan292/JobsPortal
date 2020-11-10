package com.example.infinityjobportal.ui.postedJobs.closedJobs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ClosedJobsAdapter extends FirestoreRecyclerAdapter<PostJobPojo, ClosedJobsAdapter.ClosedJobsViewHolder> {
    private static final String TAG = "ClosedJobsAdapter";


    public ClosedJobsAdapter(@NonNull FirestoreRecyclerOptions<PostJobPojo> options) {
        super(options);
    }


    @NonNull
    @Override
    public ClosedJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.closed_jobs_row_layout, parent, false);
        ClosedJobsViewHolder viewHolder = new ClosedJobsViewHolder(view);
        return viewHolder;
    }


    @Override
    protected void onBindViewHolder(@NonNull ClosedJobsViewHolder closedJobsViewHolder, int i, @NonNull PostJobPojo postJobPOJO) {
        Log.d(TAG, "onBindViewHolder: called");
        closedJobsViewHolder.jobTitle.setText(postJobPOJO.getJobTitle());
        closedJobsViewHolder.companyName.setText(postJobPOJO.getCompanyName());
        closedJobsViewHolder.companyAddress.setText(postJobPOJO.getCityAddress() + "," + postJobPOJO.getProvinceAddress());

        closedJobsViewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Jobs");
                Navigation.findNavController(view).navigate(R.id.myJobsFragment);
            }
        });
        closedJobsViewHolder.makeActiveClosedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Applications");
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });


    }

    public class ClosedJobsViewHolder extends RecyclerView.ViewHolder {

        public TextView jobTitle, companyName, companyAddress, numberOfApplications;
        public Button viewDetails, makeActiveClosedJobs;
        public ConstraintLayout constraintLayout; //used to attach onClickListener

        public ClosedJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ActiveJobsViewHolder: called");
            jobTitle = itemView.findViewById(R.id.jobTitleDraftJobsTextView);
            companyName = itemView.findViewById(R.id.companyNameDraftJobsTextView);
            companyAddress = itemView.findViewById(R.id.companyAddressDraftJobsTextView);
//          numberOfApplications = itemView.findViewById(R.id.numberOfApplcationsActiveJobsTextView);
            viewDetails = itemView.findViewById(R.id.jobDetailsClosedJobsButton);
            makeActiveClosedJobs = itemView.findViewById(R.id.makeActiveClosedJobsButton);
            constraintLayout = itemView.findViewById(R.id.closed_jobs_constraint_layout);


        }
    }
}
