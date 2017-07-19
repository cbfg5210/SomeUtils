package com.ue.someutils;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.ue.someutils.base.BaseActivity;

/**
 * Created by hujiang on 2017/6/29.
 */

public class CallbackUtils {
    /**
     * onCreate时调用无效
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

    /**
     * 显示DialogFragment
     * 避免NullPointerException和IllegalStateException
     *
     * @param context
     * @param dialog
     * @param tag
     */
    public static void showDialogFragment(FragmentActivity context, DialogFragment dialog, String tag) {
        if (!isActivityValid(context)) {
            return;
        }
        if (!(context instanceof BaseActivity)) {
            throw new IllegalArgumentException(context.getClass().getSimpleName() + "没有继承BaseActivity");
        }
        BaseActivity baseActivity = (BaseActivity) context;
        if (baseActivity.isHasSavedInstanceState()) {
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

    /**
     * 注销DialogFragment
     * 避免NullPointerException和IllegalStateException
     *
     * @param fragment
     */
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
     * 备注：在onResume前调用无效
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
