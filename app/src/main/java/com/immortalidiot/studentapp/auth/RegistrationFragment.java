package com.immortalidiot.studentapp.auth;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.immortalidiot.studentapp.databinding.FragmentRegistrationBinding;
import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.StudentRequests;
import com.immortalidiot.studentapp.ui.profile.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends FragmentUtils {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    CallbackFragment fragment;
    FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ProgressBar progressBar = binding.progressBar;

        binding.toLogin.setOnClickListener(v -> {
            if (fragment != null) {
                closeFragment();
            }
        });

        binding.regButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(binding.regEmail.getText());
            String password = String.valueOf(binding.regPassword.getText());
            String passwordConfirmation = String.valueOf(binding.passwordConfirmation.getText());

            if (TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordConfirmation)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordConfirmation)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }
            registerUser(email, password);
        });
        return view;
    }

    public void setCallbackFragment(CallbackFragment fragment) {
        this.fragment = fragment;
    }
    private void closeFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
    }

    private void registerUser(String userName, String password) {
        ServiceAPI serviceAPI = ClientAPI.getClient().create(ServiceAPI.class);
        StudentRequests login = new StudentRequests(userName, password);
        login.setEmail(userName);
        login.setPassword(password);

        Call<StudentRequests> call = serviceAPI.register(login);
        call.enqueue(new Callback<StudentRequests>() {
            @Override
            public void onResponse(Call<StudentRequests> call, Response<StudentRequests> response) {
                Toast.makeText(getContext(),
                        "Аккаунт создан",
                        Toast.LENGTH_SHORT)
                        .show();
                if (fragment != null) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setCallbackFragment(fragment);
                    fragment.changeFragment(profileFragment, false);
                }
            }

            @Override
            public void onFailure(Call<StudentRequests> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Ошибка сети",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}