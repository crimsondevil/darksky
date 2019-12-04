package com.example.weatherapplication.detailActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapplication.R;
import com.example.weatherapplication.network.Model.Daily;
import com.example.weatherapplication.network.Model.Datum;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class WeeklyFragment extends Fragment {

    private Daily dailyData;
    private String summary;
    private String icon;
    private List<ILineDataSet> chartDataSet;
    private TextView summaryTv;
    private ImageView weatherIcon;


    public static Fragment getInstance(Daily daily, String summary, String icon) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Daily", daily);
        bundle.putString("Summary", summary);
        bundle.putString("Icon", icon);
        WeeklyFragment weeklyFragment = new WeeklyFragment();
        weeklyFragment.setArguments(bundle);
        return weeklyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dailyData = (Daily) getArguments().getSerializable("Daily");
        summary = getArguments().getString("Summary");
        icon = getArguments().getString("Icon");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weekly_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        summaryTv = view.findViewById(R.id.summary);
        weatherIcon = view.findViewById(R.id.weather_icon);
        LineChart lineChart = view.findViewById(R.id.lineChart);

        summaryTv.setText(summary);
        setWeatherIcon();

        lineChart.setBackgroundColor(Color.BLACK);
        lineChart.getXAxis().setDrawGridLines(false);
        configureAxisData();
        LineData data = new LineData(chartDataSet);
        lineChart.setData(data);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }

    private void setWeatherIcon() {
        switch (icon) {
            case "clear-night":
                weatherIcon.setImageResource(R.drawable.weather_night);
                break;
            case "rain":
                weatherIcon.setImageResource(R.drawable.weather_rainy);
                break;
            case "sleet":
                weatherIcon.setImageResource(R.drawable.weather_snowy_rainy);
                break;
            case "snow":
                weatherIcon.setImageResource(R.drawable.weather_snowy);
                break;
            case "wind":
                weatherIcon.setImageResource(R.drawable.weather_windy_variant);
                break;
            case "fog":
                weatherIcon.setImageResource(R.drawable.weather_fog);
                break;
            case "cloudy":
                weatherIcon.setImageResource(R.drawable.weather_cloudy);
                break;
            case "partly-cloudy-night":
                weatherIcon.setImageResource(R.drawable.weather_night_partly_cloudy);
                break;
            case "partly-cloudy-day":
                weatherIcon.setImageResource(R.drawable.weather_partly_cloudy);
                break;
            default:
                weatherIcon.setImageResource(R.drawable.weather_sunny);
        }
    }

    private void configureAxisData() {
        List<Entry> lowTempData = new ArrayList<Entry>();
        List<Entry> highTempData = new ArrayList<Entry>();
        List<Datum> data = dailyData.getData();
        float count = 0;
        for (Datum d : data) {
            lowTempData.add(new Entry(count, d.getTemperatureLow().floatValue()));
            highTempData.add(new Entry(count, d.getTemperatureHigh().floatValue()));
            count += 1;
        }
        LineDataSet dataSetLowTemp = new LineDataSet(lowTempData, "Low Temperature");
        LineDataSet dataSetHighTemp = new LineDataSet(highTempData, "High Temperature");
        dataSetHighTemp.setColor(Color.parseColor("#FFB300"));
        dataSetLowTemp.setColor(Color.parseColor("#5E35B1"));
        chartDataSet = new ArrayList<>();
        chartDataSet.add(dataSetLowTemp);
        chartDataSet.add(dataSetHighTemp);
        Log.d("Convertion", "Conversion complete");
    }
}
