package com.example.myapplication.Activity.ManageAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activity.ManageHome.HomeActivity;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class settingAccountActivity extends AppCompatActivity {
    private EditText field1,field2;
    private Button button;
    private long id;
    private String argument;
    private User user= new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeting_account);
        field1 = findViewById(R.id.field1);
        field2 = findViewById(R.id.field2);
        button = findViewById(R.id.send);
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        argument = intent.getStringExtra("argument");
        if (argument.equals("password")){
            field1.setHint("Mot de passe");
            field1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            field2.setHint("Confirmer mot de passe");
            field2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (argument.equals("Email")) {
            field1.setHint("Adresse Email");
            field1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            field2.setVisibility(View.GONE);
        } else if (argument.equals("name")) {
            field1.setHint("Entrer votre nom");
            field2.setVisibility(View.GONE);
        }
        button.setOnClickListener(view-> updateAccount());

    }

    private void updateAccount(){
        if (argument.equals("password")){
            password();
        } else if (argument.equals("Email")) {
            Email();
        } else if (argument.equals("name")) {
            name();
        }
    }

    public void password(){
        String pass =field1.getText().toString();
        String confirmPass=field2.getText().toString();
        if (pass.isEmpty()){
            field1.setError("entrer votre mot de pass");
            return;
        }
        if (confirmPass.isEmpty()){
            field2.setError("confirmer votre mot de pass");
            return;
        }
        if (pass.equals(confirmPass)){
            user.setPassword(pass);
            updateAccountRequest(user);
            Toast.makeText(settingAccountActivity.this, "Mot de pass a étéchangé", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(settingAccountActivity.this, "Verifié votre mot de pass", Toast.LENGTH_SHORT).show();
        }
    }

    public void Email(){
            String Email = field1.getText().toString();
            if (Email.isEmpty()){
                field1.setError("entrer votre adress email");
                return;
            }
            user.setEmail(Email);
            Toast.makeText(settingAccountActivity.this, "Email a été changé", Toast.LENGTH_SHORT).show();
            updateAccountRequest(user);

    }
    public void name(){
        String name = field1.getText().toString();
        if (name.isEmpty()){
            Toast.makeText(this, "entrer votre nom", Toast.LENGTH_SHORT).show();
            return;
        }
        int index = name.indexOf(" ");
        if (index != -1){
            String firstName = name.substring(0,name.indexOf(" "));
            String lastName = name.substring(name.indexOf(" ")+1);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            Toast.makeText(settingAccountActivity.this, "nom a été changé", Toast.LENGTH_SHORT).show();
            updateAccountRequest(user);
        }else {
            user.setFirstname(name);
            updateAccountRequest(user);
        }


    }

    public void updateAccountRequest(User user1){
        UserService userService = RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<ResponseBody> call = userService.upadateAccount(id,user1.getPassword(),user1.getFirstname(),user1.getLastname()
        ,user1.getEmail());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body()!= null){
                    Intent intent = new Intent(settingAccountActivity.this, HomeActivity.class);
                    intent.putExtra("id",id);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(settingAccountActivity.this, "Network error", Toast.LENGTH_SHORT).show();

            }
        });


    }


}