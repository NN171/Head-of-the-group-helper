package com.immortalidiot.studentapp.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        String NOTHING_TEXT = "Здесь пока ничего нет :D";
        mText.setValue(NOTHING_TEXT);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
