package com.example.weatherapplication.searchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.detailActivity.DetailActivity;
import com.example.weatherapplication.mainActivity.WeeklyAdapter;
import com.example.weatherapplication.network.ApiInterface;
import com.example.weatherapplication.network.Model.Currently;
import com.example.weatherapplication.network.Model.Daily;
import com.example.weatherapplication.network.Model.Weather;
import com.example.weatherapplication.network.NetworkClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ApiInterface client;
    private String city;
    private TextView tempTv;
    private TextView sumTv;
    private TextView cityTv;
    private TextView pressureTv;
    private TextView humidityTv;
    private TextView windSpeedTv;
    private TextView visibilityTv;
    private RecyclerView weeklyStats;
    private WeeklyAdapter weeklyAdapter;
    private ProgressBar progressBar;
    private Button detailButton;

    private Currently currentDetails;
    private Daily dailyDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = NetworkClient.getRetrofit().create(ApiInterface.class);
        linkView();
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SearchActivity.this, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("Currently", currentDetails);
                mBundle.putSerializable("Daily", dailyDataSet);
                mBundle.putString("City", city);
                i.putExtras(mBundle);
                startActivity(i);
            }
        });
        city = getIntent().getExtras().getString("Query");
        setTitle(city);

        setProgressTo(true);
        setRecyclerView();
        fetchWeatherDetails();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This city has been added to favorites.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void linkView() {
        tempTv = findViewById(R.id.curr_temp);
        sumTv = findViewById(R.id.curr_summ);
        cityTv = findViewById(R.id.curr_city);
        pressureTv = findViewById(R.id.pressure_value);
        humidityTv = findViewById(R.id.humidity_value);
        windSpeedTv = findViewById(R.id.wind_value);
        visibilityTv = findViewById(R.id.visibility_value);
        weeklyStats = findViewById(R.id.weekStatsRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        detailButton = findViewById(R.id.summaryDetail);
    }

    private void fetchWeatherDetails() {
        client.getCityWeather(city).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                setProgressTo(false);
                Weather weather = response.body();
                currentDetails = weather.getCurrently();
                dailyDataSet = weather.getDaily();
                setValueForCurrentView(weather.getCurrently());
                addDataToRecycleView(weather);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setValueForCurrentView(Currently currently) {
        tempTv.setText(currently.getTemperature().toString() + " F");
        sumTv.setText(currently.getSummary());
        pressureTv.setText(currently.getPressure().toString() + " mb");
        humidityTv.setText(currently.getHumidity().toString() + " %");
        windSpeedTv.setText(currently.getWindSpeed().toString() + " mph");
        visibilityTv.setText(currently.getVisibility().toString() + " km");
        cityTv.setText(city);
    }

    private void addDataToRecycleView(Weather weather) {
        weeklyAdapter.setDataList(weather.getDaily().getData());
    }


    private void setProgressTo(boolean visible) {
        if (visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        weeklyStats.setLayoutManager(new LinearLayoutManager(this));
        weeklyAdapter = new WeeklyAdapter();
        weeklyStats.setAdapter(weeklyAdapter);
    }
}
