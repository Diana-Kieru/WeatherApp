package com.moringaschool.weatherapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.weatherapi.R;
import com.moringaschool.weatherapi.models.Daily;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.PlaceViewHolder> {
    private Context mContext;
    private List<Daily> dailies;

    public DailyAdapter(Context mContext, List<Daily> dailies) {
        this.mContext = mContext;
        this.dailies = dailies;
    }

    @NonNull
    @Override
    public DailyAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.displaydaily, parent, false);
        PlaceViewHolder placeviewholder = new PlaceViewHolder(view);
        return placeviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.PlaceViewHolder holder, int position) {
holder.bindDaily(dailies.get(position));
    }

    @Override
    public int getItemCount() {
        return dailies.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tempid)
        TextView mTemp;
        @BindView(R.id.pressureid)
        TextView mpressure;
        @BindView(R.id.humidityid)
        TextView mhumidity;
        @BindView(R.id.weatherid)
        TextView mweather;
        @BindView(R.id.cloudsid)
        TextView mcloud;
        @BindView(R.id.rainid)
        TextView mrain;


        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindDaily(Daily daily) {
            mTemp.setText(daily.getTemp().toString());
            mpressure.setText(daily.getPressure());
            mhumidity.setText(daily.getHumidity());
            mcloud.setText(daily.getClouds());
            mrain.setText(daily.getClouds());

        }
    }
}