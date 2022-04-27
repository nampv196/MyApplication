package com.example.myapplication.ads

import android.app.Activity
import android.content.Context
import com.example.myapplication.MyApplication
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialHelperV2 {

    private var interstitialAd: InterstitialAd? = null

    private var adRequest = AdRequest.Builder().build()

    private var myOnInterstitialCallback: OnInterstitialCallback? = null

    private var isLoading: Boolean = false

    private fun isCanNotLoadAd(): Boolean {
        return isLoading || interstitialAd != null
    }

    private fun loadRequest(context: Context) {
        if (isCanNotLoadAd()) return
        isLoading = true
        interstitialAd?.fullScreenContentCallback = null
        InterstitialAd.load(
            context,
            Ads.getInterstitialAds(),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    isLoading = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    isLoading = false
                    InterstitialHelperV2.interstitialAd = interstitialAd
                }
            })
    }

    fun loadAd(context: Context) {
        if (isCanNotLoadAd()) return
        isLoading = true
        interstitialAd?.fullScreenContentCallback = null
        InterstitialAd.load(
            context,
            Ads.getInterstitialAds(),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    isLoading = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    isLoading = false
                    InterstitialHelperV2.interstitialAd = interstitialAd
                }
            })
    }

    private fun setFullScreenContentCallback(activity: Activity) {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                interstitialAd = null
                myOnInterstitialCallback?.onWork()
                MyApplication.getInstance().setIsFullScreenAds(false)
                loadAd(activity)
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                interstitialAd = null
                myOnInterstitialCallback?.onWork()
                MyApplication.getInstance().setIsFullScreenAds(false)
                loadAd(activity)
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                MyApplication.getInstance().setIsFullScreenAds(true)
            }
        }
    }

    fun showInterstitialAd(activity: Activity, onInterstitialCallback: OnInterstitialCallback?) {
        myOnInterstitialCallback = onInterstitialCallback
        loadRequest(activity)
        if (interstitialAd != null && Ads.isInterstitialAdInvalid(activity)) {
            if (Ads.shouldShowInterstitialAd(activity)) {
                PreferencesHelper(activity).setInterstitialDisplay()
                setFullScreenContentCallback(activity)
                interstitialAd!!.show(activity)
            } else {
                myOnInterstitialCallback?.onWork()
            }
        } else {
            interstitialAd = null
            myOnInterstitialCallback?.onWork()
            loadAd(activity)
        }
    }

}