package com.example.infinityjobportal;

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

import com.example.infinityjobportal.model.PostJobPojo;

import java.util.ArrayList;
public class MapFilterDataAdapter extends RecyclerView.Adapter<MapFilterDataAdapter.MapFilterViewHolder> {
    private Context context;
    private ArrayList<PostJobPojo>  MapJobsList;
    public MapFilterDataAdapter(Context context, ArrayList<PostJobPojo> MapJobsList) {
        this.context = context;
        this.MapJobsList = MapJobsList;
    }
@NonNull
    @Override
    public MapFilterDataAdapter.MapFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new MapFilterDataAdapter.MapFilterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_map_filtered_jobs,parent,false));
}

    @Override
    public void onBindViewHolder(@NonNull final MapFilterViewHolder holder, int position) {


        final PostJobPojo postJobPOJO = MapJobsList.get(position);

                    holder.title.setText(postJobPOJO.getJobTitle());
                    holder.company.setText(postJobPOJO.getCompanyName());
                    holder.location.setText(postJobPOJO.getCityAddress());
                    holder.id.setText(postJobPOJO.getId());

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
        return MapJobsList.size();
    }

    public class MapFilterViewHolder extends RecyclerView.ViewHolder  {

        CardView lout;

        TextView title, company, location,id;
        ImageView saveJob;

        public MapFilterViewHolder(View view) {
            super(view);
            title=view.findViewById(R.id.title);
            company=view.findViewById(R.id.company);
            location=view.findViewById(R.id.location);

            lout=view.findViewById(R.id.lout);
            saveJob  = view.findViewById(R.id.saveJob);
            id  = view.findViewById(R.id.id);



        }




}



    }

