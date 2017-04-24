package com.desitum.library.game_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.MovementAnimator;

import java.util.ArrayList;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
@SuppressWarnings("unused")
public class GameObject extends Sprite implements Comparable<GameObject> {

    public static final int DEFAULT_Z = 0;
    private ArrayList<Animator> mAnimators;
    private ArrayList<Animator> mAnimatorsToRemove;
    private OnFinishedMovingListener mOnFinishedMovingListener;
    private int mZ;

    private float mSpeed, mSpeedX, mSpeedY, mGravityX, mGravityY, mRotationSpeed, mRotationResistance;
    private float[] moveTo;

    public GameObject(TextureRegion textureRegion) {
        super(textureRegion);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(Texture texture) {
        super(texture);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(TextureRegion textureRegion, float width, float height) {
        super(textureRegion);
        setSize(width, height);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height) {
        super(texture);
        setSize(width, height);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(TextureRegion textureRegion, float width, float height, float x, float y) {
        super(textureRegion);
        setSize(width, height);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height, float x, float y) {
        super(texture);
        setSize(width, height);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        mZ = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height, float x, float y, int z) {
        super(texture);
        setSize(width, height);
        mAnimators = new ArrayList<Animator>();
        mAnimatorsToRemove = new ArrayList<Animator>();
        this.mZ = z;
    }

    public void update(float delta) {
        updateAnimators(delta);
        updatePosition(delta);
    }

    private void updatePosition(float delta) {
        setSpeedX(getSpeedX() + getGravityX() * delta);
        setSpeedY(getSpeedY() + getGravityY() * delta);
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
        setRotationSpeed(getRotationSpeed() * mRotationResistance);
        if (moveTo != null) {
            updateMovement();
        }
    }

    private void updateAnimators(float delta) {
        if (!mAnimators.isEmpty()) {
            for (Animator animator: mAnimators) {
                animator.update(delta);
                if (animator.didFinish()) {
                    mAnimatorsToRemove.add(animator);
                }
            }
            for (Animator animator: mAnimatorsToRemove) {
                mAnimators.remove(animator);
            }
            mAnimatorsToRemove.clear();
        }
    }

    private void updateMovement() {
        if (moveTo != null) {
            if (mSpeedX < 0) {
                if (getX() < moveTo[0]) {
                    moveFinished();
                }
            } else if (mSpeedX > 0) {
                if (getX() > moveTo[0]) {
                    moveFinished();
                }
            } if (mSpeedY < 0) {
                if (getY() < moveTo[1]) {
                    moveFinished();
                }
            } else if (mSpeedY > 0) {
                if (getY() > moveTo[1]) {
                    moveFinished();
                }
            }
        }
    }

    private void moveFinished() {
        setX(moveTo[0]);
        setY(moveTo[1]);
        moveTo = null;
        if (mOnFinishedMovingListener != null) {
            mOnFinishedMovingListener.onFinished(this);
        }
    }

    @SuppressWarnings("unused")
    public void moveToPosition(float x, float y) {
        // x = cos
        // y = sin
        float deltaX = getX() - x;
        float deltaY = getY() - y;
        float angle = (float) Math.atan2(deltaY, deltaX); // in radians
        mSpeedX = (float) Math.cos(angle) * mSpeed;
        mSpeedY = (float) Math.sin(angle) * mSpeed;
    }

    /**
     * Called when user touches the {@link GameObject}
     * @param touchPos position of touch in {@link com.badlogic.gdx.graphics.Camera} coordinates
     * @param touchDown whether the touch was down or not (touchDown, touchUp)
     */
    @SuppressWarnings("unused")
    public void updateTouchInput(Vector3 touchPos, boolean touchDown) {

    }

    @SuppressWarnings("unused")
    public void smoothMoveTo(float x, float y, float duration, int interpolation) {
        MovementAnimator xAnimator = new MovementAnimator(this, getX(), x, duration, 0, interpolation, true, false);
        MovementAnimator yAnimator = new MovementAnimator(this, getY(), y, duration, 0, interpolation, false, true);
        xAnimator.start(true);
        yAnimator.start(true);
        this.mAnimators.add(xAnimator);
        this.mAnimators.add(yAnimator);
    }

    @SuppressWarnings("unused")
    public void addAndStartAnimator(Animator anim) {
        anim.setSprite(this);
        mAnimators.add(anim);
    }

    public int getZ() {
        return mZ;
    }

    @Override
    public int compareTo(GameObject gameObject2D) {
        return this.getZ() - gameObject2D.getZ();
    }

    @SuppressWarnings("unused")
    public void setZ(int z) {
        this.mZ = z;
    }

    @SuppressWarnings("unused")
    public float getSpeed() {
        return mSpeed;
    }

    @SuppressWarnings("unused")
    public void setSpeed(float speed) {
        this.mSpeed = speed;
    }

    @SuppressWarnings("unused")
    public float getSpeedX() {
        return mSpeedX;
    }

    @SuppressWarnings("unused")
    public void setSpeedX(float speedX) {
        this.mSpeedX = speedX;
    }

    @SuppressWarnings("unused")
    public float getSpeedY() {
        return mSpeedY;
    }

    @SuppressWarnings("unused")
    public void setSpeedY(float speedY) {
        this.mSpeedY = speedY;
    }

    @SuppressWarnings("unused")
    public float getGravityX() {
        return mGravityX;
    }

    @SuppressWarnings("unused")
    public void setGravityX(float gravityX) {
        this.mGravityX = gravityX;
    }

    @SuppressWarnings("unused")
    public float getGravityY() {
        return mGravityY;
    }

    @SuppressWarnings("unused")
    public void setGravityY(float gravityY) {
        this.mGravityY = gravityY;
    }

    @SuppressWarnings("unused")
    public float getRotationSpeed() {
        return mRotationSpeed;
    }

    @SuppressWarnings("unused")
    public void setRotationSpeed(float rotationSpeed) {
        this.mRotationSpeed = rotationSpeed;
    }

    @SuppressWarnings("unused")
    public float getRotationResistance() {
        return mRotationResistance;
    }

    /**
     * Set rotation resistance where less than 1 slows down
     * more than 1 mSpeed up rotation
     * and where 0 stops immediately
     * @param rotationResistance rate to slow down by
     */
    @SuppressWarnings("unused")
    public void setRotationResistance(float rotationResistance) {
        this.mRotationResistance = rotationResistance;
    }

    @SuppressWarnings("unused")
    public void setOnFinishedMovingListener(OnFinishedMovingListener onFinishedMovingListener) {
        this.mOnFinishedMovingListener = onFinishedMovingListener;
    }

    @SuppressWarnings("unused")
    public OnFinishedMovingListener getOnFinishedMovingListener() {
        return mOnFinishedMovingListener;
    }

    public void dispose() {
        try {
            getTexture().dispose();
        } catch (Exception e) {
            // Texture has been disposed of elsewhere
        }
    }
}
