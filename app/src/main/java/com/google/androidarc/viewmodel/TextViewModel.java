package com.google.androidarc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TextViewModel extends ViewModel {
    private MutableLiveData<String> text = new MutableLiveData<>();

    public void setViewModelValue(String string) {
        text.setValue(string);
    }

    public LiveData<String> getViewModelValue() {
        return text;
    }
}
