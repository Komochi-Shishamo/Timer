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
    private HashMap<SoundType, Integer> soundPoolMap = new HashMap<SoundType, Integer>();
    // ロードした効果音の数
    private int loadCounter = 0;
    // 効果音利用可能状態
    private boolean available = false;
    // 再生中のストリームID
    private int streamId = 0;

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
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(3)
                .build();
        available = false;
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            /**
             *  効果音の読み込み終了時
             * @param soundPool
             * @param sampleId
             * @param status
             */
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadCounter++;
                if (loadCounter == 3) {
                    // すべてロードしたら再生可能とする
                    available = true;
                }
            }
        });
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
    public void loadSound(Context context) {
        if (soundPoolMap.isEmpty()) {
            soundPoolMap.put(SoundType.KIRAKIRA, mSoundPool.load(context, R.raw.ta_ta_kirarara01, 1));
            soundPoolMap.put(SoundType.SUCCEED, mSoundPool.load(context, R.raw.muci_hono_04, 1));
            soundPoolMap.put(SoundType.FAILED, mSoundPool.load(context, R.raw.muci_hono_01, 1));
        }
    }

    /**
     * すべての効果音をメモリからリリースします。
     */
    public void  unloadSounds() {
        if (mSoundPool != null) {
            for (int id : soundPoolMap.values()) {
                mSoundPool.unload(id);
            }
            mSoundPool.release();
            soundPoolMap.clear();
        }
        available = false;
        loadCounter = 0;
    }

    /**
     * キラキラ音楽を再生します。
     */
    public void playKirakira() {
        if (available) {
            streamId = mSoundPool.play(soundPoolMap.get(SoundType.KIRAKIRA), 1.0f, 1.0f, 0, 1, 1.0f);
        }
    }

    /**
     * 成功時の効果音を再生します。
     */
    public void playSucceed() {
        if (available) {
            streamId = mSoundPool.play(soundPoolMap.get(SoundType.SUCCEED), 1.0f, 1.0f, 0, 1, 1.0f);
        }
    }

    /**
     * 失敗時の効果音を再生します。
     */
    public void playFailed() {
        if (available && mSoundPool != null) {
            mSoundPool.play(soundPoolMap.get(SoundType.FAILED), 1.0f, 1.0f, 0, 1, 1.0f);
        }
    }

    /**
     * 音楽の再生をとめます。
     */
    public void stopSound() {
        if (streamId != 0) {
            mSoundPool.stop(streamId);
        }
        streamId = 0;
    }
}
