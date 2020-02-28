package com.diego.m07proyecto.ui.options;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OptionsAndSignOutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OptionsAndSignOutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}