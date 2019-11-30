package com.example.weatherapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.network.Model.Datum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.CustomViewHolder> {

    private List<Datum> dataList = new ArrayList<>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_stats, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<Datum> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageView icon;
        private TextView low_temp;
        private TextView high_temp;
        private Calendar calendar = Calendar.getInstance();

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date_value);
            icon = (ImageView) itemView.findViewById(R.id.weather_icon);
            low_temp = (TextView) itemView.findViewById(R.id.low_temp_value);
            high_temp = (TextView) itemView.findViewById(R.id.high_temp_value);
        }


        public void bindView(Datum datum) {
            calendar.setTimeInMillis(datum.getTime());
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            date.setText(format1.format(calendar.getTime()));
            switch (datum.getIcon()){
                case "clear-day": icon.setImageResource(R.drawable.weather_sunny);
                break;
                case "clear-night": icon.setImageResource(R.drawable.weather_night);
                break;
                case "rain": icon.setImageResource(R.drawable.weather_rainy);
                break;
                case "sleet": icon.setImageResource(R.drawable.weather_snowy_rainy);
                break;
                case "snow": icon.setImageResource(R.drawable.weather_snowy);
                break;
                case "wind": icon.setImageResource(R.drawable.weather_windy_variant);
                break;
                case "fog": icon.setImageResource(R.drawable.weather_fog);
                break;
                case "cloudy": icon.setImageResource(R.drawable.weather_cloudy);
                break;
                case "partly-cloudy-night": icon.setImageResource(R.drawable.weather_night_partly_cloudy);
                break;
                case "partly-cloudy-day": icon.setImageResource(R.drawable.weather_partly_cloudy);
                break;
                default: icon.setImageResource(R.drawable.weather_sunny);
            }
            low_temp.setText(datum.getTemperatureLow().toString());
            high_temp.setText(datum.getTemperatureHigh().toString());
        }
    }
}
