package com.example.bbcnewsreader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ArticleDetailActivity extends AppCompatActivity {

    private String articleUrl;
    private String articleTitle;
    private boolean isFavorite = false;
    private DatabaseHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        dbHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        Intent intent = getIntent();
        articleTitle = intent.getStringExtra("TITLE");
        String description = intent.getStringExtra("DESCRIPTION");
        String date = intent.getStringExtra("DATE");
        articleUrl = intent.getStringExtra("URL");

        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView urlTextView = findViewById(R.id.urlTextView);

        titleTextView.setText(articleTitle != null ? articleTitle : "No Title");
        descriptionTextView.setText(description != null ? description : "No Description");
        dateTextView.setText(date != null ? date : "No Date");
        urlTextView.setText(articleUrl != null ? articleUrl : "No URL");

        Button goToArticleButton = findViewById(R.id.goToArticleButton);
        goToArticleButton.setOnClickListener(v -> {
            if (articleUrl != null && !articleUrl.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
                startActivity(browserIntent);
            }
        });

        Button addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
        addToFavoritesButton.setOnClickListener(v -> {
            if (isFavorite) {
                dbHelper.removeFavorite(articleTitle);
                addToFavoritesButton.setText("Add to Favorites");
            } else {
                dbHelper.addFavorite(articleTitle, description);
                addToFavoritesButton.setText("Remove from Favorites");
            }
            isFavorite = !isFavorite;
        });

        checkIfFavorite();
    }

    private void checkIfFavorite() {
        if (articleTitle != null) {
            isFavorite = dbHelper.isFavorite(articleTitle);
            Button addToFavoritesButton = findViewById(R.id.addToFavoritesButton);
            addToFavoritesButton.setText(isFavorite ? "Remove from Favorites" : "Add to Favorites");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_button) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
            return true;
        } else if (id == R.id.back_button) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
