package com.example.myapplication.Activity.ManageFriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activity.ManageHome.HomeActivity;
import com.example.myapplication.Activity.ManagePost.ButtomSheetFragement;
import com.example.myapplication.Activity.ManageProfile.ProfileActivity;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.FriendService;
import com.example.myapplication.services.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedFriendButtomSheetFragment extends ButtomSheetFragement implements View.OnClickListener {
    private TextView modifier;
    private TextView supprimer;
    private TextView enregistrer;
    private Context context;

    private long followerid;
    private long followedid;
    private OnFriendProfileClick onFriendStateChangeListnner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttom_sheet_dialogue,container,false);
        modifier = view.findViewById(R.id.modifier);
        supprimer = view.findViewById(R.id.supprimer);
        enregistrer = view.findViewById(R.id.enregistrer);
        enregistrer.setVisibility(View.GONE);
        modifier.setText(R.string.block);
        supprimer.setText(R.string.retirer);
        modifier.setCompoundDrawablesWithIntrinsicBounds(R.drawable.block,0,0,0);
        modifier.setOnClickListener(this);
        supprimer.setOnClickListener(this);
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setFollowerid(long followerid) {
        this.followerid = followerid;
    }

    public void setFollowedid(long followedid) {
        this.followedid = followedid;
    }

    public void setOnFriendStateChangeListnner(OnFriendProfileClick onFriendStateChangeListnner) {
        this.onFriendStateChangeListnner = onFriendStateChangeListnner;
    }

    public void blockFriend(){
        FriendService friendService = RetrofitClient.getRetrofitInsantce().create(FriendService.class);
        Call<ResponseBody> call = friendService.blockFriend(followedid,followerid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null){

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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


            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.modifier){
            blockFriend();
            onItemSelect(R.string.blocked);
            dismiss();
        } else if (v.getId() == R.id.supprimer) {
            cancelFriendRequest();
            onItemSelect(R.string.Ajouter);
            dismiss();
        }
    }

    public void onItemSelect(int state){
        if (context instanceof FriendProfileActivity){
            FriendProfileActivity friendProfileActivity = (FriendProfileActivity) context;
            Button button = friendProfileActivity.findViewById(R.id.state);
           String buttontxt = friendProfileActivity.getString(state);
           button.setText(buttontxt);
            if (onFriendStateChangeListnner != null){
                if (state == R.string.Ajouter){
                    onFriendStateChangeListnner.onFriendStateChange(null);
                } else if (state == R.string.blocked) {
                    onFriendStateChangeListnner.onFriendStateChange("BLOCKED");
                }
            }
        }
    }
}
