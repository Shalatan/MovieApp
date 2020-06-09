package com.example.popularmoviesapp.Favourite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteEntry
{
    @PrimaryKey
    private int mId;
    private String mTitle;
    private String mDate;
    private String mRating;

    public FavouriteEntry(int mId, String mTitle, String mDate, String mRating) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mRating = mRating;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getRating() {
        return mRating;
    }

}
