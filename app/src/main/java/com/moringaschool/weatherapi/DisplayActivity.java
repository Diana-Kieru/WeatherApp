package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {
    private DisplayAdapter mDisplayAdapter;
    private List<Weather> mList;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.coordid) TextView mcoordinates;
    @BindView(R.id.tempid) TextView mTemp;
    @BindView(R.id.pressureid) TextView mpressure;
    @BindView(R.id.humidityid) TextView mhumidity;
    @BindView(R.id.weatherid) TextView mweather;
    @BindView(R.id.cloudsid) TextView mcloud;
    @BindView(R.id.rainid) TextView mrain;


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
                    mcoordinates.setText("Coodinates "+ " "+response.body().getCoord().getLat().toString() + " " + response.body().getCoord().getLon().toString());
                    mTemp.setText("Temperature " + " "+response.body().getMain().getTemp().toString());
                    mpressure.setText("Pressure "+ " "+response.body().getMain().getPressure().toString());
                    mhumidity.setText("Humidity "+ " "+response.body().getMain().getHumidity().toString());
                    mcloud.setText("Cloud " + " "+response.body().getClouds().getAll().toString());
                    mrain.setText("Rain " + " "+response.body().getMain().getFeelsLike().toString());

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

    @Override
    public void onClick(View view) {

    }
}