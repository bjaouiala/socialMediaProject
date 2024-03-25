package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class displayPicture extends AppCompatActivity {
    private Intent intent;
    private ImageView postImage;
    private Long postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_picture);
        postImage = findViewById(R.id.image);
        intent = getIntent();
        postId = intent.getLongExtra("postId",0);
        getPostById();
    }



void getPostById(){
    PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
    Call<PostResponse> call = postService.getPostById(postId);

    call.enqueue(new Callback<PostResponse>() {
        @Override
        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
            if (response.isSuccessful() && response.body() != null){
                PostResponse postResponse = response.body();
                postImage.setImageBitmap(Helper.parseImage(postResponse.getPostFile()));
            }
        }

        @Override
        public void onFailure(Call<PostResponse> call, Throwable t) {

        }
    });
}}