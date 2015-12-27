package com.desitum.library.widgets;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public class PopupMenuException extends Exception {
    public PopupMenuException() {
        super();
    }

    public PopupMenuException(String message) {
        super(message);
        System.out.println("Can't add PopupMenu inside a PopupMenu");
    }

    public PopupMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public PopupMenuException(Throwable cause) {
        super(cause);
    }
}