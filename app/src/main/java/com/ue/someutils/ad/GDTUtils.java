package com.ue.someutils.ad;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.socks.library.KLog;
import com.ue.someutils.BuildConfig;
import com.ue.someutils.CallbackUtils;

/**
 * Created by hujiang on 2017/7/14.
 */

public class GDTUtils {
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
        final BannerView bannerView = new BannerView(context, ADSize.BANNER, BuildConfig.GDT_APP_ID, adId);
        bannerView.setTag(adId);
        bannerView.setRefresh(30);
        bannerView.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
                if (!CallbackUtils.isActivityValid(context)) {
                    return;
                }
                adContainer.setVisibility(View.VISIBLE);
                adContainer.addView(bannerView);
            }
        });
        bannerView.loadAD();
    }

    /**
     * 返回一个banner view，一般预加载使用
     *
     * @param context
     * @param adId
     * @return
     */
    public static BannerView getBannerAdView(Activity context, String adId) {
        if (!CallbackUtils.isActivityValid(context)) {
            return null;
        }
        final BannerView bannerView = new BannerView(context, ADSize.BANNER, BuildConfig.GDT_APP_ID, adId);
        bannerView.setRefresh(30);
        bannerView.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int arg0) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerView.loadAD();

        return bannerView;
    }

    /**
     * 每次初始化比单次初始化广告的加载成功率高
     *
     * @param context
     * @param adId
     * @param listener
     */
    public void showInterstitialAD(final Activity context, String adId, final AbstractInterstitialADListener listener) {
        if (!CallbackUtils.isActivityValid(context)) {
            return;
        }
        final InterstitialAD iad = new InterstitialAD(context, BuildConfig.GDT_APP_ID, adId);
        iad.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onADReceive() {
                KLog.e("onADReceive");
                if (!CallbackUtils.isActivityValid(context)) {
                    return;
                }
                iad.show();
                if (listener != null) {
                    listener.onADReceive();
                }
            }

            @Override
            public void onNoAD(int i) {
                KLog.e("no ad,code=" + i);
            }

            @Override
            public void onADClosed() {
                super.onADClosed();
                // http://bbs.e.qq.com/forum.php?mod=viewthread&tid=1631&highlight=%E6%8F%92%E5%B1%8F%E5%B9%BF%E5%91%8A
                // onadclose是代表广告将要进行关闭，后面会有一些清理工作。
                // 只有等清理工作完毕之后才能进行广告的后续操作。所以不建议您在onadclose里面调用loadAd，
                // 如果需要此操作，可重新new一个新的实例再去loadad
                //isADLoaded = false;
                //KLog.e("iad=" + iad);
                //iad.loadAD();
                //initGDTInterstitialAD();
            }
        });
        iad.loadAD();
    }
}
