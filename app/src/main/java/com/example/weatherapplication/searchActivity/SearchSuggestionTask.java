package com.example.weatherapplication.searchActivity;

import android.app.SearchManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.widget.SearchView;

import com.example.weatherapplication.network.ApiInterface;
import com.example.weatherapplication.network.Model.Prediction;
import com.example.weatherapplication.network.Model.Suggestion;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SearchSuggestionTask extends AsyncTask<String, Void, Cursor> {

    private static final String[] colNames = new String[]{
            BaseColumns._ID,                         // necessary for adapter
            SearchManager.SUGGEST_COLUMN_TEXT_1      // the full search term
    };
    private ApiInterface client;
    private SearchView searchView;

    public SearchSuggestionTask(ApiInterface client, SearchView searchView) {
        this.client = client;
        this.searchView = searchView;

    }

    @Override
    protected Cursor doInBackground(String... strings) {

        final MatrixCursor cursor = new MatrixCursor(colNames);
        try {
            Response<Suggestion> response = client.getPrediction(strings[0]).execute();
            List<Prediction> predictionList = response.body().getPredictions();

            for (int index = 0; index < predictionList.size(); index++) {
                Object[] row = new Object[]{index, predictionList.get(index).getDescription()};
                cursor.addRow(row);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        /*client.getPrediction(strings[0]).enqueue(new Callback<Suggestion>() {
            @Override
            public void onResponse(Call<Suggestion> call, Response<Suggestion> response) {
                List<Prediction> predictionList = response.body().getPredictions();

                for(int index = 0; index < predictionList.size(); index++){
                    Object[] row = new Object[] { index, predictionList.get(0).getDescription()};
                    cursor.addRow(row);
                }
            }

            @Override
            public void onFailure(Call<Suggestion> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        searchView.getSuggestionsAdapter().changeCursor(cursor);
    }
}
