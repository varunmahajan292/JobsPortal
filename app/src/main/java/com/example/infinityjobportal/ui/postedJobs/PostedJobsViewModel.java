package com.example.infinityjobportal.ui.postedJobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostedJobsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PostedJobsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Let's get started!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}