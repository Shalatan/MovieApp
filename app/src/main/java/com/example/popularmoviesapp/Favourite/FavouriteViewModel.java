package com.example.popularmoviesapp.Favourite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private LiveData<List<FavouriteEntry>> tasks;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        tasks = appDatabase.favouriteDao().loadAllFavourites();
    }

    public LiveData<List<FavouriteEntry>> getTasks() {
        return tasks;
    }
}
