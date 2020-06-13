package com.example.popularmoviesapp.Objects;

public class Movie{
    private String mMovieName;
    private String mMovieRating;
    private String mMovieImage;
    private String mMovieSynopsis;
    private String mMovieReleaseDate;
    private String mMovieId;



    public Movie(String movieName, String movieRating, String movieImage, String movieSynopsis, String movieReleaseDate, String movieId) {
        mMovieName = movieName;
        mMovieRating = movieRating;
        mMovieImage = movieImage;
        mMovieSynopsis = movieSynopsis;
        mMovieReleaseDate = movieReleaseDate;
        mMovieId = movieId;
    }

    public String getMovieName() {
        return mMovieName;
    }

    public String getMovieRating() {
        return mMovieRating;
    }

    public String getMovieImage() {
        return mMovieImage;
    }

    public String getMovieSynopsis() {
        return mMovieSynopsis;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public String getmMovieId() { return mMovieId; }


}
