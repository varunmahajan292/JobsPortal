package com.example.infinityjobportal.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.Skill;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSkillAdapter extends RecyclerView.Adapter<AddSkillAdapter.ViewHolder> implements Filterable {
FirebaseAuth mAuth;
    private Skill skill;
    private Context context;
    private List<Skill> skillList;
    private List<Skill> skillListFiltered;




    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSkill;
        AppCompatImageView ivAdd;


        ViewHolder(View view) {
            super(view);

            tvSkill = view.findViewById(R.id.tvSkill);
            ivAdd = view.findViewById(R.id.ivAdd);
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skill = skillList.get(getAdapterPosition());
                    FirebaseFirestore   firebaseFirestore = FirebaseFirestore.getInstance();
                    String id = firebaseFirestore.collection("myskills").document().getId();

                    mAuth = FirebaseAuth.getInstance();

                    Map<String, Object> item = new HashMap<>();
                    item.put("id", id);
                    item.put("skillId",skill.getId());
                    item.put("name", skill.getName());
                    item.put("userId",mAuth.getCurrentUser().getEmail());

                    firebaseFirestore.collection("myskills").document(id)
                            .set(item)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(context, "Skill Added Successfully", Toast.LENGTH_SHORT).show();

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


    }

    public AddSkillAdapter(Context mContext, List<Skill> skillList) {
        this.context = mContext;
        this.skillList = skillList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_skill, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        skill = skillList.get(position);
        holder.tvSkill.setText(skill.getName());
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    skillListFiltered = skillList;
                } else {

                    ArrayList<Skill> filteredList = new ArrayList<>();
                    filteredList.clear();
                    for (Skill androidVersion : skillList) {

                        if (androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    skillListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = skillListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                skillList.clear();
                skillList.addAll((ArrayList<Skill>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

}