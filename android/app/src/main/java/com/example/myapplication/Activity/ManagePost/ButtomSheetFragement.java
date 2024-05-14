package com.example.myapplication.Activity.ManagePost;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Activity.ManageHome.HomeListnner;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.PostService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtomSheetFragement extends BottomSheetDialogFragment implements View.OnClickListener{
    private TextView modifie;
    private TextView supprimer;
    private TextView enregistrer;
    long userId;
    long postId;
    int position;
    long item;
    String state;
    RemovePostListnner listnner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttom_sheet_dialogue,container,false);
        enregistrer = view.findViewById(R.id.modifier);
        supprimer = view.findViewById(R.id.supprimer);
        modifie = view.findViewById(R.id.enregistrer);
        modifie.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        userId = bundle.getLong("userId");
        postId = bundle.getLong("postId");
        position = bundle.getInt("position");
        item = bundle.getLong("item",0);
        Log.d("fragmentposition",position+" ");
        getRegistredPost();
        enregistrer.setOnClickListener(this);
        supprimer.setOnClickListener(this);
        Log.d("item",""+item);
        if (item == 1){
            supprimer.setVisibility(View.GONE);
        }

        return view;
    }

    public void getRegistredPost(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<String> call = postService.getrRegistredPost(userId,postId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null){
                     state = response.body();
                    if (state.equals("saved")){
                        enregistrer.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.unsave),null,null,null);
                        enregistrer.setText("Oublier la poste");
                    } else if (state.equals("not saved")) {
                        enregistrer.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.savedd),null,null,null);
                        enregistrer.setText("Enregistrer la poste");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void registerPost(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.registerPost(userId,postId);
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

    public void forgotPost(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.forgotPost(userId,postId);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void deletePost(){
        PostService postService = RetrofitClient.getRetrofitInsantce().create(PostService.class);
        Call<ResponseBody> call = postService.deletePost(userId,postId);

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
            deletePost();
            dismiss();
            if (listnner != null){
                listnner.getRemovedPostPosition(position);
            }


        } else if (v.getId() == R.id.modifier) {
            if (state.equals("not saved")){
                registerPost();
                dismiss();
            } else if (state.equals("saved")) {
                forgotPost();
                dismiss();
            }

        }
    }

    public void setOnRemovePostListnner(RemovePostListnner listnner){
        this.listnner = listnner;
    }
}
