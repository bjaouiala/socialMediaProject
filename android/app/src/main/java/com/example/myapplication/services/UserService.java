package com.example.myapplication.services;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ManageProfileService {
    @Multipart
    @POST("users/images/{type}/{id}")
    Call<ResponseBody> changeProfilePicture(@Part MultipartBody.Part part, @Path("id") long id, @Path("type") String type );

}
