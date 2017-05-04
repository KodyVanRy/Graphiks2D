package com.desitum.library.view;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class LayoutConstraints {

    public static final float WRAP_CONTENT = -1;
    public static final float MATCH_PARENT = -2;

    float x;
    float y;

    float width;
    float height;

    public LayoutConstraints(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
