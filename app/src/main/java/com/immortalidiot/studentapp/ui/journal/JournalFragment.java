package com.immortalidiot.studentapp.ui.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.immortalidiot.studentapp.auth.FragmentUtils;
import com.immortalidiot.studentapp.databinding.FragmentJournalBinding;

public class JournalFragment extends FragmentUtils {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {

        com.immortalidiot.studentapp.databinding.FragmentJournalBinding binding =
                FragmentJournalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
