package com.example.myapplication.ConfirmAccountService;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @FormUrlEncoded
    @POST("users")
    Call<String> register(@Field("Firstname")String Firstname,
                        @Field("Lastname")String Lastname,
                        @Field("email")String email,
                        @Field("emailRecuperation")String emailRecuperation,
                        @Field("dateBirth")String dateBirth,
                        @Field("gender")String gender,
                        @Field("password")String password,
                        @Field("phoneNumber")String phoneNumber);
}
