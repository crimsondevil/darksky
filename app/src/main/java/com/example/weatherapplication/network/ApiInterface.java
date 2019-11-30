package com.example.weatherapplication.network;

import com.example.weatherapplication.network.Model.User;
import com.example.weatherapplication.network.Model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("currloc")
    Call<Weather> getData();
}
