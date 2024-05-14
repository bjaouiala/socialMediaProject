package com.example.myapplication.services;

import com.example.myapplication.Model.FriendRequest;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FriendService {
    @GET("/friend/friendPosts/{followerid}/{followedid}")
    Call<User> getFriendProfile(@Path("followedid") long followedid,@Path("followerid") long followerid);
    @POST("/friend/{followerid}/{followedid}")
    Call<ResponseBody> addFriend(@Path("followedid") long followedId,
                                 @Path("followerid") long followerId);

    @DELETE("/friend/{followerid}/{followedid}")
    Call<ResponseBody> cancelFriendRequest(
            @Path("followedid") long followedId,
            @Path("followerid") long followerId);
    @PUT("/friend/{followerid}/{followedid}")
    Call<ResponseBody> acceptFriendRequest(
            @Path("followerid") long followerId,
            @Path("followedid") long followedId);
    @PUT("/friend/blockFriend/{followerid}/{followedid}")
    Call<ResponseBody> blockFriend( @Path("followedid")long followedid,@Path("followerid")long followerid);
    @GET("/friend/{userId}")
    Call<List<PostResponse>> getFriendpPosts(@Path("userId")long userId );
    @GET("/friend/AcceptedFriends/{userId}")
    Call<List<User>> getFriend(@Path("userId")long userId );
    @GET("friend/friendRequest/{userId}")
    Call<List<FriendRequest>> getFriendsRequests(@Path("userId")long userId );

    @GET("friend/blockedFriend/{id}")
    Call<List<User>> getBlockedFriend(@Path("id") long id);
    @DELETE("friend/unblockFriend/{followerid}/{followedid}")
    Call<ResponseBody> unblockFriend(@Path("followerid")long followerid,@Path("followedid")long followedid);
}
