package com.shishamo.shishamotimer.stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shishamo.shishamotimer.R;

import java.util.List;

/**
 * スタンプをGridViewに設定するアダプター
 */
    public class StampAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    // adapterにContextとLayoutInflaterを設定
    public StampAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    // 表示用データを受け取るリスト
    private List<Integer> stampList = null;

    /**
     *  GridViewに設定する内容をViewHolderとして定義
     */
    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * 表示用データをadapterに設定する
     * @param _stampList
     */
    public void setData(List<Integer> _stampList){
        this.stampList = _stampList;
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

        // 表示ありの場合は、位置によってスタンプ画像を変更する。
        if(stampType != null){
            if(stampType.intValue() == 1) {
                if(position < 4){
                    viewHolder.imageView.setImageResource(R.drawable.fish);
                }else if(position < 8){
                    viewHolder.imageView.setImageResource(R.drawable.shell);
                }else if(position < 12) {
                    viewHolder.imageView.setImageResource(R.drawable.jellyfish);
                }else if(position < 16){
                    viewHolder.imageView.setImageResource(R.drawable.crab);
                }else{
                    viewHolder.imageView.setImageResource(R.drawable.starfish);
                }
            }
        }
        return convertView;
    }
}
