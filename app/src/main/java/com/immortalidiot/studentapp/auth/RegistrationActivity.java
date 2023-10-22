package com.immortalidiot.studentapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.immortalidiot.studentapp.MainActivity;
import com.immortalidiot.studentapp.R;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final TextInputEditText editTextEmail = findViewById(R.id.reg_email);
        final TextInputEditText editTextPassword = findViewById(R.id.reg_password);
        final TextInputEditText editTextPasswordConfirmation = findViewById(R.id.password_confirmation);
        final AppCompatButton regButton = findViewById(R.id.reg_button);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        final TextView toLogin = findViewById(R.id.to_login);

        regButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            final String email = String.valueOf(editTextEmail.getText());
            final String password = String.valueOf(editTextPassword.getText());
            final String passwordConfirmation = String.valueOf
                    (editTextPasswordConfirmation.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegistrationActivity.this,
                        "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegistrationActivity.this,
                        "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordConfirmation)) {
                Toast.makeText(RegistrationActivity.this,
                        "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordConfirmation)) {
                Toast.makeText(RegistrationActivity.this,
                        "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,
                            "Аккаунт создан", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this,
                            "Ошибка регистрации", Toast.LENGTH_SHORT).show();
                }
            });
        });

        toLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}