package com.shishamo.shishamotimer.meal;

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

    // 食べ物画像を格納する
    // key: FoodType, value:drawableのID
    private HashMap<FoodType, List<Integer>> foodMap_ = new HashMap<FoodType, List<Integer>>();

    // 食べ物タイプ
    private enum FoodType {
        // ごはんもの
        RICE,
        // 主菜
        MAIN,
        // 汁物
        SOUP,
        // 副菜
        FUKUSAI,
        // サラダ系
        SALADA
    }

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
     * ランダムにごはんの画像IDを取得します。
     * @return
     */
    public int getRice() {
        List<Integer> list = foodMap_.get(FoodType.RICE);
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * ランダムに主菜の画像IDを取得します。
     * @return
     */
    public int getMain() {
        List<Integer> list = foodMap_.get(FoodType.MAIN);
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * ランダムに副菜の画像IDを取得します。
     * @return
     */
    public int getFukusai() {
        List<Integer> list = foodMap_.get(FoodType.FUKUSAI);
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * ランダムに汁物の画像IDを取得します。
     * @return
     */
    public int getSoup() {
        List<Integer> list = foodMap_.get(FoodType.SOUP);
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * ランダムにサラダ系の画像IDを取得します。
     * @return
     */
    public int getSalada() {
        List<Integer> list = foodMap_.get(FoodType.SALADA);
        Collections.shuffle(list);
        return list.get(0);
    }
}
