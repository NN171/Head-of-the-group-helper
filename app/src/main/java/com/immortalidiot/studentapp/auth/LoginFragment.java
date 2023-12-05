package com.immortalidiot.studentapp.auth;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.immortalidiot.studentapp.databinding.ForgotDialogBinding;
import com.immortalidiot.studentapp.databinding.FragmentLoginBinding;
import com.immortalidiot.studentapp.ui.profile.ProfileFragment;
import com.immortalidiot.studentapp.R;

import java.util.Objects;


public class LoginFragment extends FragmentUtils {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    CallbackFragment fragment;
    FragmentLoginBinding binding;
    ForgotDialogBinding forgotDialogBinding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        forgotDialogBinding = ForgotDialogBinding.inflate(inflater, container, false);
        ProgressBar progressBar = binding.progressBar;
        binding.toRegistration.setOnClickListener(v -> {
            if (fragment != null) {
                fragment.changeFragment(new SignUpFragment(), true);
            }
        });

        binding.loginButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(binding.loginEmail.getText());
            String password = String.valueOf(binding.loginPassword.getText());

            if (TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Введите почту",
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

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
                        Toast.makeText(getContext(),
                                "Успешный вход",
                                Toast.LENGTH_SHORT
                        ).show();

                        if (fragment != null) {
                            ProfileFragment profileFragment = new ProfileFragment();
                            profileFragment.setCallbackFragment(fragment);
                            fragment.changeFragment(profileFragment, true);
                        }
                    } else {
                        Toast.makeText(getContext(),
                                "Неверный логин, или пароль или почта не подтверждена",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Неверный логин или пароль",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });

        binding.passwordReset.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View dialogView = getLayoutInflater().inflate(R.layout.forgot_dialog, null);
            TextInputEditText email = dialogView.findViewById(R.id.email_text_field);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            dialogView.findViewById(R.id.reset_button)
                    .setOnClickListener(t -> {
                        String user_email = email.toString();
                        if (TextUtils.isEmpty(user_email) && !Patterns.EMAIL_ADDRESS.
                                matcher(user_email).matches()) {
                            Toast.makeText(getContext(),
                                            "Введите почту",
                                            Toast.LENGTH_LONG
                            ).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(user_email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(),
                                                        "На вашу почту отправлено письмо",
                                                        Toast.LENGTH_LONG
                                        ).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(),
                                                        "Что-то пошло не так :(",
                                                        Toast.LENGTH_SHORT
                                        ).show();
                                    }
                                });
                    });

            dialogView.findViewById(R.id.cancel_button)
                    .setOnClickListener(t -> dialog.dismiss());
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();
        });

        return view;
    }
    public void setCallbackFragment(CallbackFragment fragment) {
        this.fragment = fragment;
    }
}