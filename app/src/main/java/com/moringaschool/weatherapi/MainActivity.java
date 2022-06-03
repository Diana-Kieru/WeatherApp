package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.moringaschool.weatherapi.models.WeatherResponse;
import com.moringaschool.weatherapi.network.WeatherApi;
import com.moringaschool.weatherapi.network.WeatherClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherApi weatherApiClient = WeatherClient.getClient();
                Call<WeatherResponse> call = weatherApiClient.getCurrentWeather("Nairobi");
                call.enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "Result" + response.body().getName());

                        }else {
                            Log.e(TAG, "Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {

                        Log.e("Error Message", "onFailure: ",t);

                    }


                });
            }
        });



    }
    public void onResume() {


        super.onResume();

    }
}