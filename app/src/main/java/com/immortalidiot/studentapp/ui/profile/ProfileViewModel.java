package com.immortalidiot.studentapp.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        String NOTHING_TEXT = "Здесь пока нихуя нет :D";
        mText.setValue(NOTHING_TEXT);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
