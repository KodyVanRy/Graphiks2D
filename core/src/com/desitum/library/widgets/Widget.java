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

    private ArrayList<Animator> animators;

    private Layout parent;

    private Texture shadow;

    private String name;

    private int visibility;

    private float mScaleX;
    private float mScaleY;
    private float mx;
    private float my;
    private float z;

    private boolean enabled;
    private boolean clickIsDown;


    /**
     * Create a new {@link Widget}
     * @param text {@link Texture} to be used as the background
     * @param name String used to identify {@link Widget}
     * @param width Width of widget
     * @param height height of widget
     * @param x x position of widget
     * @param y y position of widget
     */
    public Widget(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, text.getWidth(), text.getHeight());
        setSize(width, height);
        this.name = name;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mx = x;
        this.my = y;
        this.visibility = VISIBLE;
        enabled = true;
        clickIsDown = false;

        this.setOriginCenter();

        this.animators = new ArrayList<Animator>();
    }

    /**
     * update every frame
     * @param delta time since last update
     */
    public void update(float delta) {
        for (Animator anim : animators) {
            anim.update(delta);
            updateAnim(anim);
        }

        // Adjust the position (x, y); the scale of width and height to fit parent
        if (parent != null) {
            setX(getMx() + parent.getBaseX());
            setY(getMy() + parent.getBaseY());
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
     * Useful when scaling a parent view
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
     * Useful when rotating a parent view
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
        batch.dr
    }

    /**
     * Use instead of draw. Allows for use of {@link com.badlogic.gdx.scenes.scene2d.utils.ScissorStack}
     * @param batch {@link Batch} to draw to
     * @param camera {@link Camera} to use when calculating the scissor
     */
    public void draw(Batch batch, Viewport camera) {
        if (visibility == VISIBLE) {
            if (getParent() == null) drawShadow(batch);
            super.draw(batch);
        }
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getVisibility() {
        return visibility;
    }

    public void drawShadow(Batch spriteBatch) {
        if (shadow == null) return;
        spriteBatch.draw(shadow, getX(), getY() - getHeight() * (z / 6.0f), getWidth() / 2, getHeight() / 2,
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                shadow.getWidth(), shadow.getHeight(), false, false);
    }

    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
        clickIsDown = touchDown;
    }

    //region Getters and Setters
    public boolean isClickDown() {
        return clickIsDown;
    }

    public boolean isPointInWidget(Vector3 point) {
        return getBoundingRectangle().contains(point.x, point.y);
    }

    public float getMx() {
        return mx;
    }

    public void setMx(float x) {
        mx = x;
    }

    public float getMy() {
        return my;
    }

    public void setMy(float y) {
        my = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Layout getParent() {
        return parent;
    }

    public void setParent(Layout parent) {
        this.parent = parent;
    }

    public ArrayList<Animator> getAnimators() {
        return animators;
    }

    public void setAnimators(ArrayList<Animator> animators) {
        this.animators = animators;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
        if (parent != null && getZ() < parent.getZ()) {
            throw new LayeringException("Can't place widget beneath parent");
        }
    }

    public Texture getShadow() {
        return shadow;
    }

    public void setShadow(Texture shadow) {
        this.shadow = shadow;
    }

    public void startAnimator(Animator animator) {
        animator.setSprite(this);
        animator.start(true);
        animators.add(animator);
    }

    public void addAnimator(Animator animator) {
        animator.setSprite(this);
        animators.add(animator);
    }

    public void startAnimators() {
        for (Animator animator : animators) {
            animator.start(true);
        }
    }

    public Widget findByName(String name) {
        if (this.name.equals(name)) {
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
        return this.visibility == VISIBLE;
    }
    //endregion

    public void dispose() {
        try {
            shadow.dispose();
            getTexture().dispose();
        } catch (Exception e) {
            // Texture or shadow have been disposed elsewhere
        }
    }
}
