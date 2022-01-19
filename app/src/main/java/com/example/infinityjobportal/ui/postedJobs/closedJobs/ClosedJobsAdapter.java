package com.example.infinityjobportal.ui.postedJobs.closedJobs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.ClientLogin;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.ui.postedJobs.activeJobs.JobDetailsActiveJobs;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClosedJobsAdapter extends RecyclerView.Adapter<ClosedJobsAdapter.ClosedJobsViewHolder> {
    private static final String TAG = "ClosedJobsAdapter";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String > l = new ArrayList<String>();

    int p;

    private Context context;
    private ArrayList<PostJobPojo> postJobPojoArrayList;



    public ClosedJobsAdapter(Context context, ArrayList<PostJobPojo> postJobPojoArrayList) {
        this.context = context;
        this.postJobPojoArrayList = postJobPojoArrayList;
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
    public void onBindViewHolder(@NonNull ClosedJobsViewHolder closedJobsViewHolder, int position) {

         final PostJobPojo postJobPOJO = postJobPojoArrayList.get(position);
         p = position;
        closedJobsViewHolder.jobTitle.setText(postJobPOJO.getJobTitle());
        closedJobsViewHolder.companyName.setText(postJobPOJO.getCompanyName());
        closedJobsViewHolder.companyAddress.setText(postJobPOJO.getCityAddress() + "," + postJobPOJO.getProvinceAddress());

        closedJobsViewHolder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Jobs");
                Intent intent = new Intent(view.getContext(), JobDetailsActiveJobs.class);
                intent.putExtra("activeJobID", postJobPOJO.getId());
                intent.putExtra("from", "closedJobs");
                view.getContext().startActivity(intent);
               // Navigation.findNavController(view).navigate(R.id.myJobsFragment);
            }
        });
        closedJobsViewHolder.makeActiveClosedJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: View Applications");
                DocumentReference docRef = db.collection("Jobs").document(postJobPOJO.getId());
                Map<String,Object> updates = new HashMap<>();
                updates.put("status", "active");
                docRef.update((Map<String, Object>) updates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "onComplete: called");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Job moved to active jobs section successfully...")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                              //  postJobPojoArrayList.remove(p);
                                                //notifyItemRemoved(p);

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: called");
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Job not moved to active jobs section. Please try again...")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return postJobPojoArrayList.size();
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
