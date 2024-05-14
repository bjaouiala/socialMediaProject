package com.example.myapplication.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private final static  String Base_Url="http://10.0.2.2:3000/";

    public static Retrofit getRetrofitInsantce(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }return retrofit;
    }
}
