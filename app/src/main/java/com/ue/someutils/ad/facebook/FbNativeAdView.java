package com.ue.someutils.ad.facebook;

import android.content.Context;
import android.content.res.TypedArray;
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

/**
 * Created by xin on 5/3/17.
 */

public class FbNativeAdView extends FrameLayout {
    private int layoutRes;

    private ImageView ivAdIcon;
    private TextView tvAdTitle;
    private TextView tvAdContent;
    private ImageView ivAdPoster;
    private TextView tvAdAction;
    private ViewGroup vgAdChoiceContainer;

    public FbNativeAdView(Context context) {
        super(context);
        init(context, null);
    }

    public FbNativeAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FbNativeAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FbNativeAdView);
        if (typedArray != null) {
            layoutRes = typedArray.getResourceId(R.styleable.FbNativeAdView_layoutRes, -1);
            typedArray.recycle();
        }
        if (layoutRes == -1) {
            throw new IllegalArgumentException("no layoutRes");
        }
        View.inflate(context, layoutRes, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ivAdIcon = (ImageView) findViewWithTag(getResources().getString(R.string.tag_ad_icon));
        tvAdTitle = (TextView) findViewWithTag(getResources().getString(R.string.tag_ad_title));
        tvAdContent = (TextView) findViewWithTag(getResources().getString(R.string.tag_ad_content));
        ivAdPoster = (ImageView) findViewWithTag(getResources().getString(R.string.tag_ad_poster));
        tvAdAction = (TextView) findViewWithTag(getResources().getString(R.string.tag_ad_action));
        vgAdChoiceContainer = (ViewGroup) findViewWithTag(getResources().getString(R.string.tag_ad_choice_container));
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
