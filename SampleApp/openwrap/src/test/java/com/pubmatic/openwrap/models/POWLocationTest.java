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

import android.location.Location;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 19)
public class POWLocationTest {

    private static final float TEST_ACCURACY = 12.2f;
    private static final double TEST_LATITUDE = 11.00;
    private static final double TEST_LONGITUDE = 122.00;
    private static final String TEST_SOURCE = "user";
    private static final POWLocation.Source TEST_LOCATION_SOURCE = POWLocation.Source.IP_ADDRESS;

    @Test
    public void testAllClass() {
        // Test constructor 1
        POWLocation location = new POWLocation(TEST_LOCATION_SOURCE, TEST_LATITUDE, TEST_LONGITUDE);

        Assert.assertEquals(TEST_LATITUDE, location.getLatitude());
        Assert.assertEquals(TEST_LONGITUDE, location.getLongitude());
        Assert.assertEquals(TEST_LOCATION_SOURCE, location.getSource());

        // Test constructor 2 i.e. with android location
        Location androidLocation = new Location(TEST_SOURCE);
        androidLocation.setLatitude(TEST_LATITUDE);
        androidLocation.setLongitude(TEST_LONGITUDE);
        androidLocation.setAccuracy(TEST_ACCURACY);

        POWLocation locationTwo = new POWLocation(androidLocation);

        Assert.assertEquals(TEST_LATITUDE, locationTwo.getLatitude());
        Assert.assertEquals(TEST_LONGITUDE, locationTwo.getLongitude());
        Assert.assertEquals(getTestSource(androidLocation.getProvider()), locationTwo.getSource());
    }

    /**
     * @param provider location provider
     * @return Returns the transformed location source from string provider to Source enum
     */
    private POWLocation.Source getTestSource(String provider) {

        POWLocation.Source source;
        if (provider != null && (provider.equalsIgnoreCase("network") || provider.equalsIgnoreCase("gps") || provider.equalsIgnoreCase("wifi")))
            source = POWLocation.Source.GPS;
        else
            source = POWLocation.Source.USER;

        return source;
    }
}
