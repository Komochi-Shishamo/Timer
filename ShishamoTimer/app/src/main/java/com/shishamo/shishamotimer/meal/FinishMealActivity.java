package com.shishamo.shishamotimer.meal;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;
import com.shishamo.shishamotimer.common.Globals;
import com.shishamo.shishamotimer.stamp.StampActivity;

public class FinishMealActivity extends AppCompatActivity {

    private final static String TAG = FinishMealActivity.class.getSimpleName();

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
     * 最前面表示のイベント
     */
    @Override
    protected void onResume() {
        super.onResume();

        // 結果を取得
        Globals globals = (Globals)this.getApplication();
        int mResultId = globals.mealResultId;

        // 結果テキストを表示
        TextView message = (TextView)findViewById(R.id.resultLabel);
        message.setText(getResources().getString(mResultId));

        // 表示するボタンを決定
        FloatingActionButton btnBack = (FloatingActionButton)findViewById(R.id.btnBack);
        Button btnStamp = (Button)findViewById(R.id.stampBtn);
        // 結果にあわせたアニメーションの表示と効果音の再生を行う
        ImageView img = (ImageView)findViewById(R.id.imageView);
        // 前画面の効果音再生が続いている場合は停止状態にします。
        MealSoundPlayer player = MealSoundPlayer.getInstance();
        player.stopSound();
        if (mResultId == R.string.message_succeed) {
            // 成功の場合
            // AnimationDrawableのXMLリソースを指定
            try {
                img.setBackgroundResource(R.drawable.succeed_finish_animation);
            } catch (Exception e) {
                img.setImageDrawable(null);
                Log.e(TAG, "ごちそうさまアニメーションで失敗", e);
            }
            btnStamp.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
            player.playSucceed();
        }
        else {
            // 失敗した場合
            try {
                img.setBackgroundResource(R.drawable.fail_finish_animation);
            } catch (Exception e) {
                img.setImageDrawable(null);
                Log.e(TAG, "ごちそうさまできなかったアニメーションで失敗", e);
            }
            btnBack.setVisibility(View.VISIBLE);
            btnStamp.setVisibility(View.GONE);
            player.playFailed();
        }
        // AnimationDrawableを取得
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        // アニメーションの開始
        frameAnimation.start();
    }

    /**
     * 一時停止
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * スタンプボタン押下
     * @param view
     */
    public void onStampButtonTapped(View view){
        // 再生中の効果音を止める
        MealSoundPlayer.getInstance().stopSound();

        // スタンプ画面へ
        Intent intent = new Intent(this, StampActivity.class);
        startActivity(intent);
        // Activity詰める
        ActivityStack.stackHistory(this);
    }

    /**
     * 終了ボタンタップ
     * @param view
     */
    public void onBtnEndTapped(View view) {
        // Activity終了
        ActivityStack.stackHistory(this);
        ActivityStack.removeHistory();
    }
}
