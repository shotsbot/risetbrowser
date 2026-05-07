package com.risetbrowser.android.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.risetbrowser.android.R;
import com.risetbrowser.android.database.BrowserDatabase;
import com.risetbrowser.android.model.BookmarkItem;

import java.util.List;

public class BookmarksActivity extends AppCompatActivity {
    private ListView bookmarksListView;
    private ProgressBar loadingBar;
    private TextView emptyView;
    private BrowserDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        initializeViews();
        setupDatabase();
        loadBookmarks();
    }

    private void initializeViews() {
        bookmarksListView = findViewById(R.id.bookmarksListView);
        loadingBar = findViewById(R.id.loadingBar);
        emptyView = findViewById(R.id.emptyView);
    }

    private void setupDatabase() {
        database = new BrowserDatabase(this);
    }

    private void loadBookmarks() {
        new Thread(() -> {
            List<BookmarkItem> bookmarks = database.getBookmarks();
            runOnUiThread(() -> {
                loadingBar.setVisibility(View.GONE);
                if (bookmarks.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    // Set adapter for bookmarks list
                }
            });
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}
