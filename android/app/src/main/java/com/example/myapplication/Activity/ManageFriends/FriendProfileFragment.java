package com.example.myapplication.Activity.ManageFriends;

import android.content.Context;
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

import com.example.myapplication.Activity.ManagePost.ButtomSheetFragement;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.FriendService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendProfileFragment extends ButtomSheetFragement implements View.OnClickListener{
    private OnFriendProfileClick onFriendStateChangeListnner;
    private TextView modifier;
    private TextView supprimer;
    private TextView enregistrer;
    private long followerid;
    private long followedid;
    private Context context;

    public void setOnFriendStateChangeListnner(OnFriendProfileClick onFriendStateChangeListnner) {
        this.onFriendStateChangeListnner = onFriendStateChangeListnner;
    }

    public void setFollowerid(long followerid) {
        this.followerid = followerid;
    }

    public void setFollowedid(long followedid) {
        this.followedid = followedid;
    }





    public void setContext(Context friendProfileActivityContext){
        this.context=friendProfileActivityContext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttom_sheet_dialogue,container,false);
        modifier = view.findViewById(R.id.modifier);
        supprimer = view.findViewById(R.id.supprimer);
        enregistrer = view.findViewById(R.id.enregistrer);
        enregistrer.setVisibility(View.GONE);
        modifier.setText(R.string.AccepterInvtation);
        supprimer.setText(R.string.removeInvitation);
        modifier.setOnClickListener(this);
        supprimer.setOnClickListener(this);
        return view;
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
                Toast.makeText(getContext(), "erreur de connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void acceptFriendRequest(){
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
    public void onClick(View v) {
        if (v.getId() == R.id.supprimer){
            cancelFriendRequest();
            onItemSelect(R.string.Ajouter);
            dismiss();}
        else if (v.getId() == R.id.modifier) {
            acceptFriendRequest();
            onItemSelect(R.string.Amie);
            dismiss();
        }
    }

    private void onItemSelect(int state){
        if (context instanceof FriendProfileActivity){
            FriendProfileActivity friendProfileActivity = (FriendProfileActivity) context;
            Button button = friendProfileActivity.findViewById(R.id.state);
            if (button != null){
                String buttonText = friendProfileActivity.getString(state);
                button.setText(buttonText);
                if (onFriendStateChangeListnner != null){
                    if (state == R.string.Amie){
                        onFriendStateChangeListnner.onFriendStateChange("ACCEPTED");
                    } else if (state == R.string.Ajouter) {
                        onFriendStateChangeListnner.onFriendStateChange(null);
                    }
                }else {
                    throw new IllegalArgumentException("issue");
                }
            }else {
                throw new IllegalArgumentException("buton not found");
            }
        }
    }
}
