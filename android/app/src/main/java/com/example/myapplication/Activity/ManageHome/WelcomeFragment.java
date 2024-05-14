package com.example.myapplication.Activity.ManageHome;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Activity.ManageFriends.FriendProfileActivity;
import com.example.myapplication.Activity.ManagePost.ButtomSheetFragement;
import com.example.myapplication.Activity.ManagePost.RemovePostListnner;
import com.example.myapplication.Activity.ManageProfile.ConfirmUpload;
import com.example.myapplication.Activity.ManageProfile.ImageSelectionListnner;
import com.example.myapplication.Activity.ManageProfile.ProfileActivity;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.displayPicture;
import com.example.myapplication.services.FriendService;
import com.example.myapplication.services.LikeService;
import com.example.myapplication.services.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeFragment extends Fragment implements HomeListnner {
    private HomeAdapter homeAdapter;
    private User user;
    private long id;
    private RecyclerView recyclerView;
    private List<PostResponse> posts = new ArrayList<>();
    private final static int REQUEST_CODE = 1;
    private long item=1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome,container,false);
        recyclerView = view.findViewById(R.id.pubRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        id = bundle.getLong("id",id);

        getUser();
        return view;
    }

    public void getUser(){
        UserService userService = RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<User> call = userService.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null){
                    user = response.body();
                    getFriendPosts();

                }else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void listMenuItem(int position, long postId) {
        Bundle bundle = new Bundle();
        bundle.putLong("userId",id);
        bundle.putLong("postId",postId);
        bundle.putLong("position",position);
        bundle.putLong("item",item);
        ButtomSheetFragement buttomSheetFragement = new ButtomSheetFragement();
        buttomSheetFragement.setArguments(bundle);
        buttomSheetFragement.show(getActivity().getSupportFragmentManager(), buttomSheetFragement.getTag());
        buttomSheetFragement.setOnRemovePostListnner(new RemovePostListnner() {
            @Override
            public void getRemovedPostPosition(int position) {
                homeAdapter.removePost(position);
            }
        });

    }

    @Override
    public void like(long userId, long postId, View itemView) {
        LikeService likeService = RetrofitClient.getRetrofitInsantce().create(LikeService.class);
        Call<Long> call = likeService.like(id,postId);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful() && response.body() != null){
                    long like = response.body();
                    if (like == 0){
                        Button button =  itemView.findViewById(R.id.jaime);
                        button.setTextColor(getResources().getColor(R.color.blue, Resources.getSystem().newTheme()));
                        Drawable img = getContext().getResources().getDrawable(R.drawable.bluelike);
                        button.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    }else {
                        Button button =  itemView.findViewById(R.id.jaime);
                        button.setTextColor(getResources().getColor(R.color.black, Resources.getSystem().newTheme()));
                        Drawable img = getContext().getResources().getDrawable(R.drawable.jaime);
                        button.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    }

                }


            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });

    }



    public void getFriendPosts(){
        FriendService friendService= RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<List<PostResponse>> call = friendService.getFriendpPosts(id);
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful() && response.body() != null){
                    posts = response.body();
                    Log.d("postsSize",""+posts.size());
                    if (posts.size()>0 && user != null){
                        homeAdapter = new HomeAdapter(posts,user,WelcomeFragment.this,id);
                        recyclerView.setAdapter(homeAdapter);
                    }else if (posts.size() == 0 && posts != null && user != null){
                        homeAdapter = new HomeAdapter(posts,user,WelcomeFragment.this,id);
                        recyclerView.setAdapter(homeAdapter);
                    }


                }else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(getContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void pubText() {
        Intent intent = new Intent(getContext(), TextPubActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("picture","");
        startActivity(intent);
    }

    @Override
    public void pubPicture() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            Uri uri = data.getData();
            Intent intent = new Intent(getContext(), ConfirmUpload.class);
            intent.putExtra("id",id);
            intent.putExtra("picture","");
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @Override
    public void displayPicture(long id) {
        Intent intent = new Intent(getContext(), displayPicture.class);
        intent.putExtra("postId",id);
        startActivity(intent);
    }

    @Override
    public void getprofile() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void listMenuItem() {

    }

    @Override
    public void getFriendProfile(long followerid,long followedid) {
        Log.d("ids",followedid+" "+followerid);
        Intent intent;
        if (followerid == followedid){
            intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("id",followerid);
            startActivity(intent);
        }else{
            intent = new Intent(getContext(), FriendProfileActivity.class);
            intent.putExtra("followerid",followerid);
            intent.putExtra("followedid",followedid);
            startActivity(intent);

        }
    }


}