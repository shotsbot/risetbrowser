package com.risetbrowser.android.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

public class GestureHandler extends GestureDetector.SimpleOnGestureListener {
    private WebView webView;
    private GestureListener listener;

    public interface GestureListener {
        void onSwipeRight();
        void onSwipeLeft();
        void onDoubleTap();
        void onLongPress();
    }

    public GestureHandler(WebView webView, GestureListener listener) {
        this.webView = webView;
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final float SWIPE_THRESHOLD = 100;
        final float SWIPE_VELOCITY_THRESHOLD = 100;

        try {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0 && listener != null) {
                        listener.onSwipeRight(); // Go back
                    } else if (diffX < 0 && listener != null) {
                        listener.onSwipeLeft(); // Go forward
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (listener != null) {
            listener.onDoubleTap();
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (listener != null) {
            listener.onLongPress();
        }
        super.onLongPress(e);
    }
}
