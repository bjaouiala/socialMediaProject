package com.example.myapplication.Activity.ManageFriends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Activity.ManageHome.HomeActivity;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.displayPicture;
import com.example.myapplication.services.FriendService;
import com.example.myapplication.services.PostService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendProfileActivity extends AppCompatActivity implements OnFriendProfileClick {
    private RecyclerView recyclerView;
    private FriendProfileAdapter friendProfileAdapter;
    private List<PostResponse> postResponses = new ArrayList<>();
    private User user;
    private PostResponse postResponse;
    private long followerid;
    private long followedid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent intent = getIntent();
        followerid = intent.getLongExtra("followerid",0);
        followedid = intent.getLongExtra("followedid",0);
        recyclerView = findViewById(R.id.Friendrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getFriendProfileHeader();
    }

    public void getFriendProfileHeader(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<User> call = friendService.getFriendProfile(followedid,followerid);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null){
                    user = response.body();
                    getFriendPosts();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(FriendProfileActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void getFriendPosts(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<List<PostResponse>> call = postService.getAllPosts(followerid);
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                postResponses = response.body();
                if (postResponses.size()!=0 && user !=null){
                    friendProfileAdapter = new FriendProfileAdapter(postResponses,user,FriendProfileActivity.this,followedid,followerid);
                    recyclerView.setAdapter(friendProfileAdapter);

                }else if (postResponses.size() == 0 && postResponses != null && user != null){
                    friendProfileAdapter = new FriendProfileAdapter(postResponses,user,FriendProfileActivity.this,followedid,followerid);
                    recyclerView.setAdapter(friendProfileAdapter);

            }}

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {

            }
        });
    }
    public void cancelFriendRequest(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<ResponseBody> call = friendService.cancelFriendRequest(followedid,followerid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(FriendProfileActivity.this, "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void addFriend(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<ResponseBody> call = friendService.addFriend(followedid,followerid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()&& response.body() != null){
                    friendProfileAdapter.updateFollowers(followerid,followedid);
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(FriendProfileActivity.this, "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void displayPicture(long id) {
        Intent intent = new Intent(this, displayPicture.class);
        intent.putExtra("postId",id);
        startActivity(intent);


    }
    @Override
    public void onFriendStateChange(String state) {
        if (state.equals("PENDING")){
            if (followerid == user.getFollowedId() && followedid == user.getFollowerId()){
                startFragment();
            } else if (followerid == user.getFollowerId() && followedid == user.getFollowedId()) {
                cancelFriendRequest();
                friendProfileAdapter.updateDataSet(null);
                friendProfileAdapter.notifyItemChanged(0);
            }
        } else if (state.isEmpty()) {
            addFriend();
            friendProfileAdapter.updateDataSet("PENDING");

            } else if (state.equals("ACCEPTED")) {
            startFragmentAcceptedFriend();

        }

    }

    public void startFragmentAcceptedFriend(){
        AcceptedFriendButtomSheetFragment acceptedFriendButtomSheetFragment = new AcceptedFriendButtomSheetFragment();
        acceptedFriendButtomSheetFragment.show(getSupportFragmentManager(),acceptedFriendButtomSheetFragment.getTag());
        acceptedFriendButtomSheetFragment.setContext(this);
        acceptedFriendButtomSheetFragment.setFollowedid(followedid);
        acceptedFriendButtomSheetFragment.setFollowerid(followerid);
        acceptedFriendButtomSheetFragment.setOnFriendStateChangeListnner(new OnFriendProfileClick() {
            @Override
            public void onFriendStateChange(String state) {
                friendProfileAdapter.updateDataSet(state);
                if (state.equals("BLOCKED")){
                    Intent intent = new Intent(FriendProfileActivity.this, HomeActivity.class);
                    intent.putExtra("id",followedid);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }



    public void startFragment(){
        FriendProfileFragment friendProfileFragment = new FriendProfileFragment();
        friendProfileFragment.show(getSupportFragmentManager(),friendProfileFragment.getTag());
        friendProfileFragment.setContext(this);
        friendProfileFragment.setFollowedid(followedid);
        friendProfileFragment.setFollowerid(followerid);
        friendProfileFragment.setOnFriendStateChangeListnner(new OnFriendProfileClick() {
            @Override
            public void onFriendStateChange(String state) {
                friendProfileAdapter.updateDataSet(state);
            }
        });
    }
}