/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import com.pubmatic.openwrap.models.POWApplicationInfo;
import com.pubmatic.openwrap.models.POWUserInfo;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Provides global configurations for the OpenWrap module, e.g. location access, CCPA, GDPR, etc.
 * These configurations are globally applicable for OpenWrap module; you don't have to set these for
 * every ad request.
 */
public class POWConfiguration {

    /**
     * Private Static instance
     */
    private static POWConfiguration sSelf;
    /**
     * Object having user information, such as birth year, gender, region, etc, for more relevant ads
     */
    private POWUserInfo userInfo;
    /**
     * Object having application information, which contains various attributes about app,
     * such as application category, store URL, domain, etc, for more relevant ads.
     */
    private POWApplicationInfo appInfo;
    /**
     * Enable GDPR compliance, it indicates whether or not the ad request is GDPR(General Data Protection Regulation) compliant.
     * Note: By default, this parameter is omitted in the ad request.
     */
    private Boolean enableGDPR;
    /**
     * Set GDPR consent string to convey user consent when GDPR regulations are in effect. A valid
     * Base64 encoded consent string as per
     * https://github.com/InteractiveAdvertisingBureau/GDPR-Transparency-and-Consent-Framework.
     * The user consent string is optional, but highly recommended if the request is subject to GDPR
     * regulations (i.e. gdpr = YES). The default sense of consent under GDPR is “opt-out” and
     * as such, an omitted consent string in a request subject to GDPR would be interpreted as
     * equivalent to the user fully opting out of all defined purposes for data use by all parties.
     */
    private String gdprConsent;
    /**
     * CCPA compliant string, it helps publisher toward compliance with the California Consumer Privacy Act (CCPA).
     * For more details refer https://www.iab.com/guidelines/ccpa-framework/
     * Make sure that the string value you use is compliant with the IAB Specification, refer
     * https://iabtechlab.com/wp-content/uploads/2019/11/U.S.-Privacy-String-v1.0-IAB-Tech-Lab.pdf
     * <p>
     * If this is not set, it looks for app's UserDefault with key 'IABUSPrivacy_String'
     * If CCPA is applied through both options, the OpenWrap will honour only API property.
     * If both are not set then CCPA parameter is omitted from an ad request.
     */
    private String ccpaString;
    /**
     * Linearity type
     */
    private Linearity linearity;
    /**
     * Hash type to be applied on the advertising id befire sending it in bid request.
     */
    private HashType hashTypeForAdvertisingId = HashType.RAW;
    /**
     * Map of key-value pairs to be passed in the OpenWrap request.
     */
    private Map<String, String> customKeyValues;

    /**
     * Method to get the shared instance of OpenWrap module configuration.
     *
     * @return Shared instance of POWConfiguration
     */
    public static POWConfiguration getInstance() {
        if (sSelf == null) {
            synchronized (POWConfiguration.class) {
                sSelf = new POWConfiguration();
            }
        }
        return sSelf;
    }

    /**
     * Private constructor
     */
    private POWConfiguration() {
        linearity = Linearity.LINEAR;
    }

    /**
     * Returns the custom key-value pairs to be passed in the OpenWrap request.
     *
     * @return custom key-value pairs to be passed in the OpenWrap request.
     */
    public @Nullable
    Map<String, String> getCustomKeyValues() {
        return customKeyValues;
    }

    /**
     * Method to set the custom key-value pairs to be passed in the OpenWrap request.
     *
     * @param customKeyValues custom key-value pairs to be passed in the OpenWrap request.
     */
    public void setCustomKeyValues(@NonNull Map<String, String> customKeyValues) {
        this.customKeyValues = customKeyValues;
    }

    /**
     * Returns the Hash type to be applied on the advertising id befire sending it in bid request.
     *
     * @return Hash type
     */
    public HashType getHashTypeForAdvertisingId() {
        return hashTypeForAdvertisingId;
    }

    /**
     * Sets the Hash type to be applied on the advertising id befire sending it in bid request.
     *
     * @param hashTypeForAdvertisingId Hash type
     */
    public void setHashTypeForAdvertisingId(@NonNull HashType hashTypeForAdvertisingId) {
        this.hashTypeForAdvertisingId = hashTypeForAdvertisingId;
    }

    /**
     * Returns the Linearity type. Its value could be LINEAR / NON_LINEAR
     *
     * @return Linearity type
     */
    @NonNull
    public Linearity getLinearity() {
        return linearity;
    }

    /**
     * Sets the Linearity type. Its value could be LINEAR / NON_LINEAR
     *
     * @param linearity Linearity type
     */
    public void setLinearity(@NonNull Linearity linearity) {
        this.linearity = linearity;
    }

    /**
     * Returns CCPA compliant string
     *
     * @return CCPA compliant string
     */
    @Nullable
    public String getCCPAString() {
        return ccpaString;
    }

    /**
     * Sets the CCPA compliant string
     *
     * @param ccpaString CCPA compliant string
     */
    public void setCCPAString(@NonNull String ccpaString) {
        this.ccpaString = ccpaString;
    }

    /**
     * Returns the GDPR compliance state
     *
     * @return GDPR compliance state
     */
    public Boolean isEnableGDPR() {
        return enableGDPR;
    }

    /**
     * Sets the GDPR compliance state
     *
     * @param enableGDPR GDPR compliance state
     */
    public void setEnableGDPR(boolean enableGDPR) {
        this.enableGDPR = enableGDPR;
    }

    /**
     * Returns the GDPR consent string
     *
     * @return GDPR consent string
     */
    @Nullable
    public String getGdprConsent() {
        return gdprConsent;
    }

    /**
     * Set GDPR consent string
     *
     * @param gdprConsent GDPR consent string
     */
    public void setGdprConsent(@NonNull String gdprConsent) {
        this.gdprConsent = gdprConsent;
    }

    /**
     * Returns the user info object
     *
     * @return user info object
     */
    @Nullable
    public POWUserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * Sets the user info object
     *
     * @param info user info object
     */
    public void setUserInfo(@NonNull POWUserInfo info) {
        userInfo = info;
    }

    /**
     * Returns the application info object
     *
     * @return the application info object
     */
    @Nullable
    public POWApplicationInfo getAppInfo() {
        return appInfo;
    }

    /**
     * Sets the POWApplicationInfo object
     *
     * @param info POWApplicationInfo object
     */
    public void setAppInfo(@NonNull POWApplicationInfo info) {
        appInfo = info;
    }

    /**
     * Enum for Linearity type
     */
    public enum Linearity {
        UNKNOWN(0), LINEAR(1), NON_LINEAR(2);

        private final int value;

        Linearity(int val) {
            value = val;
        }

        /**
         * Returns the int identifier of Linearity. Possible values are:
         * 1 - LINEAR
         * 2 - NON_LINEAR
         *
         * @return int identifier of Linearity
         */
        public int getValue() {
            return value;
        }
    }

    /**
     * Enum for Advertising id hashing
     */
    public enum HashType {
        RAW(1), SHA1(2), MD5(3);

        private final int value;

        HashType(int val) {
            value = val;
        }

        /**
         * Returns the int identifier of HashType. Possible values are:
         * 1 - RAW
         * 2 - SHA1
         * 3 - MD5
         *
         * @return int identifier of HashType
         */
        public int getValue() {
            return value;
        }
    }
}