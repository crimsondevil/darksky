package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.weatherapplication.network.ApiInterface;
import com.example.weatherapplication.network.Model.Currently;
import com.example.weatherapplication.network.Model.Weather;
import com.example.weatherapplication.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface client;

    private String TAG = "MainActivity";

    private TextView tempTv;
    private TextView summTv;
    private TextView cityTv;
    private TextView pressureTv;
    private TextView humidityTv;
    private TextView windSpeedTv;
    private TextView visibilityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkView();

        fetchDataFromApi();


    }

    private void linkView() {
        tempTv = (TextView) findViewById(R.id.curr_temp);
        summTv = (TextView) findViewById(R.id.curr_summ);
        cityTv = (TextView) findViewById(R.id.curr_city);
        pressureTv = (TextView) findViewById(R.id.pressure_value);
        humidityTv = (TextView) findViewById(R.id.humidity_value);
        windSpeedTv = (TextView) findViewById(R.id.wind_value);
        visibilityTv = (TextView) findViewById(R.id.visibility_value);
    }

    private void fetchDataFromApi() {
        client = NetworkClient.getRetrofit().create(ApiInterface.class);

        client.getData().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                setValueForCurrentView(weather.getCurrently());
                Log.d(TAG, weather.toString());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setValueForCurrentView(Currently currently) {
        tempTv.setText(currently.getTemperature().toString() + " mb");
        summTv.setText(currently.getSummary().toString() + " mb");
        pressureTv.setText(currently.getPressure().toString() + " mb");
        humidityTv.setText(currently.getHumidity().toString() + " %");
        windSpeedTv.setText(currently.getWindSpeed().toString() + " mph");
        visibilityTv.setText(currently.getVisibility().toString() + " km");
    }


}
