package com.pubmatic.openwrap;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

import androidx.annotation.NonNull;

@RunWith(RobolectricTestRunner.class)
public class POWUtilTest {

    @Test
    public void testBuildUrlWithQueryString() throws JSONException {
        String url = "https://www.google.com";
        String testKey1 = "testkey1";
        String testKey2 = "testkey2";
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(testKey1, testValue1);
        jsonObject.put(testKey2, testValue2);
        String finalString = POWUtil.buildUrlWithQueryString(url, jsonObject);
        JSONObject queryJson = getQueryParams(finalString);
        Assert.assertEquals(testValue1, queryJson.getString(testKey1));
        Assert.assertEquals(testValue2, queryJson.getString(testKey2));
    }

    @NonNull
    public static JSONObject getQueryParams(@NonNull String url) throws JSONException {
        Uri uri = Uri.parse(url);
        Set<String> queryParamKeys = uri.getQueryParameterNames();
        JSONObject queryParams = new JSONObject();
        for (String key : queryParamKeys) {
            queryParams.put(key, uri.getQueryParameter(key));
        }
        return queryParams;
    }

    @Test
    public void testBuildUrlWithQueryStringWithEmptyJson() throws JSONException {
        String finalString = POWUtil.buildUrlWithQueryString("", new JSONObject());
        JSONObject queryJson = getQueryParams(finalString);
        Assert.assertEquals(0, queryJson.length());
    }

    @Test
    public void testGenerateEncodedQueryParams() throws JSONException, UnsupportedEncodingException {
        String testKey1 = "testkey1";
        String testKey2 = "testkey2";
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(testKey1, testValue1);
        jsonObject.put(testKey2, testValue2);
        String finalString = POWUtil.generateEncodedQueryParams(jsonObject);
        String decodedString = "?" + URLDecoder.decode(finalString, "UTF-8");
        JSONObject queryJson = getQueryParams(decodedString);
        Assert.assertEquals(testValue1, queryJson.getString(testKey1));
        Assert.assertEquals(testValue2, queryJson.getString(testKey2));
    }

    @Test
    public void testGenerateEncodedQueryParamsWithEmptyJSON() throws JSONException, UnsupportedEncodingException {
        JSONObject emptyJson = new JSONObject();
        String finalString = POWUtil.generateEncodedQueryParams(emptyJson);
        Assert.assertEquals(0, finalString.length());
    }
}
