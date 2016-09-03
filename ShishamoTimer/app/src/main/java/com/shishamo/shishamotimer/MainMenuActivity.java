package com.shishamo.shishamotimer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shishamo.shishamotimer.app.AppMainActivity;
import com.shishamo.shishamotimer.common.ActivityStack;
import com.shishamo.shishamotimer.common.Globals;
import com.shishamo.shishamotimer.meal.StartMealActivity;
import com.shishamo.shishamotimer.stamp.StampActivity;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Globals.gContext = getApplication();
    }

    /**
     * お食事タイマーボタンタップ時のイベント処理
     * @param view
     */
    public void onMealButtonTapped(View view) {
        // 現在の方向を格納
        Configuration config = getResources().getConfiguration();
        Globals globals = (Globals)getApplication();
        // タイマー結果の初期値設定
        globals.mealResultId = R.string.message_failed;

        // 次画面へ遷移
        Intent intent = new Intent(this, StartMealActivity.class);
        startActivity(intent);

        // Activity詰める
        ActivityStack.stackHistory(this);
    }

    /**
     * アプリタイマーボタンタップ時のイベント処理
     * @param view
     */
    public void onAppButtonTapped(View view) {
        // 次画面へ遷移
        Intent intent = new Intent(this, AppMainActivity.class);
        startActivity(intent);
    }
    /**
     * スタンプ参照ボタンタップ時のイベント処理
     * @param view
     */
    public void onStampBookButtonTapped(View view) {
        // 次画面へ遷移
        Intent intent = new Intent(this, StampActivity.class);
        intent.putExtra("SHOW_TYPE",1);
        startActivity(intent);

        // Activity詰める
        ActivityStack.stackHistory(this);
    }
}
