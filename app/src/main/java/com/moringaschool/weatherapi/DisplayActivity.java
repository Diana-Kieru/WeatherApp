package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.moringaschool.weatherapi.models.Main;
import com.moringaschool.weatherapi.models.Weather;
import com.moringaschool.weatherapi.models.WeatherResponse;
import com.moringaschool.weatherapi.network.WeatherApi;
import com.moringaschool.weatherapi.network.WeatherClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayActivity extends AppCompatActivity {
    private DisplayAdapter mDisplayAdapter;
    private List<Weather> mList;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Vollkorn/static/Vollkorn-Black.ttf"); //change this to your own font

        ButterKnife.bind(this);

        WeatherApi weatherApiClient = WeatherClient.getClient();
        Call<WeatherResponse> call = weatherApiClient.getCurrentWeather(getIntent().getStringExtra("city"));
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    mList = response.body().getWeather();

                    DisplayAdapter displayAdapter = new DisplayAdapter(DisplayActivity.this, mList);
                    mRecyclerView.setAdapter(displayAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                Log.e("Error Message", "onFailure: ",t);

            }


        });
    }
}