package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Activity.ManageProfile.Profile;
import com.example.myapplication.Activity.Register.NomPrenomActivity;
import com.example.myapplication.Activity.ResetPassword.SendCodeWithEmail;
import com.example.myapplication.Model.AuthModel;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     private EditText email;
    private EditText password;
    private Button button2;
    private Button creer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        button2 = findViewById(R.id.button);
        creer = findViewById(R.id.button23);
        textView = findViewById(R.id.textView2);

        button2.setOnClickListener(login->loginUser());
        creer.setOnClickListener(creer-> creerUnCompte());
        textView.setOnClickListener(pass->motPasssOublie());

    }

    public void creerUnCompte(){
        Intent i  =new Intent(this, NomPrenomActivity.class);
        startActivity(i);

    }
    public void motPasssOublie(){
        Intent i = new Intent(this, SendCodeWithEmail.class );
        startActivity(i);
    }

    public void loginUser(){
        String username = email.getText().toString();
        String pass = password.getText().toString();

        AuthService authService = RetrofitClient.getRetrofitInsantce().create(AuthService.class);
        Call<AuthModel> call = authService.login(username,pass);
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(@NonNull Call<AuthModel> call, @NonNull Response<AuthModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AuthModel authModel =response.body();
                    long id = authModel.getUser().getId();
                    String JWT = authModel.getToken();
                    SharedPreferences.Editor editor = getSharedPreferences("shared",MODE_PRIVATE).edit();
                    editor.putString("token",JWT);
                    editor.apply();
                    Intent i = new Intent(LoginActivity.this, Profile.class);
                    i.putExtra("id",id);
                    startActivity(i);

                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AuthModel> call, @NonNull Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(LoginActivity.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}