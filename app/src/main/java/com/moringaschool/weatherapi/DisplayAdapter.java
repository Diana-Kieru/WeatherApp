package com.moringaschool.weatherapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.moringaschool.weatherapi.models.Main;
import com.moringaschool.weatherapi.models.Weather;
import com.moringaschool.weatherapi.models.WeatherResponse;
import com.moringaschool.weatherapi.utils.ItemTouchHelperViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.DisplayViewHolder> implements ItemTouchHelperViewHolder {
    private Context mContext;
    private List<Weather> weatherList;


    public DisplayAdapter(Context mContext, List<Weather> weatherList) {
        this.mContext = mContext;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public DisplayAdapter.DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displayrecyclerview, parent, false);
        DisplayViewHolder displayViewHolder = new DisplayViewHolder(view);

        return displayViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayAdapter.DisplayViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.bindWeather(weatherList.get(position));
        holder.itemView.startAnimation(animation);


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    @Override
    public void onItemSelected() {


    }

    @Override
    public void onItemClear() {

    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.temperature)
        TextView mTemeperature;
        @BindView(R.id.pressure)
        TextView mPressure;
        @BindView(R.id.humidity)
        TextView mHumidity;

        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindWeather(Weather weather) {
            mTemeperature.setText(weather.getMain());
            mPressure.setText(weather.getDescription());
            mHumidity.setText(weather.getIcon());
        }
        //PROGRAMMATIC ANIMATIONS

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
    }
}
