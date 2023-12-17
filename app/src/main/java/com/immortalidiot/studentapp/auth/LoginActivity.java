package com.immortalidiot.studentapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.immortalidiot.studentapp.MainActivity;
import com.immortalidiot.studentapp.R;
import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.LoginRequest;
import com.immortalidiot.studentapp.requests.StudentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputEditText editTextEmail = findViewById(R.id.login_email);
        final TextInputEditText editTextPassword = findViewById(R.id.login_password);
        final AppCompatButton loginButton = findViewById(R.id.login_button);
        final TextView toRegistration = findViewById(R.id.to_registration);

        toRegistration.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        });

        loginButton.setOnClickListener(v -> {
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this,
                        "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this,
                        "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            loginStudent(email, password);
        });
    }

    private void loginStudent(String email, String password) {
        ServiceAPI serviceAPI = ClientAPI.getClient().create(ServiceAPI.class);
        LoginRequest requests = new LoginRequest(email, password);
        Call<StudentResponse> responseCall = serviceAPI.authenticate(requests);

        responseCall.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(@NonNull Call<StudentResponse> call,
                                   @NonNull Response<StudentResponse> response) {
                if (response.isSuccessful()) {
                    StudentResponse studentResponse = response.body();
                    if (studentResponse != null) {
                        String token = studentResponse.getToken();
                        AuthManager.saveToken(LoginActivity.this, token);
                        Toast.makeText(LoginActivity.this,
                                "Успешный вход",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        // TODO: добавьте переключение на профиль, если необходимо

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Ответ сервера не содержит данных пользователя",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Неверный логин или пароль"+response.code(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StudentResponse> call,
                                  @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}