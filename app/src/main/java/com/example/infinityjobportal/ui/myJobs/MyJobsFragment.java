package com.example.infinityjobportal.ui.myJobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.infinityjobportal.R;

import com.example.infinityjobportal.adapter.myJobsPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyJobsFragment extends  Fragment {
    private static final String TAG = "PostedJobsFragment";

    TabLayout tabLayout;
    TabItem activeTab, closedTab, draftTab;
    ViewPager2 viewPager2;
    com.example.infinityjobportal.adapter.myJobsPagerAdapter myJobsPagerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: has started");
        View root = inflater.inflate(R.layout.fragment_my_jobs, container, false);

        tabLayout = root.findViewById(R.id.myJobsTabBar);

        myJobsPagerAdapter = new myJobsPagerAdapter(this);
        viewPager2 = root.findViewById(R.id.viewPager_myjobs);
        viewPager2.setAdapter(myJobsPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "onConfigureTab: called");

                switch (position) {
                    case 0:
                        tab.setText("Applied Jobs");
                        break;
                    case 1:
                        tab.setText("Saved Jobs");
                        break;


                }
            }
        });
        tabLayoutMediator.attach();

        Log.d(TAG, "onCreateView: has ended");
        return root;
    }
}