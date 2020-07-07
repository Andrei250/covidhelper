package com.example.covidhelper.ui.showVulPerson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowVulPersonModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ShowVulPersonModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
