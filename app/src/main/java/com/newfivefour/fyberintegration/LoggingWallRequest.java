package com.newfivefour.fyberintegration;

import android.app.Activity;
import android.content.Intent;

import com.fyber.ads.AdFormat;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.newfivefour.fyberintegration.logging.Logger;

public class LoggingWallRequest implements RequestCallback {
    private static final String TAG = "LoggingWallRequest";
    private Activity activity;

    public LoggingWallRequest(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAdAvailable(Intent intent) {
        Logger.debugOutput(TAG, "Offers are available");
        activity.startActivityForResult(intent, 303);
    }

    @Override
    public void onAdNotAvailable(AdFormat adFormat) {
        Logger.debugOutput(TAG, "No ad available");
    }

    @Override
    public void onRequestError(RequestError requestError) {
        Logger.debugOutput(TAG, "Something went wrong with the request: " + requestError.getDescription());
    }
}