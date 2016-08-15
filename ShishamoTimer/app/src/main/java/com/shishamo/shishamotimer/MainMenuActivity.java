package com.shishamo.shishamotimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shishamo.shishamotimer.app.AppMainActivity;
import com.shishamo.shishamotimer.common.ActivityStack;
import com.shishamo.shishamotimer.meal.StartMealActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * お食事タイマーボタンタップ時のイベント処理
     * @param view
     */
    public void onMealButtonTapped(View view) {
        // 次画面へ遷移
        Intent intent = new Intent(this, StartMealActivity.class);
        startActivity(intent);

        // 一方通行にするため自分は終わる
        finish();
        //// Activity詰める
        //ActivityStack.stackHistory(this);
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
}
