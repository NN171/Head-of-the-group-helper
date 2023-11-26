package com.immortalidiot.studentapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.immortalidiot.studentapp.auth.CallbackFragment;
import com.immortalidiot.studentapp.auth.FragmentUtils;
import com.immortalidiot.studentapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends FragmentUtils {
    CallbackFragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {

        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        com.immortalidiot.studentapp.databinding.FragmentProfileBinding binding =
                FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
