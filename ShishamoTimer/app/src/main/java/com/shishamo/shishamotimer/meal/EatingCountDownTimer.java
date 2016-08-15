package com.shishamo.shishamotimer.meal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.Globals;

/**
 * 食事のカウントダウンタイマー
 * Created by rika on 2016/06/17.
 */
public class EatingCountDownTimer extends CountDownTimer {
    // タイマー処理で使う画像
    private StartMealActivity context;
    private long firstInterval;
    // 残り時間
    private long countMillis;
    /**
     * コンストラクタ
     * @param millisInFuture
     * @param countDownInterval
     * @param context
     */
    public EatingCountDownTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = (StartMealActivity)context;
        // 最初に呼ばれる残り時間
        this.firstInterval = millisInFuture - countDownInterval;
    }
    /**
     * 一定時間後のイベント処理
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        // 残り時間を退避
        countMillis = millisUntilFinished;

        if (millisUntilFinished > this.firstInterval) {
            // 初回にいきなり呼ばれるので無視する
            return;
        }
        ImageView img = context.getNextFoods();
        // 順番に画像をフェードアウトしていく
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img, "alpha", 1f, 0f);
        alpha.setDuration(5000);
        alpha.start();
        context.playKirakira();
    }

    /**
     * タイムアウトイベント処理
     */
    @Override
    public void onFinish() {
        // 次画面へ遷移
        context.GoNextIntent();
    }
}
