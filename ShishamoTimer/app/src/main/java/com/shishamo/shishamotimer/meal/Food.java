package com.shishamo.shishamotimer.meal;

import android.widget.ImageView;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 食べ物画像の情報を保有するクラス
 */
@AllArgsConstructor
@Data
public class Food {
    // 画像ファイルのID
    private Integer id;

    // タイプ
    private FoodType foodType;

    // 画像ビュー
    private ImageView image;

    // 表示状態
    private boolean viewable;

}
