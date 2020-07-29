/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface definition to load ads from OpenWrap system
 * <p> notify the
 */
public interface POWAdLoading {
    /**
     * Load the ad for given request {@link POWAdRequest} and to get loading event use
     * {@link POWAdLoading#setAdsLoaderListener(AdsLoaderListener)}
     *
     * @param request the instance of {@link POWAdRequest}
     */
    void loadAd(@NonNull POWAdRequest request);

    /**
     * Setter to get OpenWrap ad loading callbacks
     *
     * @param listener the reference {@link AdsLoaderListener}
     */
    void setAdsLoaderListener(AdsLoaderListener listener);

    /**
     * Cancel the OpenWrap Ad loading
     */
    void invalidate();

    /**
     * Listener for OpenWrap ads loading events.
     */
    interface AdsLoaderListener {
        /**
         * Called when OpenWrap Ad loader loads ad successfully.
         *
         * @param response the instance {@link POWAdResponse}
         */
        void onAdReceived(@NonNull POWAdResponse response);

        /**
         * Called when OpenWrap Ad loader fails to load ad.
         *
         * @param errorCode the error code indicating error type
         * @param errorMsg  the error message indicates the error reason
         */
        void onAdFailed(int errorCode, @Nullable String errorMsg);
    }
}
