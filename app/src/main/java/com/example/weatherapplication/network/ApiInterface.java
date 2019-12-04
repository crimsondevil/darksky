package com.example.weatherapplication.network;

import com.example.weatherapplication.network.Model.GoogleImageModel;
import com.example.weatherapplication.network.Model.IpApi;
import com.example.weatherapplication.network.Model.Suggestion;
import com.example.weatherapplication.network.Model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("currloc")
    Call<Weather> getData(@Query("lat") Double latitude, @Query("lon") Double longitude);

    @GET("search")
    Call<Weather> getCityWeather(@Query("addr") String city);

    @GET("ctyimg")
    Call<GoogleImageModel> getImageFor(@Query("cty") String city);

    @GET
    Call<IpApi> getLocationData(@Url String url);

    @GET("autocmp")
    Call<Suggestion> getPrediction(@Query("str") String location);

}
