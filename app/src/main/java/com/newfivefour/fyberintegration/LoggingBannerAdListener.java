package com.newfivefour.fyberintegration;

import android.app.Activity;

import com.fyber.ads.banners.BannerAd;
import com.fyber.ads.banners.BannerAdView;
import com.newfivefour.fyberintegration.logging.Logger;

public class LoggingBannerAdListener implements com.fyber.ads.banners.BannerAdListener {

    private Activity activity;
    private BannerAdView bannerAdView;

    public LoggingBannerAdListener(Activity activity, BannerAdView bannerAdView) {
        this.activity = activity;
        this.bannerAdView = bannerAdView;
    }

    private static final String TAG = "LoggingBannerAdListener";

    @Override
    public void onAdError(BannerAd bannerAd, String s) {
        Logger.debugOutput(TAG, "onAdError: " + s);
    }

    @Override
    public void onAdLoaded(BannerAd bannerAd) {
        Logger.debugOutput(TAG, "onAdLoaded");
        Logger.debugOutput(TAG, "Trying to load the ad");
        //bannerAd.displayInView(bannerAdView).start(activity);
    }

    @Override
    public void onAdClicked(BannerAd bannerAd) {
        Logger.debugOutput(TAG, "onAdClicked");
    }

    @Override
    public void onAdLeftApplication(BannerAd bannerAd) {
        Logger.debugOutput(TAG, "onAdLeftApplication");
    }
}