package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.infinityjobportal.JobDetails;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.PostJobPojo;
import com.example.infinityjobportal.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Adapterjoblist extends RecyclerView.Adapter<Adapterjoblist.ViewHolder>{
    Context context;
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

            holder.lout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, JobDetails.class);
                    i.putExtra("id", holder.id.getText().toString());
                    context.startActivity(i);
                }
            });



    }

    @Override
    public int getItemCount() {
        return ar1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView lout;

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

