package com.moringaschool.weatherapi.network;

import com.moringaschool.weatherapi.Constants;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
        private static Retrofit retrofit = null;
        public static WeatherApi getClient(){


            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            if(retrofit == null){

                retrofit = new Retrofit.Builder().baseUrl(Constants.WEATHER_BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create()).build();

            }
            return retrofit.create(WeatherApi.class);
        }
}
