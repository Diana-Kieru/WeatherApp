package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.moringaschool.weatherapi.adapter.DailyAdapter;
import com.moringaschool.weatherapi.models.Daily;
import com.moringaschool.weatherapi.models.DailyWeatherResponse;
import com.moringaschool.weatherapi.models.Weather;
import com.moringaschool.weatherapi.network.WeatherApi;
import com.moringaschool.weatherapi.network.WeatherClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyWeather extends AppCompatActivity {
    private DailyAdapter mDailyAdapter;

    @BindView(R.id.recyclerviewid)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        ButterKnife.bind(this);

        WeatherApi client = WeatherClient.getClient();
        Call<DailyWeatherResponse> call = client.getDailyWeather(getIntent().getStringExtra("city"));

        call.enqueue(new Callback<DailyWeatherResponse>() {
            @Override
            public void onResponse(Call<DailyWeatherResponse> call, Response<DailyWeatherResponse> response) {
                if (response.isSuccessful()) {
                    List<Daily> mList = response.body().getDaily();

                   DailyAdapter dailyAdapter = new DailyAdapter(DailyWeather.this,mList);
                    mRecyclerView.setAdapter(dailyAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                }

            }

            @Override
            public void onFailure(Call<DailyWeatherResponse> call, Throwable t) {

            }
        });
    }
}