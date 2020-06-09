package com.example.popularmoviesapp.Favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>
{

    private List<FavouriteEntry> list = new ArrayList<>();
    private Context mContext;

    public FavouriteAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.favourite_item,parent,false);
        return new FavouriteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position)
    {
        holder.fTitle.setText(list.get(position).getTitle());
        holder.fDate.setText(list.get(position).getDate());
        holder.fRating.setText(list.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTasks(List<FavouriteEntry> taskEntries) {
        list = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private TextView fTitle;
        private TextView fDate;
        private TextView fRating;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            fTitle = itemView.findViewById(R.id.favouriteMovieTitle);
            fDate = itemView.findViewById(R.id.favouriteMovieDate);
            fRating = itemView.findViewById(R.id.favouriteMovieRating);
        }
    }
}
