package com.immortalidiot.studentapp.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.immortalidiot.studentapp.MainActivity;
import com.immortalidiot.studentapp.R;
import com.immortalidiot.studentapp.db.ClientAPI;
import com.immortalidiot.studentapp.db.ServiceAPI;
import com.immortalidiot.studentapp.requests.LoginRequest;
import com.immortalidiot.studentapp.requests.StudentResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputEditText editTextEmail = findViewById(R.id.login_email);
        final TextInputEditText editTextPassword = findViewById(R.id.login_password);
        final AppCompatButton loginButton = findViewById(R.id.login_button);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final TextView toRegistration = findViewById(R.id.to_registration);
        final TextView resetPassword = findViewById(R.id.password_reset);

        toRegistration.setOnClickListener(v -> {
            startActivity(new Intent(this, RegistrationActivity.class));
            finish();
        });

//        resetPassword.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//            View dialogView = getLayoutInflater().inflate(R.layout.forgot_dialog, null);
//            TextInputEditText email = dialogView.findViewById(R.id.email_text_field);
//            builder.setView(dialogView);
//            AlertDialog dialog = builder.create();
//
//            dialogView.findViewById(R.id.reset_button)
//                    .setOnClickListener(t -> {
//                        String user_email = email.toString();
//                        if (TextUtils.isEmpty(user_email) && !Patterns.EMAIL_ADDRESS.
//                                matcher(user_email).matches()) {
//                            Toast.makeText(LoginActivity.this,
//                                            "Введите почту",
//                                            Toast.LENGTH_LONG)
//                                    .show();
//                            return;
//                        }
//                        auth.sendPasswordResetEmail(user_email)
//                                .addOnCompleteListener(task -> {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(LoginActivity.this,
//                                                        "На вашу почту отправлено письмо",
//                                                        Toast.LENGTH_LONG)
//                                                .show();
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(LoginActivity.this,
//                                                        "Что-то пошло не так :(",
//                                                        Toast.LENGTH_SHORT)
//                                                .show();
//                                    }
//                                });
//                    });
//
//            dialogView.findViewById(R.id.cancel_button)
//                    .setOnClickListener(t -> dialog.dismiss());
//            if (dialog.getWindow() != null) {
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//            }
//            dialog.show();
//        });

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

//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//                progressBar.setVisibility(View.GONE);
//
//                if (task.isSuccessful()) {
//                    if (Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified()) {
//                        Toast.makeText(LoginActivity.this,
//                                "Успешный вход", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(this, MainActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this,
//                                "Неверный логин, или пароль или почта не подтверждена", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this,
//                            "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
//                }
//            });
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