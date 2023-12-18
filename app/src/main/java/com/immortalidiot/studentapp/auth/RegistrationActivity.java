package com.immortalidiot.studentapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.immortalidiot.studentapp.R;
import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.StudentRequests;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextInputEditText editTextPassword = findViewById(R.id.reg_password);
        TextInputEditText editTextEmail = findViewById(R.id.reg_email);
        TextInputEditText editTextPasswordConfirmation = findViewById(R.id.password_confirmation);
        AppCompatButton regButton = findViewById(R.id.reg_button);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        TextView toLogin = findViewById(R.id.to_login);

        regButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());
            String passwordConfirmation = String.valueOf
                    (editTextPasswordConfirmation.getText());

            if (TextUtils.isEmpty(email)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this,
                        "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this,
                        "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordConfirmation)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this,
                        "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordConfirmation)) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RegistrationActivity.this,
                        "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            // registerUser(email, password, );
        });

        toLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

//    private void registerUser(String userName, String password, int studentId) {
//        ServiceAPI serviceAPI = ClientAPI.getClient().create(ServiceAPI.class);
//        StudentRequests login = new StudentRequests(userName, password, studentId);
//        login.setEmail(userName);
//        login.setPassword(password);
//
//        Call<StudentRequests> call = serviceAPI.register(login);
//        call.enqueue(new Callback<StudentRequests>() {
//            @Override
//            public void onResponse(@NonNull Call<StudentRequests> call,
//                                   @NonNull Response<StudentRequests> response) {
//                Toast.makeText(RegistrationActivity.this,
//                        "Аккаунт создан",
//                        Toast.LENGTH_SHORT).show();
//                // TODO: add switching to Profile
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<StudentRequests> call,
//                                  @NonNull Throwable t) {
//                Toast.makeText(RegistrationActivity.this,
//                        "Ошибка сети",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//  }
}