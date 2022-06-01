package com.moringaschool.weatherapi.network;

import com.moringaschool.weatherapi.models.Weather;
import com.moringaschool.weatherapi.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("/weather")
    Call<WeatherResponse> getCurrentWeather(@Query("q") String city);
}