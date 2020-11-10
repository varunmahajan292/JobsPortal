package com.example.infinityjobportal.admin.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.infinityjobportal.ClientLogin;
import com.example.infinityjobportal.R;
import com.example.infinityjobportal.admin.ChangePassword;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {
LinearLayout changePassword, logout;
    FirebaseAuth mAuth;


    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.admin_fragment_settings, container, false);

/*
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null)
        {
            //User NOT logged In
          // AdminMainActivity.finish();
            startActivity(new Intent(getContext(), ClientLogin.class));
        }

 */
            changePassword = root.findViewById(R.id.changePassword);

            changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), ChangePassword.class));
                }
            });




        return root;
    }
}