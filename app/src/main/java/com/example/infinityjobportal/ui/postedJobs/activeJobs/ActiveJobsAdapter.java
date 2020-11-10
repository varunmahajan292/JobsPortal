package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ActiveJobsAdapter extends RecyclerView.Adapter<ActiveJobsAdapter.ActiveJobsViewHolder> {
    private static final String TAG = "ActiveJobsAdapter";


    private Context context;
    private ArrayList<PostJobPojo> postJobPojoArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ActiveJobsAdapter(Context context, ArrayList<PostJobPojo> postJobPojoArrayList) {
        this.context = context;
        this.postJobPojoArrayList = postJobPojoArrayList;
    }

    @NonNull
    @Override
    public ActiveJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_jobs_row_layout, parent, false);
        ActiveJobsViewHolder viewHolder = new ActiveJobsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveJobsViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        final PostJobPojo postJobPOJO = postJobPojoArrayList.get(position);


        holder.jobTitle.setText(postJobPOJO.getJobTitle());
        holder.companyName.setText(postJobPOJO.getCompanyName());
        holder.companyAddress.setText(postJobPOJO.getCityAddress() + " " + postJobPOJO.getProvinceAddress());

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), JobDetailsActiveJobs.class);
                intent.putExtra("activeJobID", postJobPOJO.getId());
                view.getContext().startActivity(intent);

            }
        });
        holder.viewApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Applications");
                Intent intent = new Intent(view.getContext(), ViewApplicationActiveJobs.class);
                intent.putExtra("activeJobID", postJobPOJO.getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postJobPojoArrayList.size();
    }





    public class ActiveJobsViewHolder extends RecyclerView.ViewHolder {

        public TextView jobTitle, companyName, companyAddress, numberOfApplications;
        public Button viewDetails, viewApplication;
        public ConstraintLayout constraintLayout; //used to attach onClickListener.
        int position = getAdapterPosition();

        public ActiveJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ActiveJobsViewHolder: called");
            jobTitle = itemView.findViewById(R.id.jobTitleDraftJobsTextView);
            companyName = itemView.findViewById(R.id.companyNameDraftJobsTextView);
            companyAddress = itemView.findViewById(R.id.companyAddressDraftJobsTextView);
            numberOfApplications = itemView.findViewById(R.id.numberOfApplicationTextView);
            viewDetails = itemView.findViewById(R.id.jobDetailsDraftJobsButton);
            viewApplication = itemView.findViewById(R.id.viewApplicationsActiveJobButton);
            constraintLayout = itemView.findViewById(R.id.active_jobs_constraint_layout);

        }
    }

}
