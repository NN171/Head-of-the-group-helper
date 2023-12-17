package com.immortalidiot.studentapp.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;


import com.immortalidiot.studentapp.MainActivity;
import com.immortalidiot.studentapp.R;
import com.immortalidiot.studentapp.auth.CallbackFragment;
import com.immortalidiot.studentapp.auth.FragmentUtils;

public class ProfileFragment extends FragmentUtils {
    CallbackFragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {
        ((MainActivity)getActivity()).setNavHostVisibility(View.VISIBLE);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void setCallbackFragment(CallbackFragment fragment) {
        this.fragment = fragment;

    }
}
