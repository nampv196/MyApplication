package com.videomusiceditor.addmusictovideo.ads.native_ad

import com.google.android.gms.ads.nativead.NativeAdView

interface NativeAdListener {
    fun onNativeAdLoaded(adView: NativeAdView)
    fun onAdFailedToLoad()
}