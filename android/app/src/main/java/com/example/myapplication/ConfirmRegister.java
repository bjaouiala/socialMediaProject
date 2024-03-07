package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Model.User;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.service.RegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyAccount extends AppCompatActivity {
   private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        button= findViewById(R.id.button22);
        button.setOnClickListener(view->register());

    }

    public void register(){
        Intent i  = getIntent();
        String Firstname = i.getStringExtra("Firstname");
        String Lastname = i.getStringExtra("Lastname");
        String date = i.getStringExtra("date");
        String phoneNumber = i.getStringExtra("phoneNumber");
        String email = i.getStringExtra("email");
        String emailRecuperation =  i.getStringExtra("emailRecuperation");
        String genre = i.getStringExtra("genre");
        String password = i.getStringExtra("password");

        RegisterService registerService = RetrofitClient.getRetrofitInsantce().create(RegisterService.class);
        Call<String> call = registerService.register(Firstname,Lastname,email,emailRecuperation,date,genre,password,phoneNumber);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    Toast.makeText(VerifyAccount.this, "user Registred", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(VerifyAccount.this, "probleme", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(VerifyAccount.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}