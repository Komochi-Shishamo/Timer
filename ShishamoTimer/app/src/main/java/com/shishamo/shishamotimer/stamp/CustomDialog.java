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

    private static int MID_TYPE = 1;

    private static int GOAL_TYPE = 2;

    private  int message;

    private int imageUrl;

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
        titleView.setText(string.DOROID_FROM);

        // イメージ設定
        ImageView imageView = (ImageView) dialog.findViewById(id.imageView);
        imageView.setImageResource(imageUrl);

        // メッセージ設定
        TextView messageView = (TextView) dialog.findViewById(id.message);
        messageView.setText(this.message);
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

    public  void setType(int type){
        if(type == 1){
            this.imageUrl = drawable.cake;
            this.message = string.MESSAGE_MID;
        }else if(type == 2){
            this.imageUrl = drawable.ginger;
            this.message = string.MESSAGE_GOAL;
        }
    }
}
