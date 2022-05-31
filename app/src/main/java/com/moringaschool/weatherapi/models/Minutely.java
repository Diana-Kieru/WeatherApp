package com.moringaschool.weatherapi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Minutely {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("precipitation")
    @Expose
    private Integer precipitation;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Minutely() {
    }

    /**
     * 
     * @param dt
     * @param precipitation
     */
    public Minutely(Integer dt, Integer precipitation) {
        super();
        this.dt = dt;
        this.precipitation = precipitation;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Integer getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Integer precipitation) {
        this.precipitation = precipitation;
    }

}
