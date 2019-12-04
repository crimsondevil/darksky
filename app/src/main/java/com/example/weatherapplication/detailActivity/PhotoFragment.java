package com.example.weatherapplication.detailActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.network.ApiInterface;
import com.example.weatherapplication.network.Model.GoogleImageModel;
import com.example.weatherapplication.network.Model.Item;
import com.example.weatherapplication.network.NetworkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoFragment extends Fragment {

    private String city;

    private ApiInterface client;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;

    public static Fragment getInstance(String city) {
        Bundle bundle = new Bundle();
        bundle.putString("City", city);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        city = getArguments().getString("City");
        client = NetworkClient.getRetrofit().create(ApiInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photos_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PhotoAdapter();
        recyclerView.setAdapter(adapter);
        new ImageFetchAsyncTask(client).execute();

    }


    class ImageFetchAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private ApiInterface networkClient;

        public ImageFetchAsyncTask(ApiInterface networkClient) {
            this.networkClient = networkClient;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            final List<String> imageUrlList = new ArrayList<>();
            networkClient.getImageFor(city).enqueue(new Callback<GoogleImageModel>() {
                @Override
                public void onResponse(Call<GoogleImageModel> call, Response<GoogleImageModel> response) {
                    GoogleImageModel googleImageModel = response.body();
                    for (Item i : googleImageModel.getItems()) {
                        imageUrlList.add(i.getLink());
                    }
                    adapter.setDataList(imageUrlList);
                }

                @Override
                public void onFailure(Call<GoogleImageModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            return imageUrlList;
        }
    }

}
