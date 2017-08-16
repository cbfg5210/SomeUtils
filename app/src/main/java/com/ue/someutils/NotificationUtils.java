package com.ue.someutils;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;

/**
 * Created by hujiang on 2017/6/26.
 */

public class NotificationUtils {
    private static final String TAG = NotificationUtils.class.getSimpleName();
    private static final int DEF_NOTIFICATION_COLOR = Color.GRAY;

    /**
     * 获取通知栏颜色
     *
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentText("sfsdf");
        Notification notification = builder.build();

        if (notification.contentView == null) {
            return DEF_NOTIFICATION_COLOR;
        }
        KLog.e("contentView!=null");

        int layoutId = notification.contentView.getLayoutId();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title) != null) {
            Log.e(TAG, "getNotificationColor,find notification title view");
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return DEF_NOTIFICATION_COLOR;
    }
}
