package com.risetbrowser.android.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.risetbrowser.android.R;
import com.risetbrowser.android.database.BrowserDatabase;
import com.risetbrowser.android.model.DownloadItem;

import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private ListView downloadsListView;
    private ProgressBar loadingBar;
    private TextView emptyView;
    private BrowserDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        initializeViews();
        setupDatabase();
        loadDownloads();
    }

    private void initializeViews() {
        downloadsListView = findViewById(R.id.downloadsListView);
        loadingBar = findViewById(R.id.loadingBar);
        emptyView = findViewById(R.id.emptyView);
    }

    private void setupDatabase() {
        database = new BrowserDatabase(this);
    }

    private void loadDownloads() {
        new Thread(() -> {
            List<DownloadItem> downloads = database.getDownloads();
            runOnUiThread(() -> {
                loadingBar.setVisibility(View.GONE);
                if (downloads.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    // Set adapter for downloads list
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
