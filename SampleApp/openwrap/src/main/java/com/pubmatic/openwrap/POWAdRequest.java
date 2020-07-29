/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.pubmatic.openwrap.models.POWApplicationInfo;
import com.pubmatic.openwrap.models.POWLocation;
import com.pubmatic.openwrap.models.POWUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Model class represents OpenWrap ad request.
 * Publisher should create instance of this class and set the required/applicable parameters before
 * passing it to ad loader
 */
public class POWAdRequest {

    public static final int DEFAULT_REQUEST_TIMEOUT = 5000;
    public static final int DEFAULT_VERSION_ID = 0;
    public static final int DEFAULT_JS_ENABLE_VALUE = 1;
    private static final String TAG = "POWAdRequest";
    // OpenWrap keys
    private static final String PUB_ID_KEY = "pubId";
    private static final String PROFILE_ID_KEY = "profId";
    private static final String AD_UNIT_ID_KEY = "pwtm_iu";
    private static final String AD_SIZE_KEY = "pwtm_sz";
    private static final String VERSION_ID_KEY = "pwtv";
    private static final String DEBUG_KEY = "pwtvc";
    private static final String AD_FORMAT_KEY = "pwtplt";
    private static final String RESPONSE_FORMAT_KEY = "f";
    private static final String REQUEST_MIME_KEY = "pwtmime";
    private static final String AD_SERVER_KEY = "adserver";
    private static final String LINEARITY_KEY = "pwtvlin";
    // Device Object
    private static final String LMT_KEY = "pwtlmt";
    private static final String DNT_KEY = "pwtdnt";
    private static final String JS_KEY = "pwtjs";
    private static final String IFA_KEY = "pwtifa";
    private static final String SHA1_KEY = "pwtdpidsha1";
    private static final String MD5_KEY = "pwtdpidmd5";
    private static final String LAT_KEY = "pwtlat";
    private static final String LON_KEY = "pwtlon";
    private static final String GEO_TYPE_KEY = "pwtgtype";
    private static final String COUNTRY_KEY = "pwtcntr";
    private static final String CITY_KEY = "pwtcity";
    private static final String METRO_KEY = "pwtmet";
    private static final String ZIP_KEY = "pwtzip";
    private static final String UTC_OFFSET_KEY = "pwtuto";
    private static final String BIDDER_PARAM_KEY = "pwtbidrprm";
    // User Object
    private static final String BIRTH_YEAR_KEY = "pwtyob";
    private static final String GENDER_KEY = "pwtgender";
    // App Object
    private static final String APP_KEY = "pwtapp";
    private static final String APP_NAME_KEY = "pwtappname";
    private static final String APP_BUNDLE_KEY = "pwtappbdl";
    private static final String APP_DOMAIN_KEY = "pwtappdom";
    private static final String APP_URL_KEY = "pwtm_url";
    private static final String APP_STORE_URL_KEY = "pwtappurl";
    private static final String APP_CATEGORY_KEY = "pwtappcat";
    private static final String APP_PAID_KEY = "pwtapppd";
    // Extension param
    private static final String GDPR_KEY = "pwtgdpr";
    private static final String GDPR_CONSENT_KEY = "pwtcnst";
    private static final String CCPA_KEY = "pwtccpa";
    // Default values
    private static final String APP_PARAM_VALUE = "1";
    private static final String AD_FORMAT_VALUE = "video";
    private static final String AD_SERVER_VALUE = "DFP";
    private static final String RESPONSE_FORMAT_VALUE = "json";
    private static final String REQUEST_MIME_VALUE = "1";

    // OpenWrap Base Url
    private static final String OW_URL = "https://ow.pubmatic.com/openrtb/2.5/video";

    /**
     * OpenWrap Publisher Id
     */
    @NonNull
    private String publisherId;

    /**
     * OpenWrap Profile Id
     */
    private int profileId;

    /**
     * Request timeout in milliseconds
     */
    private int networkTimeout = DEFAULT_REQUEST_TIMEOUT;

    /**
     * OpenWrap Ad unit Id
     */
    @NonNull
    private String adUnitId;

    /**
     * Size of the video ad
     */
    @NonNull
    private POWAdSize adSize;

    /**
     * Flag to maintain OpenWrap debug mode
     */
    private Boolean debugEnable;

    /**
     * OpenWrap Profile Version id
     */
    private int versionId = DEFAULT_VERSION_ID;

    /**
     * JSON having custom parameters in the form of a key-value.
     * Example :
     * bidderCustomParams =
     * {
     * "pubmatic": {
     * "keywords": [
     * {
     * "key": "dctr",
     * "value": ["val1", "val2"]
     * }
     * ]
     * },
     * "appnexus": {
     * "keywords": [
     * {
     * "key": "key1",
     * "value": ["val1"]
     * },
     * {
     * "key": "key2",
     * "value": ["val2"]
     * }
     * ]
     * }
     * }
     */
    private JSONObject bidderCustomParams;

    /**
     * Advertising info consist of Advertising ID and LMT state
     */
    private AdvertisingIdClient.Info adInfo;

    /**
     * Constructs {@link POWAdRequest} with necessary arguments
     *
     * @param publisherId the OpenWrap Publisher Id
     * @param profileId   the OpenWrap Profile Id
     * @param adUnitId    the OpenWrap Video Ad Unit Id
     * @param adSize      the Video Ad Size
     */
    public POWAdRequest(@NonNull String publisherId, int profileId,
                        @NonNull String adUnitId, @NonNull POWAdSize adSize) {
        this.publisherId = publisherId;
        this.profileId = profileId;
        this.adUnitId = adUnitId;
        this.adSize = adSize;
    }

    /**
     * Setter to toggle OpenWrap request debug mode
     *
     * @param debugEnable the boolean value for debug mode
     */
    public void setDebugEnable(boolean debugEnable) {
        this.debugEnable = debugEnable;
    }

    /**
     * OpenWrap Ad request timeout in milliseconds
     *
     * @return the network timeout in millisecond
     */
    public int getNetworkTimeout() {
        return networkTimeout;
    }

    /**
     * Sets OpenWrap request network timeout if you want overwrite default timeout.
     * Default timeout is {@link POWAdRequest#DEFAULT_REQUEST_TIMEOUT}
     *
     * @param networkTimeout the network timeout in milliseconds
     */
    public void setNetworkTimeout(int networkTimeout) {
        this.networkTimeout = networkTimeout;
    }

    /**
     * Sets OpenWrap Profile Version Id
     *
     * @param versionId the integer OpenWrap version
     */
    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    /**
     * Builds/forms the final OpenWrap Url by adding provided request params as a part of query string
     *
     * @return final OpenWrap request url
     */
    public String buildUrl() {
        return POWUtil.buildUrlWithQueryString(OW_URL, getQueryParamJson());
    }

    private JSONObject getQueryParamJson() {
        JSONObject requestParamsJson = new JSONObject();
        try {
            POWConfiguration sharedConfiguration = POWConfiguration.getInstance();
            // Add default request params
            requestParamsJson.put(APP_KEY, APP_PARAM_VALUE);
            requestParamsJson.put(AD_FORMAT_KEY, AD_FORMAT_VALUE);
            requestParamsJson.put(AD_SERVER_KEY, AD_SERVER_VALUE);
            requestParamsJson.put(RESPONSE_FORMAT_KEY, RESPONSE_FORMAT_VALUE);

            /*
             Possible values are:
             0 - All
             1 - video/mp4
             2 - application/x-shockwave-flash (VPAID - FLASH)
             3 - video/wmv
             4 - video/h264
             5 - video/webm
             6 - application/javascript (VPAID - JS)
             7 - video/ogg
             8 - video/flv
             */
            requestParamsJson.put(REQUEST_MIME_KEY, REQUEST_MIME_VALUE);

            // Add request params from Ad request instance
            requestParamsJson.put(PUB_ID_KEY, publisherId);
            requestParamsJson.put(PROFILE_ID_KEY, profileId);
            requestParamsJson.put(AD_UNIT_ID_KEY, adUnitId);
            if(versionId > 0) {
                requestParamsJson.put(VERSION_ID_KEY, versionId);
            }
            if(debugEnable != null) {
                requestParamsJson.put(DEBUG_KEY, debugEnable ? 1 : 0);
            }
            if (adSize != null) {
                requestParamsJson.put(AD_SIZE_KEY, adSize.getFormattedAdSize());
            }

            // Add GDPR flag, if set by publisher.
            if (sharedConfiguration.isEnableGDPR() != null) {
                requestParamsJson.put(GDPR_KEY, sharedConfiguration.isEnableGDPR() ? 1 : 0);
            }

            // Add GDPR consent
            requestParamsJson.putOpt(GDPR_CONSENT_KEY, sharedConfiguration.getGdprConsent());

            // Add CCPA
            requestParamsJson.putOpt(CCPA_KEY, sharedConfiguration.getCCPAString());

            // Set Linearity
            POWConfiguration.Linearity linearity = POWConfiguration.getInstance().getLinearity();
            if (linearity != POWConfiguration.Linearity.UNKNOWN) {
                requestParamsJson.put(LINEARITY_KEY, linearity.getValue());
            }

            // Add device information parameters to request params
            addDeviceData(requestParamsJson);

            // Add user information parameters to request params
            addUserData(requestParamsJson);

            // Add app information parameters to request params
            addApplicationData(requestParamsJson);

            // Add bidder parameters
            if (bidderCustomParams != null) {
                requestParamsJson.putOpt(BIDDER_PARAM_KEY, bidderCustomParams.toString());
            }

            // Add custom parameters (set over POWConfiguration) to request params as is.
            Map<String, String> customParams = POWConfiguration.getInstance().getCustomKeyValues();
            if (customParams != null) {
                Set<String> set = customParams.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    requestParamsJson.putOpt(key, customParams.get(key));
                }
            }

        } catch (JSONException e) {
            Log.w(TAG, "Error while generating query json: " + e);
        }
        return requestParamsJson;
    }

    /**
     * Helper method to add device specific parameters in Ad request json
     *
     * @param requestParamsJson Ad request json
     */
    private void addDeviceData(@NonNull JSONObject requestParamsJson) {

        try {
            if (adInfo != null) {

                // Send lmt and dnt values.
                requestParamsJson.put(LMT_KEY, adInfo.isLimitAdTrackingEnabled() ? 1 : 0);
                requestParamsJson.put(DNT_KEY, adInfo.isLimitAdTrackingEnabled() ? 1 : 0);

                // Set advertising identifier with the provided HashType
                String advertisingId = adInfo.getId();
                POWConfiguration.HashType hashType = POWConfiguration.getInstance().getHashTypeForAdvertisingId();
                if (advertisingId != null) {

                    switch (hashType) {
                        case MD5:
                            requestParamsJson.putOpt(MD5_KEY, POWUtil.md5(advertisingId));
                            break;
                        case SHA1:
                            requestParamsJson.putOpt(SHA1_KEY, POWUtil.sha1(advertisingId));
                            break;
                        default:
                            requestParamsJson.putOpt(IFA_KEY, advertisingId);
                            break;
                    }
                }
            }

            // Send js param as 1.
            requestParamsJson.putOpt(JS_KEY, DEFAULT_JS_ENABLE_VALUE);

            // Set UTC offset
            requestParamsJson.putOpt(UTC_OFFSET_KEY, POWUtil.getTimeOffsetInMinutes());
        } catch (Exception e) {
            Log.w(TAG, "Error while generating App query json: " + e);
        }

    }

    /**
     * Helper method to add User specific parameters in Ad request json
     *
     * @param requestParamsJson Ad request json
     */
    private void addUserData(@NonNull JSONObject requestParamsJson) {

        POWUserInfo userInfo = POWConfiguration.getInstance().getUserInfo();
        if (userInfo != null) {
            try {
                // Save user's location if provided by user.
                POWLocation location = userInfo.getLocation();
                if (location != null) {
                    // Location is available
                    requestParamsJson.put(LAT_KEY, location.getLatitude());
                    requestParamsJson.put(LON_KEY, location.getLongitude());

                    // Location is user provided.
                    requestParamsJson.put(GEO_TYPE_KEY, location.getSource().getValue());
                }

                // Save country, city, metro, zip etc.
                requestParamsJson.putOpt(COUNTRY_KEY, userInfo.getCountry());
                requestParamsJson.putOpt(CITY_KEY, userInfo.getCity());
                requestParamsJson.putOpt(METRO_KEY, userInfo.getMetro());
                requestParamsJson.putOpt(ZIP_KEY, userInfo.getZip());

                // Get stringified value of gender and save it.
                requestParamsJson.putOpt(GENDER_KEY, userInfo.getGender() != null ? userInfo.getGender().getValue() : null);

                // Save user's birth year is available
                if (userInfo.getBirthYear() > 0) {
                    requestParamsJson.put(BIRTH_YEAR_KEY, userInfo.getBirthYear());
                }
            } catch (JSONException e) {
                Log.w(TAG, "Error while generating user query json: " + e);
            }
        }
    }

    /**
     * Helper method to add Application specific parameters in Ad request json
     *
     * @param requestParamsJson Ad request json
     */
    private void addApplicationData(@NonNull JSONObject requestParamsJson) {

        POWApplicationInfo appInfo = POWConfiguration.getInstance().getAppInfo();
        if (appInfo != null) {
            try {
                // Save app store url
                requestParamsJson.putOpt(APP_URL_KEY, appInfo.getStoreURL());
                requestParamsJson.putOpt(APP_STORE_URL_KEY, appInfo.getStoreURL());

                // Send app's bundle, name, domain values if provided by user.
                requestParamsJson.putOpt(APP_BUNDLE_KEY, appInfo.getPackageName());
                requestParamsJson.putOpt(APP_NAME_KEY, appInfo.getName());
                requestParamsJson.putOpt(APP_DOMAIN_KEY, appInfo.getDomain());

                // Save app categories if available
                requestParamsJson.putOpt(APP_CATEGORY_KEY, appInfo.getCategories());

                // Save app paid value
                if(appInfo.isPaid()!=null) {
                    requestParamsJson.put(APP_PAID_KEY, appInfo.isPaid() ? 1 : 0);
                }
            } catch (Exception e) {
                Log.w(TAG, "Error while generating App query json: " + e);
            }

        }
    }

    /**
     * Returns the Advertising info consist of Advertising ID and LMT state
     *
     * @return the Advertising info consist of Advertising ID and LMT state
     */
    protected AdvertisingIdClient.Info getAdvertisingInfo() {
        return adInfo;
    }

    /**
     * Setter to set the Advertising info consist of Advertising ID and LMT state, to be sent in Ad
     * request
     *
     * @param adInfo Advertising info consist of Advertising ID and LMT state
     */
    protected void setAdvertisingInfo(AdvertisingIdClient.Info adInfo) {
        this.adInfo = adInfo;
    }

    /**
     * Returns the JSON object having key-value pair for bidders
     *
     * @return JSON object having key-value pair for bidders
     */
    @Nullable
    protected JSONObject getBidderCustomParams() {
        return bidderCustomParams;
    }

    /**
     * Sets custom keywords in the form of a JSONObject. Please use below format to set the
     * partner specific keywords.
     * <p>
     * Example:
     * <p>
     * {
     * "pubmatic": {
     * "keywords": [
     * {
     * "key": "dctr",
     * "value": ["val1", "val2"]
     * }
     * ]
     * },
     * "appnexus": {
     * "keywords": [
     * {
     * "key": "key1",
     * "value": ["val1"]
     * },
     * {
     * "key": "key2",
     * "value": ["val2"]
     * }
     * ]
     * }
     * }
     *
     * @param bidderJson JSON object having key-value pair for bidders
     */
    public void setBidderCustomParams(@NonNull JSONObject bidderJson) {
        bidderCustomParams = bidderJson;
    }

    /**
     * Class to maintain OpenWrap Ad Size
     */
    public static class POWAdSize {
        /**
         * Ad Width
         */
        private int width;

        /**
         * Ad Height
         */
        private int height;

        /**
         * Constructs ad size
         *
         * @param width  the ad width
         * @param height the ad height
         */
        public POWAdSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        /**
         * Get formatted ad size value, in below string format
         * {<width>x<height>}
         *
         * @return the formatted ad size
         */
        @NonNull
        public String getFormattedAdSize() {
            return width + "x" + height;
        }
    }
}
