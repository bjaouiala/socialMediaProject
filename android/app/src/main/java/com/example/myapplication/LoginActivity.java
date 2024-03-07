package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView success;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        success = findViewById(R.id.textView2);
        button2 = findViewById(R.id.button);

        button2.setOnClickListener(login->loginUser());

    }
    private void loginUser(){
        String username = email.getText().toString();
        String pass = password.getText().toString();

        AuthService authService = RetrofitClient.getRetrofitInsantce().create(AuthService.class);
        Call<String> call = authService.login(username,pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String JWT = response.body();
                    Log.d("jwt", JWT);
                    Toast.makeText(MainActivity.this, "Loged in", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}