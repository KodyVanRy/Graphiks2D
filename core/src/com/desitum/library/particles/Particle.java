package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.animation.ColorEffects;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class Particle extends Sprite {
    /*

    - mLifespan
    - mGravityX
    - gravityY
    - mVelocityX
    - mVelocityY
    - mRotationAmount
    - mOpacity
    - boundaryRectangle

    By just giving the Particle these values you end up dealing with more primitives and less objects.
    This also cuts down on expensive calls such as update(delta) for 4 separate animators
    Animators aren't the cheapest thing in the world, they're cool and useful, but shouldn't be
    used everywhere.
     */

    private float mCurrentLife, mLifespan, mGravityX, mGravityY, mVelocityX, mVelocityY, mRotationAmount, mOpacity;
    private boolean mFadeOut, mRemove;
    private ColorEffects mColorEffects;

    public Particle(Texture texture, float width, float height, float lifespan) {
        super(texture);

        setSize(width, height);
        setup(lifespan, 0, 0, 0, 0, 0, 1, true);
    }

    public Particle(Texture texture, float x, float y, float width, float height, float lifespan, float gravityX,
                    float gravityY, float velocityX, float velocityY, float rotationAmount,
                    float opacity, boolean fadeOut) {
        super(texture);

        setSize(width, height);
        setPosition(x, y);
        setup(lifespan, gravityX, gravityY, velocityX, velocityY, rotationAmount, opacity, fadeOut);
    }

    public void update(float delta) {
        mCurrentLife += delta;
        if (mCurrentLife >= mLifespan) {
            mCurrentLife = mLifespan;
            mRemove = true;
        }
        mVelocityX += mGravityX * delta;
        mVelocityY += mGravityY * delta;
        this.setX(getX() + mVelocityX * delta);
        this.setY(getY() + mVelocityY * delta);
        this.setRotation(mRotationAmount * mCurrentLife / mLifespan);
        if (mFadeOut && mColorEffects != null) {
            mColorEffects.update(delta);
            this.setColor(
                    mColorEffects.getCurrentRed(),
                    mColorEffects.getCurrentGreen(),
                    mColorEffects.getCurrentBlue(),
                    mColorEffects.getCurrentAlpha());
        }
    }

    public void setup(float lifespan, float gravityX, float gravityY, float velocityX,
                       float velocityY, float rotationAmount, float opacity, boolean fadeOut) {

        this.mRemove = false;
        this.mCurrentLife = 0;
        this.mLifespan = lifespan;
        this.mGravityX = gravityX;
        this.mGravityY = gravityY;
        this.mVelocityX = velocityX;
        this.mVelocityY = velocityY;
        this.mRotationAmount = rotationAmount;
        this.mOpacity = opacity;
        this.mFadeOut = fadeOut;
        if (fadeOut) {
            mColorEffects = new ColorEffects(new Color(1, 1, 1, 1), new Color(1, 1, 1, 0.0f), lifespan);
            mColorEffects.start(false);
        }

        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public float getCurrentLife() {
        return mCurrentLife;
    }

    public void setCurrentLife(float currentLife) {
        this.mCurrentLife = currentLife;
    }

    public float getLifespan() {
        return mLifespan;
    }

    public void setLifespan(float lifespan) {
        this.mLifespan = lifespan;
    }

    public float getGravityX() {
        return mGravityX;
    }

    public void setGravityX(float gravityX) {
        this.mGravityX = gravityX;
    }

    public float getGravityY() {
        return mGravityY;
    }

    public void setGravityY(float gravityY) {
        this.mGravityY = gravityY;
    }

    public float getVelocityX() {
        return mVelocityX;
    }

    public void setVelocityX(float velocityX) {
        this.mVelocityX = velocityX;
    }

    public float getVelocityY() {
        return mVelocityY;
    }

    public void setVelocityY(float velocityY) {
        this.mVelocityY = velocityY;
    }

    public float getRotationAmount() {
        return mRotationAmount;
    }

    public void setRotationAmount(float rotationAmount) {
        this.mRotationAmount = rotationAmount;
    }

    public float getOpacity() {
        return mOpacity;
    }

    public void setOpacity(float opacity) {
        this.mOpacity = opacity;
    }

    public boolean isFadeOut() {
        return mFadeOut;
    }

    public void setFadeOut(boolean fadeOut) {
        this.mFadeOut = fadeOut;
    }

    public boolean needToRemove() {
        return mRemove;
    }
}
