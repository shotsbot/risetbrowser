package com.risetbrowser.android.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.risetbrowser.android.R;
import com.risetbrowser.android.database.BrowserDatabase;
import com.risetbrowser.android.model.DownloadItem;
import com.risetbrowser.android.service.AdBlocker;
import com.risetbrowser.android.service.PopupBlocker;
import com.risetbrowser.android.util.BrowserUtil;
import com.risetbrowser.android.util.SearchEngineUtil;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    
    private WebView webView;
    private EditText urlInput;
    private ProgressBar progressBar;
    private ImageButton btnBack, btnForward, btnRefresh, btnHome, btnMenu;
    private LinearLayout toolbarTop, toolbarBottom;
    private FrameLayout webContainer;
    private CoordinatorLayout coordinatorLayout;
    
    private SharedPreferences preferences;
    private BrowserDatabase database;
    private AdBlocker adBlocker;
    private PopupBlocker popupBlocker;
    private BroadcastReceiver downloadReceiver;
    
    private String currentUrl = "https://www.google.com";
    private int tabCount = 0;
    private boolean isDesktopMode = false;
    private boolean isNightMode = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupDatabase();
        setupPreferences();
        setupWebView();
        setupToolbar();
        setupDownloadListener();
        setupBlockers();
        setupDownloadReceiver();
        checkPermissions();
        
        // Load default page
        loadUrl("https://www.google.com");
    }

    private void initializeViews() {
        webView = findViewById(R.id.webView);
        urlInput = findViewById(R.id.urlInput);
        progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);
        btnForward = findViewById(R.id.btnForward);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnHome = findViewById(R.id.btnHome);
        btnMenu = findViewById(R.id.btnMenu);
        toolbarTop = findViewById(R.id.toolbarTop);
        toolbarBottom = findViewById(R.id.toolbarBottom);
        webContainer = findViewById(R.id.webContainer);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
    }

    private void setupDatabase() {
        database = new BrowserDatabase(this);
    }

    private void setupPreferences() {
        preferences = getSharedPreferences("browser_prefs", MODE_PRIVATE);
        isDesktopMode = preferences.getBoolean("desktop_mode", false);
        isNightMode = preferences.getBoolean("night_mode", false);
        
        if (isNightMode) {
            applyNightMode();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        
        // Cache settings
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        webView.getSettings().setAppCacheEnabled(true);
        
        // User agent
        String userAgent = webView.getSettings().getUserAgentString();
        if (isDesktopMode) {
            webView.getSettings().setUserAgentString(userAgent.replace("Mobile", ""));
        }
        
        // JavaScript interface
        webView.addJavascriptInterface(new BrowserJSInterface(), "BrowserAPI");
        
        // WebView client
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new CustomWebChromeClient());
        webView.setDownloadListener(new CustomDownloadListener());
    }

    private void setupToolbar() {
        // URL input
        urlInput.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                loadUrl(urlInput.getText().toString());
                hideKeyboard();
                return true;
            }
            return false;
        });
        
        // Navigation buttons
        btnBack.setOnClickListener(v -> {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        });
        
        btnForward.setOnClickListener(v -> {
            if (webView.canGoForward()) {
                webView.goForward();
            }
        });
        
        btnRefresh.setOnClickListener(v -> webView.reload());
        
        btnHome.setOnClickListener(v -> {
            String homepage = preferences.getString("homepage", "https://www.google.com");
            loadUrl(homepage);
        });
        
        btnMenu.setOnClickListener(v -> openMenu());
    }

    private void setupDownloadListener() {
        webView.setDownloadListener(new CustomDownloadListener());
    }

    private void setupBlockers() {
        adBlocker = new AdBlocker(this);
        popupBlocker = new PopupBlocker(webView);
    }

    private void setupDownloadReceiver() {
        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    // Handle completed download
                }
            }
        };
        
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter, Context.RECEIVER_EXPORTED);
    }

    private void checkPermissions() {
        String[] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        };
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
            }
        } else {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
                    break;
                }
            }
        }
    }

    private void loadUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = SearchEngineUtil.getSearchUrl(this, url);
        }
        
        currentUrl = url;
        urlInput.setText(url);
        webView.loadUrl(url);
        
        // Add to history
        database.addHistory(url, System.currentTimeMillis());
    }

    private void openMenu() {
        // Implementation of menu
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(urlInput.getWindowToken(), 0);
        }
    }

    private void applyNightMode() {
        // Apply night mode styles
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getWindow().setDecorFitsSystemWindows(false);
            webView.setBackgroundColor(0xFF1a1a1a);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (itemId == R.id.menu_downloads) {
            startActivity(new Intent(this, DownloadActivity.class));
            return true;
        } else if (itemId == R.id.menu_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (itemId == R.id.menu_bookmarks) {
            startActivity(new Intent(this, BookmarksActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
        if (downloadReceiver != null) {
            try {
                unregisterReceiver(downloadReceiver);
            } catch (Exception e) {
                // Already unregistered
            }
        }
    }

    // Custom WebView Client
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            urlInput.setText(url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            updateNavigationButtons();
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            
            // Block ads
            if (adBlocker.shouldBlock(url)) {
                return new WebResourceResponse("text/plain", "utf-8", null);
            }
            
            return super.shouldInterceptRequest(view, request);
        }
    }

    // Custom WebChrome Client
    private class CustomWebChromeClient extends WebChromeClient {
        private View customView;
        private int originalOrientation;

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            request.grant(request.getResources());
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {
            // File chooser implementation
            return true;
        }
    }

    // Custom Download Listener
    private class CustomDownloadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                String contentType, long contentLength) {
            
            DownloadItem item = new DownloadItem();
            item.setUrl(url);
            item.setFileName(URLUtil.guessFileName(url, contentDisposition, contentType));
            item.setContentType(contentType);
            item.setContentLength(contentLength);
            item.setTimestamp(System.currentTimeMillis());
            
            database.addDownload(item);
            
            // Start download service
            Intent intent = new Intent(MainActivity.this, DownloadService.class);
            intent.putExtra("url", url);
            intent.putExtra("fileName", item.getFileName());
            startService(intent);
            
            Toast.makeText(MainActivity.this, "Download started: " + item.getFileName(),
                Toast.LENGTH_SHORT).show();
        }
    }

    // JavaScript Interface
    public class BrowserJSInterface {
        @android.webkit.JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNavigationButtons() {
        btnBack.setEnabled(webView.canGoBack());
        btnForward.setEnabled(webView.canGoForward());
        btnBack.setAlpha(webView.canGoBack() ? 1.0f : 0.5f);
        btnForward.setAlpha(webView.canGoForward() ? 1.0f : 0.5f);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Permission handling
        }
    }
}
