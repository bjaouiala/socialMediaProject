package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.service.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     private EditText email;
    private EditText password;
    private Button button2;
    private Button creer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        button2 = findViewById(R.id.button);
        creer = findViewById(R.id.button23);

        button2.setOnClickListener(login->loginUser());
        creer.setOnClickListener(creer-> creerUnCompte());

    }

    public void creerUnCompte(){
        Intent i  =new Intent(this, NomPrenomActivity.class);
        startActivity(i);

    }

    public void loginUser(){
        String username = email.getText().toString();
        String pass = password.getText().toString();

        AuthService authService = RetrofitClient.getRetrofitInsantce().create(AuthService.class);
        Call<String> call = authService.login(username,pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String JWT = response.body();
                    Toast.makeText(LoginActivity.this, "Loged in", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}