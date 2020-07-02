package com.example.covidhelper.ui.homeAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModelAdmin extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModelAdmin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}