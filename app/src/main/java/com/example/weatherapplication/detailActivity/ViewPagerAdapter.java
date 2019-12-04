package com.example.weatherapplication.detailActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.weatherapplication.network.Model.Currently;
import com.example.weatherapplication.network.Model.Daily;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] title = {"Today", "Weekly", "Photos"};
    private Currently currentDetails;
    private Daily daily;
    private String city;

    public ViewPagerAdapter(FragmentManager manager, Currently currentDetails, Daily daily, String city) {
        super(manager);
        this.currentDetails = currentDetails;
        this.daily = daily;
        this.city = city;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return WeeklyFragment.getInstance(daily, currentDetails.getSummary(), currentDetails.getIcon());
            case 2:
                return PhotoFragment.getInstance(city);
            default:
                return TodayFragment.getInstance(position, currentDetails);
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
