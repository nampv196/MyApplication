package com.example.myapplication.ads.native_ad

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.ads.Ads
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.videomusiceditor.addmusictovideo.ads.native_ad.NativeAdListener

class NativeAdHelper{

    var nativeAdListener: NativeAdListener? = null

    private var adLoader: AdLoader? = null

    private var nativeAd: NativeAd? = null


    fun bindNativeAds(activity: Activity, layout: ViewGroup) {
        loadNativeAd(activity, object : NativeAdListener {
            override fun onNativeAdLoaded(adView: NativeAdView) {
                layout.removeAllViews()
                layout.addView(adView)
                layout.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad() {
                layout.visibility = View.GONE
            }
        })
    }

     private fun loadNativeAd(activity: Activity, nativeAdListener: NativeAdListener) {
        this.nativeAdListener = nativeAdListener
        adLoader = AdLoader.Builder(activity, Ads.getNativeVideoAdsId())
            .forNativeAd { ad: NativeAd ->
                if (activity.isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
                    ad.destroy()
                    return@forNativeAd
                }
                nativeAd?.destroy()
                nativeAd = ad

                nativeAd?.let {
                    val adView = activity.layoutInflater
                        .inflate(R.layout.native_ads_template, null) as NativeAdView
                    populateUnifiedNativeAdView(it, adView)
                    nativeAdListener.onNativeAdLoaded(adView)
                    this.nativeAdListener = null
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    nativeAdListener.onAdFailedToLoad()
                    this@NativeAdHelper.nativeAdListener = null
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
        adLoader?.loadAd(AdRequest.Builder().build())
    }

    fun release() {
        nativeAd?.destroy()
        nativeAdListener = null
    }

    private fun populateUnifiedNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView =
            adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        nativeAd.mediaContent?.let {
            adView.mediaView?.setMediaContent(it)
        }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        } else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView?.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon?.drawable
            )
            adView.iconView?.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView?.visibility = View.INVISIBLE
        } else {
            adView.priceView?.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView?.visibility = View.INVISIBLE
        } else {
            adView.storeView?.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView?.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView?.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView?.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView?.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)
    }

}