package com.example.covidhelper.ui.createVulPerson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateVulPersonModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CreateVulPersonModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
