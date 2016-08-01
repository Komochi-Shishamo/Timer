package com.shishamo.shishamotimer.app;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;

public class AppNoticeActivity extends AppCompatActivity {

    private final static String TAG = AppNoticeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_notice);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ImageView img = (ImageView)findViewById(R.id.imageView);

        try {
            // AnimationDrawableのXMLリソースを指定
            img.setBackgroundResource(R.drawable.notice_finish_animation);
            // AnimationDrawableを取得
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            // アニメーションの開始
            frameAnimation.start();
        } catch (OutOfMemoryError e) {
            img.setImageDrawable(null);
            Log.e(TAG, "アプリタイマーの終了通知アニメーションで失敗", e);
        }
    }

    public void onOffBtnTapped(View view){
        // Activity詰める
        ActivityStack.stackHistory(this);
        // Activity全削除
        ActivityStack.removeHistory();
    }
}
