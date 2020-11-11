package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.admin.ViewQuery;
import com.example.infinityjobportal.interests;
import com.example.infinityjobportal.model.InterestsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {
String rr="";
    private ArrayList<InterestsModel> l = new ArrayList<>();
    public Context context;

    // FirebaseFirestore db;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public InterestsAdapter(ArrayList<InterestsModel> o, Context context,String af) {

        this.l = o;

        this.context = context;

    }


    @NonNull
    @Override
    public InterestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_interests, parent,false);
        InterestsAdapter.ViewHolder holder=new InterestsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InterestsAdapter.ViewHolder holder, final int position) {
        final InterestsModel o =l.get(position);
        FirebaseStorage firebaseStorage;
        holder.tv.setText(o.getType_int());
        holder.del.setText(o.getId().toString());
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //////////////

                db.collection("interest")
                        .whereEqualTo("id", holder.del.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // holder.saveJob.setImageResource(R.drawable.removed);
                                        l.remove(position);
                                        notifyItemRemoved(position);
                                        rr= String.valueOf(document.getId());
                                        // Log.d(TAG, document.getId() + " => " + document.getData());
                                        delsave();
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });








          //////////////
            }
        });
    }
    private void delsave() {
        db.collection("interest").document(rr).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, rr +"deleted", Toast.LENGTH_LONG).show();

                            //  finish();
                            // startActivity(new Intent(EditEducation.this, MainEducation.class));
                        }
                    }
                });


    }

    @Override
    public int getItemCount() {
        return l.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv, minus, del;

        LinearLayout layoutclick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            minus= itemView.findViewById(R.id.minus);
            del= itemView.findViewById(R.id.del);
            ////////////


            /////////

        }

    }
}