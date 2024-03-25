package com.example.myapplication.Activity.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCompte extends AppCompatActivity {

    private EditText code;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_compte);
        code = findViewById(R.id.code);
        button = findViewById(R.id.envoyer);

        button.setOnClickListener(view->verifyAccount());
    }



    public void verifyAccount(){
        String CodeConfirmation = code.getText().toString();
        AuthService authService = RetrofitClient.getRetrofitInsantce().create(AuthService.class);
        Call<String> call = authService.verifyAccount(CodeConfirmation);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    Intent i = new Intent(VerifyCompte.this, LoginActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(VerifyCompte.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}