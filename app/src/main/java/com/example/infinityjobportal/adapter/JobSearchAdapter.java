package com.example.infinityjobportal.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobSearchAdapter extends RecyclerView.Adapter<JobSearchAdapter.ExampleViewHolder> implements Filterable {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private List<PostJobPojo> exampleList;
    private List<PostJobPojo> exampleListFull;

Context context;

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        LinearLayout lout;

        TextView title, at, location,id, category, language, salary;
        ImageView saveJob;



        ExampleViewHolder(View itemView) {
            super(itemView);
          //  imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.title);
            at=itemView.findViewById(R.id.at);
            location=itemView.findViewById(R.id.location);

            lout=itemView.findViewById(R.id.lout);
            saveJob  = itemView.findViewById(R.id.saveJob);

            id  = itemView.findViewById(R.id.id);
            language  = itemView.findViewById(R.id.language);
            salary  = itemView.findViewById(R.id.salary);
            category  = itemView.findViewById(R.id.category);

            // textView2 = itemView.findViewById(R.id.text_view2);
        }
    }

    public JobSearchAdapter(Context applicationContext, List<PostJobPojo> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.context=applicationContext;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, int position) {
        PostJobPojo currentItem = exampleList.get(position);

      //  holder.imageView.setImageResource(currentItem.gett());
        holder.textView1.setText(currentItem.getJobTitle());
        holder.at.setText(currentItem.getCompanyName());
        holder.location.setText(currentItem.getCityAddress()+", "+currentItem.getProvinceAddress());
        holder.language.setText(currentItem.getLanguage());
        holder.category.setText(currentItem.getJobCategory());
        holder.salary.setText("$"+currentItem.getMinSalary()+" - $"+ currentItem.getMaxSalary());
        holder.id.setText(currentItem.getId());
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        holder.lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, JobDetails.class);
                i.putExtra("id", holder.id.getText().toString());
                context.startActivity(i);
            }
        });

        holder.saveJob.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
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
        holder.lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, JobDetails.class);
                i.putExtra("id", holder.id.getText().toString());
                context.startActivity(i);
            }
        });



        // holder.textView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PostJobPojo> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PostJobPojo item : exampleListFull) {
                    if (item.getJobTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}