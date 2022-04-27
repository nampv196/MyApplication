package com.example.myapplication.ads

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.MyApplication

object Ads {

    var startAppTimeThreshold = 3600000L

    var popupAdsGap = 180000L

    var popUpPercent: Long = 50

    var timeInterstitialAdValid = 3600000L

    var isShowAds = true

    var timeInterstitialWithAppOpenApp = 30000L

    private const val BANER_TEST_ADS = "ca-app-pub-3940256099942544/6300978111"

    private const val POPUP_TEST_ADS = "ca-app-pub-3940256099942544/1033173712"

    private const val REWARD_TEST_ADS = "ca-app-pub-3940256099942544/5224354917"

    private const val NATIVE_VIDEO_TEST_ADS = "ca-app-pub-3940256099942544/1044960115"

    private const val APP_OPEN_ADS_TEST = "ca-app-pub-3940256099942544/3419835294"


    fun getInterstitialAds(): String {
        return if (BuildConfig.DEBUG) {
            POPUP_TEST_ADS
        } else {
            ""
        }
    }

    fun getRewardedAdsID(): String {
        return if (BuildConfig.DEBUG) {
            REWARD_TEST_ADS
        } else {
            ""
        }
    }

    fun getNativeVideoAdsId(): String {
        return if (BuildConfig.DEBUG) {
            NATIVE_VIDEO_TEST_ADS
        } else {
            ""
        }
    }

    fun getBannerAdsId(): String {
        return if (BuildConfig.DEBUG) {
            BANER_TEST_ADS
        } else {
            ""
        }
    }

    fun getOpenAppAdsId(): String {
        return if (BuildConfig.DEBUG) {
            APP_OPEN_ADS_TEST
        } else {
            ""
        }
    }

    fun isInterstitialAdInvalid(context: Context): Boolean {
        return System.currentTimeMillis() - PreferencesHelper(context).getInterstitialLoaded() < timeInterstitialAdValid
    }

    fun isShowOpenAd(context: Context): Boolean {
        val preferencesHelper = PreferencesHelper(context)
        return preferencesHelper.isShowStartAd(startAppTimeThreshold) && !MyApplication.getInstance()
            .isFullScreenAds() && isValidTimeOpenAdDisplay(context) && isValidTimeAppOpenAdWithInterstitial(
            context
        )
    }

    private fun isValidTimeOpenAdDisplay(context: Context): Boolean {
        return System.currentTimeMillis() - PreferencesHelper(context).getAppOpenAdDisplay() > popupAdsGap
    }

    fun shouldShowInterstitialAd(context: Context): Boolean {
        return isValidTimeInterstitialDisplay(context) && isValidTimeInterstitialAndAppOpenAd(
            context
        ) && isDisplayPercent() && isShowAds
    }

    private fun isValidTimeInterstitialDisplay(context: Context): Boolean {
        return System.currentTimeMillis() - PreferencesHelper(context).getInterstitialDisplay() > popupAdsGap
    }

    private fun isValidTimeInterstitialAndAppOpenAd(context: Context): Boolean {
        return System.currentTimeMillis() - PreferencesHelper(context).getAppOpenAdDisplay() > timeInterstitialWithAppOpenApp
    }

    private fun isDisplayPercent(): Boolean {
        return Math.random() < popUpPercent / 100f
    }

    private fun isValidTimeAppOpenAdWithInterstitial(context: Context): Boolean {
        return System.currentTimeMillis() - PreferencesHelper(context).getInterstitialDisplay() > timeInterstitialWithAppOpenApp
    }

}