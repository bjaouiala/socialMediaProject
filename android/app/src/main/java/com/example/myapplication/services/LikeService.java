package com.example.myapplication.services;

import com.example.myapplication.Model.LikeResponse;
import com.example.myapplication.Model.PostResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LikeService {
    @POST("like/{userId}/{postId}")
    Call<Long> like(@Path("userId")long userId, @Path("postId")long postId);
    @GET("like/{userId}")
    Call<PostResponse> count(@Path("userId")long userId);
}
