package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.service.RegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmRegister extends AppCompatActivity {
   private Button button;
   private Toolbar toolbar;
   private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);
      toolbar =findViewById(R.id.toolbar);
        button= findViewById(R.id.button22);
        textView = findViewById(R.id.textView11);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->register());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    if(response.body().equals("user exist")){
                        textView.setText("L'adresse e-mail existe déjà. Veuillez en choisir une autre.");
                    }else {
                        Intent i = new Intent(ConfirmRegister.this, VerifyCompte.class);
                        startActivity(i);
                    }



                }else {
                    Toast.makeText(ConfirmRegister.this, "probleme", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(ConfirmRegister.this, "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });

    }
}