package com.risetbrowser.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.risetbrowser.android.model.BookmarkItem;
import com.risetbrowser.android.model.DownloadItem;
import com.risetbrowser.android.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class BrowserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "risetbrowser.db";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_BOOKMARKS = "bookmarks";
    private static final String TABLE_DOWNLOADS = "downloads";

    // Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public BrowserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHistoryTable = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT NOT NULL, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER NOT NULL)";
        db.execSQL(createHistoryTable);

        String createBookmarksTable = "CREATE TABLE " + TABLE_BOOKMARKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT NOT NULL, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                "folder TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER NOT NULL)";
        db.execSQL(createBookmarksTable);

        String createDownloadsTable = "CREATE TABLE " + TABLE_DOWNLOADS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT NOT NULL, " +
                "fileName TEXT NOT NULL, " +
                "contentLength INTEGER, " +
                "downloadedLength INTEGER, " +
                "contentType TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER NOT NULL, " +
                "status INTEGER, " +
                "downloadPath TEXT)";
        db.execSQL(createDownloadsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOADS);
        onCreate(db);
    }

    // History operations
    public void addHistory(String url, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_TIMESTAMP, timestamp);
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public List<HistoryItem> getHistory() {
        List<HistoryItem> history = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY " +
                COLUMN_TIMESTAMP + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                HistoryItem item = new HistoryItem();
                item.setId(cursor.getInt(0));
                item.setUrl(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setTimestamp(cursor.getLong(3));
                history.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return history;
    }

    public void clearHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, null, null);
        db.close();
    }

    // Bookmark operations
    public void addBookmark(BookmarkItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, item.getUrl());
        values.put(COLUMN_TITLE, item.getTitle());
        values.put("folder", item.getFolder());
        values.put(COLUMN_TIMESTAMP, item.getTimestamp());
        db.insert(TABLE_BOOKMARKS, null, values);
        db.close();
    }

    public List<BookmarkItem> getBookmarks() {
        List<BookmarkItem> bookmarks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKMARKS, null);

        if (cursor.moveToFirst()) {
            do {
                BookmarkItem item = new BookmarkItem();
                item.setId(cursor.getInt(0));
                item.setUrl(cursor.getString(1));
                item.setTitle(cursor.getString(2));
                item.setFolder(cursor.getString(3));
                item.setTimestamp(cursor.getLong(4));
                bookmarks.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookmarks;
    }

    public void deleteBookmark(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARKS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Download operations
    public void addDownload(DownloadItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, item.getUrl());
        values.put("fileName", item.getFileName());
        values.put("contentLength", item.getContentLength());
        values.put("contentType", item.getContentType());
        values.put(COLUMN_TIMESTAMP, item.getTimestamp());
        values.put("status", item.getStatus());
        db.insert(TABLE_DOWNLOADS, null, values);
        db.close();
    }

    public List<DownloadItem> getDownloads() {
        List<DownloadItem> downloads = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DOWNLOADS + " ORDER BY " +
                COLUMN_TIMESTAMP + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                DownloadItem item = new DownloadItem();
                item.setId(cursor.getInt(0));
                item.setUrl(cursor.getString(1));
                item.setFileName(cursor.getString(2));
                item.setContentLength(cursor.getLong(3));
                item.setDownloadedLength(cursor.getLong(4));
                item.setContentType(cursor.getString(5));
                item.setTimestamp(cursor.getLong(6));
                item.setStatus(cursor.getInt(7));
                if (cursor.getColumnIndex("downloadPath") >= 0) {
                    item.setDownloadPath(cursor.getString(8));
                }
                downloads.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return downloads;
    }

    public void updateDownload(DownloadItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("downloadedLength", item.getDownloadedLength());
        values.put("status", item.getStatus());
        values.put("downloadPath", item.getDownloadPath());
        db.update(TABLE_DOWNLOADS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deleteDownload(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOWNLOADS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void clearDownloads() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOWNLOADS, null, null);
        db.close();
    }
}
