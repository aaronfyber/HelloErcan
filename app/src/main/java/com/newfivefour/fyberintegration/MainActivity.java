package com.newfivefour.fyberintegration;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

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
import com.fyber.requesters.VirtualCurrencyCallback;
import com.fyber.requesters.VirtualCurrencyRequester;
import com.newfivefour.fyberintegration.databinding.MainActivityBinding;
import com.newfivefour.fyberintegration.logging.DebugTextView;
import com.newfivefour.fyberintegration.logging.Logger;

@FyberSDK
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ScrollView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding main = DataBindingUtil.setContentView(this, R.layout.main_activity);
        this.debugView = main.debugScrollView;
        Logger.init(new DebugTextView(main.debugTextView));
        final Fyber.Settings settings = Fyber.with(getString(R.string.fyber_appid), this)
                .withSecurityToken(getString(R.string.fyber_securitykey))
                .start();
        Logger.debugOutput(TAG, "Initialising Fyber SDK");
        Logger.debugOutput(TAG, "User ID is: " + settings.getUserId());

        main.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Offer wall clicked");
                OfferWallRequester.create(new WallRequest()).request(MainActivity.this);
            }
        });

        main.virtualCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Virtual currency request pressed");
                VirtualCurrencyRequester.create(new VirtualCurrencyRequest()).request(MainActivity.this);
            }
        });

        BannerAdView bannerAdView = new BannerAdView(this)
                .withNetworkSize(AdMobNetworkBannerSizes.LARGE_BANNER)
                .withListener(new BannerAdListener())
                .loadOnAttach();
        main.bannerPlacement.addView(bannerAdView);
        Logger.debugOutput(TAG, "Attaching BannerAdView to View");

        InterstitialRequester.create(new InterstitialRequestCallback()).request(this);
        Logger.debugOutput(TAG, "Interstitial request made");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add("Debug");
        mi.setShowAsAction(MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(debugView.getVisibility()==View.VISIBLE) {
            debugView.setVisibility(View.INVISIBLE);
        } else {
            debugView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
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
            Logger.debugOutput(TAG, "Interstitial error available");
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
}
