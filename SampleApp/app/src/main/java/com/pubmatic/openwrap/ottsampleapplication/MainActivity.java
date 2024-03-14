/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap.ottsampleapplication;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.pubmatic.openwrap.POWAdLoading;
import com.pubmatic.openwrap.POWAdRequest;
import com.pubmatic.openwrap.POWAdResponse;
import com.pubmatic.openwrap.POWAdsLoader;
import com.pubmatic.openwrap.POWConfiguration;
import com.pubmatic.openwrap.POWUtil;
import com.pubmatic.openwrap.models.POWApplicationInfo;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final POWAdRequest.POWAdSize AD_SIZE = new POWAdRequest.POWAdSize(300, 250);

    private PlayerView playerView;
    @Nullable
    private SimpleExoPlayer player;
    @Nullable
    private ImaAdsLoader adsLoader;

    @Nullable
    private POWAdsLoader owAdsLoader;

    private boolean isAdsLoaderInitialised;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize player view
        playerView = findViewById(R.id.player_view);

        // Update the GAM url by replacing required values like Ad unit id, ad size
        final String gamAdsUrl = String.format(Constants.GAM_AD_URL, Constants.AD_UNIT_ID,
                AD_SIZE.getFormattedAdSize());

        // Initialise OpenWrap Ads Loader
        owAdsLoader = new POWAdsLoader(this);

        // Create OpenWrap request with valid params
        POWAdRequest adRequest = new POWAdRequest(Constants.PUB_ID, Constants.PROFILE_ID,
                Constants.AD_UNIT_ID, AD_SIZE);

        adRequest.setTestEnable(true);

        adRequest.setUserAgent(Util.getUserAgent(this, getString(R.string.app_name)));

        // Set Application details
        POWConfiguration configuration = POWConfiguration.getInstance();
        POWApplicationInfo applicationInfo = new POWApplicationInfo(this);
        applicationInfo.setStoreURL("https://play.google.com/store/apps/details?id=com.example.lite&hl=en_IN");
        configuration.setAppInfo(applicationInfo);

        // Set listener to get loader event callbacks
        owAdsLoader.setAdsLoaderListener(new POWAdLoading.AdsLoaderListener() {
            @Override
            public void onAdReceived(@NonNull POWAdResponse response) {
                JSONArray targetingJson = response.getTargeting();
                String custParams = String.format("dp=%s&tool=%s&artid=%s&pos=%s",
                        "0", "video", "42733721", "preroll");
                String updatedGamAdsUrl = gamAdsUrl + "&cust_params=" +  URLEncoder.encode(custParams);
                if (targetingJson != null) {
                    Log.d(TAG, "targeting json :" + targetingJson);
                    updatedGamAdsUrl = updatedGamAdsUrl + POWUtil.generateEncodedQueryParams(targetingJson);
                }
                Log.d(TAG, "DFP URL :" + updatedGamAdsUrl);
                initializeAdsLoader(updatedGamAdsUrl);
            }

            @Override
            public void onAdFailed(int errorCode, @Nullable String errorMsg) {
                Log.d(TAG, "errorCode :" + errorCode + ", errorResponse" + errorMsg);
                initializeAdsLoader(gamAdsUrl);
            }
        });

        // Load OpenWrap ad
        owAdsLoader.loadAd(adRequest);
    }

    private void initializeAdsLoader(String url) {
        // Create an AdsLoader with the ad tag url.
        adsLoader = new ImaAdsLoader(this.getApplicationContext(), Uri.parse(url));
        initializePlayer();
        isAdsLoaderInitialised = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Initialize player and resumes playback for devices which has higher API level than 23
        if (isAdsLoaderInitialised && Util.SDK_INT > 23) {
            initializePlayer();
            playerView.onResume();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Pause playback and release player for devices which has higher API level than 23
        if (isAdsLoaderInitialised && Util.SDK_INT > 23) {
            playerView.onPause();
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up resources
        if (adsLoader != null) {
            adsLoader.release();
        }
        if (owAdsLoader != null) {
            owAdsLoader.invalidate();
        }
    }

    private void releasePlayer() {
        if (adsLoader != null) {
            adsLoader.setPlayer(null);
        }
        playerView.setPlayer(null);
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void initializePlayer() {
        // Create a SimpleExoPlayer and set is as the player for content and ads.
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        // Set PlayWhenReady. If true, content and ads autoplay.
        player.setPlayWhenReady(false);
        // Set player to adsloader
        if(adsLoader != null){
            adsLoader.setPlayer(player);

            // Create data source factory required for creating media source
            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(this, Util.getUserAgent(this, getString(R.string.app_name)));

            // Create media source factory required for creating media source
            ProgressiveMediaSource.Factory mediaSourceFactory =
                    new ProgressiveMediaSource.Factory(dataSourceFactory);

            // Create the MediaSource for the content you wish to play.
            MediaSource mediaSource =
                    mediaSourceFactory.createMediaSource(Uri.parse(Constants.CONTENT_URL));

            // Create the AdsMediaSource using the AdsLoader and the MediaSource.
            AdsMediaSource adsMediaSource =
                    new AdsMediaSource(mediaSource, dataSourceFactory, adsLoader, playerView);

            // Prepare the content and ad to be played with the SimpleExoPlayer.
            if (player != null) {
                player.prepare(adsMediaSource);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause playback and release player for API level 23 or below API level
        if (isAdsLoaderInitialised && Util.SDK_INT <= 23) {
            playerView.onPause();
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Initialize player and resumes playback for API level 23 or below API level
        if (isAdsLoaderInitialised && Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            playerView.onResume();
        }
    }
}