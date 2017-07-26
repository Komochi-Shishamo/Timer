package com.shishamo.shishamotimer.stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * スタンプをGridViewに設定するアダプター
 */
    public class StampAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    // 表示用データを受け取るリスト
    private List<Integer> stampList = null;

    // 季節の種類
    private SeasonType seasonType;

    // 季節ごとのスタンプ画像（季節ごと段ごとに固定）
    private Map<SeasonType, List<Integer>> mStampMap = new HashMap<>();

    /**
     * adapterにContextとLayoutInflaterを設定
     * @param context コンテキスト
     */
    public StampAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        // スタンプMap作成
        createStampMap();
    }

    /**
     *  GridViewに設定する内容をViewHolderとして定義
     */
    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * スタンプ画像のMapを季節ごとに作成します。
     */
    private void createStampMap() {
        // 春
        mStampMap.put(SeasonType.SPRING, Arrays.asList(
                R.drawable.sakura,
                R.drawable.takenoko,
                R.drawable.dango,
                R.drawable.tsukushi,
                R.drawable.ichigo
        ));
        // 夏
        mStampMap.put(SeasonType.SUMMER, Arrays.asList(
                R.drawable.fish,
                R.drawable.shell,
                R.drawable.jellyfish,
                R.drawable.crab,
                R.drawable.starfish
        ));
        // 夏
        mStampMap.put(SeasonType.AUTUMN, Arrays.asList(
                R.drawable.tonbo,
                R.drawable.ityo,
                R.drawable.donguri,
                R.drawable.momiji,
                R.drawable.kuri
        ));
        // 冬
        mStampMap.put(SeasonType.WINTER, Arrays.asList(
                R.drawable.hiiragi,
                R.drawable.snowman,
                R.drawable.koma,
                R.drawable.yuki,
                R.drawable.yukiusagi
        ));
    }

    @Override
    public int getCount() {
        if(stampList == null){
            return 0;
        }
        return stampList.size();
    }

    @Override
    public Object getItem(int position) {
        if(stampList == null || stampList.get(position) == null) {
            return null;
        }
        return  stampList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        // 表示するレイアウトが存在しない場合
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.hue_imageview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // スタンプの表示有無(0:表示なし、1:表示あり)
        Number stampType = stampList.get(position);
        if (stampType == null || stampType.intValue() == 0) {
            return convertView;
        }

        // 季節ごとのスタンプ画像を取得
        if (!mStampMap.containsKey(seasonType)){
            return convertView;
        }
        List<Integer> list = mStampMap.get(seasonType);
        if (!list.isEmpty()) {
            // 位置によってスタンプ画像を変更する。
            if(position < 4){
                viewHolder.imageView.setImageResource(list.get(0));
            }else if(position < 8){
                viewHolder.imageView.setImageResource(list.get(1));
            }else if(position < 12) {
                viewHolder.imageView.setImageResource(list.get(2));
            }else if(position < 16){
                viewHolder.imageView.setImageResource(list.get(3));
            }else{
                viewHolder.imageView.setImageResource(list.get(4));
            }
        }
        return convertView;
    }

    /**
     * 表示用データをadapterに設定する
     * @param _stampList 表示用データ
     */
    public void setData(List<Integer> _stampList){
        this.stampList = _stampList;
    }

    /**
     * 季節の種類を設定します。
     * @param type 季節
     */
    public void setSeason(SeasonType type) {
        this.seasonType = type;
    }

}
