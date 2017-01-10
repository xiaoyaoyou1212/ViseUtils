package com.vise.utils.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.vise.log.ViseLog;

import java.util.List;

/**
 * Created by xyy on 16/4/10.
 */
public class ActivityUtil {

    public static void startForwardActivity(Activity context, Class<?> forwardActivity) {
        startForwardActivity(context, forwardActivity, false);
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Bundle bundle, Boolean
            isFinish, int animIn, int animOut) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
        try {
            context.overridePendingTransition(animIn, animOut);
        } catch (Exception e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
    }

    public static void startForwardActivity(Activity context, Class<?> forwardActivity, Bundle bundle, Boolean
            isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context, Class<?> forwardActivity, int requestCode, Bundle
            bundle, Boolean isFinish) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (isFinish) {
            context.finish();
        }
    }

    public static void startForResultActivity(Activity context, Class<?> forwardActivity, int requestCode, Bundle
            bundle, Boolean isFinish, int animIn, int animOut) {
        Intent intent = new Intent(context, forwardActivity);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (isFinish) {
            context.finish();
        }
        try {
            context.overridePendingTransition(animIn, animOut);
        } catch (Exception e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
    }

    /**
     * @param @param  context
     * @param @return 设定文件
     * @return String    返回类名
     * @Title: getTopActivity
     * @Description: 获取栈顶activity
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

        if (runningTaskInfo != null)
            return (runningTaskInfo.get(0).topActivity.getClassName())
                    .toString();
        else
            return "";
    }

    /**
     * 判断某一Activity是否在当前栈顶
     *
     * @return true 当前Activity在栈顶，即在最前端显示
     * false 当前Activity不在栈顶，即在后台运行
     */
    public static boolean isTopActivity(Context context, String className) {
        final String topActivity = getTopActivity(context);
        if (className.equals(topActivity))
            return true;
        return false;
    }

    /**
     * 设置Activity全屏显示。
     *
     * @param activity Activity引用
     * @param isFull   true为全屏，false为非全屏
     */
    public static void setFullScreen(Activity activity, boolean isFull) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (isFull) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 默认隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(Activity activity, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏Activity的系统默认标题栏
     *
     * @param activity Activity对象
     */
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 强制设置Activity的显示方向为垂直方向。
     *
     * @param activity Activity对象
     */
    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 强制设置Activity的显示方向为横向。
     *
     * @param activity Activity对象
     */
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 使UI适配输入法
     *
     * @param activity
     */
    public static void adjustSoftInput(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
