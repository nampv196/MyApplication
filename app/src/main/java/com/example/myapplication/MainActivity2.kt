package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ads.native_ad.NativeAdHelper

class MainActivity2 : AppCompatActivity() {

    companion object {
        fun instance(context: Context): Intent {
            return Intent(context, MainActivity2::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        NativeAdHelper().bindNativeAds(this, findViewById(R.id.layoutNativeAds))
    }
}