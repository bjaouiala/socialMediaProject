package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GenreActivity extends AppCompatActivity {
    private Toolbar toolbar ;
    private RadioButton femme;
    private RadioButton homme;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
      toolbar =findViewById(R.id.toolbar);
      femme = findViewById(R.id.femme);
      homme = findViewById(R.id.homme);
      button= findViewById(R.id.Gbutton);
      setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view -> setGenre());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    public void setGenre() {
        String genre ="" ;
        Intent intent = new Intent(this, PasswordActivity.class);
        Intent i = getIntent();

            String Firstname = i.getStringExtra("Firstname");
            String Lastname = i.getStringExtra("Lastname");
            String date = i.getStringExtra("date");
            String phoneNumber = i.getStringExtra("phoneNumber");
            String email = i.getStringExtra("email");
            String emailRecuperation =  i.getStringExtra("emailRecuperation");

        if (femme.isChecked()){
            genre = femme.getText().toString();
            intent.putExtra("genre",genre);
            intent.putExtra("date",date);
            intent.putExtra("phoneNumber",phoneNumber);
            intent.putExtra("Firstname",Firstname);
            intent.putExtra("Lastname",Lastname);
            intent.putExtra("email",email);
            intent.putExtra("emailRecuperation",emailRecuperation);
            startActivity(intent);
        }else if(homme.isChecked()){
            genre = homme.getText().toString();
            intent.putExtra("genre",genre);
            intent.putExtra("date",date);
            intent.putExtra("phoneNumber",phoneNumber);
            intent.putExtra("Firstname",Firstname);
            intent.putExtra("Lastname",Lastname);
            intent.putExtra("email",email);
            intent.putExtra("emailRecuperation",emailRecuperation);

            startActivity(intent);
        }else Toast.makeText(this, "selectionner votre genre", Toast.LENGTH_SHORT).show();





    }
}