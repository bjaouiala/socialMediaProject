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

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPassword extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.Pbutton);
        setSupportActionBar(toolbar);
        editText = findViewById(R.id.password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->updatePassword());
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

    public void updatePassword(){
        String password = editText.getText().toString();
        Intent i = getIntent();
        String CodeConfirmation = i.getStringExtra("CodeConfirmation");
       UserService userService = RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<String> call = userService.updatePassword(CodeConfirmation,password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    Intent i = new Intent(SetPassword.this, LoginActivity.class);
                    startActivity(i);
                }else Toast.makeText(SetPassword.this, "code incorrect", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(SetPassword.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });

    }

}