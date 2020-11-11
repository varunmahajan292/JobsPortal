package com.example.infinityjobportal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.pojoAddNewEducation;

import java.util.List;

public class NewEducationAdapterProfile extends RecyclerView.Adapter<NewEducationAdapterProfile.EducationViewHolder> {


    private Context context;
    private List<pojoAddNewEducation> educationList;

    // public NewEducationAdapter(Context context, List<pojoAddNewEducation> educationList) {
    //  this.context = context;
    // this.educationList = educationList;
    // }

    public NewEducationAdapterProfile(Context context, List<pojoAddNewEducation> educationList) {
        this.context = context;
        this.educationList = educationList;
    }


    @NonNull
    @Override
    public NewEducationAdapterProfile.EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EducationViewHolder(LayoutInflater.from(context).inflate(R.layout.education_item_recyclerview_profile,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull NewEducationAdapterProfile.EducationViewHolder holder, final int position) {
        final pojoAddNewEducation pojoAddNewEducation = educationList.get(position);
        holder.school.setText(pojoAddNewEducation.getSchool());
        holder.details.setText(pojoAddNewEducation.getDegree());
        holder.stream.setText(pojoAddNewEducation.getFieldOfStudy());


    }



    @Override
    public int getItemCount() {
        return educationList.size();
    }

    public class EducationViewHolder extends RecyclerView.ViewHolder  {

        private TextView school;
        private TextView details;
        private TextView stream;
        private TextView extraacts;
        private TextView description;
        private TextView timeperiod;
        private Button update_education;

        public EducationViewHolder(View view) {
            super(view);
            school = itemView.findViewById(R.id.institution_name);
            details = itemView.findViewById(R.id.details_0f_education);
         stream = itemView.findViewById(R.id.stream);
           // extraacts = itemView.findViewById(R.id.txtextraAct);
            //description = itemView.findViewById(R.id.txtdescription);
            //timeperiod=itemView.findViewById(R.id.timeperiod);
            //update_education=itemView.findViewById(R.id.update_education);


        }


    }
}
