package com.example.myapplication.Activity.ManageAccount;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.FriendService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlockedFriendsActivity extends AppCompatActivity implements BlockFriendListnner {
    private  RecyclerView recyclerView;
    private BlockedFriendAdapter blockedFriendAdapter;
    private Long id;
    List<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blocked_friends_activity);
        recyclerView = findViewById(R.id.BlockedRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        getBlockedFriends();

    }

    public void getBlockedFriends(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<List<User>> call = friendService.getBlockedFriend(id);

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null){
                    users = response.body();
                     if (users.size() > 0){
                         blockedFriendAdapter = new BlockedFriendAdapter(users,BlockedFriendsActivity.this);
                         recyclerView.setAdapter(blockedFriendAdapter);
                     }else {
                         setContentView(R.layout.notfound);
                     }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(BlockedFriendsActivity.this, "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void unblockFriend(long followerId, long followedId,int position) {
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<ResponseBody> call = friendService.unblockFriend(followerId, followedId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null){
                    blockedFriendAdapter.updateList(position);
                    if (users.size() == 0){
                        setContentView(R.layout.notfound);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}