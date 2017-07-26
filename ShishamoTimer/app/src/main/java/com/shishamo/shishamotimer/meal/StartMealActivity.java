package com.shishamo.shishamotimer.meal;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;
import com.shishamo.shishamotimer.common.DateUtil;
import com.shishamo.shishamotimer.common.Globals;

import java.util.Date;

import static android.app.TimePickerDialog.*;

/**
 * 食事タイマーのメイン処理
 */
public class StartMealActivity extends AppCompatActivity  {

    // ログ出力用TAG
    private static final String TAG = StartMealActivity.class.getSimpleName();

    // メッセージキー：タイマー終了通知
    public static final String MESSENGER_SERVICE_KEY = "stopService";
    public static final String ACTION_TIMER_STOPPED = "timerAction";

    // 効果音再生
    private MealSoundPlayer mPlayer;

    // Toastの背景画像
    private ImageView mToastView;

    // タイマー設定ダイアログ
    private TimePickerDialog mTimeDialog;

    // タイマー設定値
    private Date mDateTime;

    // タイマー表示テキスト
    private TextView mtxtClock;

    // アプリ共有変数
    Globals globals;

    // タイマーレシーバ
    private MealBroadcastReceiver mReceiver;

    /**
     * アクティビティ生成
     * @param savedInstanceState アクティビティ保存状態
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meal);

        // ディスプレイの向きは初期起動時の方向で固定とする
        globals = (Globals)getApplication();

        // 効果音の準備
        mPlayer = MealSoundPlayer.getInstance();

        // 画像の準備
        loadImage();

        // キーボードは非表示kk
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // タイマー初期値設定
        mtxtClock = (TextView)findViewById(R.id.txtClock);
        mDateTime = DateUtil.getSysDate();
        mtxtClock.setText(DateUtil.format(mDateTime, "kk:mm"));

        // タイマー設定ダイアログ生成
        createTimeDialog();

        // タイマーレシーバ登録
        mReceiver = new MealBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIMER_STOPPED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * foreground状態
     */
    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.loadSound(getApplicationContext());
    }

    /**
     * 一時停止
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     *　画面表示の準備完了後のイベント処理
     * @param hasFocus フォーカス状態
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // テーブルビュー設定
        globals.tableView = (FrameLayout)findViewById(R.id.table);
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 設定変更時（自動回転など）のイベント
     * @param newConfig 変更後の定義
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * アクティビティ終了
     */
    @Override
    public void finish() {
        mPlayer.stopSound();
        mPlayer.unloadSounds();
        super.finish();
    }

    /**
     * アクティビティ破棄
     */
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * 時計タップ時のイベント処理
     * @param view 対象ビュー
     */
    public void onClockTapped(View view) {
        mTimeDialog.show();
    }

    /**
     * いただきますボタンタップ時のイベント処理
     * @param view 対象ビュー
     */
    public void onStartButtonTapped(View view) {
        // 指定時間までの秒数を計算
        Date nowTime = DateUtil.getSysDate();
        long seconds = mDateTime.getTime() - nowTime.getTime();
        if (seconds < 60000) {
            // 過去や1分未満は実行不可とする
            showToast(R.string.warning_passedtime);
            updateClock(nowTime);
            return;
        }
        // 時計利用不可
        mtxtClock.setEnabled(false);
        // スタートボタン非表示
        Button btnS = (Button)this.findViewById(R.id.btnStart);
        btnS.setVisibility(View.GONE);
        // ごちそうさまタップ可能
        Button btnE = (Button)this.findViewById(R.id.btnEnd);
        btnE.setVisibility(View.VISIBLE);


        // ごはん画像の数にあわせて警告間隔を計算
        int intervalMillis = (int)seconds / 5;

        // タイマーサービス開始
        MealTimerService.scheduleJob(this, intervalMillis);

        // スタートトースト表示
        showToast(R.string.message_start);
    }

    /**
     * ごちそうさまボタンタップ時のイベント処理
     * @param view 対象ビュー
     */
    public void onEndButtonTapped(View view) {
        // 次画面へ遷移する
        goNextIntent(R.string.message_succeed);
    }

    /**
     * 停止ボタンタップ時のイベント処理
     * @param view 対象ビュー
     */
    public void onStopButtonTapped(View view) {
        // TODO 次のレベルアップで機能追加
        // タイマーを止める
        MealTimerService.cancelJob(this);
        // 画面を閉じる
        finish();
    }

    /**
     * たべもの画像を読み込んでランダム表示し、
     * タッチイベントを登録します。
     */
    private void loadImage() {
        FoodFactory factory = FoodFactory.getInstance();
        // ごはん
        ImageView img = (ImageView)findViewById(R.id.rice);
        factory.loadFood(FoodType.RICE, img);

        // 主菜
        img = (ImageView) findViewById(R.id.main);
        factory.loadFood(FoodType.MAIN, img);

        // 副菜
        img = (ImageView) findViewById(R.id.fukusai);
        factory.loadFood(FoodType.FUKUSAI, img);

        // 汁物
        img = (ImageView) findViewById(R.id.soup);
        factory.loadFood(FoodType.SOUP, img);

        // サラダ系
        img = (ImageView) findViewById(R.id.salad);
        factory.loadFood(FoodType.SALAD, img);

        // Toastの背景画像
    }

    /**
     * タイマー設定するダイアログを生成します。
     */
    private void createTimeDialog() {
        // 現在表示している時間で初期表示
        final int hour = DateUtil.getHour(mDateTime);
        final int minute = DateUtil.getMinute(mDateTime);

        mTimeDialog = new TimePickerDialog(this, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Date updateTime = DateUtil.updateDateTime(mDateTime, hourOfDay, minute);
                Date sysDate = DateUtil.getSysDate();
                if (sysDate.compareTo(updateTime) >= 0) {
                    // 現在時刻より過去は不可
                    showToast(R.string.warning_passedtime);
                    updateClock(sysDate);
                } else {
                    // 設定した時刻で更新
                    updateClock(updateTime);
                }
            }
        }, hour, minute, true);
    }

    /**
     * 指定の文言をトースト表示します。
     * @param msgId StringリソースID
     */
    private void showToast(int msgId) {
        String message = getResources().getString(msgId);
        if (message.isEmpty()) {
            // 文字列が取得できない場合は表示しない
            return;
        }
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }

    /**
     * タイマー時計を指定の時間で再表示します。
     *
     * @param time 表示する時間
     */
    private void updateClock(Date time) {
        mDateTime = time;
        mtxtClock.setText(DateUtil.format(mDateTime, "kk:mm"));
    }

    /**
     * 次画面へ遷移します。
     *
     * @param result 結果
     */
    private void goNextIntent(int result) {
        // タイマーを止める
        MealTimerService.cancelJob(this);
        mPlayer.stopSound();

        // 結果画面へ
        if (globals == null) {
            globals = (Globals) this.getApplication();
        }
        globals.mealResultId = result;
        Intent intent = new Intent(this, FinishMealActivity.class);
        startActivity(intent);

        // Activity詰める
        ActivityStack.stackHistory(this);
    }

    /**
     * お食事タイマーサービスの通信を行うブロードキャストレシーバクラス
     */
    private class MealBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 終了通知を受け取る
            boolean stopped = intent.getExtras().getBoolean(MESSENGER_SERVICE_KEY);
            if (stopped) {
                // タイマー終了時のみ処理
                goNextIntent(R.string.message_failed);
            }
        }
    }
}
