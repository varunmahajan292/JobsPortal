package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.infinityjobportal.MyJobDetails;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.ViewProfile;
import com.example.infinityjobportal.model.InterestsModel;
import com.example.infinityjobportal.model.User;
import com.example.infinityjobportal.profileViaadmin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class adapteruserlist extends RecyclerView.Adapter<adapteruserlist.ViewHolder>{
    public Context context;
    private ArrayList<User> l = new ArrayList<>();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public adapteruserlist(ArrayList<User> o, Context context, String af) {
        this.l=o;
        this.context=context;

    }

    @NonNull
    @Override
    public adapteruserlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsuserlist, parent,false);
        adapteruserlist.ViewHolder holder=new adapteruserlist.ViewHolder(view);
        return holder;
        //////////

    }

    @Override
    public void onBindViewHolder(@NonNull final adapteruserlist.ViewHolder holder, int position) {

        final User o =l.get(position);
        FirebaseStorage firebaseStorage;
        StorageReference storageReference;
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference= firebaseStorage.getReference();

        // Toast.makeText(context,o.getUserProfilePic(),Toast.LENGTH_SHORT).show();
        StorageReference imageRef=storageReference.child("user/"+o.getUserProfilePic());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).circleCrop().into(holder.imageView1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //
            }
        });










        ////////////////////////''''''''''''''''''''''///////////////////
        holder.nm.setText(o.getFirstName().toString() +" "+o.getLastName().toString());
        holder.eml.setText(o.getEmail().toString());
        holder.loc.setText(o.getCity().toString());
        holder.lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewProfile.class);
                i.putExtra("uid", holder.eml.getText().toString());
                // i.putExtra("status",s );
                context.startActivity(i);
            }
        });

        //holder.imageView.


    }

    @Override
    public int getItemCount() {
        return l.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout lout;
        TextView uid;
        TextView nm,eml,loc;
        ImageView imageView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nm=itemView.findViewById(R.id.name);
            eml=itemView.findViewById(R.id.email);
            loc=itemView.findViewById(R.id.location);
            uid=itemView.findViewById(R.id.usid);
            lout=itemView.findViewById(R.id.ulist);
            imageView1  = itemView.findViewById(R.id.imageView1);
        }
    }
}



