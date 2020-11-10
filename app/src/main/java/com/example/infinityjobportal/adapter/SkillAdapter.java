package com.example.infinityjobportal.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.Skill;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {

    private Skill skill;
    private Context context;
    private List<Skill> skillList;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSkill;
        AppCompatImageView ivEdit;


        ViewHolder(View view) {
            super(view);

            tvSkill = view.findViewById(R.id.tvSkill);
            ivEdit = view.findViewById(R.id.ivEdit);
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skill = skillList.get(getAdapterPosition());

                    final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                    LayoutInflater inflater = LayoutInflater.from(context);
                    final View dialogView = inflater.inflate(R.layout.dialog_add_skill, null);
                    AppCompatTextView tvTitle = dialogView.findViewById(R.id.textView);
                    final AppCompatEditText etSkill = dialogView.findViewById(R.id.etSkill);
                    AppCompatButton btnSubmit = dialogView.findViewById(R.id.buttonSubmit);
                    AppCompatButton btnCancel = dialogView.findViewById(R.id.buttonCancel);
                    tvTitle.setText("Update Skill");
                    etSkill.setText(skill.getName());
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(etSkill.getText().toString())) {
                                etSkill.setError("");
                                return;
                            }
                            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                            DocumentReference skillUpdate = firebaseFirestore.collection("skills").document(skill.getId());
                            skillUpdate.update("name", etSkill.getText().toString())

                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Updated Successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            skill.setName(etSkill.getText().toString());
                                            notifyItemChanged(getAdapterPosition());
                                            dialogBuilder.dismiss();
                                        }
                                    });

                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                        }
                    });

                }
            });


        }


    }

    public SkillAdapter(Context mContext, List<Skill> skillList) {
        this.context = mContext;
        this.skillList = skillList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);


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


}