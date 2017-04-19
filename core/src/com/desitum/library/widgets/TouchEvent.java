package com.desitum.library.widgets;

/**
 * Created by kodyvanry on 4/19/17.
 */

public class TouchEvent {

    public enum Action {
        DOWN,
        UP,
        MOVE,
        NONE
    }

    private Action action;
    private float x;
    private float y;

    public TouchEvent() {
        // Default constructor
        action = Action.NONE;
        x = 0;
        y = 0;
    }

    public TouchEvent(Action action, float x, float y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
