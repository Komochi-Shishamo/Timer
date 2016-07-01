package com.shishamo.shishamotimer.stamp;

import java.util.Date;

import io.realm.RealmObject;

/**
 * スタンプカードクラス
 *
 */
public class StampCard extends RealmObject {

    // 識別するID
    private long id;

    // スタンプ個数
    private long stampNo;

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
     * スタンプ個数を取得する
     * @return
     */
    public long getStampNo() {
        return stampNo;
    }

    /**
     * スタンプ個数を設定する
     * @param stampNo
     */
    public void setStampNo(long stampNo) {
        this.stampNo = stampNo;
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
