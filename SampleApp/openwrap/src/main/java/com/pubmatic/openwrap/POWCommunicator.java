/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Singleton class definition responsible to handle network communication.
 * Internally it uses volley library to make network calls.
 */
class POWCommunicator {

    private static final String TAG = "POWCommunicator";
    private static POWCommunicator communicator;
    @NonNull
    private RequestQueue requestQueue;

    /**
     * Creates only instance of POWCommunicator for the entire application by using the application
     * context
     *
     * @param context the application context required to create instance of network handler
     * @return instance of {@link POWCommunicator}
     */
    @NonNull
    public static POWCommunicator getInstance(@NonNull Context context) {
        if (communicator == null) {
            synchronized (POWCommunicator.class) {
                if (communicator == null) {
                    communicator = new POWCommunicator(context);
                }
            }
        }
        return communicator;
    }

    /**
     * Constructs POBCommunicator by setting up volley request queue.
     *
     * @param context instance of application context
     */
    private POWCommunicator(@NonNull Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Sends the {@link Request.Method#GET} network request using {@link POWAdRequest} and provides callbacks based on network result.
     * Also sets tag for the request that can be further used cancel the specific request.
     *
     * @param request              the instance of {@link POWAdRequest} which is required generate request data
     * @param communicatorListener reference of {@link CommunicatorListener}, to get network result callbacks
     */
    public void requestAd(@NonNull POWAdRequest request, @Nullable CommunicatorListener communicatorListener) {
        String url = request.buildUrl();
        Log.d(TAG, "url :" + url);

        // Create request
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                response -> {
                    Log.d(TAG, "response :" + response);
                    if (communicatorListener != null) {
                        communicatorListener.onSuccess(response);
                    }
                },
                error -> {
                    Log.d(TAG, "error :" + error);
                    if (communicatorListener != null) {
                        communicatorListener.onFailure(parseVolleyError(error), error.getMessage());
                    }
                });

        // Sets network timeout through retry policy
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(request.getNetworkTimeout(),
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        // Set tag with respect to request
        jsonRequest.setTag(request.hashCode());

        // Make network call
        requestQueue.add(jsonRequest);
    }

    /**
     * Parse volley error and returns HTTP specific error codes
     *
     * @param volleyError the instance of volley error
     * @return the specific error code
     */
    private int parseVolleyError(@NonNull VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            return volleyError.networkResponse.statusCode;
        } else {
            POWError error;
            if (volleyError instanceof ServerError) {
                error = POWError.SERVER_ERROR;
            } else if (volleyError instanceof AuthFailureError) {
                error = POWError.AUTH_FAILURE_ERROR;
            } else if (volleyError instanceof ParseError) {
                error = POWError.PARSE_ERROR;
            } else if (volleyError instanceof NoConnectionError) {
                error = POWError.NO_CONNECTION_ERROR;
            } else if (volleyError instanceof TimeoutError) {
                error = POWError.TIMEOUT_ERROR;
            } else {
                error = POWError.NETWORK_ERROR;
            }
            return error.errorCode;
        }

    }

    /**
     * Cancel the ongoing network request attached to {@link POWAdRequest}
     *
     * @param request the instance of {@link POWAdRequest} to cancel the network request.
     */
    public void cancel(@NonNull POWAdRequest request) {
        requestQueue.cancelAll(request.hashCode());
    }

    /**
     * Volley Error equivalent Enum class with error codes
     */
    enum POWError {
        AUTH_FAILURE_ERROR(401),
        NETWORK_ERROR(410),
        PARSE_ERROR(204),
        SERVER_ERROR(500),
        TIMEOUT_ERROR(408),
        NO_CONNECTION_ERROR(502);

        private int errorCode;

        POWError(int errorCode) {
            this.errorCode = errorCode;
        }
    }

    /**
     * Interface definition to provide the communicator callbacks.
     */
    interface CommunicatorListener {
        /**
         * Notifies the network success with json data
         *
         * @param response the json response
         */
        void onSuccess(@NonNull JSONObject response);

        /**
         * Notifies the network failure with error details
         *
         * @param errorCode the error code
         * @param errorMsg  the the error message
         */
        void onFailure(int errorCode, @Nullable String errorMsg);
    }
}
