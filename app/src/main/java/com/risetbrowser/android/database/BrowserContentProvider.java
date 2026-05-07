package com.risetbrowser.android.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BrowserContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.risetbrowser.android.provider";
    private static final String HISTORY_PATH = "history";
    private static final String BOOKMARKS_PATH = "bookmarks";
    private static final String DOWNLOADS_PATH = "downloads";

    public static final Uri HISTORY_URI = Uri.parse("content://" + AUTHORITY + "/" + HISTORY_PATH);
    public static final Uri BOOKMARKS_URI = Uri.parse("content://" + AUTHORITY + "/" + BOOKMARKS_PATH);
    public static final Uri DOWNLOADS_URI = Uri.parse("content://" + AUTHORITY + "/" + DOWNLOADS_PATH);

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private BrowserDatabase dbHelper;

    static {
        uriMatcher.addURI(AUTHORITY, HISTORY_PATH, 1);
        uriMatcher.addURI(AUTHORITY, HISTORY_PATH + "/#", 2);
        uriMatcher.addURI(AUTHORITY, BOOKMARKS_PATH, 3);
        uriMatcher.addURI(AUTHORITY, BOOKMARKS_PATH + "/#", 4);
        uriMatcher.addURI(AUTHORITY, DOWNLOADS_PATH, 5);
        uriMatcher.addURI(AUTHORITY, DOWNLOADS_PATH + "/#", 6);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new BrowserDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                       @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = db.query("history", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 3:
                cursor = db.query("bookmarks", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case 5:
                cursor = db.query("downloads", projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
            case 3:
            case 5:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY;
            case 2:
            case 4:
            case 6:
                return "vnd.android.cursor.item/vnd." + AUTHORITY;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        switch (uriMatcher.match(uri)) {
            case 1:
                id = db.insert("history", null, values);
                break;
            case 3:
                id = db.insert("bookmarks", null, values);
                break;
            case 5:
                id = db.insert("downloads", null, values);
                break;
        }

        if (id > 0) {
            Uri newUri = Uri.withAppendedPath(uri, String.valueOf(id));
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case 2:
                count = db.delete("history", selection, selectionArgs);
                break;
            case 4:
                count = db.delete("bookmarks", selection, selectionArgs);
                break;
            case 6:
                count = db.delete("downloads", selection, selectionArgs);
                break;
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                     @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case 2:
                count = db.update("history", values, selection, selectionArgs);
                break;
            case 4:
                count = db.update("bookmarks", values, selection, selectionArgs);
                break;
            case 6:
                count = db.update("downloads", values, selection, selectionArgs);
                break;
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }
}
