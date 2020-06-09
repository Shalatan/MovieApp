package com.example.popularmoviesapp.Favourite;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouriteEntry.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    private static final String DATABASE_NAME = "favouritelist";
    public static AppDatabase getInstance(Context context)
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                Log.d(AppDatabase.class.getSimpleName(), "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,AppDatabase.DATABASE_NAME)
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(AppDatabase.class.getSimpleName(), "Getting the database instance");
        return sInstance;
    }
    public abstract FavouriteDao favouriteDao();

}
