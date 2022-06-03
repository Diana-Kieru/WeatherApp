package com.moringaschool.weatherapi.network;

import com.moringaschool.weatherapi.Constants;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
        private static Retrofit retrofit = null;
        public static WeatherApi getClient(){


            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            if(retrofit == null){
//            HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
//            urlBuilder.addQueryParameter(Constants.API_KEY_QUERY_PARAMETER, Constants.WEATHER_API_KEY);
//            urlBuilder.addQueryParameter("&units", "metric");
                retrofit = new Retrofit.Builder().baseUrl(Constants.WEATHER_BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create()).build();

            }
            return retrofit.create(WeatherApi.class);
        }
}
