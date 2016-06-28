package com.shishamo.shishamotimer.stamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.shishamo.shishamotimer.R;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * スタンプを保存、表示するクラス
 */
public class StampActivity extends AppCompatActivity {

    private static int MID_TYPE = 1;

    private static int GOAL_TYPE = 2;

    private GridView mGridView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);

        Intent intent = getIntent();

        // idが設定されない場合は1で作成←将来的には入力チェック必要
        long id = intent.getLongExtra("ID",1);

        mGridView = (GridView) findViewById(R.id.stampList);

        // テーブル構造など変更があった場合のテーブル初期化←1回だけ実行。
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
//        realm.deleteRealm(realmConfig);

        // Realmインスタンスを生成
        realm = Realm.getInstance(this);

        // クエリーを作成する
        RealmQuery<StampCard> stampCardQuery = realm.where(StampCard.class).equalTo("id",id);

        // 選択されたIDのデータ件数を取得
        long stampCnt = stampCardQuery.count();

        // トランザクション開始
        realm.beginTransaction();

        // 20個が最大
        if(stampCnt > 19) {
            clear(stampCardQuery);
        }

        // 新しいデータ(StampCardクラス)を作成
        StampCard _stampCrad = realm.createObject(StampCard.class);
        Number maxStampId = stampCardQuery.max("inStamp");
        _stampCrad.setId(id);
        if (maxStampId == null) {
            _stampCrad.setInStamp(1);
        } else {
            _stampCrad.setInStamp(maxStampId.intValue() + 1);
        }

        _stampCrad.setStampDate(new Date());

        // トランザクション終了
        realm.commitTransaction();

        // 表示用データ取得
        RealmResults<StampCard> _stampCards = stampCardQuery.findAll();

        // アダプターに設定
        StampAdapter stampAdapter = new StampAdapter(this, _stampCards, true);
        mGridView.setAdapter(stampAdapter);

        // スタンプカードの個数によりメッセージを出力
        if(_stampCards.size() == 10){
            callAlert(MID_TYPE);
        }
        if(_stampCards.size() == 20){
            callAlert(GOAL_TYPE);
        }

    }

    /**
     * カスタムダイアログ呼び出し
     * @param type
     */
    private void callAlert(int type) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.setType(type);
        customDialog.show(getFragmentManager(),"カスタム");
    }

    /**
     * テーブルデータをクリアする。
     * @param target
     */
    public void clear(RealmQuery<StampCard> target){
        target.findAll().clear();
    }

    public void onOffBtnTapped(View view){
        finish();
    }
}
