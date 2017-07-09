package com.ue.someutils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by xin on 6/20/17.
 */

public final class ProcessUtil {

    private ProcessUtil() {
    }

    /**
     * @param context 上下文
     * @return 当前进程名
     */
    public static String getProcessName(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoApp = am.getRunningAppProcesses();
        if (infoApp == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : infoApp) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}
