package com.example.myapplication.services;

import com.example.myapplication.Model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

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

    @Multipart
    @POST("users/images/{type}/{id}")
    Call<ResponseBody> changeProfilePicture(@Part MultipartBody.Part part,
                                            @Part("description") RequestBody description,
                                            @Part("dateCreate") RequestBody dateCreate,
                                            @Path("id") long id,
                                            @Path("type") String type );

    @FormUrlEncoded
    @PUT("users/changePassword")
    Call<String> updatePassword(@Field("CodeConfirmation")String CodeConfirmation, @Field("password")String password);

    @FormUrlEncoded
    @PUT("/users")
    Call<User> sendCodeWithEmail(@Field("email")String email, @Field("emailRecuperation")String emailRecuperation);

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") long id);


}
