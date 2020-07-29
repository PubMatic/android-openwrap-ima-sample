package com.pubmatic.openwrap;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class POWCommunicatorTest {

    private CommunicatorListenerImp communicatorListenerImp;

    @Before
    public void setUp() {
        communicatorListenerImp = new CommunicatorListenerImp();
    }

    @Test
    public void requestAdTestSuccess() throws InterruptedException {
        Context context = ApplicationProvider.getApplicationContext();
        POWAdRequest adRequest = new POWAdRequest(POWAdRequestTest.PUB_ID, POWAdRequestTest.PROFILE_ID,
                POWAdRequestTest.AD_UNIT_ID, POWAdRequestTest.AD_SIZE);
        POWCommunicator communicator = POWCommunicator.getInstance(context);
        communicator.requestAd(adRequest, communicatorListenerImp);
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();
        Thread.sleep(5000);
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();
        Assert.assertNotNull(communicatorListenerImp.responseJson);
    }

    @Test
    public void requestAdTestFailure() throws InterruptedException {
        Context context = ApplicationProvider.getApplicationContext();
        POWAdRequest adRequest = new POWAdRequest(POWAdRequestTest.PUB_ID, POWAdRequestTest.PROFILE_ID,
                POWAdRequestTest.AD_UNIT_ID, POWAdRequestTest.AD_SIZE) {
            @Override
            public String buildUrl() {
                return "";
            }
        };
        POWCommunicator communicator = POWCommunicator.getInstance(context);
        communicator.requestAd(adRequest, communicatorListenerImp);
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();
        Thread.sleep(100);
        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();
        Assert.assertTrue(communicatorListenerImp.isFailure);
    }

    static class CommunicatorListenerImp implements POWCommunicator.CommunicatorListener {

        JSONObject responseJson;
        boolean isFailure;

        @Override
        public void onSuccess(@NonNull JSONObject response) {
            responseJson = response;
        }

        @Override
        public void onFailure(int errorCode, @Nullable String errorMsg) {
            isFailure = true;
        }
    }
}
