package com.pubmatic.openwrap;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.pubmatic.openwrap.models.POWApplicationInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class POWAdvertisingIdClientTest {

    @Test
    public void testRequestWithNecessaryParams() throws JSONException, InterruptedException {
        Context context = ApplicationProvider.getApplicationContext();
        POWAdvertisingIdClient adClient = new POWAdvertisingIdClient(context);
        POWAdvertisingIdListener listener = new POWAdvertisingIdListener();
        adClient.getAdvertisingInfo(listener);
        Robolectric.flushForegroundThreadScheduler();
        Thread.sleep(5000);
        Robolectric.flushBackgroundThreadScheduler();
        Assert.assertEquals(true, listener.isFailed);
    }

    static class POWAdvertisingIdListener implements POWAdvertisingIdClient.AdvertisingIdListener {
        AdvertisingIdClient.Info adInfo;
        boolean isFailed;
        @Override
        public void onAdvertisingInfoFetched(@NonNull AdvertisingIdClient.Info info) {
            adInfo = info;
        }

        @Override
        public void onAdvertisingInfoFailed() {
            isFailed = true;
        }
    }
}
