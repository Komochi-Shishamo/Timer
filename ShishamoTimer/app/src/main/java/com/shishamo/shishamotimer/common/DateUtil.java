package com.shishamo.shishamotimer.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日付・時刻に関するユーティリティクラス
 */
public class DateUtil {

    /**
     * 現在日時を返します。
     *
     * @return Date型の現在日時
     */
    public static Date getSysDate() {
        return getJpCalendar().getTime();
    }

    /**
     * Date型日付の時間(hour)を取得します。
     *
     * @param target Date型の日付
     * @return 時間(hour)
     */
    public static int getHour(Date target) {
        if (target == null) {
            return 0;
        }
        Calendar cal = convert(target);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Date型日付の分(minute)を取得します。
     *
     * @param target Date型の日付
     * @return 分(minute)
     */
    public static int getMinute(Date target) {
        if (target == null) {
            return 0;
        }
        Calendar cal = convert(target);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * Date型日付に指定の時刻を設定します。
     * 秒はゼロでリセットされます。
     *
     * @param target Date型の日付
     * @param hourOfDay 時刻の時(hour)
     * @param minute 時刻の分(minute)
     */
    public static Date updateDateTime(Date target, int hourOfDay, int minute) {
        if (target == null) {
            return null;
        }
        Calendar cal = convert(target);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    /**
     * 指定日付を指定の書式で返します。
     *
     * @param target 変換対象の日付
     * @param format 日付書式
     * @return フォーマット後の日付
     */
    public static String format(Date target, String format) {
        if (format == null || format.isEmpty() || target == null) {
            return null;
        }
        final DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(target);
    }

    /**
     * 日本のタイムゾーンでカレンダーオブジェクトを取得します。
     *
     * @return Calendar
     */
    private static Calendar getJpCalendar() {
        // 日本のタイムゾーン、秒は未使用のためゼロ固定
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"));
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    /**
     * Date型の日付をCalendar型に変換して返します。
     *
     * @param target 変換対象のDate型日付
     * @return Calendar
     */
    private static Calendar convert(Date target) {
        Calendar cal = getJpCalendar();
        cal.setTime(target);
        return cal;
    }
}
