package com.moringaschool.weatherapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.weatherapi.models.WeatherResponse;
import com.moringaschool.weatherapi.network.WeatherApi;
import com.moringaschool.weatherapi.network.WeatherClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
   @BindView(R.id.button) Button mButton;
    @BindView(R.id.city)
    TextInputLayout mCity;

    private DatabaseReference mSearchedCity;
    private ValueEventListener mSearchedCityReferenceListener;

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSearchedCity = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.API_SEARCHED_LOCATION);

        mSearchedCityReferenceListener = mSearchedCity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot: snapshot.getChildren()){
                    String city = shot.getValue().toString();
                    Log.d("Cities", "Cities: " + city);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();



        mButton.setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
            if (v == mButton) {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String city = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
                if (city == null || city.equals("")){
                    city = "Nairobi";
                    addToSharedPreferences(city);
                }



                saveToFirebase(city);
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("city", city);
                startActivity(intent);


            }


        }

        public void saveToFirebase(String city){
        mSearchedCity.push().setValue(city);
        }





    public void onResume() {


        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedCity.removeEventListener(mSearchedCityReferenceListener);
    }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }
}