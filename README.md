# Introduction
This is a reference application showing you how you can enable OpenWrap header bidding for an in-stream video ad in an Android app. The app will make the initial ad request to OpenWrap server which runs a cloud-side auction with bids received from Prebid S2S bidders. The client receives the winning bid from the OpenWrap server and then passes on the winning bid price to GAM (Google Ad Manager). If GAM finds a campaign of higher price, GAM returns and renders the video ad. Otherwise, OpenWrap's winning bid will be sent back to the client and be rendered using IMA video player.


## Required dependencies to run this App
### 1. Development Environment

| Environment |Version|
|--------------|---------|
| Android Studio | 3.2 or higher |
| [IMA SDK](https://developers.google.com/interactive-media-ads/docs/sdks/android/client-side) | Verified with 3.x |
| minSdkVersion | 16 or higher |
| compileSdkVersion | 28 or higher |


### 2. Test Profile/Placement
This sample application uses the below test placement. 

|Placement Name|Test Data|
|--------------|---------|
| Publisher ID | 156276 |
| OpenWrap Profile ID | 2486 |
| OpenWrap Ad Unit Id | /15671365/pm_ott_video |

To get the actual placement details, please contact [support@pubmatic.com](support@pubmatic.com)

```diff
Important Note: If you are re-using the implementation on your application, 
please ensure you are using the  actual Profile ID and Pub ID associated with your account.
- PubMatic assumes no financial responsibility for going live with test placements.
```

## More about Sample application
Please refer [Getting Started](https://github.com/PubMatic/android-openwrap-ima-sample/wiki/Getting-Started) document to know about the Sample application and [Supported parameters and Testing](https://github.com/PubMatic/android-openwrap-ima-sample/wiki/Supported-Parameters-and-Testing).

## License
Copyright 2006-2020, PubMatic Inc.

Licensed under the [PubMatic License Agreement](https://github.com/PubMatic/android-openwrap-ima-sample/blob/master/LICENSE). All rights reserved.


## Support
You will need a PubMatic account to enable the ads. Please contact us via [PubMatic.com](https://pubmatic.com/)
