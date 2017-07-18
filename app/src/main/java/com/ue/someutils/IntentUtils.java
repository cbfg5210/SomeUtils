package com.ue.someutils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by hujiang on 2017/7/18.
 */

public class IntentUtils {
    private static boolean isActivityValid(Activity context) {
        if (context == null) {
            return false;
        }
        if (context.isFinishing()) {
            return false;
        }
        return true;
    }

    /**
     * 安全跳转页面
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean safelyStartActivity(final Activity context, Intent intent, final String errTip) {
        if (!isActivityValid(context)) {
            return false;
        }
        if (intent == null) {
            return false;
        }
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception exp) {
            if (TextUtils.isEmpty(errTip)) {
                return false;
            }
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, errTip, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return false;
    }

    /**
     * 调用系统打开url
     *
     * @param context
     * @param url
     * @param errTip
     */
    public static void openUrlWithViewIntent(Activity context, String url, final String errTip) {
        if (!isActivityValid(context)) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        openUriWithViewIntent(context, Uri.parse(url), errTip);
    }

    /**
     * 调用系统打开uri
     *
     * @param context
     * @param uri
     * @param errTip
     */
    public static void openUriWithViewIntent(final Activity context, Uri uri, final String errTip) {
        if (!isActivityValid(context)) {
            return;
        }
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        safelyStartActivity(context, intent, errTip);
    }

    /**
     * 跳转应用市场
     *
     * @param context
     * @param errTip
     */
    public static void goToAppMarket(Activity context, String errTip) {
        if (!isActivityValid(context)) {
            return;
        }
        Uri marketUri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
        if (marketUri == null) {
            return;
        }
        openUriWithViewIntent(context, marketUri, errTip);
    }

    /**
     * 调用系统发邮件功能
     *
     * @param context
     * @param toEmail
     * @param subject
     * @param chooserTitle
     * @param errTip
     */
    public static void goToSendEmail(Activity context, String toEmail, String subject, String chooserTitle, String errTip) {
        if (!isActivityValid(context)) {
            return;
        }
        if (TextUtils.isEmpty(toEmail)) {
            return;
        }
        if (TextUtils.isEmpty(chooserTitle)) {
            return;
        }
        Uri uri = Uri.fromParts("mailto", toEmail, null);
        if (uri == null) {
            return;
        }
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

        safelyStartActivity(context, Intent.createChooser(emailIntent, chooserTitle), errTip);
    }
}
