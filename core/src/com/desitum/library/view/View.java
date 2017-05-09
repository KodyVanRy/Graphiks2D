package com.desitum.library.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.animation.Animator;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.game.G2DSprite;
import com.desitum.library.game.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class View extends G2DSprite {

    protected static final int VERTEX_SIZE = 2 + 1 + 2;
    protected static final int VIEW_SIZE = 4 * VERTEX_SIZE;

    public static final int VISIBLE = 1;
    public static final int INVISIBLE = 1 << 1;
    public static final int GONE = 1 << 2;

    public static final float MATCH_PARENT = LayoutConstraints.MATCH_PARENT;
    public static final float WRAP_CONTENT = LayoutConstraints.WRAP_CONTENT;

    // TOUCH
    private boolean mFocus = false;
    private boolean mEnabled = true;
    private boolean mFocusable = true;
    private boolean mClickable = false;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;

    // DRAWING
    private Drawable mBackgroundDrawable;
    private int mVisibility;
    private float mRotation;

    // LAYOUT
    private LayoutConstraints mLayoutConstraints;
    private boolean mDirty = true;

    // CLASS VARS
    private World mWorld;
    private ViewGroup mParent;

    private int mId;
    private String mName;
    private List<Animator> mAnimators;

    public View(World world) {
        this(world, null);
    }

    public View(World world, LayoutConstraints layoutConstraints) {
        super();
        mWorld = world;
        mLayoutConstraints = layoutConstraints;
        mAnimators = new ArrayList<Animator>();
    }

    // -----------------------------
    // region Animation methods
    // -----------------------------
    @Override
    public void update(float delta) {
        if (mDirty)
            dispatchLayout();
        for (Animator animator : mAnimators) {
            animator.update(delta);
        }
    }

    public void addAnimation(Animator animator) {
        if (mAnimators == null)
            mAnimators = new ArrayList<Animator>();
        mAnimators.add(animator);
    }

    public void startAnimator(Animator animator) {
        addAnimation(animator);
        animator.start(false);
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Layout methods
    // -----------------------------
    public LayoutConstraints onMeasure() {
        // TODO this needs to actually measure things and work correctly :D
        return mLayoutConstraints;
    }

    protected void dispatchLayout() {
        if ((mVisibility & GONE) != 0) { // VISIBILITY IS SET TO GONE
            super.setX(0);
            super.setY(0);
            super.setSize(0, 0);
        } else if (mLayoutConstraints != null) {
            if (mLayoutConstraints.width == LayoutConstraints.MATCH_PARENT && mParent != null)
                super.setSize(mParent.getWidth(), mLayoutConstraints.height);
            else if (mLayoutConstraints.height == LayoutConstraints.MATCH_PARENT && mParent != null)
                super.setSize(mLayoutConstraints.width, mParent.getHeight());
            else
                super.setSize(mLayoutConstraints.width, mLayoutConstraints.height);

            if (mParent != null) {
                if (mLayoutConstraints.x == LayoutConstraints.CENTER_HORIZONTAL)
                    super.setX(mParent.getX() + mParent.getWidth() - getWidth() / 2);
                else
                    super.setX(mParent.getX() + mLayoutConstraints.x);
                if (mLayoutConstraints.y == LayoutConstraints.CENTER_VERTICAL)
                    super.setY(mParent.getY() + mParent.getHeight() - getHeight() / 2);
                super.setY(mParent.getY() + mLayoutConstraints.y);
            } else {
                super.setX(mLayoutConstraints.x);
                super.setY(mLayoutConstraints.y);
            }
        }
        mDirty = false;
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Drawing methods
    // -----------------------------
    public void draw(Batch batch, Viewport viewport) {
        /*
        Need to draw in the following order

        1. Background
        2. Draw view itself
        3. Draw any possible children
        4. Draw foreground
         */
        // 1. Draw background
        if (mBackgroundDrawable != null)
            mBackgroundDrawable.draw(batch, getX(), getY(), getWidth(), getHeight());

        // 2. Draw view itself
        onDraw(batch, viewport);

        // 3. Draw any possible children
        dispatchDraw(batch, viewport);

        // 4. Draw the foreground / view decorations
        drawForeground(batch, viewport);
    }

    public void onDraw(Batch spriteBatch, Viewport viewport) {

    }

    public void dispatchDraw(Batch spriteBatch, Viewport viewport) {

    }

    public void drawForeground(Batch spriteBatch, Viewport viewport) {

    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Input methods
    // -----------------------------
    public final boolean dispatchTouchEvent(TouchEvent touchEvent) {
        if (mOnTouchListener != null) {
            mOnTouchListener.onTouchEvent(this, touchEvent);
        }
        if (hasFocus()) {
            if (mOnClickListener != null
                    && mClickable
                    && touchEvent.getAction() == TouchEvent.Action.UP
                    && isTouching(touchEvent)) {
                mOnClickListener.onClick(this);
            }
        }
        if (isEnabled() || this instanceof EditText) {
            if (touchEvent.getAction() == TouchEvent.Action.DOWN) {
                mWorld.requestFocus(this);
            }
            return onTouchEvent(touchEvent);
        }
        return true;
    }

    public boolean onTouchEvent(TouchEvent touchEvent) {
        return true;
    }

    public boolean isTouching(TouchEvent touchEvent) {
        return getBoundingRectangle().contains(touchEvent.getX(), touchEvent.getY());
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Getters & Setters
    // -----------------------------
    public boolean hasFocus() {
        return mFocus;
    }

    public void setFocus(boolean focus) {
        this.mFocus = focus;
    }

    public View requestFocus(TouchEvent touchEvent) {
        if (!mFocusable || (mVisibility & INVISIBLE) != 0) {
            return null;
        }
        if (!isTouching(touchEvent)) {
            return null;
        }
        setFocus(true);
        return this;
    }

    public void clearFocus() {
        mFocus = false;
    }

    public boolean isFocusable() {
        return mFocusable;
    }

    public void setFocusable(boolean focusable) {
        this.mFocusable = focusable;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public Drawable getBackgroundDrawable() {
        return mBackgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.mBackgroundDrawable = backgroundDrawable;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setRotation(float rotation) {
        this.mRotation = rotation;
    }

    public LayoutConstraints getLayoutConstraints() {
        return mLayoutConstraints;
    }

    public void setLayoutConstraints(LayoutConstraints layoutConstraints) {
        this.mLayoutConstraints = layoutConstraints;
    }

    public World getWorld() {
        return mWorld;
    }

    public void setWorld(World mWorld) {
        this.mWorld = mWorld;
    }

    public ViewGroup getParent() {
        return mParent;
    }

    public void setParent(ViewGroup parent) {
        this.mParent = parent;
    }

    @Override
    public void setSize(float width, float height) {
        if (mLayoutConstraints == null)
            mLayoutConstraints = new LayoutConstraints(getX(), getY(), width, height);

        mLayoutConstraints.width = width;
        mLayoutConstraints.height = height;
        invalidate();
    }

    @Override
    public void setX(float x) {
        if (mLayoutConstraints == null)
            mLayoutConstraints = new LayoutConstraints(getX(), getY(), getWidth(), getHeight());
        mLayoutConstraints.x = x;
    }

    @Override
    public void setY(float y) {
        if (mLayoutConstraints == null)
            mLayoutConstraints = new LayoutConstraints(getX(), getY(), getWidth(), getHeight());
        mLayoutConstraints.y = y;
    }

    @Override
    public void setPosition(float x, float y) {
        if (mLayoutConstraints == null)
            mLayoutConstraints = new LayoutConstraints(getX(), getY(), getWidth(), getHeight());
        mLayoutConstraints.x = x;
        mLayoutConstraints.y = y;
        invalidate();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setVisibility(int mVisibility) {
        this.mVisibility = mVisibility;
    }

    public int getVisibility() {
        return mVisibility;
    }

    public boolean isClickable() {
        return mClickable;
    }

    public void setClickable(boolean mClickable) {
        this.mClickable = mClickable;
    }

    @Override
    public void setColor(float color) {
        super.setColor(color);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
    }

    @Override
    public void setColor(Color tint) {
        super.setColor(tint);
    }

    public void invalidate() {
        mDirty = true;
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Listeners
    // -----------------------------
    public interface OnTouchListener {
        void onTouchEvent(View view, TouchEvent touchEvent);
    }

    public interface OnClickListener {
        void onClick(View view);
    }
    // -----------------------------
    // endregion
    // -----------------------------
}
