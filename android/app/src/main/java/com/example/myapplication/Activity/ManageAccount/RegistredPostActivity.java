package com.example.myapplication.Activity.ManageAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.PostService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistredPostActivity extends AppCompatActivity implements RemovePostListnner {
    private RegistredPostAdapter registredPostAdapter;
    private RecyclerView recyclerView;
    private Long id;
    List<PostResponse> postResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred_post);
        recyclerView = findViewById(R.id.Registredrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        getRegistredPosts();
    }

    public void getRegistredPosts(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<List<PostResponse>> call = postService.getAllRegistredPosts(id);

        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful() && response.body() != null){
                    postResponses = response.body();
                    if (postResponses.size() > 0){
                        registredPostAdapter = new RegistredPostAdapter(postResponses,RegistredPostActivity.this);
                        recyclerView.setAdapter(registredPostAdapter);
                    }else {
                        setContentView(R.layout.nopicturefound);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void removePost(long userId, long postId, int position) {
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.unsavePost(userId,postId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null){
                    registredPostAdapter.updatePostList(position);
                    if (postResponses.size() == 0){
                        setContentView(R.layout.nopicturefound);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}