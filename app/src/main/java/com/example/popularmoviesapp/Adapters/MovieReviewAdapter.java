package com.example.popularmoviesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.Objects.MovieReview;
import com.example.popularmoviesapp.R;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ReviewHolder> {

    private List<MovieReview> movieReviewList;
    private Context context;

    public MovieReviewAdapter(List<MovieReview> movieReviewList, Context context) {
        this.movieReviewList = movieReviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_layout, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.author.setText("Author: " + movieReviewList.get(position).getMovieReviewerName());
        holder.content.setText(movieReviewList.get(position).getGetMovieReviewerReview());
    }

    @Override
    public int getItemCount() {
        return movieReviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView content;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.movieReviewerName);
            content = itemView.findViewById(R.id.movieReviewerReview);
        }
    }
}
