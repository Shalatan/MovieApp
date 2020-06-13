package com.example.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesapp.Adapters.MovieTrailerAdapter;
import com.example.popularmoviesapp.Favourite.AppDatabase;
import com.example.popularmoviesapp.Favourite.FavouriteEntry;
import com.example.popularmoviesapp.Favourite.FavouriteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity
{
    String movieName;
    String movieRelease;
    String movieRating;
    String movieSyno;
    String movieImage;
    String movieId;
    int flag = 0;
    private AppDatabase mDb;
    List<FavouriteEntry> favouriteEntries;
    FavouriteViewModel favouriteViewModel;

    MovieVideoAsyncTask movieVideoAsyncTask;
    RecyclerView movieTrailerRecyclerView;
    MovieTrailerAdapter movieTrailerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        fillDetailActivity(intent);
        updateVideo();

        movieVideoAsyncTask = new MovieVideoAsyncTask();
        movieVideoAsyncTask.execute(getVideoApiLink(movieId));
        mDb = AppDatabase.getInstance(getApplicationContext());


        FloatingActionButton favButton = findViewById(R.id.set_favourite);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < favouriteEntries.size(); i++) {
                    if (favouriteEntries.get(i).getId() == Integer.parseInt(movieId)) {
                        flag = 1;
                    }
                }

                if (flag == 0) {

                    addToFavourite();
                    flag = 1;
                } else {
                    flag = 0;
                    deleteFromFavourite();
                }

            }
        });
        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        favouriteViewModel.getTasks().observe(this, new Observer<List<FavouriteEntry>>() {
            @Override
            public void onChanged(List<FavouriteEntry> entries) {
                favouriteEntries = entries;
            }
        });
    }

    private void deleteFromFavourite() {
        final FavouriteEntry favouriteEntry = new FavouriteEntry(Integer.parseInt(movieId),movieName,movieRelease,movieRating);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run()
            {
                mDb.favouriteDao().deleteFavourite(favouriteEntry);
            }
        });
        Toast.makeText(MovieDetailActivity.this, "Removed from favourite", Toast.LENGTH_SHORT).show();
    }

    private void addToFavourite() {
        final FavouriteEntry favouriteEntry = new FavouriteEntry(Integer.parseInt(movieId),movieName,movieRelease,movieRating);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run()
            {
                mDb.favouriteDao().insertFavourite(favouriteEntry);
            }
        });
        Toast.makeText(MovieDetailActivity.this, "Added to favourite", Toast.LENGTH_SHORT).show();
    }

    //Creates VideoApi link from id
    private String getVideoApiLink(String id) {
        String videoLink = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=ea9a49ebf2b74721a75aae271ebd3036";
        return videoLink;
    }

    //Initiate Video View with empty ArrayList
    private void updateVideo() {
        ArrayList<String> movieVideoArrayList = new ArrayList<String>();
        movieTrailerRecyclerView = findViewById(R.id.movieTrailerRecyclerView);
        movieTrailerRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        movieTrailerAdapter = new MovieTrailerAdapter(movieVideoArrayList, MovieDetailActivity.this);
        movieTrailerRecyclerView.setAdapter(movieTrailerAdapter);
    }

    public class MovieVideoAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (movieTrailerRecyclerView.getVisibility() == View.VISIBLE) {
                movieTrailerRecyclerView.setVisibility(View.GONE);
            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            return MovieUtils.fetchMovieVideo(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
            super.onPostExecute(list);

            movieTrailerAdapter = new MovieTrailerAdapter(list, MovieDetailActivity.this);
            movieTrailerRecyclerView.setAdapter(movieTrailerAdapter);

            if (movieTrailerRecyclerView.getVisibility() == View.GONE) {
                movieTrailerRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private void fillDetailActivity(Intent intent)
    {
        movieName = intent.getStringExtra("title");
        movieRating = intent.getStringExtra("rating");
        movieSyno = intent.getStringExtra("synopsis");
        movieRelease = intent.getStringExtra("date");
        movieImage = intent.getStringExtra("image");
        movieId = intent.getStringExtra("id");

        TextView nameTextView = findViewById(R.id.movieName);
        TextView releaseTextView = findViewById(R.id.movieReleaseDate);
        TextView ratingTextView = findViewById(R.id.movieRating);
        TextView synoTextView = findViewById(R.id.movieSyno);
        ImageView posterImageView = findViewById(R.id.movieImage);

        nameTextView.setText(movieName);
        ratingTextView.setText("Rating : " + movieRating);
        releaseTextView.setText("Release Date : " + movieRelease);
        synoTextView.setText(movieSyno);
        Picasso.get().load(movieImage).into(posterImageView);
    }

}
