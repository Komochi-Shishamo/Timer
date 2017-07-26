package com.shishamo.shishamotimer.meal;

import android.animation.ObjectAnimator;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import static com.shishamo.shishamotimer.meal.StartMealActivity.ACTION_TIMER_STOPPED;
import static com.shishamo.shishamotimer.meal.StartMealActivity.MESSENGER_SERVICE_KEY;

/**
 * お食事タイマーのサービスクラスです。
 */
public class MealTimerService extends JobService {

    // ログ出力用TAG
    private static final String TAG = MealTimerService.class.getSimpleName();

    // ジョブID
    private static final int PERIODIC_JOB_ID = 1;

    /**
     * 一定間隔で実行するスケジュールを登録します。
     *
     * @param context コンテキスト
     * @param intervalMillis 実行間隔（ミリ秒）
     */
    public static void scheduleJob(Context context, long intervalMillis) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo jobInho = new JobInfo.Builder(PERIODIC_JOB_ID,
                new ComponentName(context, MealTimerService.class))
                .setPeriodic(intervalMillis)
                .setPersisted(false)    // デバイス再起動後にタスク実行はしない
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)   // ネットワーク接続問わず
                .build();

        scheduler.schedule(jobInho);
    }

    /**
     * 　一定期間で実行するスケジュールをキャンセルします。
     *
     * @param context コンテキスト
     */
    public static void cancelJob(Context context) {
        Log.d(TAG, "MealTimerService Created.");
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(PERIODIC_JOB_ID);
    }

    /**
     * 生成処理です。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MealTimerService Created.");
    }

    /**
     * 破棄処理です。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MealTimerService destroyed.");
    }

    /**
     * Jobを実行します。
     *
     * @param params Jobパラメータ
     * @return true - Jobを実行する
     */
    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d(TAG, "onStartJob");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!doJob()) {
                    Log.d(TAG, "Time is over...");
                    // Jobキャンセル通知
                    sendEndMessage();
                }
                // Job終了
                jobFinished(params, false);
            }
        }, 100);
        return true;
    }

    /**
     * Jobをキャンセルします。
     *
     * @param params Jobパラメータ
     * @return 再スケジューリングする場合はtrue、再スケジューリングしないはfalse
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob");
        return false;
    }

    /**
     * Jobを実行します。
     * ランダムに画像をフェードアウトします。
     *
     * @return Job継続の場合はtrue, Job終了の場合はfalse
     */
    private boolean doJob() {
        Food food = FoodFactory.getInstance().getViewableFood();
        if (food == null) {
            // 本来はここを通らないはずだが念のため
            return false;
        }

        // 画像をフェードアウト
        ObjectAnimator alpha = ObjectAnimator.ofFloat(food.getImage(), "alpha", 1f, 0f);
        alpha.setDuration(5000);
        alpha.start();

        // キラキラ音楽を流す
        MealSoundPlayer.getInstance().playKirakira();

        // 非表示状態に設定
        food.setViewable(false);
        // これで最後の食べ物の場合は終了する
        return !(FoodFactory.getInstance().isNoFood());
    }

    /**
     * 終了通知を送信します。
     */
    private void sendEndMessage() {
        Intent intent = new Intent();
        intent.putExtra(MESSENGER_SERVICE_KEY, true);
        intent.setAction(ACTION_TIMER_STOPPED);
        getBaseContext().sendBroadcast(intent);
    }
}
