package com.example.popularmoviesapp.Favourite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.popularmoviesapp.R;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class FavouriteListActivity extends AppCompatActivity {

    private AppDatabase mDb;
    private RecyclerView mRecyclerView;
    private FavouriteAdapter favouriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        favouriteAdapter = new FavouriteAdapter(this);
        mRecyclerView.setAdapter(favouriteAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        favouriteAdapter.setTasks(mDb.favouriteDao().loadAllFavourites());
    }
}
