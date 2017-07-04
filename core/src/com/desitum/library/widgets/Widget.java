package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.animation.ScaleAnimator;

import java.util.ArrayList;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public class Widget extends Sprite implements Comparable<Widget> {

    public static final String WIDGET = "widget";
    public static final String BUTTON = "button";
    public static final String FLOATING_BUTTON = "floating button";
    public static final String EDIT_TEXT = "edit text";
    public static final String LINEAR_LAYOUT = "linear layout";
    public static final String SLIDER = "slider";

    public static final int VISIBLE = 0;
    public static final int INVISIBLE = 1;
    public static final int GONE = 2;

    private ArrayList<Animator> mAnimators;

    private Layout mParent;

    private Texture mShadow;

    private String mName;

    private int mVisibility;

    private float mScaleX;
    private float mScaleY;
    private float mX;
    private float mY;
    private float mZ;

    private boolean mEnabled;
    private boolean mFocus;

    private int mWidgetFlags = 0;

    private int FOCUSABLE = 1;


    /**
     * Create a new {@link Widget}
     * @param name String used to identify {@link Widget}
     * @param width Width of widget
     * @param height height of widget
     * @param x x position of widget
     * @param y y position of widget
     */
    public Widget(String name, float width, float height, float x, float y) {
        super();
        setSize(width, height);
        this.mName = name;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mX = x;
        this.mY = y;
        this.mVisibility = VISIBLE;
        mEnabled = true;

        this.setOriginCenter();

        this.mAnimators = new ArrayList<Animator>();
    }

    /**
     * update every frame
     * @param delta time since last update
     */
    public void update(float delta) {
        for (Animator anim : mAnimators) {
            anim.update(delta);
            updateAnim(anim);
        }

        // Adjust the position (x, y); the scale of width and height to fit mParent
        if (mParent != null) {
            setX(getMx() + mParent.getBaseX());
            setY(getMy() + mParent.getBaseY());
        } else {
            setX(getMx());
            setY(getMy());
        }
    }

    /**
     * update the widget based on animations, usually used in a for loop
     * @param anim Animator to update
     */
    private void updateAnim(Animator anim) {
        if (anim.isRunning()) {
            if (anim instanceof MovementAnimator && ((MovementAnimator) anim).isControllingX()) {
                setMx(((MovementAnimator) anim).getCurrentPos());
            } else if (anim instanceof MovementAnimator && ((MovementAnimator) anim).isControllingY()) {
                setMy(((MovementAnimator) anim).getCurrentPos());
            } else if (anim instanceof ScaleAnimator && ((ScaleAnimator) anim).isControllingX()) {
                setScale(((ScaleAnimator) anim).getScaleSize(), getScaleY());
            } else if (anim instanceof ScaleAnimator && ((ScaleAnimator) anim).isControllingY()) {
                setScale(getScaleX(), ((ScaleAnimator) anim).getScaleSize());
            }
            //TODO need to add in Rotate Animation! Should be fun with Labels and Edit Text :/
        }
    }

    /**
     * Useful when scaling a mParent view
     * @param originX originX to scale to
     * @param originY originY to scale to
     * @param scaleX amount to scale x by
     * @param scaleY amount to scale y by
     */
    private void scaleToOrigin(float originX, float originY, float scaleX, float scaleY) {
        setOrigin(originX, originY);
        setScale(scaleX, scaleY);
        setOriginCenter();
    }

    /**
     * Useful when rotating a mParent view
     * @param originX x point to rotate around
     * @param originY y point to rotate around
     * @param rotation how much to rotate by
     */
    private void rotateAroundOrigin(float originX, float originY, float rotation) {
        setOrigin(originX, originY);
        setRotation(rotation);
        setOriginCenter();
    }

    @Override
    public void draw(Batch batch) {
        throw new DrawingException();
    }

    /**
     * Use instead of draw. Allows for use of {@link com.badlogic.gdx.scenes.scene2d.utils.ScissorStack}
     * @param batch {@link Batch} to draw to
     * @param camera {@link Camera} to use when calculating the scissor
     */
    public void draw(Batch batch, Viewport camera) {
        if (mVisibility == VISIBLE) {
            if (getParent() == null) drawShadow(batch);
        }
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return mVisibility;
    }

    public void drawShadow(Batch spriteBatch) {
        if (mShadow == null) return;
        spriteBatch.draw(mShadow, getX(), getY() - getHeight() * (mZ / 6.0f), getWidth() / 2, getHeight() / 2,
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                mShadow.getWidth(), mShadow.getHeight(), false, false);
    }

    public boolean onTouchEvent(com.desitum.library.view.TouchEvent touchEvent) {
        return isPointInWidget(touchEvent.getX(), touchEvent.getY());
    }

    public Widget requestFocus(Vector3 touchPoint) {
        return this;
    }

    public boolean isPointInWidget(Vector3 point) {
        return getBoundingRectangle().contains(point.x, point.y);
    }

    public boolean isPointInWidget(float x, float y) {
        return getBoundingRectangle().contains(x, y);
    }

    public float getMx() {
        return mX;
    }

    public void setMx(float x) {
        mX = x;
    }

    public float getMy() {
        return mY;
    }

    public void setMy(float y) {
        mY = y;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Layout getParent() {
        return mParent;
    }

    public void setParent(Layout parent) {
        this.mParent = parent;
    }

    public ArrayList<Animator> getAnimators() {
        return mAnimators;
    }

    public void setAnimators(ArrayList<Animator> animators) {
        this.mAnimators = animators;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public float getZ() {
        return mZ;
    }

    public void setZ(float z) {
        this.mZ = z;
        if (mParent != null && getZ() < mParent.getZ()) {
            throw new LayeringException("Can't place widget beneath mParent");
        }
    }

    public Texture getShadow() {
        return mShadow;
    }

    public void setShadow(Texture shadow) {
        this.mShadow = shadow;
    }

    public void startAnimator(Animator animator) {
        animator.setSprite(this);
        animator.start();
        mAnimators.add(animator);
    }

    public void addAnimator(Animator animator) {
        animator.setSprite(this);
        mAnimators.add(animator);
    }

    public void startAnimators() {
        for (Animator animator : mAnimators) {
            animator.start();
        }
    }

    public Widget findByName(String name) {
        if (this.mName.equals(name)) {
            return this;
        }
        return null;
    }

    public ArrayList<Widget> tree(ArrayList<Widget> widgets) {
        if (widgets == null) widgets = new ArrayList<Widget>();
        widgets.add(this);
        return widgets;
    }

    @Override
    public int compareTo(Widget widget) {
        return Float.compare(this.getZ(), widget.getZ());
    }

    public boolean isVisible() {
        return this.mVisibility == VISIBLE;
    }
    //endregion

    public void dispose() {
        try {
            mShadow.dispose();
            getTexture().dispose();
        } catch (Exception e) {
            // Texture or mShadow have been disposed elsewhere
        }
    }

    public boolean isFocus() {
        return mFocus;
    }
}
