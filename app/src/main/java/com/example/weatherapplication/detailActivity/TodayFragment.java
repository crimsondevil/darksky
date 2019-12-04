package com.example.weatherapplication.detailActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapplication.R;
import com.example.weatherapplication.network.Model.Currently;

import java.util.Locale;

public class TodayFragment extends Fragment {

    private Currently currently;

    private TextView tempTv;
    private TextView weatherLabel;
    private TextView cityTv;
    private TextView pressureTv;
    private TextView humidityTv;
    private TextView windSpeedTv;
    private TextView visibilityTv;
    private TextView weatherTv;
    private TextView precipitationTv;
    private TextView cloudCoverTv;
    private TextView ozoneTv;
    private ImageView weatherIv;

    public static Fragment getInstance(int position, Currently currently) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        bundle.putSerializable("Currently", currently);
        TodayFragment todayFragment = new TodayFragment();
        todayFragment.setArguments(bundle);
        return todayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currently = (Currently) getArguments().getSerializable("Currently");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.today_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView(view);
        setView();
    }

    private void setView() {
        String windSpeed = String.format(Locale.getDefault(), "%.2f mph", currently.getWindSpeed());
        String pressure = String.format(Locale.getDefault(), "%.2f mb", currently.getPressure());
        String precipitation = String.format(Locale.getDefault(), "%.2f mmph", currently.getPrecipIntensity());
        String temprature = String.format(Locale.getDefault(), "%.2f F", currently.getTemperature());
        String humidity = String.format(Locale.getDefault(), "%.2f", currently.getHumidity()) + "%";
        String visibility = String.format(Locale.getDefault(), "%.2f Km", currently.getVisibility());
        String cloud = String.format(Locale.getDefault(), "%.2f", currently.getCloudCover()) + "%";
        String ozone = String.format(Locale.getDefault(), "%.2f DU", currently.getOzone());
        windSpeedTv.setText(windSpeed);
        pressureTv.setText(pressure);
        precipitationTv.setText(precipitation);
        weatherLabel.setText(currently.getSummary());
        setWeatherIcon(currently.getIcon());
        tempTv.setText(temprature);
        humidityTv.setText(humidity);
        visibilityTv.setText(visibility);
        cloudCoverTv.setText(cloud);
        ozoneTv.setText(ozone);
    }

    private void getView(View view) {
        windSpeedTv = view.findViewById(R.id.wind_value);
        pressureTv = view.findViewById(R.id.pressure_value);
        precipitationTv = view.findViewById(R.id.precipitation_value);
        weatherLabel = view.findViewById(R.id.weather_label);
        weatherIv = view.findViewById(R.id.weather_image);
        humidityTv = view.findViewById(R.id.humidity_value);
        visibilityTv = view.findViewById(R.id.visibility_value);
        cloudCoverTv = view.findViewById(R.id.cloud_value);
        ozoneTv = view.findViewById(R.id.ozone_value);
        tempTv = view.findViewById(R.id.temperature_value);

    }

    private void setWeatherIcon(String icon) {
        switch (icon) {
            case "clear-day":
                weatherIv.setImageResource(R.drawable.weather_sunny);
                break;
            case "clear-night":
                weatherIv.setImageResource(R.drawable.weather_night);
                break;
            case "rain":
                weatherIv.setImageResource(R.drawable.weather_rainy);
                break;
            case "sleet":
                weatherIv.setImageResource(R.drawable.weather_snowy_rainy);
                break;
            case "snow":
                weatherIv.setImageResource(R.drawable.weather_snowy);
                break;
            case "wind":
                weatherIv.setImageResource(R.drawable.weather_windy_variant);
                break;
            case "fog":
                weatherIv.setImageResource(R.drawable.weather_fog);
                break;
            case "cloudy":
                weatherIv.setImageResource(R.drawable.weather_cloudy);
                break;
            case "partly-cloudy-night":
                weatherIv.setImageResource(R.drawable.weather_night_partly_cloudy);
                break;
            case "partly-cloudy-day":
                weatherIv.setImageResource(R.drawable.weather_partly_cloudy);
                break;
            default:
                weatherIv.setImageResource(R.drawable.weather_sunny);
        }
    }
}
