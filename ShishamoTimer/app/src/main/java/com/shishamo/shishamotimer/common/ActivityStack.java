package com.shishamo.shishamotimer.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Activityをスタックするクラス
 * Created by yoshizawa on 2016/06/29.
 */
public class ActivityStack {

    private static List<Activity> stackList = new ArrayList<Activity>();

    /**
     * コンストラクタ
     */
    private ActivityStack(){
    }

    /**
     * Activityをリストから除く。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void removeHistory() {
        for(Activity stack : stackList){
            if (stack == null) continue;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                stack.finish();
            }
            else{
                // 完全終了させる
                stack.finishAndRemoveTask();
            }
        }
    }

    /**
     * Activityをリストに詰める
     * @param stack
     */
    public static void stackHistory(Activity stack){
        stackList.add(stack);
    }
}
