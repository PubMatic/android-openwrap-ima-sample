/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Utility class for OpenWrap classes
 */
public class POWUtil {

    private static final String TAG = "POWUtil";

    /**
     * Method is used to generate SHA1 string
     *
     * @param string on which SHA1 to be perform
     * @return SHA string
     */
    public static String sha1(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            for (final byte b : bytes) {
                stringBuilder.append(String.format("%02X", b));
            }

            return stringBuilder.toString().toLowerCase(Locale.getDefault());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Method is used generate MD5 String
     *
     * @param string on which MD5 algorithm to be perform
     * @return MD5 string
     */
    public static String md5(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            for (final byte b : bytes) {
                stringBuilder.append(String.format("%02X", b));
            }

            return stringBuilder.toString().toLowerCase(Locale.getDefault());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Utility method to create encoded query string from targeting json
     *
     * @param targetingJson the targeting json
     * @return encoded query params
     */
    @NonNull
    public static String generateEncodedQueryParams(@NonNull JSONObject targetingJson) {
        try {
            // Generate query params on blank url
            String customParams = buildUrlWithQueryString("", targetingJson).replace("?", "");
            // encode and append the params to give url
            return URLEncoder.encode(customParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, e.toString());
            // In case if encoding is not supported then return blank
            return "";
        }
    }

    /**
     * Utility method to create query string from provided url and json object
     *
     * @param url       the url for which query string is going to be created
     * @param queryJSON the json from which query string is going to be formulated
     * @return the formulated url query string
     */
    @NonNull
    public static String buildUrlWithQueryString(@NonNull String url, @Nullable JSONObject queryJSON) {
        Uri.Builder uriBuilder = Uri.parse(url).buildUpon();
        if (queryJSON != null) {
            Iterator<String> iterator = queryJSON.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                uriBuilder.appendQueryParameter(key, String.valueOf(queryJSON.opt(key)));
            }
        }
        return uriBuilder.build().toString();
    }

    /**
     * Returns local time as the number +/- of minutes from UTC.
     *
     * @return Local time in minutes
     */
    public static int getTimeOffsetInMinutes() {
        // Calculate offset in minutes
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        return (calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000);
    }

}
