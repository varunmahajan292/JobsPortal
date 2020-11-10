package com.example.infinityjobportal.ui.postJob;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostJobViewModel extends ViewModel {
    private static final String TAG = "PostJobViewModel";
    private MutableLiveData<String> mText;

    public PostJobViewModel() {
        Log.d(TAG, "PostJobViewModel: PostJobViewModel Started");
        mText = new MutableLiveData<>();
        mText.setValue("Let's get started!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}