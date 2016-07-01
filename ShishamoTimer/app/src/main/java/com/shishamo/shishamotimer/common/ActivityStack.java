package com.shishamo.shishamotimer.common;

import android.app.Activity;

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
    public static void removeHistory() {
        for(Activity stack : stackList){
            stack.finish();
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
