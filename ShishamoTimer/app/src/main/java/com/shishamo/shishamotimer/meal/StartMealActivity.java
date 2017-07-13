package com.shishamo.shishamotimer.meal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;
import com.shishamo.shishamotimer.common.Globals;

/**
 * 食事タイマーのメイン処理
 */
public class StartMealActivity extends AppCompatActivity  {

    // ログ出力用TAG
    private static final String TAG = StartMealActivity.class.getSimpleName();

    // メッセージキー：タイマー終了通知
    public static final String MESSENGER_SERVICE_KEY = "stopService";
    public static final String ACTION_TIMER_STOPPED = "timerAction";

    // 時間ピッカー
    private NumberPicker mTimePicker = null;

    // 効果音再生
    private MealSoundPlayer player;

    // アプリ共有変数
    Globals globals;

    // タイマーレシーバ
    private MealBroadcastReceiver mReceiver;

    /**
     * アクティビティ生成
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meal);

        // ディスプレイの向きは初期起動時の方向で固定とする
        globals = (Globals)getApplication();

        // 効果音の準備をします。
        player = MealSoundPlayer.getInstance();

        // 食べ物画像の準備をします。
        loadImage();

        // タイマー時刻の初期化
        mTimePicker = (NumberPicker) findViewById(R.id.numberPicker);
        setNumberPicker(mTimePicker, 5, 60, 5, R.string.timeFormat);

        // 初期値は30分に設定
        mTimePicker.setValue(5);

        // キーボードは非表示にする
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        player.loadSound(getApplicationContext());
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
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // テーブルビュー設定
        globals.tableView = (FrameLayout)findViewById(R.id.table);
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 設定変更時（自動回転など）のイベント
     * @param newConfig
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
        player.stopSound();
        player.unloadSounds();
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
     * いただきますボタンタップ時のイベント処理
     * @param view
     */
    public void onStartButtonTapped(View view) {
        // いただきますタップ禁止
        Button btnS = (Button)this.findViewById(R.id.btnStart);
        btnS.setEnabled(false);
        // ごちそうさまタップ可能
        Button btnE = (Button)this.findViewById(R.id.btnEnd);
        btnE.setEnabled(true);

        // 入力時間とTicker設定（デフォルト30秒・5秒おき）
        int seconds = 30 * 1000;
        int intervalMillis = 5000;
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkDebug);
        if (!checkBox.isChecked()) {
            // Debugオフの場合は設定した時間
            seconds = (mTimePicker.getValue() + 1) * 5 * 60 * 1000;
            // ごはん画像の数にあわせてTickerを計算
            intervalMillis = seconds / 5 - 1000;
        }
        checkBox.setEnabled(false);
        mTimePicker.setEnabled(false);

        // タイマーサービス開始
        MealTimerService.scheduleJob(this, intervalMillis);
    }

    /**
     * ごちそうさまボタンタップ時のイベント処理
     * @param view
     */
    public void onEndButtonTapped(View view) {
        // 次画面へ遷移する
        goNextIntent(R.string.message_succeed);
    }

    /**
     * 停止ボタンタップ時のイベント処理
     * @param view
     */
    public void onStopButtonTapped(View view) {
        // TODO 次のレベルアップで機能追加
        // タイマーを止める
        MealTimerService.cancelJob(this);
        // 画面を初期表示する
    }

    /**
     * 次画面へ遷移します。
     *
     * @param result 結果
     */
    private void goNextIntent(int result) {
        // タイマーを止める
        MealTimerService.cancelJob(this);

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
     * たべもの画像を読み込んでランダム表示して、
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
        img = (ImageView) findViewById(R.id.salada);
        factory.loadFood(FoodType.SALADA, img);
    }

    /**
     * 数字ピッカーを初期化します。
     * @param picker 数字ピッカー
     * @param min 最小値
     * @param max 最大値
     * @param step カウントアップする値
     * @param format 表示書式
     */
    private void setNumberPicker(NumberPicker picker, int min, int max, int step, int format) {
        picker.setMinValue(min / step - 1);
        picker.setMaxValue((max / step) - 1);
        String[] valueSet = new String[max / min];
        for (int i = min; i <= max; i += step) {
            valueSet[(i / step) - 1] = getString(format, i);
        }
        picker.setDisplayedValues(valueSet);
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
