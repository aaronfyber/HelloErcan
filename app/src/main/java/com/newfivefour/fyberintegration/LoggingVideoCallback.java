package com.newfivefour.fyberintegration;

import android.app.Activity;
import android.content.Intent;

import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.newfivefour.fyberintegration.logging.Logger;

public class LoggingVideoCallback implements RequestCallback {
    private static final String TAG = "LoggingVideoCallback";
    private Activity activity;

    public LoggingVideoCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAdAvailable(Intent intent) {
        Logger.debugOutput(TAG, "Video available");
        activity.startActivityForResult(intent, 305);
    }

    @Override
    public void onAdNotAvailable(AdFormat adFormat) {
        Logger.debugOutput(TAG, "No video available: " + adFormat);
    }

    @Override
    public void onRequestError(RequestError requestError) {
        Logger.debugOutput(TAG, "Video request error: " + requestError);
    }
}