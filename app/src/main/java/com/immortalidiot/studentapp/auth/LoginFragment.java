package com.immortalidiot.studentapp.auth;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.immortalidiot.studentapp.databinding.ForgotDialogBinding;
import com.immortalidiot.studentapp.databinding.FragmentLoginBinding;
import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.LoginRequest;
import com.immortalidiot.studentapp.requests.StudentResponse;
import com.immortalidiot.studentapp.ui.profile.ProfileFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends FragmentUtils {
    CallbackFragment fragment;
    FragmentLoginBinding binding;
    ForgotDialogBinding forgotDialogBinding;
    private SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean rememberMe = preferences.getBoolean("remember_me", false);

        if (rememberMe) {
            String email = preferences.getString("email", "");
            String password = preferences.getString("password", "");
            binding.loginEmail.setText(email);
            binding.loginPassword.setText(password);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        AppCompatCheckBox checkBox = binding.rememberMeCheckBox;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> sharedPreferences
                .edit()
                .putBoolean("remember_me", isChecked).apply());

        forgotDialogBinding = ForgotDialogBinding.inflate(inflater, container, false);
        ProgressBar progressBar = binding.progressBar;
        binding.toRegistration.setOnClickListener(v -> {
            if (fragment != null) {
                fragment.changeFragment(new RegistrationFragment(), true);
            }
        });

        binding.loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(binding.loginEmail.getText());
            String studentId = String.valueOf(binding.regStudentIdField.getText());
            String password = String.valueOf(binding.loginPassword.getText());

            if (TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите почту",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (TextUtils.isEmpty(studentId)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите номер студенческого билета",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите пароль",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            loginStudent(email, password, Integer.parseInt(studentId));
        });

        // TODO: password reset feature using personal db (not FB)
//        binding.passwordReset.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            View dialogView = getLayoutInflater().inflate(R.layout.forgot_dialog, null);
//            TextInputEditText email = dialogView.findViewById(R.id.email_text_field);
//            builder.setView(dialogView);
//            AlertDialog dialog = builder.create();

//            dialogView.findViewById(R.id.reset_button)
//                    .setOnClickListener(t -> {
//                        String user_email = email.toString();
//                        if (TextUtils.isEmpty(user_email) && !Patterns.EMAIL_ADDRESS.
//                                matcher(user_email).matches()) {
//                            Toast.makeText(getContext(),
//                                            "Введите почту",
//                                            Toast.LENGTH_LONG
//                            ).show();
//                            return;
//                        }
//                        auth.sendPasswordResetEmail(user_email)
//                                .addOnCompleteListener(task -> {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(getContext(),
//                                                        "На вашу почту отправлено письмо",
//                                                        Toast.LENGTH_LONG
//                                        ).show();
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(getContext(),
//                                                        "Что-то пошло не так :(",
//                                                        Toast.LENGTH_SHORT
//                                        ).show();
//                                    }
//                                });
//                    });
//            dialogView.findViewById(R.id.cancel_button)
//                    .setOnClickListener(t -> dialog.dismiss());
//            if (dialog.getWindow() != null) {
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//            }
//            dialog.show();
//        });

        return view;
    }
    public void setCallbackFragment(CallbackFragment fragment) {
        this.fragment = fragment;
    }

    private void loginStudent(String email, String password, int studentId) {
        ServiceAPI serviceAPI = ClientAPI.getClient().create(ServiceAPI.class);
        LoginRequest request = new LoginRequest(email, password, studentId);
        Call<StudentResponse> responseCall = serviceAPI.authenticate(request);

        responseCall.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                StudentResponse studentResponse = response.body();
                if (studentResponse != null) {
                    String token = studentResponse.getToken();
                    AuthManager.saveToken(getContext(), token);
                    Toast.makeText(getContext(),
                            "Успешный вход",
                            Toast.LENGTH_SHORT)
                            .show();
                    goToProfileFragment();
                }
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {

            }
        });
    }
    private void goToProfileFragment() {
        if (fragment != null) {
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setCallbackFragment(fragment);
            fragment.changeFragment(profileFragment, false);
        }
    }
}