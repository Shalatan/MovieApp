package com.example.popularmoviesapp.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;

import java.util.ArrayList;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.VideoViewHolder> {
    private ArrayList<String> movieLinkList;
    private Context mContext;

    public MovieTrailerAdapter(ArrayList<String> movieLinkList, Context mContext) {
        this.movieLinkList = movieLinkList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_trailer_layout, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        holder.trailerView.setText("Trailer " + String.valueOf(position + 1));
        holder.trailerView.setOnClickListener(new View.OnClickListener() {
            String id = movieLinkList.get(position);

            @Override
            public void onClick(View v) {

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
                try {
                    mContext.startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLinkList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private TextView trailerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerView = itemView.findViewById(R.id.movieTrailerTextView);
        }
    }

}
