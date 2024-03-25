package com.example.myapplication.services;

import com.example.myapplication.Model.Post;
import com.example.myapplication.Model.PostResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostService {
    @GET("/post/posts/{id}")
    Call<List<PostResponse>> getAllPosts(@Path("id")long id);
    @GET("/post/{id}")
    Call<PostResponse> getPostById(@Path("id") long id);
    @Multipart
    @POST("/post/{type}/{id}")
    Call<ResponseBody> savePost(@Part MultipartBody.Part part,
                                            @Part("description") RequestBody description,
                                            @Part("dateCreate") RequestBody dateCreate,
                                            @Path("id") long id,
                                            @Path("type") String type );
}
