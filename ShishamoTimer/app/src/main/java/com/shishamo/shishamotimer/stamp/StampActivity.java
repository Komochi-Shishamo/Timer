package com.shishamo.shishamotimer.stamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;

import com.shishamo.shishamotimer.R;
import com.shishamo.shishamotimer.common.ActivityStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

/**
 * スタンプを保存、表示するクラス
 */
public class StampActivity extends AppCompatActivity {

    // 半分達成
    private static int MID_TYPE = 1;

    // GOAL達成
    private static int GOAL_TYPE = 2;

    // 参照
    private static int READ_ONLY = 3;

    // スタンプ最大個数
    private static int MAX_STAMP = 20;

    // 初期スタンプ個数
    private static int INIT_NO = 1;

    // 表示フラグ
    private static int ON = 1;

    // 非表示フラグ
    private static int OFF = 0;

    private GridView mGridView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);

        Intent intent = getIntent();

        // idが設定されない場合は1で作成←将来的には入力チェック必要
        long id = intent.getLongExtra("ID",1);
        int showType = intent.getIntExtra("SHOW_TYPE",0);

        mGridView = (GridView) findViewById(R.id.stampList);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        // テーブル構造など変更があった場合のテーブル初期化←1回だけ実行。
        //  realm.deleteRealm(realmConfig);

        // Realmインスタンスを生成
        realm = Realm.getInstance(realmConfig);

        // クエリーを作成する
        RealmQuery<StampCard> stampCardQuery = realm.where(StampCard.class).equalTo("id",id);

        // 該当データを取得
        StampCard _stampCard = stampCardQuery.findFirst();

        if(showType == 0) {
            // トランザクション開始
            realm.beginTransaction();

            // データが存在しなければ、作成、存在するなら、スタンプ個数とスタンプ日を更新
            Number maxStampNo = INIT_NO;
            if (_stampCard == null) {
                _stampCard = realm.createObject(StampCard.class);
                _stampCard.setId(id);
            } else {
                maxStampNo = _stampCard.getStampNo();
                if (maxStampNo.intValue() < MAX_STAMP) {
                    maxStampNo = maxStampNo.longValue() + 1;
                } else {
                    maxStampNo = INIT_NO;
                }
            }

            _stampCard.setStampNo(maxStampNo.longValue());
            _stampCard.setStampDate(new Date());

            // トランザクション終了
            realm.commitTransaction();
        }

        // 最新のスタンプ個数を取得　←　あえて再取得　←　後で書き直す
        Number viewMax = stampCardQuery.findFirst().getStampNo();

        // 表示用データリスト作成
        List<Integer> viewList = new ArrayList<>();
       for(int cnt=0; cnt < MAX_STAMP ; cnt++){
           if(cnt < viewMax.intValue()) {
               viewList.add(ON);
           }else{
               viewList.add(OFF);
           }
       }

        // Adapterを作成、データリストを設定
        StampAdapter stampAdapter = new StampAdapter(this);
        stampAdapter.setData(viewList);

        // gridviewにadapterを設定
        mGridView.setAdapter(stampAdapter);

        if(showType == 0) {
            // スタンプカードの個数によりメッセージを出力
            if (viewMax.intValue() == 10) {
                callAlert(MID_TYPE,viewMax.intValue());
            }
            if (viewMax.intValue() == 20) {
                callAlert(GOAL_TYPE,viewMax.intValue());
            }
        } else {
            // スタンプ参照用
            callAlert(READ_ONLY,viewMax.intValue());
        }

    }

    /**
     * カスタムダイアログ呼び出し
     * @param type
     */
    private void callAlert(int type, int num) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setType(type, num);
        customDialog.show(getFragmentManager(),"カスタム");
    }

    /**
     * テーブルデータをクリアする。(今は未使用メソッド)
     * @param target
     */
    public void clear(RealmQuery<StampCard> target){
        target.findAll().clear();
    }

    /**
     * Activityのスタックリストを削除
     * @param view
     */
    public void onOffBtnTapped(View view){
        // Activity詰める
        ActivityStack.stackHistory(this);
        // Activity全削除
        ActivityStack.removeHistory();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode != KeyEvent.KEYCODE_BACK){
            return super.onKeyDown(keyCode,event);
        }else{
            return false;
        }
    }
}
