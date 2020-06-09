package com.example.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmoviesapp.Favourite.AppDatabase;
import com.example.popularmoviesapp.Favourite.FavouriteEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity
{
    String movieName;
    String movieRelease;
    String movieRating;
    String movieSyno;
    String movieImage;
    String movieId;
    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mDb = AppDatabase.getInstance(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        fillDetailActivity(intent);
        FloatingActionButton favButton = findViewById(R.id.set_favourite);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouriteEntry favouriteEntry = new FavouriteEntry(Integer.parseInt(movieId),movieName,movieRelease,movieRating);
                mDb.favouriteDao().insertFavourite(favouriteEntry);
                //finish();
                Log.e("YEAHAHHHH","MOVIE UPLOADEDDDD");
            }
        });
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
