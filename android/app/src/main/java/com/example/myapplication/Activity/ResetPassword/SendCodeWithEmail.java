package com.example.myapplication.Activity.ResetPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.service.SetCodeConfirmationService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText email;
    private EditText emailRecuperation;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        toolbar = findViewById(R.id.toolbar);
        email = findViewById(R.id.editTextText4);
        emailRecuperation = findViewById(R.id.editTextText5);
        button = findViewById(R.id.button2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->resetPassword());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void resetPassword(){
        String eemail = email.getText().toString();
        String emailRecu = emailRecuperation.getText().toString();
        SetCodeConfirmationService service = RetrofitClient.getRetrofitInsantce().create(SetCodeConfirmationService.class);
        Call<String> call = service.sendCode(eemail,emailRecu);
        if (eemail.isEmpty()){
            email.setError("entrer votre email");
            return;
        }
        if (emailRecu.isEmpty()){
            emailRecuperation.setError("entrer votre email de recuperation");
            return;
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    Intent i = new Intent(ResetPassword.this,Confirmation_Code.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(ResetPassword.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}