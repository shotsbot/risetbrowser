package com.risetbrowser.android.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.risetbrowser.android.R;
import com.risetbrowser.android.database.BrowserDatabase;
import com.risetbrowser.android.model.HistoryItem;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ListView historyListView;
    private ProgressBar loadingBar;
    private TextView emptyView;
    private BrowserDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        setupDatabase();
        loadHistory();
    }

    private void initializeViews() {
        historyListView = findViewById(R.id.historyListView);
        loadingBar = findViewById(R.id.loadingBar);
        emptyView = findViewById(R.id.emptyView);
    }

    private void setupDatabase() {
        database = new BrowserDatabase(this);
    }

    private void loadHistory() {
        new Thread(() -> {
            List<HistoryItem> history = database.getHistory();
            runOnUiThread(() -> {
                loadingBar.setVisibility(View.GONE);
                if (history.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    // Set adapter for history list
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
