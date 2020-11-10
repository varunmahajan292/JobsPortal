package com.example.infinityjobportal.ui.postedJobs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.infinityjobportal.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PostedJobsFragment extends Fragment {
    private static final String TAG = "PostedJobsFragment";

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    PostedJobsPagerAdapter postedJobsPagerAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: has started");
        View root = inflater.inflate(R.layout.fragment_posted_jobs, container, false);

        tabLayout = root.findViewById(R.id.postedJobsTabBar);
        postedJobsPagerAdapter = new PostedJobsPagerAdapter(this);

        viewPager2 = root.findViewById(R.id.viewPager);
        viewPager2.setAdapter(postedJobsPagerAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "onConfigureTab: called");

                switch (position) {
                    case 0:
                        tab.setText("Active");
                        break;
                    case 1:
                        tab.setText("Closed");
                        break;
                    case 2:
                        tab.setText("Draft");
                        break;

                }
            }
        });
        tabLayoutMediator.attach();

        Log.d(TAG, "onCreateView: has ended");
        return root;
    }
}