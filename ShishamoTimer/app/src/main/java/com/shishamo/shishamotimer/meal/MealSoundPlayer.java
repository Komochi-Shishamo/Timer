package com.shishamo.shishamotimer.meal;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.shishamo.shishamotimer.R;

import java.util.HashMap;

/**
 * 食事タイマーで使う効果音の再生クラスです。
 * シングルトンクラスです。
 * Created by rika on 2016/06/19.
 */
public class MealSoundPlayer {
    // 唯一のインスタンス
    private static MealSoundPlayer mPlayer = new MealSoundPlayer();
    // SoundPoolオブジェクト
    private SoundPool mSoundPool;
    // 再生する効果音の格納庫
    private HashMap<SoundType, Integer> soundPoolMap;

    // 効果音の種類
    private enum SoundType {
        KIRAKIRA ,
        SUCCEED ,
        FAILED
    }

    /**
     * コンストラクタ。
     * 効果音再生の準備をします。
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private MealSoundPlayer() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // API Level 21以降は非推奨
            mSoundPool = new SoundPool(3,  AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(3)
                    .build();
        }
        soundPoolMap = new HashMap<SoundType, Integer>();
    }

    /**
     * 食事用のプレイヤーを取得します。
     * @return
     */
    public static MealSoundPlayer getInstance() {
        if (mPlayer == null) {
            mPlayer = new MealSoundPlayer();
        }
        return mPlayer;
    }
    /**
     * 効果音を読み込みます。
     * 読み込みには時間がかかるので最初に呼び出すこと
     */
    @SuppressWarnings("deprecation")
    public void loadSound(Context context) {
        //  一時的に追加　←　今後の対応要
        mSoundPool = new SoundPool(3,  AudioManager.STREAM_MUSIC, 0);

        soundPoolMap.put(SoundType.KIRAKIRA, mSoundPool.load(context, R.raw.ta_ta_kirarara01, 1));
        soundPoolMap.put(SoundType.SUCCEED, mSoundPool.load(context, R.raw.muci_hono_04, 1));
        soundPoolMap.put(SoundType.FAILED, mSoundPool.load(context, R.raw.muci_hono_01, 1));
    }

    /**
     * キラキラ音楽を再生します。
     */
    public void playKirakira() {
        mSoundPool.play(soundPoolMap.get(SoundType.KIRAKIRA), 1.0f, 1.0f, 0, 1, 1.0f);
    }

    /**
     * 成功時の効果音を再生します。
     */
    public void playSucceed() {
        mSoundPool.play(soundPoolMap.get(SoundType.SUCCEED), 1.0f, 1.0f, 0, 1, 1.0f);
    }

    /**
     * 失敗時の効果音を再生します。
     */
    public void playFailed() {
        mSoundPool.play(soundPoolMap.get(SoundType.FAILED), 1.0f, 1.0f, 0, 1, 1.0f);
    }

    /**
     * すべての効果音をメモリからリリースします。
     */
    @SuppressWarnings("deprecation")
    public void  unloadSounds() {
        //  一時的に追加　←　今後の対応要
        mSoundPool = new SoundPool(3,  AudioManager.STREAM_MUSIC, 0);

        for (int id : soundPoolMap.values()) {
            mSoundPool.unload(id);
        }
        mSoundPool.release();
        mSoundPool = null;
    }
}
