package com.example.myapplication.ConfirmAccountService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("auth")
    Call <String> login(@Field("email") String email, @Field("password") String password);
}
