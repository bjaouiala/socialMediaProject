package com.example.myapplication.Activity.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class PasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText password;
    private EditText confirmPassword;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        toolbar = findViewById(R.id.toolbar);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm);
        button = findViewById(R.id.Pbutton);
        button.setOnClickListener(view-> setPassword());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
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

    public void setPassword(){
        String pass = password.getText().toString().trim();
        String confirmpass = confirmPassword.getText().toString().trim();

        Intent i  = getIntent();
        String Firstname = i.getStringExtra("Firstname");
        String Lastname = i.getStringExtra("Lastname");
        String date = i.getStringExtra("date");
        String phoneNumber = i.getStringExtra("phoneNumber");
        String email = i.getStringExtra("email");
        String emailRecuperation =  i.getStringExtra("emailRecuperation");
        String genre = i.getStringExtra("genre");
        if (pass.isEmpty()){
            password.setError("entrer votre mot de pass");
            return;
        }if (confirmpass.isEmpty()) {
            confirmPassword.setError("confirmer votre mot de pass");
        return;}

        if (pass.equals(confirmpass)) {
            Intent intent = new Intent(this, ConfirmRegister.class);
            intent.putExtra("password",pass);
            intent.putExtra("genre",genre);
            intent.putExtra("date",date);
            intent.putExtra("phoneNumber",phoneNumber);
            intent.putExtra("Firstname",Firstname);
            intent.putExtra("Lastname",Lastname);
            intent.putExtra("email",email);
            intent.putExtra("emailRecuperation",emailRecuperation);
            startActivity(intent);
        }else {
            Toast.makeText(this, "mot pass incorrect", Toast.LENGTH_SHORT).show();
        }







    }
}