package com.newfivefour.fyberintegration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.fyber.Fyber;
import com.fyber.ads.banners.BannerAdView;
import com.fyber.annotations.FyberSDK;
import com.fyber.mediation.admob.banner.AdMobNetworkBannerSizes;
import com.fyber.requesters.InterstitialRequester;
import com.fyber.requesters.OfferWallRequester;
import com.fyber.requesters.RewardedVideoRequester;
import com.fyber.requesters.VirtualCurrencyRequester;
import com.newfivefour.fyberintegration.databinding.MainActivityBinding;
import com.newfivefour.fyberintegration.logging.DebugTextView;
import com.newfivefour.fyberintegration.logging.Logger;

@FyberSDK
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainActivityBinding bindings;
    private BannerAdView bannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindings = DataBindingUtil.setContentView(this, R.layout.main_activity);

        Logger.init(new DebugTextView(bindings.debugTextView));

        Fyber.Settings settings = Fyber.with(getString(R.string.fyber_appid), this)
                .withSecurityToken(getString(R.string.fyber_securitykey))
                .start();
        Logger.debugOutput(TAG, "Initialising SDK");
        Logger.debugOutput(TAG, "User ID is: " + settings.getUserId());

        bindings.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Offer wall request");
                OfferWallRequester
                        .create(new LoggingWallRequest(MainActivity.this))
                        .request(MainActivity.this);
            }
        });

        bindings.bannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAndShowBanner(bindings.bannerPlacementRelativelayout);
            }
        });

        bindings.virtualCurrencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Virtual currency request");
                VirtualCurrencyRequester
                        .create(new LoggingVirtualCurrencyRequest())
                        .request(MainActivity.this);
            }
        });

        bindings.interstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterstitialRequester
                        .create(new LoggingInterstitialRequestCallback(MainActivity.this))
                        .request(MainActivity.this);
                Logger.debugOutput(TAG, "Interstitial request made");
            }
        });

        bindings.videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.debugOutput(TAG, "Video request made");
                RewardedVideoRequester
                        .create(new LoggingVideoCallback(MainActivity.this))
                        .request(MainActivity.this);
            }
        });

    }

    private void createAndShowBanner(ViewGroup bannerPlacement) {
        if(bannerAdView!=null) {
            bannerAdView.destroy();
        }
        bannerAdView = new BannerAdView(MainActivity.this).withNetworkSize(AdMobNetworkBannerSizes.BANNER);
        bannerAdView.withListener(new LoggingBannerAdListener(this, bannerAdView));
        bannerAdView.load();
        Logger.debugOutput(TAG, "Removing all views from banner layout and attaching BannerAdView to View");
        bannerPlacement.removeAllViews();
        bannerPlacement.addView(bannerAdView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createAndShowBanner(bindings.bannerPlacementRelativelayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(bannerAdView!=null) {
            Logger.debugOutput(TAG, "Calling destory() on banner ad.");
            bannerAdView.destroy();
        }
    }

}
