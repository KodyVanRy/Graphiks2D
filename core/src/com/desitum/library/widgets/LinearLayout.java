package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class LinearLayout extends Layout {

    public static final int VERTICAL_ORIENTATION = 0;
    public static final int HORIZONTAL_ORIENTATION = 1;
    public static final int ALIGNMENT_TOP = 0;
    public static final int ALIGNMENT_BOTTOM = 1;
    public static final int ALIGNMENT_CENTER = 2;
    public static final int ALIGNMENT_RIGHT = 1;
    public static final int ALIGNMENT_LEFT = 0;

    private int mOrientation;
    private float mSpacing;
    private int mAlignment;
    private boolean mReverseFill;

    public LinearLayout(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);
        mOrientation = HORIZONTAL_ORIENTATION;
        this.mSpacing = 0;
        mReverseFill = false;
    }

    public LinearLayout(Texture text, String name, float width, float height, float x, float y, Layout parent, int orientation) {
        super(text, name, width, height, x, y, parent);
        this.mOrientation = orientation;
        this.mSpacing = 0;
        mReverseFill = false;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
    }

    public int getAlignment() {
        return mAlignment;
    }

    public void setAlignment(int alignment) {
        this.mAlignment = alignment;
    }

    public float getSpacing() {
        return mSpacing;
    }

    public void setSpacing(float spacing) {
        this.mSpacing = spacing;
    }

    public boolean isReverseFill() {
        return mReverseFill;
    }

    public void setReverseFill(boolean reverseFill) {
        this.mReverseFill = reverseFill;
    }

    @Override
    public void addWidget(Widget widget) {
        widget.setParent(this);
        float posX = 0;
        float posY = 0;
        if (mReverseFill) {
            posX = getWidth() - (getWidgets().size() >= 1 ? getWidgets().get(0).getWidth() : widget.getWidth());
        } else {
            posY = getHeight() - (getWidgets().size() >= 1 ? getWidgets().get(0).getHeight() : widget.getHeight());
        }
        for (Widget preWidget : getWidgets()) {
            if (mReverseFill) {
                posX -= preWidget.getWidth() + mSpacing;
                posY += preWidget.getHeight() + mSpacing;
            } else {
                posX += preWidget.getWidth() + mSpacing;
                posY -= preWidget.getHeight() + mSpacing;
            }
        }
        if (mOrientation == HORIZONTAL_ORIENTATION) {
            if (mAlignment == ALIGNMENT_TOP) {
                posY = this.getHeight() - widget.getHeight();
            } else if (mAlignment == ALIGNMENT_CENTER) {
                posY = this.getHeight() / 2 - widget.getHeight() / 2;
            } else if (mAlignment == ALIGNMENT_BOTTOM) {
                posY = 0;
            }
        } else if (mOrientation == VERTICAL_ORIENTATION) {
            if (mAlignment == ALIGNMENT_LEFT) {
                posX = 0;
            } else if (mAlignment == ALIGNMENT_CENTER) {
                posX = this.getWidth() / 2 - widget.getWidth() / 2;
            } else if (mAlignment == ALIGNMENT_RIGHT) {
                posX = this.getWidth() - widget.getWidth();
            }
        }
        widget.setMx(posX);
        widget.setMy(posY);
        super.addWidget(widget);
    }
}
