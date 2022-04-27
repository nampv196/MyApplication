package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.example.myapplication.ads.InterstitialHelperV2
import com.example.myapplication.ads.OnInterstitialCallback
import com.example.myapplication.ads.banner.BannerHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BannerHelper(this).loadBanner(findViewById<FrameLayout>(R.id.layout_ads))

        findViewById<View>(R.id.btn_next_screen).setOnClickListener {
            InterstitialHelperV2.showInterstitialAd(this, object : OnInterstitialCallback{
                override fun onWork() {
                    startActivity(MainActivity2.instance(this@MainActivity))
                }
            })
        }

    }
}