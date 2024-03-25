package com.example.myapplication.services;

import com.example.myapplication.Model.AuthModel;
import com.example.myapplication.Model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthService {
    @FormUrlEncoded
    @POST("auth")
    Call <AuthModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @PUT("auth")
    Call<String> verifyAccount(@Field("CodeConfirmation")String CodeConfirmation);

    @GET("auth/{code}")
    Call<User> getCodeConfirmation(@Path("code") String CodeConfirmation);
}
