package com.immortalidiot.studentapp.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ChatViewModel() {
        mText = new MutableLiveData<>();
        String NOTHING_TEXT = "Здесь пока нихуя нет :D";
        mText.setValue(NOTHING_TEXT);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
