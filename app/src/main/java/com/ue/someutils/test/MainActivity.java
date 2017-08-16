package com.ue.someutils.test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.ue.someutils.NotificationUtils;
import com.ue.someutils.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.layout_notification);
        contentView.setImageViewResource(R.id.iv_notification_icon, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.tv_notification_title, getResources().getString(R.string.app_name));
        contentView.setImageViewResource(R.id.tv_notification_switch, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.tv_notification_content, "testsestestsettset");

        int notificationTxtColor = NotificationUtils.getNotificationColor(this);
        contentView.setTextColor(R.id.tv_notification_title, notificationTxtColor);
        contentView.setTextColor(R.id.tv_notification_content, notificationTxtColor);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentIntent(pendingIntent)
                .setContent(contentView);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = notificationBuilder.build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, notification);
    }
}
