package com.example.infinityjobportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinityjobportal.adapter.JobSearchAdapter;
import com.example.infinityjobportal.model.PostJobPojo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jobs_search extends AppCompatActivity {
    private RecyclerView recjoblist;
    ImageView back;
    ArrayList<String> saveIdList = new ArrayList<>();
    SearchView searchView;
    private ArrayList<PostJobPojo> list=new ArrayList<PostJobPojo>();
    JobSearchAdapter adapter;
    FirebaseFirestore db;
    private List<PostJobPojo> exampleList;
    LinearLayout filtersContainer;
    TextView filter,msg, count;
    private Spinner jobCategorySpinner;
    Query q;
    String category;
    FirebaseAuth mAuth;
int cout=0;
    CollectionReference collectionReference ;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_search);
        back = findViewById(R.id.back);
        recjoblist=findViewById(R.id.recJobList);
        count = findViewById(R.id.count);

        filter = findViewById(R.id.filter);
        msg = findViewById(R.id.msg);
        jobCategorySpinner = findViewById(R.id.jobCategorySpinner);
        db= FirebaseFirestore.getInstance();
        collectionReference=db.collection("Jobs");
        mAuth = FirebaseAuth.getInstance();

        setTitle("Search Jobs");
        //msg.setVisibility(View.GONE);
        fillExampleList();
       // setUpRecyclerView();


        //Toast.makeText(getApplicationContext(), (int) GlobalStorage.minSalary +" "+ (int) GlobalStorage.maxSalary,Toast.LENGTH_SHORT).show();



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              finish();
            }
        });

    }




    private void fillExampleList() {
        db = FirebaseFirestore.getInstance();
        exampleList = new ArrayList<>();


        db.collection("MyJobs").whereEqualTo("uid", mAuth.getCurrentUser().getEmail())//.whereEqualTo("type","application")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {

                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                // PostJobPojo p = d.toObject(PostJobPojo.class);
                                //  p.setJobTitle(d.getString("jobTitle"));
                                // p.setCompanyName(d.getString("companyName"));
                                //p.setCityAddress(d.getString("cityAddress"));
                                //p.setId(d.getId());

                                saveIdList.add(d.getString("jobId"));
                                // saveIdList.add(d.getId());
                                // Toast.makeText(getContext(),d.getString("jobId"),Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(),saveIdList,Toast.LENGTH_SHORT).show();
                            }

                            // showToast();
                            //adapter.notifyDataSetChanged();
                        }

                    }


                    private void showToast() {

                        for(int i=0; i<saveIdList.size(); i++) {
                            // text.setText(saveIdList.get(i));
                            Toast.makeText(getApplicationContext(), saveIdList.get(i), Toast.LENGTH_SHORT).show();
                        }

                    }


                });











      //  query=collectionReference.whereEqualTo("language", GlobalStorage.language).whereEqualTo("jobCategory",GlobalStorage.jobCatogory).whereEqualTo("provinceAddress","QC");//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
//        Toast.makeText(this, GlobalStorage.province, Toast.LENGTH_SHORT).show();

        if (!GlobalStorage.language.equals("") && !GlobalStorage.jobCatogory.equals("Any") && GlobalStorage.province.equals("Any")) {// botth active province disabled
            query=collectionReference.whereEqualTo("language", GlobalStorage.language).whereEqualTo("jobCategory",GlobalStorage.jobCatogory);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);

        } else if (!GlobalStorage.language.equals("") && GlobalStorage.jobCatogory.equals("Any") && GlobalStorage.province.equals("Any")) {// only language active && category disabled && province disabled{
            query=collectionReference.whereEqualTo("language", GlobalStorage.language);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
        }
        else if (GlobalStorage.language.equals("") && !GlobalStorage.jobCatogory.equals("Any") && GlobalStorage.province.equals("Any")) {//  language & province disabled && category active {
            query=collectionReference.whereEqualTo("jobCategory",GlobalStorage.jobCatogory);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
        }
        else if (!GlobalStorage.language.equals("") && GlobalStorage.jobCatogory.equals("Any") && !GlobalStorage.province.equals("Any")) {//  language & province active && category disabled  {
            query=collectionReference.whereEqualTo("language", GlobalStorage.language).whereEqualTo("provinceAddress",GlobalStorage.province);
        }
        else if (GlobalStorage.language.equals("") && !GlobalStorage.jobCatogory.equals("Any") && !GlobalStorage.province.equals("Any") ) {//  language   disabled && category & province active {
            query=collectionReference.whereEqualTo("jobCategory",GlobalStorage.jobCatogory).whereEqualTo("provinceAddress",GlobalStorage.province);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
        }
        else if (GlobalStorage.language.equals("") && GlobalStorage.jobCatogory.equals("Any") && !GlobalStorage.province.equals("Any") ) {//  language & category disabled && province active {
            query=collectionReference.whereEqualTo("provinceAddress",GlobalStorage.province);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
        }
        else  if (!GlobalStorage.language.equals("") && !GlobalStorage.jobCatogory.equals("Any") && !GlobalStorage.province.equals("Any")) {// botth active plus province
        query=collectionReference.whereEqualTo("language", GlobalStorage.language).whereEqualTo("jobCategory",GlobalStorage.jobCatogory).whereEqualTo("provinceAddress",GlobalStorage.province);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);
        }
         else {
            query=collectionReference;//.whereEqualTo("language", GlobalStorage.language).whereEqualTo("jobCategory",GlobalStorage.jobCatogory).whereEqualTo("provinceAddress",GlobalStorage.province);//.whereLessThan("minSalary",GlobalStorage.maxSalary).whereGreaterThan("minSalary",GlobalStorage.minSalary);

        }
        cout = 0;
                  query.whereEqualTo("status","active").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    if (!queryDocumentSnapshots.isEmpty()) {

                        List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot d : list1) {
                          int cout=0;
                            PostJobPojo p = d.toObject(PostJobPojo.class);
                            p.setJobTitle(d.getString("jobTitle"));
                            p.setCompanyName(d.getString("companyName"));
                            p.setCityAddress(d.getString("cityAddress"));
                            p.setId(d.getId());


                            for(int i=0; i<saveIdList.size(); i++) {
                                if(d.getId().equals(String.valueOf(saveIdList.get(i)))) {
                                    cout=1;
                                }
                            }
                            if(cout==0)
                                exampleList.add(p);



                        }

                        RecyclerView recyclerView = findViewById(R.id.recJobList);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Jobs_search.this);
                        adapter = new JobSearchAdapter(getApplicationContext(), exampleList);

                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                      msg.setVisibility(View.GONE);

                    }
                    count.setText("Total Result : "+exampleList.size());
                }
            });
    }



    //fillExamplelist     end here


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }

    public void back(MenuItem item) {
        finish();
    }
}