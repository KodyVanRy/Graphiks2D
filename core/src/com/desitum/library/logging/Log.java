package com.desitum.library.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by kodyvanry on 4/20/17.
 */

public class Log {

    private static SimpleDateFormat sdf;
    private static Date date;

    private static final int MAX_TAG_LENGTH = 20;

    public static void d(Object o, String log) {
        d(o.getClass(), log);
    }

    public static void d(Class c, String log) {
        d(c.getSimpleName(), log);
    }

    public static void d(String tag, String log) {
        if (tag.length() > MAX_TAG_LENGTH) {
            throw new RuntimeException(String.format("tag '%s' is too long. Max limit is 15.", tag));
        }
        System.out.println(getCurrentTime() +
                " L/D " +
                padRight(tag, MAX_TAG_LENGTH) +
                " " +
                log
        );
    }

    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    private static String getCurrentTime() {
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        }
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        date.setTime(System.currentTimeMillis());
        return sdf.format(date);
    }
}
