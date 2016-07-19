package com.newfivefour.fyberintegration;

import com.fyber.currency.VirtualCurrencyErrorResponse;
import com.fyber.currency.VirtualCurrencyResponse;
import com.fyber.requesters.RequestError;
import com.fyber.requesters.VirtualCurrencyCallback;
import com.newfivefour.fyberintegration.logging.Logger;

public class LoggingVirtualCurrencyRequest implements VirtualCurrencyCallback {
    private static final String TAG = "LoggingVirtualCurrencyRequest";

    @Override
    public void onError(VirtualCurrencyErrorResponse virtualCurrencyErrorResponse) {
        Logger.debugOutput(TAG, "VCS error received - " + virtualCurrencyErrorResponse.getErrorMessage());
    }

    @Override
    public void onSuccess(VirtualCurrencyResponse virtualCurrencyResponse) {
        double deltaOfCoins = virtualCurrencyResponse.getDeltaOfCoins();
        Logger.debugOutput(TAG, "Delta of coins " + deltaOfCoins);
    }

    @Override
    public void onRequestError(RequestError requestError) {
        Logger.debugOutput(TAG, "request error: " + requestError.getDescription());
    }
}