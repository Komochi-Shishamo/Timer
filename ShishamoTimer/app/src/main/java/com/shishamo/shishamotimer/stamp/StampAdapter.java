package com.shishamo.shishamotimer.stamp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * スタンプをGridViewに設定するアダプター（Realm）
 */
    public class StampAdapter extends RealmBaseAdapter<StampCard> {

    /**
     *  GridViewに設定する内容をViewHolderとして定義
     */
    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * コンストラクタ
     * @param context
     * @param realmResults
     * @param automaticUpdate
     */
    public StampAdapter(Context context, RealmResults<StampCard> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        /**
         * 画面が設定されていない場合
         */
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.hue_imageview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        /**
         * 該当データの要素分、スタンプを設定する
         */
        viewHolder.imageView.setImageResource(R.drawable.good3);

        return convertView;
    }
}
