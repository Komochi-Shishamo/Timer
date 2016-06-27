package com.shishamo.shishamotimer.meal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.shishamo.shishamotimer.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 食事タイマーのメイン処理
 */
public class StartMealActivity extends AppCompatActivity  {
    // 使用する画像
    private List<ImageView> foods = new ArrayList<ImageView>();
    // 時間ピッカー
    private NumberPicker mTimePicker = null;
    // タイマー
    private EatingCountDownTimer mEatingTimer = null;
    private int counter = -1;
    // 効果音再生
    private MealSoundPlayer player;

    /**
     * 起動時のイベント処理
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_meal);

        // 効果音の準備をします。
        player = MealSoundPlayer.getInstance();
        player.loadSound(getApplicationContext());

        // 食べ物画像の準備をします。
        loadImage();
        setTouchImageListener();

        // タイマー時刻の初期化
        mTimePicker = (NumberPicker) findViewById(R.id.numberPicker);
        setNumberPicker(mTimePicker, 5, 60, 5, R.string.timeFormat);

        // 初期値は20分に設定
        mTimePicker.setValue(3);

        // キーボードは非表示にする
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void loadImage() {
        FoodFactory factory = FoodFactory.getInstance();
        // ごはん
        ImageView img = (ImageView)findViewById(R.id.rice);
        img.setImageResource(factory.getRice());
        foods.add(img);
        // 主菜
        img = (ImageView) findViewById(R.id.main);
        img.setImageResource(factory.getMain());
        foods.add(img);

        // 副菜
        img = (ImageView) findViewById(R.id.fukusai);
        img.setImageResource(factory.getFukusai());
        foods.add(img);

        // 汁物
        img = (ImageView) findViewById(R.id.soup);
        img.setImageResource(factory.getSoup());
        foods.add(img);

        // サラダ系
        img = (ImageView) findViewById(R.id.salada);
        img.setImageResource(factory.getSalada());
        foods.add(img);

        // 順番をシャッフルする
        Collections.shuffle(foods);
    }
    /**
     * ビューにタッチイベントのリスナーを登録します。
     */
    private void setTouchImageListener() {
        for (ImageView dragView : foods) {
            // タッチイベントを登録
            DragViewListener listener = new DragViewListener(dragView);
            dragView.setOnTouchListener(listener);
        }
    }

    /**
     * 数字ピッカーを初期化します。
     * @param picker
     * @param min
     * @param max
     * @param step
     * @param format
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
     * 一時停止
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * アクティビティ終了
     */
    @Override
    public void finish() {
        super.finish();

        if (mEatingTimer != null) {
            mEatingTimer.cancel();
            mEatingTimer = null;
        }
        player.unloadSounds();
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
        int tickTime = 5000;
                CheckBox checkBox = (CheckBox) findViewById(R.id.chkDebug);
        if (!checkBox.isChecked()) {
            // Debugオフの場合は設定した時間
            seconds = (mTimePicker.getValue() + 1) * 5 * 60 * 1000;
            // ごはん画像の数にあわせてTickerを計算
            tickTime = seconds / foods.size() - 1000;
        }


        // タイマー開始
        mEatingTimer = new EatingCountDownTimer(seconds, tickTime, this);
        mEatingTimer.start();
    }

    /**
     * ごちそうさまボタンタップ時のイベント処理
     * @param view
     */
    public void onEndButtonTapped(View view) {
        // タイマーをとめる
        mEatingTimer.cancel();
        mEatingTimer = null;

        // 次画面へ遷移
        Intent intent = new Intent(this, FinishMealActivity.class);
        intent.putExtra("MESSAGE",R.string.message_succeed);
        startActivity(intent);
    }

    /**
     * 次に対象となる画像を取得します。
     * @return
     */
    public ImageView getNextFoods() {
        counter++;
        return this.foods.get(counter);
    }

    /**
     * キラキラ効果音を再生します。
     */
    public void playKirakira() {
        player.playKirakira();
    }
}
