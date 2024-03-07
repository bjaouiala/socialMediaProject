package com.example.myapplication.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;

public interface ConfirmAccountService {
    @FormUrlEncoded
    @PUT("auth")
    Call<String> verifyAccount(@Field("CodeConfirmation")String CodeConfirmation);
}
