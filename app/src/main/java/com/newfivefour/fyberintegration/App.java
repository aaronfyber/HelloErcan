package com.newfivefour.fyberintegration;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.newfivefour.fyberintegration.logging.Logger;

public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity created===");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity started===");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity resumed===");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity paused===");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity stopped===");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity save instance state===");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.debugOutput(activity.getClass().getSimpleName(), "===activity destroyed===");
            }
        });
    }
}
