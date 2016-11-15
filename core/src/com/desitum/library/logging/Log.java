package com.desitum.library.logging;

/**
 * Created by Dakota Van Ry on 11/14/2016. Use it
 */

public class Log {

    public static void d(String log, String message) {
        System.out.println(System.currentTimeMillis() + " : D/" + log + " " + message);
    }
}
