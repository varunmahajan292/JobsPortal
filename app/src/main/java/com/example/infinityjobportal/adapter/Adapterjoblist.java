package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.infinityjobportal.JobDetails;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapterjoblist extends RecyclerView.Adapter<Adapterjoblist.ViewHolder>{

    FirebaseFirestore db;
    FirebaseAuth mAuth; Context context;
    //ArrayList<PostJobPojo> ar1;
    private ArrayList<PostJobPojo> ar1 ;

    public Adapterjoblist(Context context, ArrayList<PostJobPojo> ar1) {

        this.context=context;
        this.ar1=ar1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_job, parent, false);

        ViewHolder holder=new ViewHolder(listItem);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {




            PostJobPojo pj = ar1.get(position);

            holder.title.setText(pj.getJobTitle());
            holder.at.setText(pj.getCompanyName());
            holder.location.setText(pj.getCityAddress() + ", " + pj.getProvinceAddress());
            holder.id.setText(pj.getId());
            holder.language.setText(pj.getLanguage());
            holder.category.setText(pj.getJobCategory());
            holder.salary.setText("$" + pj.getMinSalary() + " - $" + pj.getMaxSalary());

        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

            holder.lout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, JobDetails.class);
                   // holder.id.setText("np");
                    if(holder.id.getText().toString()=="np"){
                        return;
                    }
                    i.putExtra("id", holder.id.getText().toString());
                    context.startActivity(i);
                }
            });

        holder.saveJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.id.getText().toString()=="np"){
                    return;
                }
                HashMap apllication = new HashMap();
                apllication.put("uid",mAuth.getCurrentUser().getEmail());
                apllication.put("jobId",holder.id.getText().toString());
                apllication.put("type","save");

                db.collection("MyJobs").add(apllication)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Job saved to my jobs section.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                holder.saveJob.setImageResource(R.drawable.tickgreen);
                                                holder.id.setText("np");

                                                //  finish();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return ar1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout lout;

        TextView title, at, location,id, category, language, salary;
        ImageView saveJob;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            at=itemView.findViewById(R.id.at);
            location=itemView.findViewById(R.id.location);
            lout=itemView.findViewById(R.id.lout);
            saveJob  = itemView.findViewById(R.id.saveJob);
            id  = itemView.findViewById(R.id.id);
            language  = itemView.findViewById(R.id.language);
            salary  = itemView.findViewById(R.id.salary);
            category  = itemView.findViewById(R.id.category);

        }
    }
}

