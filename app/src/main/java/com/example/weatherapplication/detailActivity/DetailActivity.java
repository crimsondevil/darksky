package com.example.weatherapplication.detailActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapplication.R;
import com.example.weatherapplication.network.Model.Currently;
import com.example.weatherapplication.network.Model.Daily;
import com.google.android.material.tabs.TabLayout;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Currently currentDetails;
    private Daily daily;
    private String city;

    private int[] tabIcon = {
            R.drawable.calendar_today,
            R.drawable.trending_up,
            R.drawable.google_photos
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        currentDetails = (Currently) getIntent().getSerializableExtra("Currently");
        daily = (Daily) getIntent().getSerializableExtra("Daily");
        city = getIntent().getExtras().getString("City");

        setTitle(city);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), currentDetails, daily, city);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcon();
    }

    private void setUpTabIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout.getTabAt(1).setIcon(tabIcon[1]);
        tabLayout.getTabAt(2).setIcon(tabIcon[2]);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.twitter:
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.twitter.com/intent/tweet?text=(Check Out CITY’s Weather! It is " +
                        currentDetails.getTemperature().toString() + "°F! #CSCI571WeatherSearch"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_activity_menu, menu);
        return true;
    }
}
