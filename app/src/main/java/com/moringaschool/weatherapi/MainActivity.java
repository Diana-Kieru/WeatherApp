package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.moringaschool.weatherapi.models.WeatherResponse;
import com.moringaschool.weatherapi.network.WeatherApi;
import com.moringaschool.weatherapi.network.WeatherClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    @BindView(R.id.city)
    TextInputLayout mCity;
    @BindView(R.id.daily) Button dailyButton;
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);


        ButterKnife.bind(this);

        dailyButton.setOnClickListener((View.OnClickListener) this);

        button.setOnClickListener((View.OnClickListener) this);

    }
            @Override
            public void onClick(View v) {
                if (v == button) {
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("city", mCity.getEditText().getText().toString());
                    startActivity(intent);


                }
                if (v == dailyButton) {
                    Intent intent = new Intent(MainActivity.this, DailyWeather.class);
                    intent.putExtra("city", mCity.getEditText().getText().toString());
                    startActivity(intent);


                }


            }





    public void onResume() {


        super.onResume();

    }


}