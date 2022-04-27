package com.example.myapplication.ads.banner

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.myapplication.ads.Ads
import com.google.android.gms.ads.*

class BannerHelper(private val context: Context) {
    var adView: AdView? = null
    var adSize: AdSize? = null

    fun loadBanner(layout: ViewGroup) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = layout.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()

        adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
        adView = AdView(context)

        adView?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                layout.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                layout.visibility = View.GONE
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
        layout.addView(adView)
        adView?.adUnitId = Ads.getBannerAdsId()
        adView?.adSize = adSize
        adView?.loadAd(AdRequest.Builder().build())
    }
}