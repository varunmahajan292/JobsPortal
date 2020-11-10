package com.example.infinityjobportal.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.infinityjobportal.AppliedJobs;
import com.example.infinityjobportal.SaveJobs;

public class myJobsPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "MyjobsPAgerAdapter";

    public myJobsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "MyJobsPostedAdapter: fragment created");
        switch (position) {
            case 0:
                return new AppliedJobs();
            case 1:
                return new SaveJobs();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}