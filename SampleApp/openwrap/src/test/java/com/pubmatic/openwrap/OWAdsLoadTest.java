package com.pubmatic.openwrap;

import android.content.Context;
import android.os.Build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1, Build.VERSION_CODES.P})
public class OWAdsLoadTest {

    public static final String PUB_ID = "156276";
    public static final int PROFILE_ID = 2486;
    public static final String AD_UNIT_ID = "/15671365/pm_ott_video";
    private AdsLoaderListenerImp adsLoaderListenerImp;

    @Before
    public void setUp() {
        adsLoaderListenerImp = new AdsLoaderListenerImp();
    }

    @Test
    public void testLoadAdForValidResponse() throws InterruptedException {
        Context appContext = ApplicationProvider.getApplicationContext();
        POWAdsLoader adsLoader = new POWAdsLoader(appContext);
        adsLoader.setAdsLoaderListener(adsLoaderListenerImp);
        POWAdRequest request = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID,
                new POWAdRequest.POWAdSize(320, 640)) {
            @Override
            public String buildUrl() {
                return "https://ow.pubmatic.com/openrtb/2.5/video?pwtplt=video&adserver=DFP&f=json&pwtmime=1&pubId=156276&profId=2486&pwtm_iu=%2F15671365%2Fpm_ott_video&pwtm_sz=640x480&pwtv=2&pwtm_url=https%3A%2F%2Fgoogle.com&pwtvc=0";
            }
        };
        adsLoader.loadAd(request);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Thread.sleep(10000);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Assert.assertTrue(adsLoaderListenerImp.adResponse.getTargeting().length() > 0);
    }

    @Test
    public void testLoadAdForEmptyResponse() throws InterruptedException {
        Context appContext = ApplicationProvider.getApplicationContext();
        POWAdsLoader adsLoader = new POWAdsLoader(appContext);
        adsLoader.setAdsLoaderListener(adsLoaderListenerImp);
        POWAdRequest request = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID,
                new POWAdRequest.POWAdSize(320, 640));
        adsLoader.loadAd(request);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Thread.sleep(5000);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Assert.assertEquals(0, adsLoaderListenerImp.adResponse.getTargeting().length());
    }

    @Test
    public void testLoadAdForError() throws InterruptedException {
        Context appContext = ApplicationProvider.getApplicationContext();
        POWAdsLoader adsLoader = new POWAdsLoader(appContext);
        adsLoader.setAdsLoaderListener(adsLoaderListenerImp);
        POWAdRequest request = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID,
                new POWAdRequest.POWAdSize(320, 640)) {
            @Override
            public String buildUrl() {
                return "";
            }
        };
        adsLoader.loadAd(request);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Thread.sleep(5000);
        Robolectric.flushBackgroundThreadScheduler();
        Robolectric.flushForegroundThreadScheduler();
        Assert.assertTrue(adsLoaderListenerImp.isFailed);
    }

    @Test
    public void testLoadAdAndInvalidate() {
        Context appContext = ApplicationProvider.getApplicationContext();
        POWAdRequest request = new POWAdRequest(PUB_ID, PROFILE_ID, AD_UNIT_ID,
                new POWAdRequest.POWAdSize(320, 640));
        POWAdsLoader adsLoader = new POWAdsLoader(appContext);
        adsLoader.setAdsLoaderListener(adsLoaderListenerImp);
        adsLoader.loadAd(request);
        adsLoader.invalidate();
        Assert.assertNotNull(adsLoader);
    }


    static class AdsLoaderListenerImp implements POWAdLoading.AdsLoaderListener {

        POWAdResponse adResponse;
        boolean isFailed;

        @Override
        public void onAdReceived(@NonNull POWAdResponse response) {
            this.adResponse = response;
        }

        @Override
        public void onAdFailed(int errorCode, @Nullable String errorMsg) {
            isFailed = true;
        }
    }
}
