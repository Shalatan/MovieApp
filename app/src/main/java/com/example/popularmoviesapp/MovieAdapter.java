package com.example.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    private Context mContext;
    private List<Movie> mMovieDetailsList;

    public MovieAdapter(@NonNull Context context,List<Movie> arrayList)
    {
        mContext = context;
        mMovieDetailsList = arrayList;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, final int position)
    {
        final String link = "https://image.tmdb.org/t/p/w500/" + mMovieDetailsList.get(position).getMovieImage();
        Picasso.get()
                .load(link)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                Movie movie = mMovieDetailsList.get(position);
                intent.putExtra("title",movie.getMovieName());
                intent.putExtra("rating",movie.getMovieRating());
                intent.putExtra("image",link);
                intent.putExtra("synopsis",movie.getMovieSynopsis());
                intent.putExtra("date",movie.getMovieReleaseDate());
                intent.putExtra("id",movie.getmMovieId());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return mMovieDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.cardViewImage);
        }
    }
}
