package com.example.popularmoviesapp.Favourite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.popularmoviesapp.AppExecutors;
import com.example.popularmoviesapp.R;

import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class FavouriteListActivity extends AppCompatActivity {

    private static final String TAG = FavouriteListActivity.class.getSimpleName();
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
        decoration.setDrawable(getDrawable(R.drawable.favourite_list_item_divider));
        mRecyclerView.addItemDecoration(decoration);
        favouriteAdapter = new FavouriteAdapter(this);
        mRecyclerView.setAdapter(favouriteAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());
        setUpViewModel();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<FavouriteEntry> tasks = favouriteAdapter.getTasks();
                        mDb.favouriteDao().deleteFavourite(tasks.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpViewModel() {
        FavouriteViewModel favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        favouriteViewModel.getTasks().observe(this, new Observer<List<FavouriteEntry>>() {
            @Override
            public void onChanged(List<FavouriteEntry> favouriteEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                favouriteAdapter.setTasks(favouriteEntries);
            }
        });
    }
}
