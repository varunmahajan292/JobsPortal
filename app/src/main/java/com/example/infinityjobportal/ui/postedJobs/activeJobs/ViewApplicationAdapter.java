package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.ViewProfile;
import com.example.infinityjobportal.model.PostJobPojo;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.Filter;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.User;

import java.util.ArrayList;

public class ViewApplicationAdapter extends RecyclerView.Adapter<ViewApplicationAdapter.ViewApplicationViewHolder>{
    private static final String TAG = "ViewApplicationAdapter";
    Context context;
    private ArrayList<User> userArrayList;

    public ViewApplicationAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_row_layout, parent, false);
        ViewApplicationViewHolder viewHolder = new ViewApplicationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewApplicationViewHolder holder, int position) {
        final User postJobPOJO = userArrayList.get(position);

        holder.applicantName.setText(postJobPOJO.getFirstName()+" "+postJobPOJO.getLastName());
        holder.applicantAddress.setText(postJobPOJO.getCity()+", "+postJobPOJO.getProvince());

        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewProfile.class);
                intent.putExtra("uid", postJobPOJO.getEmail());
                view.getContext().startActivity(intent);

            }
        });

        holder.contactApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={postJobPOJO.getEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Regarding job application");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello "+postJobPOJO.getFirstName()+ " "+ postJobPOJO.getLastName()+".");
                intent.putExtra(Intent.EXTRA_CC,"");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                view.getContext().startActivity(Intent.createChooser(intent, "Send mail"));


            }
        });

//        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(context, postJobPOJO.getEmail(), Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(context, ViewProfile.class);
//               // i.putExtra("uid",postJobPOJO.getEmail());
//                context.startActivity(new Intent(context, ViewProfile.class));
//            }
//        });

//        holder.contactApplicant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  Toast.makeText(context, "ail", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                String[] recipients={"a@b.c"};
//                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//                intent.putExtra(Intent.EXTRA_SUBJECT,"Reply to your query ");
//                intent.putExtra(Intent.EXTRA_TEXT,"Hello ");
//                intent.putExtra(Intent.EXTRA_CC,"");
//                intent.setType("text/html");
//                intent.setPackage("com.google.android.gm");
//                context.startActivity(Intent.createChooser(intent, "Send mail"));
//
//
//                }
//        });


        //holder.applicantAddress.setText("postJobPOJO.getCompanyName()");
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewApplicationViewHolder extends RecyclerView.ViewHolder {

        public TextView applicantName, applicantAddress;
        public Button contactApplicant, viewProfile;


        public ViewApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ActiveJobsViewHolder: called");
            applicantName = itemView.findViewById(R.id.applicantNameTextView);
            applicantAddress = itemView.findViewById(R.id.applicantAddressTextView);
            contactApplicant = itemView.findViewById(R.id.contactApplicantButton);
            viewProfile = itemView.findViewById(R.id.viewApplicantProfileButton);


        }
    }
}
