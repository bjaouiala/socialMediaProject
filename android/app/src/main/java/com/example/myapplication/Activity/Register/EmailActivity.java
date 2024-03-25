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

import com.example.myapplication.R;

public class EmailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText email;
    private EditText emailRecuperation;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar) ;
        email = findViewById(R.id.email);
        emailRecuperation = findViewById(R.id.emailR);
        button = findViewById(R.id.Ebutton);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->setEmail());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void setEmail(){
        Intent i = getIntent();
        String emaill = email.getText().toString();
        String emailR = emailRecuperation.getText().toString();
        String Firstname = i.getStringExtra("Firstname");
        String Lastname = i.getStringExtra("Lastname");
        String date = i.getStringExtra("date");
        String phoneNumber = i.getStringExtra("phoneNumber");

        if (emaill.isEmpty()){
            email.setError("email is required");
        }else if (emailR.isEmpty()){
            emailRecuperation.setError("email recuperation is required");
        }else {
            Intent intent = new Intent(this, GenreActivity.class);
            intent.putExtra("email",emaill);
            intent.putExtra("emailRecuperation",emailR);
            intent.putExtra("Firstname",Firstname);
            intent.putExtra("Lastname",Lastname);
            intent.putExtra("date",date);
            intent.putExtra("phoneNumber",phoneNumber);
            startActivity(intent);
        }
    }
}