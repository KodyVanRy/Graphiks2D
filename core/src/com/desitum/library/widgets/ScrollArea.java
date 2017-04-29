package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.interpolation.Interpolation;

import java.util.ArrayList;

/**
 * Created by dvan6234 on 4/23/2015.
 */
public class ScrollArea extends Layout {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    private float scrollAmount;
    private int scrollDirection;

    private float lastDelta;
    private float touchDuration;
    private Vector3 lastTouchPos;

    private MovementAnimator slideAnimator;
    private Layout child;

    public ScrollArea(Texture background, String name, float x, float y, float width, float height, int scrollDirection, Layout layout) {
        super(background, name, width, height, x, y, layout);

        super.setSize(width, height);
        scrollAmount = 0;
        this.scrollDirection = scrollDirection;
        this.touchDuration = 0;
        this.setPosition(x, y);

        lastTouchPos = new Vector3(0, 0, 0);
    }

    private void updateScroll(float amount) {
        scrollAmount += amount;

        if (scrollAmount > 0) {
            scrollAmount = 0;
        } else if (scrollDirection == HORIZONTAL && scrollAmount < -getChild().getWidth()) {
            scrollAmount = -getChild().getWidth();
        } else if (scrollDirection == VERTICAL && scrollAmount < -getChild().getHeight()) {
            scrollAmount = -getChild().getHeight();
        }
        updateWidgets();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        lastDelta = delta;

        if (slideAnimator != null) {
            if (slideAnimator.isRunning()) {
                slideAnimator.update(delta);
                scrollAmount = slideAnimator.getCurrentAmount();
                updateWidgets();
            }
        }
    }

    public void updateWidgets() {
        if (scrollDirection == HORIZONTAL) {
            getChild().setX(getX() + scrollAmount); // TODO untested
        } else {
            getChild().setY(getY() + scrollAmount);
        }
    }


    /**
     * updates the Layout with touch, done automatically in {@link com.desitum.library.game.World}
     *
     * @param touchEvent  - touchPos in relation to the world
     */
    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
        super.onTouchEvent(touchEvent);
        if (touchEvent.getAction() == TouchEvent.Action.MOVE ||
                touchEvent.getAction() == TouchEvent.Action.DOWN)
            touchDuration += lastDelta;
        else touchDuration = 0;
        if (touchEvent.getAction() == TouchEvent.Action.MOVE && touchDuration >= 0.25f) {
            updateScroll(touchEvent.getY() - lastTouchPos.y);
        } else {
            getChild().onTouchEvent(touchEvent);
        }

        lastTouchPos.set(touchEvent.getX(), touchEvent.getY(), 0);
        return true;
    }

    @Override
    public Widget requestFocus(Vector3 touchPoint) {
        return this;
    }

    public void setContent(Layout toAdd) {
        if (scrollDirection == VERTICAL && toAdd.getWidth() == -1) {
            toAdd.setSize(toAdd.getHeight(), getWidth());
        } else if (scrollDirection == HORIZONTAL && toAdd.getHeight() == -1) {
            toAdd.setSize(getHeight(), toAdd.getWidth());
        }

        toAdd.setPosition(getX(), getY() + getHeight() - toAdd.getHeight()); // set it to fill the beginning of the layout

        toAdd.setParent(this);

        setChild(toAdd);
        updateWidgets();
    }

    public void slideToPosition(float position) {
        slideAnimator = new MovementAnimator(scrollAmount, position, 0.75f, Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR);
        slideAnimator.start(false);
    }

    public void goToPosition(float position) {
        scrollAmount = position;
        updateWidgets();
    }

    public float getPositionToCenter() {
        return getChild().getWidth() / 2 - getWidth() / 2;
    }

    @Override
    public ArrayList<Widget> tree(ArrayList<Widget> widgets1) {
        if (widgets1 == null)
            widgets1 = new ArrayList<Widget>();
        widgets1.add(child);
        return super.tree(widgets1);
    }

    public Layout getChild() {
        return child;
    }

    public void setChild(Layout child) {
        this.child = child;
    }

    @Override
    public void addWidget(Widget widget) {
        if (getChildCount() > 0) {
            throw new RuntimeException("Scroll Area can only contain one child.");
        }
        super.addWidget(widget);
    }

    public int getScrollDirection() {
        return scrollDirection;
    }
}
