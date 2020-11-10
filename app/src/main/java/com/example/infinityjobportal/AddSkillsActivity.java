package com.example.infinityjobportal;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.adapter.AddSkillAdapter;
import com.example.infinityjobportal.model.Skill;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddSkillsActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    private List<Skill> skillList;
    AddSkillAdapter adapter;
    RecyclerView recyclerView;
    AppCompatEditText etSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        firebaseFirestore = FirebaseFirestore.getInstance();

        skillList = new ArrayList<>();
        // setup recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new AddSkillAdapter(this, skillList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    adapter.getFilter().filter(s.toString());
                } else {
                    getSkills();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        getSkills();


    }

    private void getSkills() {
        skillList.clear();
        firebaseFirestore.collection("skills").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {

                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<Skill> skills = documentSnapshots.toObjects(Skill.class);

                            // Add all to your list
                            skillList.addAll(skills);

                            adapter.notifyDataSetChanged();
                        }
                    }


                });

    }
}