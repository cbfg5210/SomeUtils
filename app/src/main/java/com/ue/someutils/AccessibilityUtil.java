package com.ue.someutils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;

public class AccessibilityUtil {

    private AccessibilityUtil() {
        throw new UnsupportedOperationException();
    }

    private static ArrayList<String> getAllAccessibilityServices(Context context) {
        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        ArrayList<String> allAccessibilityServices = new ArrayList<String>();

        String settingValue = Settings.Secure.getString(
                context.getApplicationContext().getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (null != settingValue) {
            colonSplitter.setString(settingValue);
            while (colonSplitter.hasNext()) {
                String accessabilityService = colonSplitter.next();
                allAccessibilityServices.add(accessabilityService);
            }
        }
        return allAccessibilityServices;
    }

    /**
     * @param context
     * @param serviceName = Service.class.getName();
     * @return
     */
    public static boolean isAccessibilityServiceOn(Context context, String serviceName) {
        ArrayList<String> allAccessibilityServices = getAllAccessibilityServices(context);
        StringBuilder concat = new StringBuilder();
        concat.append(context.getPackageName());
        concat.append('/');
        concat.append(serviceName);
        return allAccessibilityServices.contains(concat.toString());
    }

    public static boolean isAccessibilityServiceOn(Context context) {
        ArrayList allAccessibilityServices = getAllAccessibilityServices(context);

        if (allAccessibilityServices == null && allAccessibilityServices.size() == 0) {
            return false;
        }
        for (int i = allAccessibilityServices.size() - 1; i >= 0; --i) {
            if (((String) allAccessibilityServices.get(i)).contains(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
