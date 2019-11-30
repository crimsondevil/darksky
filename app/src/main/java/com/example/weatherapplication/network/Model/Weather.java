
package com.example.weatherapplication.network.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("currently")
    @Expose
    private Currently currently;
    @SerializedName("daily")
    @Expose
    private Daily daily;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Currently getCurrently() {
        return currently;
    }

    public void setCurrently(Currently currently) {
        this.currently = currently;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
