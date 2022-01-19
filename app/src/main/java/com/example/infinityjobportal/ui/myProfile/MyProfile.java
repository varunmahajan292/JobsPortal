package com.example.infinityjobportal.ui.myProfile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infinityjobportal.ClientChangePassword;
import com.example.infinityjobportal.EditAvailability;
import com.example.infinityjobportal.EditNameSection;
import com.example.infinityjobportal.ListOfExperienceActiviy;
import com.example.infinityjobportal.MainEducation;
import com.example.infinityjobportal.pojoAddNewEducation;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.SkillActivity;
import com.example.infinityjobportal.UpdateAbout;
import com.example.infinityjobportal.UpdateContactSection;
import com.example.infinityjobportal.UpdateUserPic;
import com.example.infinityjobportal.adapter.InterestsAdapterProfile;
import com.example.infinityjobportal.adapter.LOEAdapterProfile;
import com.example.infinityjobportal.adapter.NewEducationAdapterProfile;
import com.example.infinityjobportal.faltu_context;
import com.example.infinityjobportal.interests;
import com.example.infinityjobportal.model.InterestsModel;
import com.example.infinityjobportal.model.LOEModel;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyProfile extends Fragment {

    ImageView userPic, editNameSection, editAboutSection, editContcatSection;
    TextView editExperienceSection, editEducationSection, editInterestSection, editSkillsSection;
    TextView name, tagLine, location, about, email, number, website, address;
    TextView mon, tue, wed, thurs, fri, sat, sun;
    ImageView editAvailabilitySection;
    String websiteUrl = "";
    LinearLayout changePassword;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    RecyclerView rec;
    InterestsAdapterProfile InteAdapter;

    ArrayList<InterestsModel> list = new ArrayList<>();


    private RecyclerView recyclerViewEducation;
    private NewEducationAdapterProfile adapter;
    private List<pojoAddNewEducation> educationList;


    RecyclerView recexp;
    LOEAdapterProfile loeAdapter;
    faltu_context context;

    ArrayList<LOEModel> listexp = new ArrayList<>();


    public MyProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);

        editAvailabilitySection = root.findViewById(R.id.editAvailabilitySection);
        mon = root.findViewById(R.id.mon);
        tue = root.findViewById(R.id.tue);
        wed = root.findViewById(R.id.wed);
        thurs = root.findViewById(R.id.thurs);
        fri = root.findViewById(R.id.fri);
        sat = root.findViewById(R.id.sat);
        sun = root.findViewById(R.id.sun);
        userPic = root.findViewById(R.id.userPic);
        name = root.findViewById(R.id.name);
        tagLine = root.findViewById(R.id.tagLine);
        location = root.findViewById(R.id.location);
        about = root.findViewById(R.id.about);
        email = root.findViewById(R.id.email);
        number = root.findViewById(R.id.number);
        website = root.findViewById(R.id.website);
        address = root.findViewById(R.id.address);
        editNameSection = root.findViewById(R.id.editNameSection);
        editAboutSection = root.findViewById(R.id.editAboutSection);
        editEducationSection = root.findViewById(R.id.openEducation);
        editExperienceSection = root.findViewById(R.id.openExperience);

        editInterestSection = root.findViewById(R.id.openInterest);
        editContcatSection = root.findViewById(R.id.editContactInfo);
        changePassword = root.findViewById(R.id.changePassword);
        rec = root.findViewById(R.id.rec);
        recyclerViewEducation = root.findViewById(R.id.recyclerEducation);
        recexp = root.findViewById(R.id.recexp);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();




      //  loadData();





        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateUserPic.class);
                startActivity(i);
            }
        });


        editNameSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditNameSection.class));
            }
        });

        editAboutSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateAbout.class));
            }
        });

        editContcatSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateContactSection.class));
            }
        });

//        website.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (!websiteUrl.equals("")) {
////                    Intent i = new Intent(Intent.ACTION_VIEW);
////                    i.setData(Uri.parse(websiteUrl));
////                    startActivity(i);
////                }
//
//            }
//        });


        editAvailabilitySection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditAvailability.class);
                startActivity(i);
            }
        });


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ClientChangePassword.class));
            }
        });


        editEducationSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainEducation.class));
            }
        });

        editInterestSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), interests.class));
            }
        });

        editExperienceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ListOfExperienceActiviy.class));
            }
        });





        return root;
    }

    @Override
    public void onResume() {
        //other stuff
        super.onResume();
        loadData();
    }

    private void loadData() {


        DocumentReference dref = db.collection("Availability").document(mAuth.getCurrentUser().getEmail());
        dref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;

                }
                if (documentSnapshot.exists()) {
                    String Mon = documentSnapshot.getString("monday");
                    String Tue = documentSnapshot.getString("tuesday");
                    String Wed = documentSnapshot.getString("wednessday");
                    String Thurs = documentSnapshot.getString("thursday");
                    String Fri = documentSnapshot.getString("friday");
                    String Sat = documentSnapshot.getString("saturday");
                    String Sun = documentSnapshot.getString("sunday");

                    mon.setText(Mon);
                    tue.setText(Tue);
                    wed.setText(Wed);
                    thurs.setText(Thurs);
                    fri.setText(Fri);
                    sat.setText(Sat);
                    sun.setText(Sun);
                }
            }
        });


        DocumentReference docRef = db.collection("users").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        User user = document.toObject(User.class);
                        email.setText(user.getEmail());

                        name.setText(user.getFirstName().substring(0, 1).toUpperCase() + user.getFirstName().substring(1) + " " + user.getLastName().substring(0, 1).toUpperCase() + user.getLastName().substring(1));
                        number.setText(user.getNumber());

                        if (user.getTagLine().equals("")) {
                            tagLine.setText("Add your tag line.");
                        } else {
                            tagLine.setText(user.getTagLine());
                        }


                        if (user.getAbout().equals("")) {
                            about.setText("Add your about information.");
                        } else {
                            about.setText(user.getAbout());
                        }

                        if (user.getWebsite().equals("")) {
                            website.setText("Add your website.");
                            websiteUrl = "";
                            website.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        } else {
                            website.setText(user.getWebsite());
                            websiteUrl = user.getWebsite();
                            website.setTextColor(ContextCompat.getColor(getContext(), R.color.mainAppçColor));
                        }

                        if (user.getCity().equals("") || user.getProvince().equals("") || user.getCountry().equals("")) {
                            location.setText("Add your location for better experience.");
                            address.setText("Add your Address");

                        } else {
                            location.setText(user.getCity().substring(0, 1).toUpperCase() + user.getCity().substring(1) + ", " + user.getProvince().substring(0, 1).toUpperCase() + user.getProvince().substring(1) + ", " + user.getCountry().substring(0, 1).toUpperCase() + user.getCountry().substring(1));
                            address.setText(user.getApartment() + "-" + user.getBuilding() + ", " + user.getStreet().substring(0, 1).toUpperCase() + user.getStreet().substring(1) + ", " + user.getCity().substring(0, 1).toUpperCase() + user.getCity().substring(1) + ", " + user.getProvince().substring(0, 1).toUpperCase() + user.getProvince().substring(1) + ", " + user.getCountry().substring(0, 1).toUpperCase() + user.getCountry().substring(1) + ", " + user.getZipCode());
                        }

                        FirebaseStorage firebaseStorage;
                        StorageReference storageReference;

                        firebaseStorage = FirebaseStorage.getInstance();
                        storageReference = firebaseStorage.getReference();

//        StorageReference imageRef = storageReference.child("Images/my.png");
                        StorageReference imageRef = storageReference.child("user/" + user.getUserProfilePic());

                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Glide.with(getContext())
                                        .load(uri)
                                        .circleCrop()
                                        .into(userPic);

                                //Picasso.get().load(uri).into(limg);

                                // Toast.makeText(getContext(),"Success.",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Profile Pic is not available", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {

                    }
                } else {

                }
            }
        });


        listexp.clear();

        db.collection("LOE").whereEqualTo("a", "extra").whereEqualTo("userId",mAuth.getCurrentUser().getEmail()).limit(2).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                LOEModel p = d.toObject(LOEModel.class);
                                p.setDesignation(d.getString("designation"));
                                p.setInstitute(d.getString("institute"));
                                p.setStartdate(d.getString("startdate"));
                                p.setEnddate(d.getString("enddate"));
                                p.setId(d.getString("id"));
                                p.setUserId(d.getString("userId"));
                                listexp.add(p);
                            }
                            loeAdapter.notifyDataSetChanged();
                        }
                    }
                });


        loeAdapter = new LOEAdapterProfile(listexp, getContext(), "af");

        recexp.setHasFixedSize(true);
        recexp.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recexp.setAdapter(loeAdapter);


        // loading interest
        list.clear();
        db.collection("interest").whereEqualTo("faltu", "extra").whereEqualTo("userid",mAuth.getCurrentUser().getEmail()).limit(2).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                InterestsModel p = d.toObject(InterestsModel.class);
                                assert p != null;
                                p.setType_int(d.getString("type_int"));
                                p.setId(d.getString("id"));

                                list.add(p);
                            }
                            InteAdapter.notifyDataSetChanged();
                        }
                    }
                });
        InteAdapter = new InterestsAdapterProfile(list, getContext(), "af");

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rec.setAdapter(InteAdapter);


        recyclerViewEducation.setHasFixedSize(true);
        recyclerViewEducation.setLayoutManager(new LinearLayoutManager(getContext()));

        educationList = new ArrayList<>();
        adapter = new NewEducationAdapterProfile(getContext(), educationList);
        recyclerViewEducation.setAdapter(adapter);

        educationList.clear();
        db.collection("Education").whereEqualTo("userid",mAuth.getCurrentUser().getEmail()).limit(2).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                pojoAddNewEducation ed = d.toObject(pojoAddNewEducation.class);
                                ed.setId(d.getId());
                                educationList.add(ed);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


    }


}


