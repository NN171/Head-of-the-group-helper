package com.immortalidiot.studentapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.immortalidiot.studentapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {

        FragmentSettingsBinding binding = FragmentSettingsBinding
                .inflate(inflater, container, false);

        return binding.getRoot();
    }
}
