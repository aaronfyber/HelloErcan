package com.newfivefour.fyberintegration;

import android.app.Activity;
import android.content.Intent;

import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.newfivefour.fyberintegration.logging.Logger;

public class LoggingInterstitialRequestCallback implements RequestCallback {
    private static final String TAG = "LoggingInterstitialRequestCallback";
    private Activity activity;

    public LoggingInterstitialRequestCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAdAvailable(Intent intent) {
        Logger.debugOutput(TAG, "Interstitial available");
        activity.startActivityForResult(intent, 304);
    }

    @Override
    public void onAdNotAvailable(AdFormat adFormat) {
        Logger.debugOutput(TAG, "Interstitial not available: " + adFormat);
    }

    @Override
    public void onRequestError(RequestError requestError) {
        Logger.debugOutput(TAG, "Interstitial request error: " + requestError);
    }
}