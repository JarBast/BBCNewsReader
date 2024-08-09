package com.example.bbcnewsreader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFavoriteArticles();
    }

    private void loadFavoriteArticles() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getFavoriteArticles();

        List<NewsItem> favoriteArticles = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");

            if (titleIndex == -1 || descriptionIndex == -1) {
                Log.e("FavoritesActivity", "Column indices are invalid. Check column names.");
            } else {
                do {
                    String title = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    favoriteArticles.add(new NewsItem(title, description, "", ""));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.w("FavoritesActivity", "Cursor is null or empty.");
        }

        NewsAdapter adapter = new NewsAdapter(this, favoriteArticles, newsItem -> {
            Intent intent = new Intent(FavoritesActivity.this, ArticleDetailActivity.class);
            intent.putExtra("TITLE", newsItem.getTitle());
            intent.putExtra("DESCRIPTION", newsItem.getDescription());
            intent.putExtra("DATE", newsItem.getDate());
            intent.putExtra("URL", newsItem.getUrl());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}