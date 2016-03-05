package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public abstract class Layout extends Widget {

    private ArrayList<Widget> widgets;

    private float paddingLeft, paddingRight, paddingBottom, paddingTop, padding;

    public Layout(Texture text, String name, float width, float height, float X, float Y, Widget parent) {
        super(text, name, width, height, X, Y);

        widgets = new ArrayList<Widget>();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
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
                widget.draw(batch);
            }
        }
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

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
        super.updateTouchInput(mousePos, clickDown);

        for (Widget widget : widgets) {
            widget.updateTouchInput(mousePos, clickDown);
        }
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
}
