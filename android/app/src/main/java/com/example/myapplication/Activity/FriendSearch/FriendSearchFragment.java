package com.example.myapplication.Activity.FriendSearch;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activity.ManageFriends.FriendProfileActivity;
import com.example.myapplication.Activity.ManageProfile.ProfileActivity;
import com.example.myapplication.Activity.Register.VerifyCompte;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendSearchFragment extends Fragment implements FriendSearchAction {
    private Toolbar toolbar;
    private EditText editText;
    private RecyclerView recyclerView ;
    private List<User> users = new ArrayList<>();
    private FriendSearchAdapter friendSearchAdapter;
    private long followedid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_search,container,false);
        toolbar= view.findViewById(R.id.toolbar3);
        editText = view.findViewById(R.id.searchText);
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendSearchAdapter = new FriendSearchAdapter(users,this);
        recyclerView.setAdapter(friendSearchAdapter);
        Bundle bundle = getArguments();
        followedid = bundle.getLong("id");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("");
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                friendSearchRequest(query);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    public void friendSearchRequest(String friend){
        UserService userService = RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<List<User>> call = userService.searchUsers(followedid,friend);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (users != null){
                        users = response.body();
                        friendSearchAdapter.Search(users);
                        friendSearchAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(getContext(), "not found", Toast.LENGTH_SHORT).show();
                    }}

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("onFailure", "Network error", t);
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void getFriendProfile(Long followerid) {
        Intent intent ;
        Log.d("id",followerid+" "+followedid);
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

    @Override
    public void displayPicture() {

    }
}