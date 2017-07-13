package com.shishamo.shishamotimer.meal;

import android.widget.ImageView;

import com.shishamo.shishamotimer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 食べ物画像を管理するクラスです。
 * シングルトンクラスです。
 * Created by rika on 2016/06/25.
 */
public class FoodFactory {
    // 唯一のインスタンス
    private static FoodFactory instance_ = new FoodFactory();

    // 全ての食べ物画像を格納する
    // key: FoodType, value:drawableのID
    private HashMap<FoodType, List<Integer>> foodMap_ = new HashMap<FoodType, List<Integer>>();

    // 現在表示している食べ物画像を格納する
    private List<Food> foods = new ArrayList<>();

    /**
     * コンストラクタ
     */
    private FoodFactory() {
        // 画像IDを設定
        // ごはんもの
        foodMap_.put(FoodType.RICE, new ArrayList<>(Arrays.asList(
                R.drawable.food_gohan_hakumai,
                R.drawable.food_mamegohan,
                R.drawable.food_chahan)));
        // 主菜
        foodMap_.put(FoodType.MAIN, new ArrayList<>(Arrays.asList(
                R.drawable.food_beefsteak,
                R.drawable.food_karaage_lemon,
                R.drawable.food_yakisake)));
        // 汁物
        foodMap_.put(FoodType.SOUP, new ArrayList<>(Arrays.asList(
                R.drawable.food_omisoshiru,
                R.drawable.food_tonjiru,
                R.drawable.food_soup_corn)));
        // 副菜
        foodMap_.put(FoodType.FUKUSAI, new ArrayList<>(Arrays.asList(
                R.drawable.food_hourensou_gomaae,
                R.drawable.food_yasai_nimono,
                R.drawable.food_kuro_nimame)));
        // サラダ系
        foodMap_.put(FoodType.SALADA, new ArrayList<>(Arrays.asList(
                R.drawable.food_potato_sald,
                R.drawable.food_salad,
                R.drawable.food_tsukemono)));
    }

    /**
     * 唯一のインスタンス取得
     * @return
     */
    public static FoodFactory getInstance() {
        if (instance_ == null) {
            instance_ = new FoodFactory();
        }
        return instance_;
    }

    /**
     * 指定の種類の食べ物画像を読み込んで情報を格納します。
     * 画像にタッチイベントを登録します。
     *
     * @param type 種類
     * @param img 画像
     */
    public void loadFood(FoodType type, ImageView img) {
        // ランダムに取得した画像をビューに表示する
        int id = getFood(type);
        img.setImageResource(id);
        Food food = new Food(id, type, img, true);
        foods.add(food);
        // タッチイベントを登録
        DragViewListener listener = new DragViewListener(img);
        img.setOnTouchListener(listener);
    }

    /**
     * 表示されている食べ物画像をランダムに返します。
     *
     * @return ランダムに取得した表示画像の情報。ない場合はnull
     */
    public Food getViewableFood(){
        Collections.shuffle(foods);
        for (Food food: foods) {
            if (food.isViewable()) {
                return food;
            }
        }
        return null;
    }

    /**
     * ランダムに指定の種類の画像IDを取得します。
     *
     * @param type 種類
     * @return 画像ID
     */
    private int getFood(FoodType type) {
        List<Integer> list = foodMap_.get(type);
        Collections.shuffle(list);
        return list.get(0);
    }

}
