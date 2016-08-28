package com.shishamo.shishamotimer.stamp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import static com.shishamo.shishamotimer.R.drawable;
import static com.shishamo.shishamotimer.R.id;
import static com.shishamo.shishamotimer.R.layout;
import static com.shishamo.shishamotimer.R.string;

/**
 * カスタムダイアログ
 */
public class CustomDialog extends DialogFragment {

    // 半分達成
    private static int MID_TYPE = 1;

    // GOAL達成
    private static int GOAL_TYPE = 2;

    // メッセージのタイプ
    private int messageId;

    // 画像のurl
    private int imageUrl;

    // スタンプ個数
    private int num;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(layout.dialog_custom);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // タイトル設定
        TextView titleView = (TextView) dialog.findViewById(id.title);
        titleView.setText(string.droid_from);

        // イメージ設定
        ImageView imageView = (ImageView) dialog.findViewById(id.imageView);
        imageView.setImageResource(imageUrl);

        // メッセージ設定
        TextView messageView = (TextView) dialog.findViewById(id.message);
        if(messageId == 1){
            messageView.setText(string.message_mid);
        }else if(messageId == 2){
            messageView.setText(string.message_goal);
        }else if(messageId == 3){
            messageView.setText(getString(string.message_readonly, num));
        }

        messageView.setTextSize(18.0f);

        // OK ボタンのリスナ
        dialog.findViewById(id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        // Close ボタンのリスナ
        dialog.findViewById(id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }

    /**
     * メッセージのタイプで画像を振り分ける。
     * @param type
     * @param num
     */
    public  void setType(int type , int num){
            this.messageId = type;
            this.num = num;
        if(messageId == 1){
            this.imageUrl = drawable.cake;
        }else if(messageId == 2){
            this.imageUrl = drawable.ginger;
        }else if(messageId == 3){
            this.imageUrl = drawable.droid_eat_apple;
        }
    }
}
