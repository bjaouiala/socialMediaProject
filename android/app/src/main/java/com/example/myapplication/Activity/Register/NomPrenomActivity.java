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

public class NomPrenomActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText firstName;
    private EditText lastName;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_prenom);
        toolbar = findViewById(R.id.toolbar);
        firstName =findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        button = findViewById(R.id.namebutton);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->setNameFirstName());


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

    public void setNameFirstName(){
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        if (fName.isEmpty()){
            firstName.setError("first name is required");


        } else if (lName.isEmpty()) {
            lastName.setError("last name is required");

        }else {
          Intent intent = new Intent(this, TeleDateActivity.class);
           intent.putExtra("Firstname",fName);
           intent.putExtra("Lastname",lName);
           startActivity(intent);

        }


    }



}