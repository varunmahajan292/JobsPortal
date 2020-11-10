package com.example.infinityjobportal.ui.postedJobs;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.infinityjobportal.ui.postedJobs.activeJobs.ActiveJobsFragment;
import com.example.infinityjobportal.ui.postedJobs.closedJobs.ClosedJobsFragment;
import com.example.infinityjobportal.ui.postedJobs.draftJobs.DraftJobsFragment;

public class PostedJobsPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "PostedJobsPagerAdapter";

    public PostedJobsPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "PostedJobsPagerAdapter: fragment created");
        switch (position) {
            case 0:
                return new ActiveJobsFragment();
            case 1:
                return new ClosedJobsFragment();
            case 2:
                return new DraftJobsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
