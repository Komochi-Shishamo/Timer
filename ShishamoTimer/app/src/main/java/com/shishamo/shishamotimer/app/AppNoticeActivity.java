package com.shishamo.shishamotimer.app;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;

public class AppNoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_notice);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        ImageView img = (ImageView)findViewById(R.id.imageView);

        // AnimationDrawableのXMLリソースを指定
        img.setBackgroundResource(R.drawable.notice_finish_animation);
        // AnimationDrawableを取得
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        // アニメーションの開始
        frameAnimation.start();
    }

    public void onOffBtnTapped(View view){
        // Activity詰める
        ActivityStack.stackHistory(this);
        // Activity全削除
        ActivityStack.removeHistory();
    }
}
