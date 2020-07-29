/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.pubmatic.openwrap.models.POWApplicationInfo;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class definition responsible for loading OpenWrap ad. Provides loading event through {@AdsLoaderListener}
 * callbacks
 */
public class POWAdsLoader implements POWAdLoading, POWCommunicator.CommunicatorListener {

    private static final String TAG = "POWAdLoader";

    private POWAdvertisingIdClient adClient;

    @NonNull
    private POWCommunicator communicator;

    @Nullable
    private POWAdRequest adRequest;

    @Nullable
    private AdsLoaderListener adsLoaderListener;

    /**
     * Constructs the {@link POWAdsLoader} with application context which required for
     * making network call.
     *
     * @param context the instance of application context
     */
    public POWAdsLoader(@NonNull Context context) {
        adClient = new POWAdvertisingIdClient(context);
        // Set the default Application info
        if(POWConfiguration.getInstance().getAppInfo() == null) {
            POWConfiguration.getInstance().setAppInfo(new POWApplicationInfo(context));
        }
        // Create communicator
        communicator = POWCommunicator.getInstance(context.getApplicationContext());
    }

    @Override
    public void loadAd(@NonNull POWAdRequest request) {
        adRequest = request;
        // Set the advertising Info to the ad request and proceed with ad loading
        adClient.getAdvertisingInfo(new POWAdvertisingIdClient.AdvertisingIdListener() {
            @Override
            public void onAdvertisingInfoFetched(@NonNull AdvertisingIdClient.Info adInfo) {
                adRequest.setAdvertisingInfo(adInfo);
                proceedAdLoading();
            }

            @Override
            public void onAdvertisingInfoFailed() {
                proceedAdLoading();
            }
        });
    }

    @Override
    public void setAdsLoaderListener(@Nullable AdsLoaderListener listener) {
        adsLoaderListener = listener;
    }

    @Override
    public void invalidate() {
        if (adRequest != null) {
            communicator.cancel(adRequest);
            adRequest = null;
        }
    }

    /**
     * Helper method to be called in the flow of loadAd
     */
    private void proceedAdLoading() {
        if (adRequest != null) {
            communicator.requestAd(adRequest, this);
        }
    }

    @Override
    public void onSuccess(@NonNull JSONObject response) {
        Log.d(TAG, "Response: " + response);
        if (adsLoaderListener != null && adRequest != null) {
            adsLoaderListener.onAdReceived(new POWAdResponse(response));
        }
    }

    @Override
    public void onFailure(int errorCode, @Nullable String errorMsg) {
        Log.d(TAG, "errorcode: " + errorCode + ", errorMsg" + errorMsg);
        if (adsLoaderListener != null) {
            adsLoaderListener.onAdFailed(errorCode, errorMsg);
        }
    }
}
