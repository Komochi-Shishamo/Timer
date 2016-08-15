package com.shishamo.shishamotimer.common;

import android.app.Application;

/**
 * アプリ共有クラスです。
 * 画面間でパラメータを引き渡すことができないケースがあるため
 * こちらの方法を採用してみます。
 * Created by rika on 2016/08/14.
 */
public class Globals extends Application {
    // 食事タイマーの結果
    public int mealResultId;
}
