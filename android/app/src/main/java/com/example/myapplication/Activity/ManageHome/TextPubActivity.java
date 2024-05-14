package com.example.myapplication.Activity.ManageHome;

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

import com.example.myapplication.Activity.ManageProfile.ProfileActivity;
import com.example.myapplication.Helper;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.PostService;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextPubActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_pub);
        toolbar = findViewById(R.id.toolbar4);
        editText = findViewById(R.id.pubText);
        button = findViewById(R.id.pubButton);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Créer une publication");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        button.setOnClickListener(view -> savepost());
    }

    private void savepost(){
        String pubText = editText.getText().toString();
        String dateString = Helper.createNewDate();
        Intent i = getIntent();
        long id = i.getLongExtra("id", 0);
        String picture = i.getStringExtra("picture");
        RequestBody postdescription = RequestBody.create(MediaType.parse("text/plain"),pubText);
        RequestBody dateCreate = RequestBody.create(MediaType.parse("text/plain"),dateString);


        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.savePostWithoutImage(postdescription,dateCreate,id,picture);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body()!= null){
                    Intent intent = new Intent(TextPubActivity.this,HomeActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }else {
                    Toast.makeText(TextPubActivity.this, "erreur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(TextPubActivity.this, "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}