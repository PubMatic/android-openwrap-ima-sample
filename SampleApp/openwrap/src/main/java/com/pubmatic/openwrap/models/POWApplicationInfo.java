/*
 * Copyright 2006-2020, PubMatic Inc.
 *
 * Licensed under the PubMatic License Agreement. All rights reserved.
 *
 * https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE
 */

package com.pubmatic.openwrap.models;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Provides setters to pass application information like store URL, domain, IAB categories etc.
 * It is very important to provide transparency for buyers of your app inventory.
 */
public class POWApplicationInfo {

    private static final String TAG = "POWApplicationInfo";

    /**
     * Application name
     */
    private String name;

    /**
     * Package name of the host application
     */
    private String packageName;

    /**
     * Indicates the domain of the mobile application (e.g., "mygame.foo.com")
     */
    private String domain;

    /**
     * URL of application on Play store
     */
    private String storeURL;

    /**
     * Indicates whether the mobile application is a paid version or not. Possible values are:
     * false - Free version
     * true - Paid version
     */
    private Boolean paid;

    /**
     * Comma separated list of IAB categories for the application. e.g. "IAB-1, IAB-2"
     */
    private String categories;

    /**
     * PMAppInfo constructor to generate
     * application info and initialize the params with specific value
     *
     * @param context android context
     */
    public POWApplicationInfo(final Context context) {
        // Get the application name and version number
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info;
            info = manager.getPackageInfo(context.getPackageName(),
                    0);
            name = info.applicationInfo.loadLabel(manager).toString();
            packageName = context.getPackageName();
        } catch (Exception e) {
            Log.e(TAG, "Failed to retrieve app info: " + e.getLocalizedMessage());
        }
    }

    /**
     * Returns the Application package / bundle name
     *
     * @return Application package / bundle name
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Returns the application name
     *
     * @return Application name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the domain of the mobile application
     *
     * @return Domain of the mobile application
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the domain of the mobile application (e.g., &quot;mygame.foo.com&quot;)
     *
     * @param domain application domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Returns the URL of application on Play store
     *
     * @return URL of application on Play store
     */
    public String getStoreURL() {
        return storeURL;
    }

    /**
     * Sets URL of application on Play store. It is mandatory to pass a valid storeURL. It is very
     * important for platform identification.
     *
     * @param storeURL Play store URL of app
     */
    public void setStoreURL(String storeURL) {
        this.storeURL = storeURL;
    }

    /**
     * Returns the state of mobile application's paid version
     *
     * @return State of mobile application's paid version
     */
    public Boolean isPaid() {
        return paid;
    }

    /**
     * Sets whether the mobile application is a paid version or not. Possible values are:
     * false - Free version
     * true - Paid version
     *
     * @param paid state of application paid status
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * Returns the IAB category for the application
     *
     * @return IAB category for the application
     */
    public String getCategories() {
        return categories;
    }

    /**
     * Sets IAB category for the application. e.g. "IAB-1, IAB-2".
     *
     * @param categories Comma separated list of IAB categories for the application. e.g. "IAB-1, IAB-2"
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

}
