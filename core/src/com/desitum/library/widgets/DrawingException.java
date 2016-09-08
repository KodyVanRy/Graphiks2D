package com.desitum.library.widgets;

/**
 * Created by kody on 2/22/15.
 * can be used by kody and people in [kody}]
 */

public class DrawingException extends RuntimeException {
    public DrawingException() {
        super("Use method draw(Batch batch, Camera camera)");
    }

    public DrawingException(String message) {
        super(message);
    }

    public DrawingException(String message, Throwable cause) {
        super("Can't put child beneath parent", cause);
    }

    public DrawingException(Throwable cause) {
        super(cause);
    }
}
