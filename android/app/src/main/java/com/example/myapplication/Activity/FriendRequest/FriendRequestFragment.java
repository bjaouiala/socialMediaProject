package com.example.myapplication.Activity.FriendRequest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Model.FriendRequest;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.FriendService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestFragment extends Fragment implements FriendRequestListnner{
    private RecyclerView recyclerView;
    private List<FriendRequest> friendRequests = new ArrayList<>();
    private  FrendRequestAdapter frendRequestAdapter;
    FriendRequestListnner friendRequestListnner;
    private long id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_request,container,false);
        recyclerView = view.findViewById(R.id.friendrequestRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        id = bundle.getLong("id",id);
        getFriendRequest();
        return view;
    }


    public void getFriendRequest(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<List<FriendRequest>> call = friendService.getFriendsRequests(id);
        call.enqueue(new Callback<List<FriendRequest>>() {
            @Override
            public void onResponse(Call<List<FriendRequest>> call, Response<List<FriendRequest>> response) {
                if (response.isSuccessful() && response.body() != null){
                    friendRequests= response.body();
                    frendRequestAdapter = new FrendRequestAdapter(friendRequests,FriendRequestFragment.this);
                    recyclerView.setAdapter(frendRequestAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FriendRequest>> call, Throwable t) {

            }
        });
    }

    @Override
    public void acceptFrienfRequest(long followerid,long followedid) {

            FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
            Call<ResponseBody> call = friendService.acceptFriendRequest(followedid,followerid);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }


    @Override
    public void deleteFrienfRequest(long followerid,long followedid) {
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<ResponseBody> call = friendService.cancelFriendRequest(followedid,followerid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "erreur de connexion", t);
                Toast.makeText(getContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });

    }
}