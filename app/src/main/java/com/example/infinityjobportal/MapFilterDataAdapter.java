package com.example.infinityjobportal;

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

import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MapFilterDataAdapter extends RecyclerView.Adapter<MapFilterDataAdapter.MapFilterViewHolder> {
    private Context context;
    private ArrayList<PostJobPojo>  MapJobsList;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    public MapFilterDataAdapter(Context context, ArrayList<PostJobPojo> MapJobsList) {
        this.context = context;
        this.MapJobsList = MapJobsList;
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
    }
@NonNull
    @Override
    public MapFilterDataAdapter.MapFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MapFilterDataAdapter.MapFilterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_map_filtered_jobs,parent,false));
}

    @Override
    public void onBindViewHolder(@NonNull final MapFilterViewHolder holder, int position) {


        final PostJobPojo pj = MapJobsList.get(position);

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
                if(holder.id.getText().toString()=="np"){
                    return;
                }
                Intent i = new Intent(context, JobDetails.class);
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
        return MapJobsList.size();
    }

    public class MapFilterViewHolder extends RecyclerView.ViewHolder  {

        LinearLayout lout;
        TextView title, at, location,id, category, language, salary;

        ImageView saveJob;

        public MapFilterViewHolder(View view) {
            super(view);
            title=view.findViewById(R.id.title);
            at=view.findViewById(R.id.at);
            location=view.findViewById(R.id.location);

            lout=view.findViewById(R.id.lout);
            saveJob  = view.findViewById(R.id.saveJob);
            id  = view.findViewById(R.id.id);
            language  = itemView.findViewById(R.id.language);
            salary  = itemView.findViewById(R.id.salary);
            category  = itemView.findViewById(R.id.category);



        }




}



    }

