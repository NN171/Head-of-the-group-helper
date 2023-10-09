package com.immortalidiot.studentapp.ui.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.immortalidiot.studentapp.databinding.FragmentJournalBinding;

public class JournalFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savesInstanceState) {

        JournalViewModel journalViewModel =
                new ViewModelProvider(this).get(JournalViewModel.class);

        com.immortalidiot.studentapp.databinding.FragmentJournalBinding binding =
                FragmentJournalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textJournal;
        journalViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
