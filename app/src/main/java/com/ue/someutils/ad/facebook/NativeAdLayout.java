package com.ue.someutils.ad.facebook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.ads.AdChoicesView;
import com.facebook.ads.NativeAd;
import com.ue.someutils.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xin on 5/3/17.
 */

//根据xml布局替换FrameLayout
public class NativeAdLayout extends FrameLayout {

    @BindView(R.id.iv_ad_icon)
    ImageView ivAdIcon;
    @BindView(R.id.tv_ad_title)
    TextView tvAdTitle;
    @BindView(R.id.tv_ad_content)
    TextView tvAdContent;
    @BindView(R.id.iv_ad_poster)
    ImageView ivAdPoster;
    @BindView(R.id.tv_ad_action)
    TextView tvAdAction;
    @BindView(R.id.vg_ad_choice_container)
    ViewGroup vgAdChoiceContainer;

    public NativeAdLayout(Context context) {
        super(context);
        init(context, null);
    }

    public NativeAdLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NativeAdLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void showFacebookAd(NativeAd nativeAd) {
        if (nativeAd == null) {
            return;
        }
        if (!nativeAd.isAdLoaded()) {
            return;
        }

        // Set the Text.
        tvAdTitle.setText(nativeAd.getAdTitle());
        tvAdContent.setText(nativeAd.getAdBody());
        tvAdAction.setText(nativeAd.getAdCallToAction());

        // Download and display the ad icon.
        NativeAd.Image adIcon = nativeAd.getAdIcon();
        NativeAd.downloadAndDisplayImage(adIcon, ivAdIcon);

        // Download and display the cover image.
        NativeAd.Image adCoverImage = nativeAd.getAdCoverImage();
        NativeAd.downloadAndDisplayImage(adCoverImage, ivAdPoster);

        // Add the AdChoices icon
        AdChoicesView adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
        vgAdChoiceContainer.addView(adChoicesView);

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(ivAdPoster);
        clickableViews.add(ivAdIcon);
        clickableViews.add(tvAdTitle);
        clickableViews.add(tvAdContent);
        clickableViews.add(tvAdAction);
        nativeAd.registerViewForInteraction(this, clickableViews);
    }
}
