package com.example.myapplication.Activity.ManageAccount;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.ManageProfile.ProfileActivity;
import com.example.myapplication.Helper;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAccountFragment extends Fragment implements View.OnClickListener {
    private long id;
    private User user;
    private ImageView imageView;
    private TextView textView;
    private ConstraintLayout getProfile,changePassword,changeEmail,changeName,registered,block,friend,savedPost,logout;
    private String argument;
    private FragmentManager saved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account, container,false);
        imageView = view.findViewById(R.id.pic);
        textView = view.findViewById(R.id.username);
        getProfile = view.findViewById(R.id.profileAccount);
        changePassword = view.findViewById(R.id.setpassword);
        changeEmail = view.findViewById(R.id.setemail);
        changeName = view.findViewById(R.id.setname);
        registered = view.findViewById(R.id.saved);
        block = view.findViewById(R.id.blocage);
        friend = view.findViewById(R.id.friendTemplate);
        savedPost = view.findViewById(R.id.saved);
        logout = view.findViewById(R.id.logout);
        Bundle bundle = getArguments();
        id = bundle.getLong("id",id);
        getUserProfileData();
        getProfile.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        changeName.setOnClickListener(this);
        registered.setOnClickListener(this);
        block.setOnClickListener(this);
        friend.setOnClickListener(this);
        savedPost.setOnClickListener(this);
        logout.setOnClickListener(this);
        return view;
    }

    public void getUserProfileData(){
        UserService userService= RetrofitClient.getRetrofitInsantce().create(UserService.class);
        Call<User> call = userService.getUserById(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    user = response.body();
                    Bitmap profilePicture = Helper.parseImage(user.getPhotoDeProfile());
                    if (profilePicture != null){
                        imageView.setImageBitmap(profilePicture);
                    }else {
                        imageView.setImageResource(R.drawable.defaultpicture);
                    }
                    textView.setText(user.getFirstname()+" "+user.getLastname());

                }else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.profileAccount){
            intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        } else if (v.getId() == R.id.setpassword) {
            intent = new Intent(getContext(), settingAccountActivity.class);
            argument = "password";
            intent.putExtra("id",id);
            intent.putExtra("argument",argument);
            startActivity(intent);
        } else if (v.getId() == R.id.setemail) {
            intent = new Intent(getContext(), settingAccountActivity.class);
            argument = "Email";
            intent.putExtra("id",id);
            intent.putExtra("argument",argument);
            startActivity(intent);
        } else if (v.getId() == R.id.setname) {
            intent = new Intent(getContext(), settingAccountActivity.class);
            argument = "name";
            intent.putExtra("id",id);
            intent.putExtra("argument",argument);
            startActivity(intent);

        } else if (v.getId() == R.id.blocage) {
            intent = new Intent(getContext(), BlockedFriendsActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        } else if (v.getId() == R.id.friendTemplate) {
            intent = new Intent(getContext(), FriendsActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        } else if (v.getId() == R.id.saved) {
            intent = new Intent(getContext(),RegistredPostActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        }else if (v.getId() == R.id.logout){
            intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}