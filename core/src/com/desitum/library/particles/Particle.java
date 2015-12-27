package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class Particle extends Sprite {
    /*
    TODO I need to make a new Particle... I think this one creates too many objects for such a simple thing
    I believe this can be done with simply using values such as...

    - lifespan
    - gravityX
    - gravityY
    - velocityX
    - velocityY
    - rotationAmount
    - opacity
    - boundaryRectangle

    By just giving the Particle these values you end up dealing with more primitives and less objects.
    This also cuts down on expensive calls such as update(delta) for 4 separate animators
    Animators aren't the cheapest thing in the world, they're cool and useful, but shouldn't be
    used everywhere.
     */

    private float currentLife, lifespan, gravityX, gravityY, velocityX, velocityY, rotationAmount, opacity;
    private boolean fadeOut, remove;

    public Particle(Texture texture, float width, float height, float lifespan) {
        super(texture);

        setSize(width, height);
        setup(lifespan, 0, 0, 0, 0, 0, 1, true);
    }

    public Particle(Texture texture, float width, float height, float lifespan, float gravityX,
                    float gravityY, float velocityX, float velocityY, float rotationAmount,
                    float opacity, boolean fadeOut) {
        super(texture);

        setSize(width, height);
        setup(lifespan, gravityX, gravityY, velocityX, velocityY, rotationAmount, opacity, fadeOut);
    }

    public void update(float delta) {
        currentLife += delta;
        if (currentLife >= lifespan) {
            currentLife = lifespan;
            remove = true;
        }
        velocityX += gravityX * delta;
        velocityY += gravityY * delta;
        this.setX(getX() + velocityX * delta);
        this.setY(getY() + velocityY * delta);
        this.setRotation(getRotationAmount() + rotationAmount * delta);
        if (fadeOut) this.setColor(1, 1, 1, opacity - opacity * (lifespan - currentLife) / 1);
    }

    private void setup(float lifespan, float gravityX, float gravityY, float velocityX,
                       float velocityY, float rotationAmount, float opacity, boolean fadeOut) {

        remove = false;
        this.currentLife = 0;
        this.lifespan = lifespan;
        this.gravityX = gravityX;
        this.gravityY = gravityY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.rotationAmount = rotationAmount;
        this.opacity = opacity;
        this.fadeOut = fadeOut;

        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public float getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(float currentLife) {
        this.currentLife = currentLife;
    }

    public float getLifespan() {
        return lifespan;
    }

    public void setLifespan(float lifespan) {
        this.lifespan = lifespan;
    }

    public float getGravityX() {
        return gravityX;
    }

    public void setGravityX(float gravityX) {
        this.gravityX = gravityX;
    }

    public float getGravityY() {
        return gravityY;
    }

    public void setGravityY(float gravityY) {
        this.gravityY = gravityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getRotationAmount() {
        return rotationAmount;
    }

    public void setRotationAmount(float rotationAmount) {
        this.rotationAmount = rotationAmount;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public boolean isFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(boolean fadeOut) {
        this.fadeOut = fadeOut;
    }

    public boolean needToRemove() {
        return remove;
    }
}
