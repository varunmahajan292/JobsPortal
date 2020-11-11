package com.example.infinityjobportal;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.infinityjobportal.adapter.AdapterSaveJob;

import com.example.infinityjobportal.adapter.adapterAppliedJobs;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SaveJobs extends Fragment {
    //EditText ed_interests;
    public String a = "";
    public String b="";
    Button bt_add;
    // FirebaseFirestore db;
    StorageReference mstorageRef;
    RecyclerView recy;
    FirebaseAuth fbauth;
    AdapterSaveJob InteAdapter;
    public FirebaseAuth fbAuth;
    Context c;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<PostJobPojo> list = new ArrayList<>();
    ArrayList<PostJobPojo> list2 = new ArrayList<>();
    ArrayList<PostJobPojo> list3 = new ArrayList<>();
    ArrayList<String > l = new ArrayList<String>();

    public SaveJobs() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_save_jobs, container, false);
        recy = root.findViewById(R.id.recy);
        fbauth = FirebaseAuth.getInstance();
        fbAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        ///////////get applied jobs by username
        String k = String.valueOf(fbauth.getCurrentUser().getEmail());


        db.collection("MyJobs").whereEqualTo("uid", k).whereEqualTo("type","save").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {


                                l.add(d.getString("jobId"));
                                //Toast.makeText(getApplicationContext(),d.getString("uid"),Toast.LENGTH_SHORT).show();
                            }

                            showdata();


                        }
                    }
                });

        return root;
    }
public void removesing(int pos){
        list2.remove(pos);
        InteAdapter.notifyItemRemoved(pos);

}
    private void showdata() {
        for (int i = 0; i < l.size(); i++) {

            b=String.valueOf(l.get(i));
            DocumentReference docRef = db.collection("Jobs").document(l.get(i));

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            PostJobPojo user = document.toObject(PostJobPojo.class);
                            user.setJobTitle(document.getString("jobTitle"));
                            user.setJobCategory(document.getString("jobCategory"));
                            user.setCityAddress(String.valueOf(document.getString("cityAddress")+", "+document.getString("provinceAddress")));
                            //user.setId(String.valueOf(b));
                            user.setId(String.valueOf(document.getId()));
                            user.setProvinceAddress("save");

                            list2.add(user);
                            //  Toast.makeText(getContext(),user.getJobTitle(),Toast.LENGTH_SHORT).show();
                            //InteAdapter.notifyDataSetChanged();
                            InteAdapter = new AdapterSaveJob( getContext(),list2);

                            recy.setHasFixedSize(true);
                            recy.setLayoutManager(new LinearLayoutManager(c, RecyclerView.VERTICAL, false));

                            recy.setAdapter(InteAdapter);

                        } else {

                        }
                    } else {

                    }
                }
            });



            // Toast.makeText(getApplicationContext(),l.get(i),Toast.LENGTH_SHORT).show();


        }


    }}