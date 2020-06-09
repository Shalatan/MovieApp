package com.example.popularmoviesapp.Favourite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao
{
    @Query("SELECT * FROM favourite")
    LiveData<List<FavouriteEntry>> loadAllFavourites();

    @Insert
    void insertFavourite(FavouriteEntry favouriteEntry);

    @Delete
    void deleteFavourite(FavouriteEntry favouriteEntry);
}
