package com.shishamo.shishamotimer.stamp;

import java.util.Date;

import io.realm.RealmObject;

/**
 * スタンプカードクラス
 * 今のところ、ID:1,inStamp:1,ID:1,inStamp:2,,,,ID:2,inStamp:1,,,のような持ち方をします。
 */
public class StampCard extends RealmObject {

    // 識別するID
    private long id;

    // スタンプ番号
    private Integer inStamp;

    // スタンプ日付
    private Date stampDate;

    /**
     * 識別IDを返却する
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * 識別IDを設定する
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * スタンプ番号を返却する
     * @return
     */
    public Integer getInStamp() {
        return inStamp;
    }

    /**
     * スタンプ番号を設定する
     * @param inStamp
     */
    public void setInStamp(Integer inStamp) {
        this.inStamp = inStamp;
    }

    /**
     * スタンプ日付を取得する
     * @return
     */
    public Date getStampDate() {
        return stampDate;
    }

    /**
     * スタンプ日付を設定する
     * @param stampDate
     */
    public void setStampDate(Date stampDate) {
        this.stampDate = stampDate;
    }
}
