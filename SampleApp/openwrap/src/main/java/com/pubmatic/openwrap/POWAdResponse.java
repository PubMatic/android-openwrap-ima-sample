/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class holds the response of OpenWrap
 */
public class POWAdResponse {

    private static final String TARGETING_KEY = "targeting";

    /**
     * OpenWrap response json
     */
    @NonNull
    private JSONObject responseJson;

    /**
     * Constructs the {@link POWAdResponse}
     *
     * @param responseJson the response json
     */
    public POWAdResponse(@NonNull JSONObject responseJson) {
        this.responseJson = responseJson;
    }

    /**
     * Retrieve targeting json from OpenWrap Ad response. Null in-case if OpenWrap response does
     * not contains targeting json
     *
     * @return the targeting json
     */
    @Nullable
    public JSONObject getTargeting() {
        return responseJson.optJSONObject(TARGETING_KEY);
    }

}
