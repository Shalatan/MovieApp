package com.example.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmoviesapp.Favourite.FavouriteListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ArrayList<Movie> movieArrayList;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    TextView emptyTextView;
    ProgressBar progressBar;
    MainActivityAsyncTask mainActivityAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        emptyTextView = findViewById(R.id.emptyTextView);
        progressBar = findViewById(R.id.progress_bar);
        updateOnInitialLaunch();
    }
    private void updateOnInitialLaunch()
    {
        movieArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        movieAdapter = new MovieAdapter(this,movieArrayList);
        recyclerView.setAdapter(movieAdapter);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            emptyTextView.setVisibility(View.GONE);
            mainActivityAsyncTask = new MainActivityAsyncTask();
            mainActivityAsyncTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036");
        }
        else
        {
            if(progressBar.getVisibility() == View.VISIBLE)
            {
                progressBar.setVisibility(View.INVISIBLE);
                emptyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
    private class MainActivityAsyncTask extends AsyncTask<String, Void, List<Movie>>
    {
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.INVISIBLE);
        }
        @Override
        protected List<Movie> doInBackground(String... strings)
        {
            if (strings.length < 1 || strings[0] == null)
            {
                return null;
            }
            return MovieUtils.fetchMovieDetails(strings[0]);
        }
        @Override
        protected void onPostExecute(List<Movie> movies)
        {
            super.onPostExecute(movies);
            emptyTextView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            movieAdapter = new MovieAdapter(MainActivity.this,movies);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            recyclerView.setAdapter(movieAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemClicked = item.getItemId();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            mainActivityAsyncTask = new MainActivityAsyncTask();
            emptyTextView.setVisibility(View.INVISIBLE);
            if (itemClicked == R.id.popular) {
                mainActivityAsyncTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=ea9a49ebf2b74721a75aae271ebd3036");
            }
            else if (itemClicked == R.id.top_rated) {
                mainActivityAsyncTask.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=ea9a49ebf2b74721a75aae271ebd3036");
            }
            else if (itemClicked == R.id.favourite) {
                Intent intent = new Intent(MainActivity.this, FavouriteListActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }
}
