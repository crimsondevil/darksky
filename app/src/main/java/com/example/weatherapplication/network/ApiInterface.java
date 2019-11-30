package com.example.weatherapplication.network;

import com.example.weatherapplication.network.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("users/{id}")
    Call<User> getUsers(@Path("id")int id);
}
