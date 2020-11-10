package com.example.infinityjobportal;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityjobportal.adapter.SkillAdapter;
import com.example.infinityjobportal.model.Skill;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    private List<Skill> skillList;
    SkillAdapter adapter;
    RecyclerView recyclerView;
    String TAG="SkillActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        firebaseFirestore = FirebaseFirestore.getInstance();

        skillList = new ArrayList<>();
        // setup recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new SkillAdapter(this, skillList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        getSkills();


    }

    private void getSkills() {
        skillList.clear();
        firebaseFirestore.collection("skills").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<Skill> skills = documentSnapshots.toObjects(Skill.class);

                            // Add all to your list
                            skillList.addAll(skills);
                            Log.d(TAG, "onSuccess: " + skillList);
                            adapter.notifyDataSetChanged();
                        }
                    }


                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_skill) {
            AddSkillDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddSkillDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_skill, null);

        final AppCompatEditText etSkill = dialogView.findViewById(R.id.etSkill);
        AppCompatButton btnSubmit = dialogView.findViewById(R.id.buttonSubmit);
        AppCompatButton btnCancel = dialogView.findViewById(R.id.buttonCancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skill = etSkill.getText().toString();
                if (!TextUtils.isEmpty(skill)) {

                    String id = firebaseFirestore.collection("skills").document().getId();

                    Map<String, Object> order = new HashMap<>();
                    order.put("id", id);
                    order.put("name", skill);


                    firebaseFirestore.collection("skills").document(id)
                            .set(order)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    getSkills();
                                    Toast.makeText(SkillActivity.this, "Skill Added Successfully", Toast.LENGTH_SHORT).show();
                                    dialogBuilder.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialogBuilder.dismiss();
                                }
                            });


                } else {
                    Toast.makeText(SkillActivity.this, "Skill cannot be blank", Toast.LENGTH_LONG).show();
                }

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
