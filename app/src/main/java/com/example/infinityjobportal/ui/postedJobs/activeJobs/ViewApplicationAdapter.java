package com.example.infinityjobportal.ui.postedJobs.activeJobs;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.ViewProfile;
import com.example.infinityjobportal.model.PostJobPojo;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.Filter;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ViewApplicationAdapter extends RecyclerView.Adapter<ViewApplicationAdapter.ViewApplicationViewHolder>{
    private static final String TAG = "ViewApplicationAdapter";
    Context context;
    String subValue;
    private ArrayList<User> userArrayList;
    ArrayList<String> resumeList ;

    public ViewApplicationAdapter(Context context, ArrayList<User> userArrayList, ArrayList<String> resumeList) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.resumeList = resumeList;
    }

    @NonNull
    @Override
    public ViewApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_row_layout, parent, false);
        ViewApplicationViewHolder viewHolder = new ViewApplicationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewApplicationViewHolder holder, final int position) {
        final User postJobPOJO = userArrayList.get(position);

        holder.applicantName.setText(postJobPOJO.getFirstName()+" "+postJobPOJO.getLastName());
        holder.applicantAddress.setText(postJobPOJO.getCity()+", "+postJobPOJO.getProvince());


        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewProfile.class);
                intent.putExtra("uid", postJobPOJO.getEmail());
                view.getContext().startActivity(intent);

            }
        });

        holder.contactApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={postJobPOJO.getEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Regarding job application");
                intent.putExtra(Intent.EXTRA_TEXT,"Hello "+postJobPOJO.getFirstName()+ " "+ postJobPOJO.getLastName()+".");
                intent.putExtra(Intent.EXTRA_CC,"");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                view.getContext().startActivity(Intent.createChooser(intent, "Send mail"));


            }
        });

        String ext = resumeList.get(position);

        //String[] separated = ext.split(".");
    //      subValue=separated[separated.length-1];
        for(int i=ext.length(); i>=0;i--) {

            char a=ext.charAt(i-1);
            if(a=='.'){
                subValue = ext.substring(i);
                break;
            }
        }

       // holder.applicantName.setText(subValue);

        holder.resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "remu", Toast.LENGTH_SHORT).show();
                StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                StorageReference ref= storageReference.child("resume").child(resumeList.get(position));
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=uri.toString();
                        downloadFile(context,
                                resumeList.get(position),"."+subValue, DIRECTORY_DOWNLOADS,url);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }

                });
            }
        });


//        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(context, postJobPOJO.getEmail(), Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(context, ViewProfile.class);
//               // i.putExtra("uid",postJobPOJO.getEmail());
//                context.startActivity(new Intent(context, ViewProfile.class));
//            }
//        });

//        holder.contactApplicant.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//              //  Toast.makeText(context, "ail", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                String[] recipients={"a@b.c"};
//                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
//                intent.putExtra(Intent.EXTRA_SUBJECT,"Reply to your query ");
//                intent.putExtra(Intent.EXTRA_TEXT,"Hello ");
//                intent.putExtra(Intent.EXTRA_CC,"");
//                intent.setType("text/html");
//                intent.setPackage("com.google.android.gm");
//                context.startActivity(Intent.createChooser(intent, "Send mail"));
//
//
//                }
//        });


        //holder.applicantAddress.setText("postJobPOJO.getCompanyName()");
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewApplicationViewHolder extends RecyclerView.ViewHolder {

        public TextView applicantName, applicantAddress;
        public Button contactApplicant, viewProfile;
        ImageView resume;


        public ViewApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ActiveJobsViewHolder: called");
            applicantName = itemView.findViewById(R.id.applicantNameTextView);
            applicantAddress = itemView.findViewById(R.id.applicantAddressTextView);
            contactApplicant = itemView.findViewById(R.id.contactApplicantButton);
            viewProfile = itemView.findViewById(R.id.viewApplicantProfileButton);
            resume = itemView.findViewById(R.id.resume);



        }
    }


    private void downloadFile(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager=(DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename+fileExtension);


        downloadManager.enqueue(request);





    }
}
