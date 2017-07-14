package com.ue.someutils.ad;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;
import com.socks.library.KLog;
import com.ue.someutils.BuildConfig;
import com.ue.someutils.CallbackUtils;

/**
 * Created by hujiang on 2017/7/4.
 */

public class AdmobUtils {
    private static final String[] TEST_DEVICES = new String[]{
            "69E4F826CC7474257A5BA1E4ABC61B05",
            "D678AD121EACA40FF80CB7E54F469439"
    };

    /**
     * @return 返回添加了测试设备的AdRequest
     */
    private static AdRequest getAdRequest() {
        AdRequest.Builder builder = new AdRequest.Builder();
        if (BuildConfig.DEBUG) {
            builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
            for (int i = 0, len = TEST_DEVICES.length; i < len; i++) {
                builder.addTestDevice(TEST_DEVICES[i]);
            }
        }
        AdRequest adRequest = builder.build();
        return adRequest;
    }

    /**
     * 代码添加广告
     *
     * @param context
     * @param adContainer
     * @param adSize
     * @param adId
     */
    public static void showNativeAd(final Activity context, final ViewGroup adContainer, AdSize adSize, String adId) {
        if (!CallbackUtils.isActivityValid(context)) {
            return;
        }
        if (adContainer == null) {
            return;
        }
        View view = adContainer.findViewWithTag(adId);
        KLog.e("view=" + view);
        if (view != null) {
            return;
        }

        final NativeExpressAdView adExitNative = new NativeExpressAdView(context);
        adExitNative.setTag(adId);
        adExitNative.setAdUnitId(adId);
        adExitNative.setAdSize(adSize);

        // Set its video options.
        adExitNative.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        adExitNative.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("AdListener", "onAdLoaded");
                if (!CallbackUtils.isActivityValid(context)) {
                    return;
                }
                adContainer.setVisibility(View.VISIBLE);
                adContainer.addView(adExitNative);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.e("AdListener", "onAdFailedToLoad,code=" + i);
            }
        });

        adExitNative.loadAd(getAdRequest());
    }

    /**
     * xml添加广告
     *
     * @param adExitNative
     */
    public static void showNativeAd(final Activity context, final NativeExpressAdView adExitNative) {
        if (!CallbackUtils.isActivityValid(context)) {
            return;
        }
        if (adExitNative == null) {
            return;
        }
        // Set its video options.
        adExitNative.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        adExitNative.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                KLog.e("onAdLoaded");
                if (!CallbackUtils.isActivityValid(context)) {
                    return;
                }
                adExitNative.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                KLog.e("onAdFailedToLoad,code=" + i);
            }
        });

        adExitNative.loadAd(getAdRequest());
    }

    /**
     * 代码添加banner广告
     *
     * @param context
     * @param adContainer
     * @param adId
     */
    public static void showBannerAd(final Activity context, final ViewGroup adContainer, String adId) {
        if (!CallbackUtils.isActivityValid(context)) {
            return;
        }
        if (adContainer == null) {
            return;
        }
        View view = adContainer.findViewWithTag(adId);
        KLog.e("view=" + view);
        if (view != null) {
            return;
        }

        final AdView mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(adId);
        mAdView.setTag(adId);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (!CallbackUtils.isActivityValid(context)) {
                    return;
                }
                adContainer.setVisibility(View.VISIBLE);
                adContainer.addView(mAdView);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                KLog.e("onAdFailedToLoad,code=" + i);
            }
        });

        mAdView.loadAd(getAdRequest());
    }

    /**
     * 返回初始化了的原生广告，一般预加载使用
     *
     * @param context
     * @param adSize
     * @param adId
     * @return
     */
    public static NativeExpressAdView getInitedNativeAdView(Activity context, AdSize adSize, String adId) {
        if (!CallbackUtils.isActivityValid(context)) {
            return null;
        }
        NativeExpressAdView adExitNative = new NativeExpressAdView(context);
        adExitNative.setAdUnitId(adId);
        adExitNative.setAdSize(adSize);

        // Set its video options.
        adExitNative.setVideoOptions(new VideoOptions.Builder()
                .setStartMuted(true)
                .build());

        // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
        // loading.
        adExitNative.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                KLog.e("onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                KLog.e("onAdFailedToLoad,code=" + i);
            }
        });
        adExitNative.loadAd(getAdRequest());

        return adExitNative;
    }
}
