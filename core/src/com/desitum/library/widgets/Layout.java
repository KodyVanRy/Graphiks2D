package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public abstract class Layout extends Widget {

    private ArrayList<Widget> widgets;

    private float paddingLeft, paddingRight, paddingBottom, paddingTop, padding;

    public Layout(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);

        widgets = new ArrayList<Widget>();
    }

    public void draw(Batch batch, Viewport camera) {
        super.draw(batch, camera);
        if (isVisible()) {
            for (Widget widget : widgets) {
                if (!widget.isVisible()) {
                    continue;
                }
                if (widget instanceof FloatingButton) {
                    widget.drawShadow(batch);
                }
                // TODO add more for each widget that is floating draw a shadow
            }
            for (Widget widget : widgets) {
                widget.draw(batch, camera);
            }
        }
        batch.flush();
    }

    public float getBaseX() {
        return getX() + getPaddingLeft();
    }

    public float getBaseY() {
        return getY() + getPaddingBottom();
    }

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public float getPaddingBottom() {
        return paddingBottom;
    }

    public float getPaddingRight() {
        return paddingRight;
    }

    public float getPaddingTop() {
        return paddingTop;
    }

    public float getPadding() {
        return padding;
    }

    public void addWidget(Widget widget) {
        if (widget.getZ() < getZ()) {
            throw new LayeringException("Can't place widget beneath parent");
        }
        widget.setParent(this);
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public void setZ(float z) {
        super.setZ(z);
        for (Widget widget : widgets) {
            if (widget.getZ() < getZ()) {
                throw new LayeringException("Can't place parent above children");
            }
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for (Widget widget : widgets) {
            widget.update(delta);
        }
    }

//    @Override
//    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
//        super.updateTouchInput(mousePos, clickDown);
//        if (isVisible()) {
//
//            for (int i = widgets.size() - 1; i >= 0; i--) {
//                widgets.get(i).updateTouchInput(mousePos, clickDown);
//                if (widgets.get(i).isPointInWidget(mousePos)) {
//                    break;
//                }
//            }
//        }
//    }


    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
        if (isVisible()) {
            for (Widget widget : widgets) {
                switch (touchEvent.getAction()) {
                    case DOWN:
                        if (widget.isPointInWidget(touchEvent.getX(), touchEvent.getY())) {
                            if (widget.onTouchEvent(touchEvent)) {
                                return true;
                            }
                        }
                        break;
                }
            }
        }
        return super.onTouchEvent(touchEvent);
    }

    @Override
    public Widget requestFocus(Vector3 touchPoint) {
        for (Widget widget : widgets) {
            if (widget.isPointInWidget(touchPoint)) {
                return widget.requestFocus(touchPoint);
            }
        }
        return this;
    }

    @Override
    public ArrayList<Widget> tree(ArrayList<Widget> widgets1) {
        if (widgets1 == null) widgets1 = new ArrayList<Widget>();
        for (Widget widget : widgets) widgets1 = widget.tree(widgets1);
        return super.tree(widgets1);
    }

    @Override
    public Widget findByName(String name) {
        for (Widget widget : widgets) {
            if (widget.findByName(name) != null) {
                return widget;
            }
        }

        return super.findByName(name);
    }

    public void sortWidgets() {
        Collections.sort(widgets);
    }

    public int getChildCount() {
        return widgets.size();
    }
}
