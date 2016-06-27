package com.shishamo.shishamotimer.meal;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;

/**
 * 食事のカウントダウンタイマー
 * Created by rika on 2016/06/17.
 */
public class EatingCountDownTimer extends CountDownTimer {
    // タイマー処理で使う画像
    private Context context;
    private long firstInterval;
    /**
     * コンストラクタ
     * @param millisInFuture
     * @param countDownInterval
     * @param context
     */
    public EatingCountDownTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        // 最初に呼ばれる残り時間
        this.firstInterval = millisInFuture - countDownInterval;
    }

    /**
     * 一定時間後のイベント処理
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        if (millisUntilFinished > this.firstInterval) {
            // 初回にいきなり呼ばれるので無視する
            return;
        }
        ImageView img = ((StartMealActivity)context).getNextFoods();
        // 順番に画像をフェードアウトしていく
        ObjectAnimator alpha = ObjectAnimator.ofFloat(img, "alpha", 1f, 0f);
        alpha.setDuration(5000);
        alpha.start();
        ((StartMealActivity)context).playKirakira();
    }

    /**
     * タイムアウトイベント処理
     */
    @Override
    public void onFinish() {
        // 次画面へ遷移
        Intent intent = new Intent(context, FinishMealActivity.class);
        intent.putExtra("MESSAGE", R.string.message_failed);
        context.startActivity(intent);
    }
}
