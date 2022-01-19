package com.example.infinityjobportal.admin.ui.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.adapter.InterestsAdapter;
import com.example.infinityjobportal.adapter.adapteruserlist;
import com.example.infinityjobportal.model.InterestsModel;
import com.example.infinityjobportal.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    //EditText ed_interests;
    public String a="";
    Button bt_add;
    // FirebaseFirestore db;
    StorageReference mstorageRef;
    RecyclerView recy;
    FirebaseAuth fbauth;
    adapteruserlist InteAdapter;
    Context c;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<User> list=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.admin_fragment_user, container, false);
       // final TextView textView = root.findViewById(R.id.text_home);
        recy = root.findViewById(R.id.recy);
        fbauth=FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("admin",false).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {


                            List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list1) {

                                User p = d.toObject(User.class);
                                assert p != null;
                                p.setFirstName(d.getString("firstName"));
                                p.setEmail(d.getString("email"));
                                p.setCity(d.getString("city"));
                                //Toast.makeText(getContext(),d.getString("firstName"),Toast.LENGTH_SHORT).show();
                                p.setUserProfilePic(d.getString("userProfilePic"));













                                list.add(p);
                            }
                            InteAdapter.notifyDataSetChanged();
                        }
                    }
                });
        InteAdapter =new adapteruserlist(list, getContext(), "af");

        recy.setHasFixedSize(true);
        recy.setLayoutManager(new LinearLayoutManager(c,RecyclerView.VERTICAL,false));
        recy.setAdapter(InteAdapter);
        return root;
    }
}