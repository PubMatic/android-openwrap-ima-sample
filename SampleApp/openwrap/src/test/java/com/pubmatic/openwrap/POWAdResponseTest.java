package com.pubmatic.openwrap;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class POWAdResponseTest {

    public static final String TARGETING_KEY = "targeting";
    public static final String TEST_TARGETING_RESPONSE = "{\"targeting\":{\"pwtbst\":\"1\",\"pwtcid\":\"2a96119c-1537-43ac-b4a0-b37e6a615abd\",\"pwtcpath\":\"/cache\",\"pwtcurl\":\"https://ow.pubmatic.com\",\"pwtecp\":\"3.00\",\"pwtpid\":\"pubmatic\",\"pwtplt\":\"video\",\"pwtprofid\":\"2486\",\"pwtpubid\":\"156276\",\"pwtsid\":\"/15671365/pm_ott_video\",\"pwtsz\":\"0x0\",\"pwtverid\":\"2\"}}";

    @Test
    public void testTargetingWithValidJSON() throws JSONException {
        JSONObject responseJson = new JSONObject(TEST_TARGETING_RESPONSE);
        POWAdResponse adResponse = new POWAdResponse(responseJson);
        Assert.assertEquals(responseJson.getJSONObject(TARGETING_KEY), adResponse.getTargeting());
    }

    @Test
    public void testTargetingWithInValidJSON() throws JSONException {
        JSONObject responseJson = new JSONObject("{}");
        POWAdResponse adResponse = new POWAdResponse(responseJson);
        Assert.assertNull(adResponse.getTargeting());
    }
}
