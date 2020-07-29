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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class is to fetch the Advertising Id and LMT state using Google play services and
 * provide it for OW ad request.
 */
public class POWAdvertisingIdClient {

    private static String TAG = "POWAdIdClient";
    private static AdvertisingIdClient.Info adInfo;
    private Context context;

    protected POWAdvertisingIdClient(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public void getAdvertisingInfo(@NonNull AdvertisingIdListener listener) {
        // Start the G-Play service and inform the listener
        // It will delay an Ad request (100-200 millisec) as it will be fetched from G-Play service
        if (POWAdvertisingIdClient.adInfo == null) {
            fetchAdvertisingInfo(listener);
        }
        // Start the G-Play service and inform the listener about already saved
        // This will make a request to fetch latest adInfo but immediately return the saved adInfo
        else {
            fetchAdvertisingInfo(null);
            listener.onAdvertisingInfoFetched(adInfo);
        }
    }

    private void fetchAdvertisingInfo(@Nullable AdvertisingIdListener listener) {
        new Thread(() -> {
            AdvertisingIdClient.Info info = null;
            try {
                Log.d(TAG, "Fetching the Advertising Id from GPlay service");
                info = AdvertisingIdClient.getAdvertisingIdInfo(context);
            } catch (Exception e) {
                Log.e(TAG, "Unable to fetch the Advertising Id using GPlay service: " + e.getMessage());
            }

            // Update the latest info in class member
            if (info != null) {
                adInfo = info;
                Log.d(TAG, "Fetched Advertising ID: " + adInfo.getId() +
                        ", and LMT=" + POWAdvertisingIdClient.adInfo.isLimitAdTrackingEnabled());
                // Inform the listener about success
                if (listener != null) {
                    listener.onAdvertisingInfoFetched(info);
                }
            } // Inform the listener about failure, if any
            else if (listener != null) {
                listener.onAdvertisingInfoFailed();
            }
        }).start();
    }

    /**
     * Interface to provide callback for Advertising Id and LMT state
     */
    protected interface AdvertisingIdListener {

        /**
         * Success callback provides the Advertising info consist of ID and LMT state
         *
         * @param adInfo Advertising info consist of ID and LMT state
         */
        void onAdvertisingInfoFetched(@NonNull AdvertisingIdClient.Info adInfo);

        /**
         * Failure callback called if unable to get Advertising info
         */
        void onAdvertisingInfoFailed();
    }
}
