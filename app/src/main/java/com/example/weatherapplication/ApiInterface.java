package com.example.weatherapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("currloc")
    Call<User> getData();
}
