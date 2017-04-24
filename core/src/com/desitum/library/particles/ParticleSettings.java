package com.desitum.library.particles;

/**
 * Created by kody on 4/8/15.
 * can be used by kody and people in []
 */
public class ParticleSettings {

    private static float lastWidth;
    private float minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY,
            maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY,
            minRotationAmount, maxRotationAmount, opacity, lifespan;
    private boolean fadeOut, square;

    public ParticleSettings(float minWidth, float maxWidth, float minHeight, float maxHeight,
                            float minGravityX, float maxGravityX, float minGravityY, float maxGravityY,
                            float minVelocityX, float maxVelocityX, float minVelocityY, float maxVelocityY,
                            float minRotationAmount, float maxRotationAmount, float opacity, float lifespan,
                            boolean fadeOut, boolean makeSquare) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minGravityX = minGravityX;
        this.maxGravityX = maxGravityX;
        this.minGravityY = minGravityY;
        this.maxGravityY = maxGravityY;
        this.minVelocityX = minVelocityX;
        this.maxVelocityX = maxVelocityX;
        this.minVelocityY = minVelocityY;
        this.maxVelocityY = maxVelocityY;
        this.minRotationAmount = minRotationAmount;
        this.maxRotationAmount = maxRotationAmount;
        this.opacity = opacity;
        this.lifespan = lifespan;
        this.fadeOut = fadeOut;
        this.square = makeSquare;
    }

    public float getMinWidth() {
        return minWidth;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public float getWidth() {
        lastWidth = ((float) Math.random() * (maxWidth - minWidth) + minWidth);
        return lastWidth;
    }

    public float getMinHeight() {
        if (square) return minWidth;
        return minHeight;
    }

    public float getMaxHeight() {
        if (square) return maxWidth;
        return maxHeight;
    }

    public float getHeight() {
        if (square) //noinspection SuspiciousNameCombination
            return lastWidth;
        return ((float) Math.random() * (maxHeight - minHeight) + minHeight);
    }

    public float getMinGravityX() {
        return minGravityX;
    }

    public float getMaxGravityX() {
        return maxGravityX;
    }

    public float getGravityX() {
        return ((float) Math.random() * (maxGravityX - minGravityX) + minGravityX);
    }

    public float getMinGravityY() {
        return minGravityY;
    }

    public float getMaxGravityY() {
        return maxGravityY;
    }

    public float getGravityY() {
        return ((float) Math.random() * (maxGravityY - minGravityY) + minGravityY);
    }

    public float getMinVelocityX() {
        return minVelocityX;
    }

    public float getMaxVelocityX() {
        return maxVelocityX;
    }

    public float getVelocityX() {
        return ((float) Math.random() * (maxVelocityX - minVelocityX) + minVelocityX);
    }

    public float getMinVelocityY() {
        return minVelocityY;
    }

    public float getMaxVelocityY() {
        return maxVelocityY;
    }

    public float getVelocityY() {
        return ((float) Math.random() * (maxVelocityY - minVelocityY) + minVelocityY);
    }

    public float getMinRotationAmount() {
        return minRotationAmount;
    }

    public float getMaxRotationAmount() {
        return maxRotationAmount;
    }

    public float getRotationAmount() {
        return ((float) Math.random() * (maxRotationAmount - minRotationAmount) + minRotationAmount);
    }

    public float getOpacity() {
        return opacity;
    }

    public float getLifespan() {
        return lifespan;
    }

    public boolean isFadeOut() {
        return fadeOut;
    }

    public void setSquare(boolean square) {
        this.square = square;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public void setMaxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public void setMaxHeight(float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setMinGravityX(float minGravityX) {
        this.minGravityX = minGravityX;
    }

    public void setMaxGravityX(float maxGravityX) {
        this.maxGravityX = maxGravityX;
    }

    public void setMinGravityY(float minGravityY) {
        this.minGravityY = minGravityY;
    }

    public void setMaxGravityY(float maxGravityY) {
        this.maxGravityY = maxGravityY;
    }

    public void setMinVelocityX(float minVelocityX) {
        this.minVelocityX = minVelocityX;
    }

    public void setMaxVelocityX(float maxVelocityX) {
        this.maxVelocityX = maxVelocityX;
    }

    public void setMinVelocityY(float minVelocityY) {
        this.minVelocityY = minVelocityY;
    }

    public void setMaxVelocityY(float maxVelocityY) {
        this.maxVelocityY = maxVelocityY;
    }

    public void setMinRotationAmount(float minRotationAmount) {
        this.minRotationAmount = minRotationAmount;
    }

    public void setMaxRotationAmount(float maxRotationAmount) {
        this.maxRotationAmount = maxRotationAmount;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void setLifespan(float lifespan) {
        this.lifespan = lifespan;
    }

    public void setFadeOut(boolean fadeOut) {
        this.fadeOut = fadeOut;
    }
}
