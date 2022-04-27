package com.example.myapplication.ads

import android.content.Context

class PreferencesHelper(context: Context) {

    companion object {

        const val APP_PREF = "app_pref"

        const val APP_OPEN_AD_DISPLAY = "app_open_ad_display"

        const val INTERSTITIAL_DISPLAY = "interstitial_display"

        const val OPEN_APP_FIRST_TIME = "open_app_first_time"

        const val KEY_INTERSTITIAL_AD_LOADED = "interstitial_ad_loaded"
    }

    private val appSharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)


    fun getAppOpenAdDisplay(): Long {
        return appSharedPreferences.getLong(APP_OPEN_AD_DISPLAY, 0)
    }

    fun setAppOpenAdDisPlay() {
        appSharedPreferences.edit()
            .putLong(APP_OPEN_AD_DISPLAY, System.currentTimeMillis()).apply()
    }

    fun getInterstitialDisplay(): Long {
        return appSharedPreferences.getLong(INTERSTITIAL_DISPLAY, 0)
    }

    fun setInterstitialDisplay() {
        appSharedPreferences.edit()
            .putLong(INTERSTITIAL_DISPLAY, System.currentTimeMillis()).apply()
    }


    fun isShowStartAd(timeThreshold: Long): Boolean {
        return System.currentTimeMillis() - appSharedPreferences.getLong(
            OPEN_APP_FIRST_TIME,
            0
        ) >= timeThreshold
    }

    fun getInterstitialLoaded(): Long {
        return appSharedPreferences.getLong(KEY_INTERSTITIAL_AD_LOADED, System.currentTimeMillis())
    }

}