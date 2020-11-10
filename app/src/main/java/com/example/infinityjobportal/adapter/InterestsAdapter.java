package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.interests;
import com.example.infinityjobportal.model.InterestsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

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
    public void onBindViewHolder(@NonNull InterestsAdapter.ViewHolder holder, int position) {
        final InterestsModel o =l.get(position);
        FirebaseStorage firebaseStorage;
        holder.tv.setText(o.getType_int());
        holder.del.setText(o.getId().toString());
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




          del= itemView.findViewById(R.id.del);
          ////////////



            /////////
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //   Map<String, Object> city = new HashMap<>();
                    // city.put("faltu", "deleted");


                    DocumentReference washingtonRef = db.collection("interest").document(del.getText().toString());

// Set the "isCapital" field of the city 'DC'
                    washingtonRef
                            .update("faltu", "khatam")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //   Log.d(TAG, "DocumentSnapshot successfully updated!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Log.w(TAG, "Error updating document", e);
                                }
                            });
                    // context.startActivity(new Intent(context, interests.class));
                    v.getContext().startActivity(new Intent(v.getContext(), interests.class));

          /////////
minus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(final View v) {
     //   Map<String, Object> city = new HashMap<>();
       // city.put("faltu", "deleted");


        DocumentReference washingtonRef = db.collection("allInterests").document(del.getText().toString());

// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("faltu", "khatam")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     //   Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Log.w(TAG, "Error updating document", e);
                    }
                });
       // context.startActivity(new Intent(context, interests.class));
        v.getContext().startActivity(new Intent(v.getContext(), interests.class));



    }
           });


                }
            });

        }

    }
}