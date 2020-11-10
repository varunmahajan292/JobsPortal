package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.Update_Exp;
import com.example.infinityjobportal.model.LOEModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LOEAdapter extends RecyclerView.Adapter<LOEAdapter.ViewHolder> {
    private LOEModel loeModel;
    private ArrayList<LOEModel> loeModelArrayList = new ArrayList<>();
    private Context context;
    FirebaseFirestore db;

    public LOEAdapter(ArrayList<LOEModel> o, Context context, String af) {
        this.loeModelArrayList = o;

        this.context = context;

    }


    @NonNull
    @Override
    public LOEAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_exp_outer, parent, false);
        LOEAdapter.ViewHolder holder = new LOEAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

         loeModel = loeModelArrayList.get(position);
        FirebaseStorage firebaseStorage;
        StorageReference storageReference;

        firebaseStorage = FirebaseStorage.getInstance();


//        StorageReference imageRef = storageReference.child("Images/my.png");


        holder.designation.setText(loeModel.getDesignation());
        holder.institute.setText(loeModel.getInstitute());
        holder.startdate.setText(loeModel.getStartdate());
        holder.enddate.setText(loeModel.getEnddate());
       holder.faltu.setText(loeModel.getId());


    }

    @Override
    public int getItemCount() {
        if (loeModelArrayList != null) {
            return loeModelArrayList.size();
        } else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView designation, institute, startdate, enddate, edit,faltu;

        LinearLayout layoutclick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            designation = itemView.findViewById(R.id.designation);
            institute = itemView.findViewById(R.id.institute);
            startdate = itemView.findViewById(R.id.startdate);
            enddate = itemView.findViewById(R.id.enddate);
            faltu=itemView.findViewById(R.id.faltu);
            edit = itemView.findViewById(R.id.edit);
            layoutclick = itemView.findViewById(R.id.layoutclick);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, Update_Exp.class);
                    intent.putExtra("loeid", faltu.getText().toString() );
                    context.startActivity(intent);
                }
            });
        }
    }
}