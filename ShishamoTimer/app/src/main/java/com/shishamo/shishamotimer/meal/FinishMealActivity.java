package com.shishamo.shishamotimer.meal;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishamo.shishamotimer.R;

public class FinishMealActivity extends AppCompatActivity {
    // 結果
    private int mResultId;

    /**
     * アプリ起動イベント
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_meal);
    }

    /**
     * アプリ開始イベント
     */
    @Override
    protected void onStart() {
        super.onStart();
    }
    /**
     * 最前面表示のイベント
     */
    @Override
    protected void onResume() {
        super.onResume();

        // 前画面からの結果を取得
        Intent intent = getIntent();
        mResultId = intent.getIntExtra("MESSAGE",0);

        // 結果テキストを表示
        TextView message = (TextView)findViewById(R.id.resultLabel);
        message.setText(getResources().getString(mResultId));

        // 結果にあわせたアニメーションの表示と効果音の再生を行う
        ImageView img = (ImageView)findViewById(R.id.imageView);
        MealSoundPlayer player = MealSoundPlayer.getInstance();
        if (mResultId == R.string.message_succeed) {
            // 成功の場合
            // AnimationDrawableのXMLリソースを指定
            img.setBackgroundResource(R.drawable.succeed_finish_animation);
            player.playSucceed();
        }
        else {
            // 失敗した場合
            img.setBackgroundResource(R.drawable.fail_finish_animation);
            player.playFailed();
        }
        // AnimationDrawableを取得
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        // アニメーションの開始
        frameAnimation.start();
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
}
