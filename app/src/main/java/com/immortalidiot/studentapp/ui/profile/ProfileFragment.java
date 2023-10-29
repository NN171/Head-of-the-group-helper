package com.immortalidiot.studentapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.immortalidiot.studentapp.R;
import com.immortalidiot.studentapp.databinding.FragmentProfileBinding;

import java.util.zip.Inflater;

public class ProfileFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
