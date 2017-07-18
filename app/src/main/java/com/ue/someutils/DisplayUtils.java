package com.ue.someutils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by hujiang on 2017/7/18.
 */

public class DisplayUtils {
    public static DisplayMetrics getScreenMetrics(Activity context) {
//        way 1:
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//        way 2:
//        Resources resources = context.getResources();
//        DisplayMetrics outMetrics = resources.getDisplayMetrics();
        return outMetrics;
    }
}
