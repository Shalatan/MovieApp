package com.example.popularmoviesapp.Objects;

public class MovieReview {
    private String movieReviewerName;
    private String getMovieReviewerReview;

    public MovieReview(String movieReviewerName, String getMovieReviewerReview) {
        this.movieReviewerName = movieReviewerName;
        this.getMovieReviewerReview = getMovieReviewerReview;
    }

    public String getMovieReviewerName() {
        return movieReviewerName;
    }

    public String getGetMovieReviewerReview() {
        return getMovieReviewerReview;
    }
}
