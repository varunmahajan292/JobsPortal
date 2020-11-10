package com.example.infinityjobportal.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.MySkill;

import java.util.List;

public class MySkillAdapter extends RecyclerView.Adapter<MySkillAdapter.ViewHolder> {

    private MySkill skill;
    private Context context;
    private List<MySkill> skillList;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSkill;


        ViewHolder(View view) {
            super(view);

            tvSkill = view.findViewById(R.id.tvSkill);



        }


    }

    public MySkillAdapter(Context mContext, List<MySkill> skillList) {
        this.context = mContext;
        this.skillList = skillList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_skill, parent, false);


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