package com.immortalidiot.studentapp;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.immortalidiot.studentapp.auth.CallbackFragment;
import com.immortalidiot.studentapp.auth.FragmentUtils;
import com.immortalidiot.studentapp.auth.LoginFragment;
import com.immortalidiot.studentapp.databinding.ActivityMainBinding;
import com.immortalidiot.studentapp.ui.journal.JournalFragment;
import com.immortalidiot.studentapp.ui.profile.ProfileFragment;
import com.immortalidiot.studentapp.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements CallbackFragment {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new LoginFragment(), false);

        ViewGroup.LayoutParams layoutParams = binding.container.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.container.setLayoutParams(layoutParams);
        binding.navView.setVisibility(View.GONE);
        binding.bottomNavView.setOnItemSelectedListener(v -> {
            if (v.getItemId() == R.id.navigation_profile) {
                replaceFragment(new ProfileFragment(), true);
            }
            if (v.getItemId() == R.id.navigation_settings) {
                replaceFragment(new SettingsFragment(), true);
            }
            if (v.getItemId() == R.id.navigation_journal) {
                replaceFragment(new JournalFragment(), true);
            }
            return true;
        });
    }

    private void replaceFragment(FragmentUtils fragment, boolean isReturning) {
        fragment.setCallbackFragment(this);
        if (isReturning) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_view, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_view, fragment)
                .commit();
    }

    @Override
    public void changeFragment(FragmentUtils fragment, boolean isReturning) {
        replaceFragment(fragment, isReturning);
    }

    @Override
    public void setNavHostVisibility(int state) {
        binding.bottomNavView.setVisibility(state);
    }

}