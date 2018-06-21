package com.example.beletsky_ma.cinemood;

import com.example.beletsky_ma.cinemood.Model.User;
import com.example.beletsky_ma.cinemood.Model.UsersList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubAPI {


    @GET("users/{username}")
    List<User> getUser(@Path("username") String username);

    @GET("/search/users")
    Call<UsersList> users(@Query("q") String user);
}
