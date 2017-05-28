package com.desitum.library.logging

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.SynchronousQueue

/**
 * Created by kodyvanry on 4/20/17.
 */

object Log {

    private var sdf: SimpleDateFormat? = null
    private var date: Date? = null

    private val MAX_TAG_LENGTH = 20

    @JvmStatic fun d(o: Any, log: String) {
        d(o.javaClass, log)
    }

    @JvmStatic fun d(c: Class<*>, log: String) {
        d(c.simpleName, log)
    }

    @JvmStatic fun d(tag: String, log: String) {
        if (tag.length > MAX_TAG_LENGTH) {
            throw RuntimeException(String.format("tag '%s' is too long. Max limit is 15.", tag))
        }
        println(currentTime +
                " L/D " +
                padRight(tag, MAX_TAG_LENGTH) +
                " " +
                log
        )
    }

    private fun padRight(s: String, n: Int): String {
        return String.format("%1$-" + n + "s", s)
    }

    private val currentTime: String
        get() {
            if (sdf == null) {
                sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
            }
            if (date == null) {
                date = Date(System.currentTimeMillis())
            }
            date!!.time = System.currentTimeMillis()
            return sdf!!.format(date)
        }
}
