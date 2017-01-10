package com.vise.utils.handler;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtil {
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }
}
