package com.example.myapplication.Activity.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Calendar;

public class TeleDateActivity extends AppCompatActivity {
    private EditText editdate;
    private  EditText phoneNumber;
    private Toolbar toolbar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_date);
        editdate = findViewById(R.id.date);
        toolbar = findViewById(R.id.toolbar);
        phoneNumber= findViewById(R.id.phoneNumber);
        button =findViewById(R.id.Tbutton);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        button.setOnClickListener(view->setTeleDate());

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


    public void setTeleDate(){
        Intent namePassword = getIntent();
        String Firstname = namePassword.getStringExtra("Firstname");
        String Lastname = namePassword.getStringExtra("Lastname");
        String phoneN = phoneNumber.getText().toString();
        String date = editdate.getText().toString();
        if (phoneN.isEmpty()){
            phoneNumber.setError("phone number is required");
        } else if (date.isEmpty()) {
            editdate.setError("birth date is required");
        }else {
            Intent intent =new Intent(this, EmailActivity.class);
            intent.putExtra("date",date);
            intent.putExtra("phoneNumber",phoneN);
            intent.putExtra("Firstname",Firstname);
            intent.putExtra("Lastname",Lastname);
            startActivity(intent);
        }


    }

    public void ShowCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int mounth =calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editdate.setText(year+"-"+(month+1)+"-"+dayOfMonth);
            }
        },year,mounth,day);
        datePickerDialog.show();
    }
}