package com.example.myapplication.Activity.ManageProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.Activity.Register.ConfirmRegister;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.displayPicture;
import com.example.myapplication.services.PostService;
import com.example.myapplication.services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity implements ImageSelectionListnner{
    private Toolbar toolbar;
    private List<PostResponse> postResponses = new ArrayList<>();
    private User user;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private String pictureType;
    private final static int REQUEST_CODE = 1;
    private Uri uri;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        userId = intent.getLongExtra("id",0);
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         getUserProfileData();

    }


    public void getAllpostByUserId(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<List<PostResponse>> call = postService.getAllPosts(userId);
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    postResponses = response.body();
                    if (postResponses.size()!=0 && user !=null){
                        profileAdapter = new ProfileAdapter(user,postResponses,Profile.this);
                        recyclerView.setAdapter(profileAdapter);
                        profileAdapter.notifyDataSetChanged();
                    }else {
                        throw new RuntimeException();
                    }
            }else {
                Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();
            }}
            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(Profile.this, "erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getUserProfileData(){
        UserService userService= RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<User> call = userService.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    user = response.body();
                    getAllpostByUserId();
                }else {
                    Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        }


    @Override
    public void pictureType(String type) {
        pictureType = type;
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==REQUEST_CODE && data != null){
           uri = data.getData();
            Intent userDetails = getIntent();
            long id = userDetails.getLongExtra("id",0);
            Intent intent = new Intent(this, ConfirmUpload.class);
            intent.putExtra("id",id);
            if (pictureType =="photoDeProfile"){
                intent.putExtra("picture","photoDeProfile");
            }else if (pictureType == "photoDeCouverture"){
                intent.putExtra("picture","photoDeCouverture");
            }
            intent.setData(uri);
            startActivity(intent);
        }
    }
    @Override
    public void displayPicture(long id) {
        Intent intent = new Intent(this, displayPicture.class);
        intent.putExtra("postId",id);
        startActivity(intent);

    }
}

