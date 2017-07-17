package com.ue.someutils;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.ue.someutils.base.BaseActivity;

/**
 * Created by hujiang on 2017/6/29.
 */

public class CallbackUtils {
    /**
     * onCreate时调用可能不对
     *
     * @param view
     * @return
     */
    public static boolean isViewValid(View view) {
        if (view == null) {
            return false;
        }
        if (!ViewCompat.isAttachedToWindow(view)) {
            return false;
        }
        return true;
    }

    public static void showDialogFragment(BaseActivity context, DialogFragment dialog, String tag) {
        if (!isActivityValid(context)) {
            return;
        }
        if (context.isHasSavedInstanceState()) {
            return;
        }
        if (dialog == null) {
            return;
        }
        if (dialog.isAdded()) {
            return;
        }
        dialog.show(context.getSupportFragmentManager(), tag);
    }

    public static void dismissDialogFragment(DialogFragment fragment) {
        if (fragment == null) {
            return;
        }
        if (!fragment.isAdded()) {
            return;
        }
        //avoid IllegalStateException:
        //Can not perform this action after onSaveInstanceState
        if (!fragment.isResumed()) {
            return;
        }
        fragment.dismiss();
    }

    /**
     * fragment内部回调调用
     *
     * @param fragment
     * @return
     */
    public static boolean isFragmentValid(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        if (!isActivityValid(fragment.getActivity())) {
            return false;
        }
        if (!fragment.isAdded()) {
            return false;
        }
        if (!fragment.isResumed()) {
            return false;
        }
        return true;
    }

    public static boolean isActivityValid(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (activity.isFinishing()) {
            return false;
        }
        return true;
    }
}
