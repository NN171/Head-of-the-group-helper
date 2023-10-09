package com.immortalidiot.studentapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.immortalidiot.studentapp.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {

        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        com.immortalidiot.studentapp.databinding.FragmentSettingsBinding binding =
                FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
