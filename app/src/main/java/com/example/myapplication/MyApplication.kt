package com.example.myapplication

import android.app.Application
import com.example.myapplication.ads.INetworkManager
import com.example.myapplication.ads.NetworkHelper
import com.example.myapplication.ads.AppOpenManager
import com.example.myapplication.ads.InterstitialHelperV2
import com.google.android.gms.ads.MobileAds

class MyApplication : Application() {

    lateinit var appOpenManager: AppOpenManager

    private var isFullScreenAds = false

    fun setIsFullScreenAds(isFullScreenAds: Boolean) {
        this.isFullScreenAds = isFullScreenAds
    }

    fun isFullScreenAds(): Boolean {
        return isFullScreenAds
    }

    companion object {

        private const val TAG = "MyApplication"

        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MobileAds.initialize(instance) {}
        appOpenManager = AppOpenManager(instance)
        InterstitialHelperV2.loadAd(this@MyApplication)
    }

}