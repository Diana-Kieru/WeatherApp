package com.moringaschool.weatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
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
    private SharedPreferences mSharedPreferences;
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
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Vollkorn/static/Vollkorn-Black.ttf"); //change this to your own font

        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("Shared Pref Location", mRecentAddress);
       apicall(mRecentAddress);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               if (s == null){
                   return false;
               }

                   addToSharedPreferences(s);
                apicall(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);


    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
    private void apicall(String city){
        WeatherApi weatherApiClient = WeatherClient.getClient();
        Call<WeatherResponse> call = weatherApiClient.getCurrentWeather(city);
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

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    DisplayAdapter displayAdapter = new DisplayAdapter(DisplayActivity.this, mList);
                    mRecyclerView.setAdapter(displayAdapter);
                    Log.d("DisplayActivity", "count" + displayAdapter.getItemCount());





                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

                Log.e("Error Message", "onFailure: ",t);

            }


        });
    }
}