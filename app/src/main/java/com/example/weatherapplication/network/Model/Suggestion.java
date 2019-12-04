
package com.example.weatherapplication.network.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Suggestion {

    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

}
