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

import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmation_Code extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText code;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_code);
        toolbar = findViewById(R.id.toolbar);
        code = findViewById(R.id.codeconf);
        button = findViewById(R.id.envoyer);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->verifieCode());
    }

    public void verifieCode(){
        String codeconfirmation = code.getText().toString();
        AuthService authService = RetrofitClient.getRetrofitInsantce().create(AuthService.class);
        Call<User> call = authService.getCodeConfirmation(codeconfirmation);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body()!=null){
                    User user = response.body();
                    if (user.getCodeConfirmation() != null && user.getCodeConfirmation().equals(codeconfirmation)){
                        Intent intent = new Intent(Confirmation_Code.this, SetPassword.class);
                        intent.putExtra("CodeConfirmation",codeconfirmation);
                        startActivity(intent);
                    }else {
                        code.setError("code incorrect");
                    }
                }else Toast.makeText(Confirmation_Code.this, "Code incorrect", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(Confirmation_Code.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });
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
}