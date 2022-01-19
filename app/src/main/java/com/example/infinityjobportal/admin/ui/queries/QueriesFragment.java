package com.example.infinityjobportal.admin.ui.queries;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.adapterquerylist;
import com.example.infinityjobportal.model.Query;
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

public class QueriesFragment extends Fragment {
    public String a="";
    public String s="", name;
    public String b="";
    Button bt_add;
    // FirebaseFirestore db;
    StorageReference mstorageRef;
    RecyclerView recy;
    FirebaseAuth fbauth;
    adapterquerylist InteAdapter;
    Context c;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    ArrayList<String > l = new ArrayList<String>();
    ArrayList<Query> lq=new ArrayList<>();
    ArrayList<Query> lq2=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.admin_fragment_queries, container, false);
        recy = root.findViewById(R.id.recy);
        fbauth=FirebaseAuth.getInstance();
        ///////////////////////////fetch data for query
        db = FirebaseFirestore.getInstance();
        ///////////get applied jobs by username
        //String k = String.valueOf(fbauth.getCurrentUser().getEmail());


        db.collection("Query").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {
                                Query p = d.toObject(Query.class);
                                assert p != null;
                                //Toast.makeText(getContext(),d.getString(s),Toast.LENGTH_SHORT).show();
                                // p.setFirstName(d.getString("firstName"));
                                //  Log.d("log_userid",p.getUserid());
                                p.setEditSubject(d.getString("editSubject"));
                                p.setUserid(d.getString("userid"));
                                p.setFeedbackQuery(d.getString("feedbackQuery"));
                                p.setId(d.getId());
                               lq.add(p);
                                //Toast.makeText(getApplicationContext(),d.getString("uid"),Toast.LENGTH_SHORT).show();
                            }
                            InteAdapter = new adapterquerylist( getContext(),lq);

                            recy.setHasFixedSize(true);
                            recy.setLayoutManager(new LinearLayoutManager(c, RecyclerView.VERTICAL, false));
                            recy.setAdapter(InteAdapter);

                           // gdata();


                        }
                    }
                });

        return root;
    }

    public void gdata(){
        //hash
        for (int i = 0; i < lq.size(); i++) {
            Query  bq =lq.get(i);
            b= bq.getEditSubject();
            db.collection("users").whereEqualTo("email", bq.getUserid().toString()).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if (!queryDocumentSnapshots.isEmpty()) {


                                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                                for (DocumentSnapshot d : list1) {

                                    Query  user  = d.toObject(Query.class);

                                    assert user != null;
                                    user.setFirstName(String.valueOf(d.getString("firstName")+" "+ d.getString("lastName")));
                                    user.setEditSubject(b);
                                    lq2.add(user);
                                    //Toast.makeText(getApplicationContext(),d.getString("uid"),Toast.LENGTH_SHORT).show();
                                }
                               // InteAdapter.notifyDataSetChanged();
                                InteAdapter = new adapterquerylist( getContext(),lq2);

                                recy.setHasFixedSize(true);
                                recy.setLayoutManager(new LinearLayoutManager(c, RecyclerView.VERTICAL, false));
                                recy.setAdapter(InteAdapter);




                            }
                        }
                    });


        }

    }
    ///////////////////
    public void showdata() {
        for (int i = 0; i < lq.size(); i++) {

         Query  bq =lq.get(i);
           b= bq.getEditSubject();
            DocumentReference docRef = db.collection("users").document(bq.getUserid().toString());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                         if (document.exists()) {

                            Query  user  = document.toObject(Query.class);
                            user.setFirstName(String.valueOf(document.getString("firstName")+" "+ document.getString("lastName")));
                           user.setEditSubject(b);


                            lq2.add(user);
                            //  Toast.makeText(getContext(),user.getJobTitle(),Toast.LENGTH_SHORT).show();
                            InteAdapter.notifyDataSetChanged();
                            InteAdapter = new adapterquerylist( getContext(),lq2);

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


        }}}