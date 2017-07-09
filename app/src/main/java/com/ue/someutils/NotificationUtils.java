package com.ue.someutils;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

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
        Notification notification = builder.build();

        if (notification.contentView == null) {
            return DEF_NOTIFICATION_COLOR;
        }

        int layoutId = notification.contentView.getLayoutId();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title) != null) {
            Log.e(TAG, "getNotificationColor,find notification title view");
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color = DEF_NOTIFICATION_COLOR;
        LinkedList<ViewGroup> viewGroups = new LinkedList<>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size() > 0) {
            ViewGroup viewGroup1 = viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                } else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor() != -1) {
                        Log.e(TAG, "findColor,find notification title view");
                        color = ((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }
}
