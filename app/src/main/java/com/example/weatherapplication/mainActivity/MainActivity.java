package com.example.weatherapplication.mainActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.detailActivity.DetailActivity;
import com.example.weatherapplication.network.ApiInterface;
import com.example.weatherapplication.network.Model.Currently;
import com.example.weatherapplication.network.Model.Daily;
import com.example.weatherapplication.network.Model.IpApi;
import com.example.weatherapplication.network.Model.Weather;
import com.example.weatherapplication.network.NetworkClient;
import com.example.weatherapplication.searchActivity.SearchActivity;
import com.example.weatherapplication.searchActivity.SearchSuggestionTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface client;

    private String TAG = "MainActivity";

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
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = NetworkClient.getRetrofit().create(ApiInterface.class);
        linkView();
        detailButton.setVisibility(View.VISIBLE);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("Currently", currentDetails);
                mBundle.putSerializable("Daily", dailyDataSet);
                mBundle.putString("City", city);
                i.putExtras(mBundle);
                startActivity(i);
            }
        });
        setProgressTo(true);
        setRecyclerView();
        fetchLatLonAndCity();
    }

    private void fetchLatLonAndCity() {
        client.getLocationData("http://ip-api.com/json").enqueue(new Callback<IpApi>() {
            @Override
            public void onResponse(Call<IpApi> call, Response<IpApi> response) {
                IpApi body = response.body();
                fetchDataFromApi(body.getLat(), body.getLon(), body.getCity());
            }

            @Override
            public void onFailure(Call<IpApi> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            //use the query to search your data somehow
        }
    }

    private void setProgressTo(boolean visible) {
        if(visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void setRecyclerView() {
        weeklyStats.setLayoutManager(new LinearLayoutManager(this));
        weeklyAdapter = new WeeklyAdapter();
        weeklyStats.setAdapter(weeklyAdapter);
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

    private void fetchDataFromApi(double latitude, double longitude, String city) {
        this.city = city;
        client.getData(latitude, longitude).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                setProgressTo(false);
                Weather weather = response.body();
                currentDetails = weather.getCurrently();
                dailyDataSet = weather.getDaily();
                setValueForCurrentView(weather.getCurrently());
                addDataToRecycleView(weather);
                Log.d(TAG, weather.toString());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addDataToRecycleView(Weather weather) {
        weeklyAdapter.setDataList(weather.getDaily().getData());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
//        SearchableInfo info = searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class));
//        searchView.setSearchableInfo(info);
        searchView.setSuggestionsAdapter(new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.simple_list_item,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{R.id.textView}
        ));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("Query", query);
                i.putExtras(mBundle);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3)
                    new SearchSuggestionTask(client, searchView).execute(newText);
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                MatrixCursor row = (MatrixCursor) searchView.getSuggestionsAdapter().getCursor();
                row.moveToPosition(position);
                searchView.setQuery(row.getString(1), true);
                return true;
            }
        });
        return true;
    }
}
