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
    private ArrayList<Animator> animators;
    private ArrayList<Animator> animatorsToRemove;
    private OnFinishedMovingListener onFinishedMovingListener;
    private int z;

    private float speed, speedX, speedY, gravityX, gravityY, rotationSpeed, rotationResistance;
    private float[] moveTo;

    public GameObject(TextureRegion textureRegion) {
        super(textureRegion);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(Texture texture) {
        super(texture);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(TextureRegion textureRegion, float width, float height) {
        super(textureRegion);
        setSize(width, height);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height) {
        super(texture);
        setSize(width, height);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(TextureRegion textureRegion, float width, float height, float x, float y) {
        super(textureRegion);
        setSize(width, height);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height, float x, float y) {
        super(texture);
        setSize(width, height);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        z = DEFAULT_Z;
    }

    public GameObject(Texture texture, float width, float height, float x, float y, int z) {
        super(texture);
        setSize(width, height);
        animators = new ArrayList<Animator>();
        animatorsToRemove = new ArrayList<Animator>();
        this.z = z;
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
        setRotationSpeed(getRotationSpeed() * rotationResistance);
        if (moveTo != null) {
            updateMovement();
        }
    }

    private void updateAnimators(float delta) {
        if (animators.size() > 0) {
            for (Animator animator: animators) {
                animator.update(delta);
                if (animator.didFinish()) {
                    animatorsToRemove.add(animator);
                }
            }
            for (Animator animator: animatorsToRemove) {
                animators.remove(animator);
            }
            animatorsToRemove.clear();
        }
    }

    private void updateMovement() {
        if (moveTo != null) {
            if (speedX < 0) {
                if (getX() < moveTo[0]) {
                    moveFinished();
                }
            } else if (speedX > 0) {
                if (getX() > moveTo[0]) {
                    moveFinished();
                }
            } if (speedY < 0) {
                if (getY() < moveTo[1]) {
                    moveFinished();
                }
            } else if (speedY > 0) {
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
        if (onFinishedMovingListener != null) {
            onFinishedMovingListener.onFinished(this);
        }
    }

    @SuppressWarnings("unused")
    public void moveToPosition(float x, float y) {
        // x = cos
        // y = sin
        float deltaX = getX() - x;
        float deltaY = getY() - y;
        float angle = (float) Math.atan2(deltaY, deltaX); // in radians
        speedX = (float) Math.cos(angle) * speed;
        speedY = (float) Math.sin(angle) * speed;
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
        this.animators.add(xAnimator);
        this.animators.add(yAnimator);
    }

    @SuppressWarnings("unused")
    public void addAndStartAnimator(Animator anim) {
        anim.setSprite(this);
        animators.add(anim);
    }

    public int getZ() {
        return z;
    }

    @Override
    public int compareTo(GameObject gameObject2D) {
        return this.getZ() - gameObject2D.getZ();
    }

    @SuppressWarnings("unused")
    public void setZ(int z) {
        this.z = z;
    }

    @SuppressWarnings("unused")
    public float getSpeed() {
        return speed;
    }

    @SuppressWarnings("unused")
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @SuppressWarnings("unused")
    public float getSpeedX() {
        return speedX;
    }

    @SuppressWarnings("unused")
    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    @SuppressWarnings("unused")
    public float getSpeedY() {
        return speedY;
    }

    @SuppressWarnings("unused")
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    @SuppressWarnings("unused")
    public float getGravityX() {
        return gravityX;
    }

    @SuppressWarnings("unused")
    public void setGravityX(float gravityX) {
        this.gravityX = gravityX;
    }

    @SuppressWarnings("unused")
    public float getGravityY() {
        return gravityY;
    }

    @SuppressWarnings("unused")
    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    @SuppressWarnings("unused")
    public float getRotationSpeed() {
        return rotationSpeed;
    }

    @SuppressWarnings("unused")
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    @SuppressWarnings("unused")
    public float getRotationResistance() {
        return rotationResistance;
    }

    /**
     * Set rotation resistance where less than 1 slows down
     * more than 1 speed up rotation
     * and where 0 stops immediately
     * @param rotationResistance rate to slow down by
     */
    @SuppressWarnings("unused")
    public void setRotationResistance(float rotationResistance) {
        this.rotationResistance = rotationResistance;
    }

    @SuppressWarnings("unused")
    public void setOnFinishedMovingListener(OnFinishedMovingListener onFinishedMovingListener) {
        this.onFinishedMovingListener = onFinishedMovingListener;
    }

    @SuppressWarnings("unused")
    public OnFinishedMovingListener getOnFinishedMovingListener() {
        return onFinishedMovingListener;
    }

    public void dispose() {
        try {
            getTexture().dispose();
        } catch (Exception e) {
            // Texture has been disposed of elsewhere
        }
    }
}
