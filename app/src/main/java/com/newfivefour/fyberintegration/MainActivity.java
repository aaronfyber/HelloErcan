package com.newfivefour.fyberintegration;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.Fyber;
import com.fyber.ads.AdFormat;
import com.fyber.ads.banners.BannerAd;
import com.fyber.ads.banners.BannerAdView;
import com.fyber.annotations.FyberSDK;
import com.fyber.currency.VirtualCurrencyErrorResponse;
import com.fyber.currency.VirtualCurrencyResponse;
import com.fyber.mediation.admob.banner.AdMobNetworkBannerSizes;
import com.fyber.requesters.InterstitialRequester;
import com.fyber.requesters.OfferWallRequester;
import com.fyber.requesters.RequestCallback;
import com.fyber.requesters.RequestError;
import com.fyber.requesters.RewardedVideoRequester;
import com.fyber.requesters.VirtualCurrencyCallback;
import com.fyber.requesters.VirtualCurrencyRequester;
import com.newfivefour.fyberintegration.databinding.MainActivityBinding;
import com.newfivefour.fyberintegration.logging.DebugTextView;
import com.newfivefour.fyberintegration.logging.Logger;

@FyberSDK
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BannerAdView bannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MainActivityBinding main = DataBindingUtil.setContentView(this, R.layout.main_activity);

        Logger.init(new DebugTextView(main.debugTextView));

        Fyber.Settings settings = Fyber.with(getString(R.string.fyber_appid), this)
                .withSecurityToken(getString(R.string.fyber_securitykey))
                .start();
        Logger.debugOutput(TAG, "Initialising SDK");
        Logger.debugOutput(TAG, "User ID is: " + settings.getUserId());

        bannerAdView = new BannerAdView(MainActivity.this)
                .withNetworkSize(AdMobNetworkBannerSizes.LARGE_BANNER)
                .withListener(new BannerAdListener())
                .loadOnAttach();

        main.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Offer wall request");
                OfferWallRequester.create(new WallRequest()).request(MainActivity.this);
            }
        });

        main.bannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Creating and attaching BannerAdView to View");
                createAndShowBanner(main.bannerPlacementRelativelayout);
            }
        });

        main.virtualCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Virtual currency request");
                VirtualCurrencyRequester.create(new VirtualCurrencyRequest()).request(MainActivity.this);
            }
        });

        main.interstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterstitialRequester.create(new InterstitialRequestCallback()).request(MainActivity.this);
                Logger.debugOutput(TAG, "Interstitial request made");
            }
        });

        main.videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Video request made");
                RewardedVideoRequester.create(new VideoCallback()).request(MainActivity.this);
            }
        });

        Logger.debugOutput(TAG, "Creating and attaching BannerAdView to View");
        createAndShowBanner(main.bannerPlacementRelativelayout);
    }

    private void createAndShowBanner(ViewGroup bannerPlacement) {
        bannerPlacement.removeAllViews();
        bannerPlacement.addView(bannerAdView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bannerAdView!=null) {
            Logger.debugOutput(TAG, "Calling destory() on banner ad.");
            bannerAdView.destroy();
        }
    }

    private class BannerAdListener implements com.fyber.ads.banners.BannerAdListener {
        @Override
        public void onAdError(BannerAd bannerAd, String s) {
            Logger.debugOutput(TAG, "onAdError: " +  s);
        }

        @Override
        public void onAdLoaded(BannerAd bannerAd) {
            Logger.debugOutput(TAG, "onAdLoaded");
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

    private class InterstitialRequestCallback implements RequestCallback {

        @Override
        public void onAdAvailable(Intent intent) {
            Logger.debugOutput(TAG, "Interstitial available");
            startActivityForResult(intent, 304);
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

    private class VirtualCurrencyRequest implements VirtualCurrencyCallback {

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

    private class WallRequest implements RequestCallback {

        @Override
        public void onAdAvailable(Intent intent) {
            Logger.debugOutput(TAG, "Offers are available");
            startActivityForResult(intent, 303);
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

    private class VideoCallback implements RequestCallback {
        @Override
        public void onAdAvailable(Intent intent) {
            Logger.debugOutput(TAG, "Video available");
            startActivityForResult(intent, 305);
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
}
