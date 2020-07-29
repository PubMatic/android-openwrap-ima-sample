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
package com.pubmatic.openwrap.models;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 19)
public class POWUserInfoTest {


    @Test
    public void testAllClass() {
        // Test constructor 1
        POWUserInfo userInfo = new POWUserInfo();

        String inputText = "domaintest";
        userInfo.setZip(inputText);
        Assert.assertEquals(inputText, userInfo.getZip());

        inputText = "domaintest";
        userInfo.setMetro(inputText);
        Assert.assertEquals(inputText, userInfo.getMetro());

        inputText = "domaintest";
        userInfo.setCountry(inputText);
        Assert.assertEquals(inputText, userInfo.getCountry());

        inputText = "domaintest";
        userInfo.setCity(inputText);
        Assert.assertEquals(inputText, userInfo.getCity());

        userInfo.setGender(POWUserInfo.Gender.MALE);
        Assert.assertEquals(POWUserInfo.Gender.MALE.getValue(), userInfo.getGender().getValue());

        userInfo.setBirthYear(1995);
        Assert.assertEquals(1995, userInfo.getBirthYear());

        userInfo.setLocation(new POWLocation(POWLocation.Source.GPS, 10d, 20d));
        POWLocation sdkLocation = userInfo.getLocation();
        Assert.assertEquals(POWLocation.Source.GPS.getValue(), sdkLocation.getSource().getValue());
        Assert.assertEquals(10d, sdkLocation.getLatitude());
        Assert.assertEquals(20d, sdkLocation.getLongitude());

    }

}
