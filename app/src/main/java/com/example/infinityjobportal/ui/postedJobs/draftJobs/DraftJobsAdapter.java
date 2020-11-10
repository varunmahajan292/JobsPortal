package com.example.infinityjobportal.ui.postedJobs.draftJobs;

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

public class DraftJobsAdapter extends FirestoreRecyclerAdapter<PostJobPojo, DraftJobsAdapter.DraftJobsViewHolder> {
    private static final String TAG = "ActiveJobsAdapter";
    PostJobPojo postJobPOJO;


    public DraftJobsAdapter(@NonNull FirestoreRecyclerOptions<PostJobPojo> options) {
        super(options);
    }


    @NonNull
    @Override
    public DraftJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.draft_jobs_row_layout, parent, false);
        DraftJobsViewHolder viewHolder = new DraftJobsViewHolder(view);
        return viewHolder;
    }


    @Override
    protected void onBindViewHolder(@NonNull DraftJobsViewHolder draftJobsViewHolder, int i, @NonNull PostJobPojo postJobPOJO) {
        Log.d(TAG, "onBindViewHolder: called");
        draftJobsViewHolder.jobTitle.setText(postJobPOJO.getJobTitle());
        draftJobsViewHolder.companyName.setText(postJobPOJO.getCompanyName());
        draftJobsViewHolder.companyAddress.setText(postJobPOJO.getCityAddress() + "," + postJobPOJO.getProvinceAddress());
        draftJobsViewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Jobs");
                Navigation.findNavController(view).navigate(R.id.myJobsFragment);
            }
        });
        draftJobsViewHolder.completePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: complete post");
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });


    }

    public class DraftJobsViewHolder extends RecyclerView.ViewHolder {

        public TextView jobTitle, companyName, companyAddress, numberOfApplications;
        public Button viewDetails, completePost;
        public ConstraintLayout constraintLayout; //used to attach onClickListener

        public DraftJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "DraftJobsViewHolder: called");
            jobTitle = itemView.findViewById(R.id.jobTitleDraftJobsTextView);
            companyName = itemView.findViewById(R.id.companyNameDraftJobsTextView);
            companyAddress = itemView.findViewById(R.id.companyAddressDraftJobsTextView);
//          numberOfApplications = itemView.findViewById(R.id.numberOfApplcationsActiveJobsTextView);
            viewDetails = itemView.findViewById(R.id.jobDetailsDraftJobsButton);
            completePost = itemView.findViewById(R.id.completeDraftJobButton);


        }
    }
}
