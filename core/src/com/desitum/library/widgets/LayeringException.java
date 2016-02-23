package com.desitum.library.widgets;

/**
 * Created by kody on 2/22/15.
 * can be used by kody and people in [kody}]
 */

public class LayeringException extends RuntimeException {
    public LayeringException() {
        super();
    }

    public LayeringException(String message) {
        super(message);
    }

    public LayeringException(String message, Throwable cause) {
        super("Can't put child beneath parent", cause);
    }

    public LayeringException(Throwable cause) {
        super(cause);
    }
}
