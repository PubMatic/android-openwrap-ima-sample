package com.pubmatic.openwrap;

import android.content.Context;

import com.pubmatic.openwrap.models.POWApplicationInfo;
import com.pubmatic.openwrap.models.POWLocation;
import com.pubmatic.openwrap.models.POWUserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class POWAdRequestTest {

    public static final String PUB_ID = "156276";
    public static final int PROFILE_ID = 2486;
    public static final String AD_UNIT_ID = "/15671365/pm_ott_video";
    public static final POWAdRequest.POWAdSize AD_SIZE = new POWAdRequest.POWAdSize(320, 640);
    private static final String PUB_ID_KEY = "pubId";
    private static final String PROFILE_ID_KEY = "profId";
    private static final String AD_UNIT_ID_KEY = "pwtm_iu";
    private static final String AD_SIZE_KEY = "pwtm_sz";
    private static final String VERSION_ID_KEY = "pwtv";
    private static final String DEBUG_KEY = "pwtvc";
    private static final String APP_URL_KEY = "pwtm_url";
    private static final String APP_KEY = "pwtapp";
    private static final String AD_FORMAT_KEY = "pwtplt";
    private static final String RESPONSE_FORMAT_KEY = "f";
    private static final String REQUEST_MIME_KEY = "pwtmime";
    private static final String AD_SERVER_KEY = "adserver";

    @Test
    public void testRequestWithNecessaryParams() throws JSONException {
        POWAdRequest adRequest = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID, AD_SIZE);
        String url = adRequest.buildUrl();
        JSONObject queryParams = POWUtilTest.getQueryParams(url);
        Assert.assertEquals(PUB_ID, queryParams.getString(PUB_ID_KEY));
        Assert.assertEquals(PROFILE_ID, queryParams.getInt(PROFILE_ID_KEY));
        Assert.assertEquals(AD_UNIT_ID, queryParams.getString(AD_UNIT_ID_KEY));
        Assert.assertEquals(AD_SIZE.getFormattedAdSize(), queryParams.getString(AD_SIZE_KEY));
        Assert.assertEquals("DFP", queryParams.getString(AD_SERVER_KEY));
        Assert.assertEquals("video", queryParams.getString(AD_FORMAT_KEY));
        Assert.assertEquals(1, queryParams.getInt(REQUEST_MIME_KEY));
        Assert.assertEquals(1, queryParams.getInt(APP_KEY));
        Assert.assertEquals("json", queryParams.getString(RESPONSE_FORMAT_KEY));
        // This parameter might be present and result in this test case failure if whole class is
        // executed at once. However if test individually then it work
        Assert.assertEquals("", queryParams.optString(APP_URL_KEY));
    }

    @Test
    public void testRequestWithOptionalParams() throws JSONException {
        String appUrl = "https://google.com";
        int versionId = 2;
        int networkTimeout = 500;
        POWAdRequest adRequest = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID, AD_SIZE);
        adRequest.setVersionId(versionId);
        adRequest.setDebugEnable(true);
        adRequest.setNetworkTimeout(networkTimeout);

        String url = adRequest.buildUrl();
        JSONObject queryParams = POWUtilTest.getQueryParams(url);
        Assert.assertEquals(PUB_ID, queryParams.getString(PUB_ID_KEY));
        Assert.assertEquals(PROFILE_ID, queryParams.getInt(PROFILE_ID_KEY));
        Assert.assertEquals(AD_UNIT_ID, queryParams.getString(AD_UNIT_ID_KEY));
        Assert.assertEquals(AD_SIZE.getFormattedAdSize(), queryParams.getString(AD_SIZE_KEY));
        Assert.assertEquals(versionId, queryParams.getInt(VERSION_ID_KEY));
        Assert.assertEquals(1, queryParams.getInt(DEBUG_KEY));
        Assert.assertEquals("DFP", queryParams.getString(AD_SERVER_KEY));
        Assert.assertEquals("video", queryParams.getString(AD_FORMAT_KEY));
        Assert.assertEquals(1, queryParams.getInt(REQUEST_MIME_KEY));
        Assert.assertEquals("json", queryParams.getString(RESPONSE_FORMAT_KEY));
        Assert.assertEquals(networkTimeout, adRequest.getNetworkTimeout());

        JSONObject bidderJson = new JSONObject("{\"targeting\": {}}");
        adRequest.setBidderCustomParams(bidderJson);
        Assert.assertEquals(bidderJson, adRequest.getBidderCustomParams());
    }


    @Test
    public void testRequestParams() throws JSONException {
        int versionId = 2;
        int networkTimeout = 500;
        POWAdRequest adRequest = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID, AD_SIZE);
        adRequest.setVersionId(versionId);
        adRequest.setDebugEnable(true);
        adRequest.setNetworkTimeout(networkTimeout);

        JSONObject customJSON = null;
        try {
            customJSON = new JSONObject("{\n" +
                    "    \"pubmatic\": {\n" +
                    "        \"keywords\": [{\n" +
                    "            \"key\": \"dctr\",\n" +
                    "            \"value\": [\"val1\", \"val2\"]\n" +
                    "        }]\n" +
                    "    },\n" +
                    "    \"appnexus\": {\n" +
                    "        \"keywords\": [{\n" +
                    "                \"key\": \"key1\",\n" +
                    "                \"value\": [\"val1\", \"val2\"]\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"key\": \"key2\",\n" +
                    "                \"value\": [\"val1\"]\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    " \n" +
                    "}");
            adRequest.setBidderCustomParams(customJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set Application details
        Context context = ApplicationProvider.getApplicationContext();
        POWApplicationInfo applicationInfo = new POWApplicationInfo(context);
        applicationInfo.setCategories("IAB-1, IAB-2");
        applicationInfo.setDomain("Sports");
        applicationInfo.setPaid(true);
        applicationInfo.setStoreURL("https://play.google.com/store/apps/details?id=com.example.lite&hl=en_IN");
        POWConfiguration.getInstance().setAppInfo(applicationInfo);

        // Set User info
        POWUserInfo userInfo = new POWUserInfo();
        userInfo.setBirthYear(1995);
        userInfo.setCity("NewYork");
        userInfo.setCountry("USA");
        userInfo.setGender(POWUserInfo.Gender.MALE);
        userInfo.setMetro("US");
        userInfo.setZip("123");
        userInfo.setLocation(new POWLocation(POWLocation.Source.GPS, 74.58d, 63.87d));
        POWConfiguration.getInstance().setUserInfo(userInfo);

        POWConfiguration.getInstance().setHashTypeForAdvertisingId(POWConfiguration.HashType.RAW);
        POWConfiguration.getInstance().setLinearity(POWConfiguration.Linearity.LINEAR);

        // GDPR status
        POWConfiguration.getInstance().setEnableGDPR(true);

        Map customConfigKV = new HashMap();
        customConfigKV.put("hobbies", "sports,news");
        customConfigKV.put("age", "22");
        POWConfiguration.getInstance().setCustomKeyValues(customConfigKV);

        String url = adRequest.buildUrl();
        JSONObject queryParams = POWUtilTest.getQueryParams(url);

        Assert.assertEquals(1, queryParams.getInt("pwtapppd"));
        Assert.assertEquals(1, queryParams.getInt("pwtvc"));
        Assert.assertEquals(1, queryParams.getInt("pwtgdpr"));

        Assert.assertEquals(customJSON.toString(), queryParams.getString("pwtbidrprm"));
        Assert.assertEquals("org.robolectric.default", queryParams.getString("pwtappname"));
        Assert.assertEquals("IAB-1, IAB-2", queryParams.getString("pwtappcat"));
        Assert.assertEquals("Sports", queryParams.getString("pwtappdom"));
        Assert.assertEquals("https://play.google.com/store/apps/details?id=com.example.lite&hl=en_IN", queryParams.getString("pwtappurl"));
        Assert.assertEquals(1995, queryParams.getInt("pwtyob"));
        Assert.assertEquals("NewYork", queryParams.getString("pwtcity"));
        Assert.assertEquals("USA", queryParams.getString("pwtcntr"));
        Assert.assertEquals(POWUserInfo.Gender.MALE.getValue(), queryParams.getString("pwtgender"));

        Assert.assertEquals("US", queryParams.getString("pwtmet"));
        Assert.assertEquals("123", queryParams.getString("pwtzip"));
        Assert.assertEquals(POWLocation.Source.GPS.getValue(), queryParams.getInt("pwtgtype"));
        Assert.assertEquals("74.58", queryParams.getString("pwtlat"));
        Assert.assertEquals("63.87", queryParams.getString("pwtlon"));
        Assert.assertEquals(POWConfiguration.Linearity.LINEAR.getValue(), queryParams.getInt("pwtvlin"));
    }
}
