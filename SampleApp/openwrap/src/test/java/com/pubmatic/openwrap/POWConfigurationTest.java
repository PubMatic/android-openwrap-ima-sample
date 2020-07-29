/*
 * PubMatic Inc. ("PubMatic") CONFIDENTIAL
 * Unpublished Copyright (c) 2006-2020 PubMatic, All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains the property of PubMatic. The intellectual and technical concepts contained
 * herein are proprietary to PubMatic and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained
 * from PubMatic.  Access to the source code contained herein is hereby forbidden to anyone except current PubMatic employees, managers or contractors who have executed
 * Confidentiality and Non-disclosure agreements explicitly covering such access or to such other persons whom are directly authorized by PubMatic to access the source code and are subject to confidentiality and nondisclosure obligations with respect to the source code.
 *
 * The copyright notice above does not evidence any actual or intended publication or disclosure  of  this source code, which includes
 * information that is confidential and/or proprietary, and is a trade secret, of  PubMatic.   ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE,
 * OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF PUBMATIC IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE
 * LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS
 * TO REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 */
package com.pubmatic.openwrap;

import android.content.Context;

import com.pubmatic.openwrap.POWConfiguration;
import com.pubmatic.openwrap.models.POWApplicationInfo;
import com.pubmatic.openwrap.models.POWUserInfo;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 19)
public class POWConfigurationTest {


    @Test
    public void testAllClass() {
        // Test constructor 1
        POWConfiguration configuration = POWConfiguration.getInstance();

        String inputText = "domaintest";
        configuration.setGdprConsent(inputText);
        Assert.assertEquals(inputText, configuration.getGdprConsent());

        inputText = "domaintest";
        configuration.setEnableGDPR(true);
        Assert.assertEquals(true, configuration.isEnableGDPR().booleanValue());

        inputText = "domaintest";
        configuration.setCCPAString(inputText);
        Assert.assertEquals(inputText, configuration.getCCPAString());

        configuration.setHashTypeForAdvertisingId(POWConfiguration.HashType.MD5);
        Assert.assertEquals(POWConfiguration.HashType.MD5.getValue(), configuration.getHashTypeForAdvertisingId().getValue());

        configuration.setLinearity(POWConfiguration.Linearity.LINEAR);
        Assert.assertEquals(POWConfiguration.Linearity.LINEAR.getValue(), configuration.getLinearity().getValue());

    }

    @Test
    public void testClassObjects() {
        Context context = ApplicationProvider.getApplicationContext();
        POWConfiguration configuration = POWConfiguration.getInstance();

        POWApplicationInfo appInfo = new POWApplicationInfo(context);
        configuration.setAppInfo(appInfo);
        org.junit.Assert.assertEquals(appInfo, configuration.getAppInfo());

        POWUserInfo userInfo = new POWUserInfo();
        configuration.setUserInfo(userInfo);
        org.junit.Assert.assertEquals(userInfo, configuration.getUserInfo());

        Map<String, String> map = new HashMap<>(1);
        configuration.setCustomKeyValues(map);
        org.junit.Assert.assertEquals(map, configuration.getCustomKeyValues());
    }

}
