package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Color;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.ColorEffects;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.animation.RotateAnimator;
import com.desitum.library.interpolation.Interpolation;

import java.util.ArrayList;

/**
 * Created by kody on 4/8/15.
 * can be used by kody and people in []
 */
public class ParticleSettings {

    private float changeX;
    private float changeY;

    private float width;
    private float height;

    private float duration;

    private ArrayList<Animator> animators;

    private MovementAnimator xAnimator;
    private MovementAnimator yAnimator;
    private ColorEffects colorAnimator;
    private RotateAnimator rotateAnimator;

    public ParticleSettings(float x, float y, float objectWidth, float objectHeight, float minDirection, float maxDirection, float minDuration, float maxDuration, float minDistance, float maxDistance, float minRotation, float maxRotation) {

        animators = new ArrayList<Animator>();

        float direction = (float) Math.random() * (maxDirection - minDirection);
        direction += minDirection;
        float distance = (float) Math.random() * (maxDistance - minDistance);
        direction += minDistance;
        duration = (float) Math.random() * (maxDuration - minDuration);
        direction += minDistance;

        changeX = (float) Math.sin(direction) * distance;
        changeY = (float) Math.cos(direction) * distance;

        float rotationAmount = (float) Math.random() * (maxRotation - minRotation);

        float startPosX = (float) Math.random() * (objectWidth) + x;
        float startPosY = (float) Math.random() * (objectHeight) + y;

        xAnimator = new MovementAnimator(startPosX, startPosX + changeX, duration, Interpolation.DECELERATE_INTERPOLATOR);
        yAnimator = new MovementAnimator(startPosY, startPosY + changeY, duration, Interpolation.ACCELERATE_INTERPOLATOR);
        ColorEffects colorEffects = new ColorEffects(new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 0.0f), duration);
        rotateAnimator = new RotateAnimator(duration, 0, rotationAmount, Interpolation.LINEAR_INTERPOLATOR);
        animators.add(xAnimator);
        animators.add(yAnimator);
        animators.add(colorEffects);
        animators.add(rotateAnimator);
    }

    public ParticleSettings(ParticleEmitter pe) {
        animators = new ArrayList<Animator>();

        float direction = (float) Math.random() * (pe.getParticleMaxAngle() - pe.getParticleMinAngle());
        direction += pe.getParticleMinAngle();
        direction = (float) Math.toRadians(direction);
        float distance = (float) Math.random() * (pe.getParticleMaxDistance() - pe.getParticleMinDistance());
        distance += pe.getParticleMinDistance();
        duration = (float) Math.random() * (pe.getParticleMaxDuration() - pe.getParticleMinDuration());
        duration += pe.getParticleMinDuration();

        width = (float) Math.random() * (pe.getParticleMaxWidth() - pe.getParticleMinWidth());
        width += pe.getParticleMinWidth();
        height = (float) Math.random() * (pe.getParticleMaxHeight() - pe.getParticleMinHeight());
        height += pe.getParticleMinHeight();
        if (pe.isSquare()) height = width;

        changeX = (float) Math.cos(direction) * distance;
        changeY = (float) Math.sin(direction) * distance;
        float startPosX = 0;
        float startPosY = 0;
        if (pe.isEmitCircle()) {
            float emitDirection = (float) Math.random() * 360;
            float distanceFromCenter = (float) Math.random() * pe.getEmitRadius();
            startPosX = (float) Math.cos(emitDirection) * distanceFromCenter + pe.getX();
            startPosY = (float) Math.sin(emitDirection) * distanceFromCenter + pe.getY();
        } else {
            startPosX = (float) Math.random() * pe.getWidth() + pe.getX();
            startPosY = (float) Math.random() * pe.getHeight() + pe.getY();
        }

        float rotationAmount = (float) Math.random() * (pe.getParticleMinRotation() - pe.getParticleMaxRotation());

        xAnimator = new MovementAnimator(startPosX, startPosX + changeX, duration, Interpolation.DECELERATE_INTERPOLATOR);
        xAnimator.setControllingX(true);
        yAnimator = new MovementAnimator(startPosY, startPosY + changeY, duration, Interpolation.ACCELERATE_INTERPOLATOR);
        yAnimator.setControllingY(true);
        ColorEffects colorEffects = new ColorEffects(new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 0.0f), duration);
        rotateAnimator = new RotateAnimator(duration, 0, rotationAmount, Interpolation.LINEAR_INTERPOLATOR);

        animators.add(xAnimator);
        animators.add(yAnimator);
        animators.add(colorEffects);
        animators.add(rotateAnimator);
    }

    public ArrayList<Animator> getAnimators() {
        return animators;
    }

    public Animator getXAnimator() {
        return xAnimator;
    }

    public Animator getYAnimator() {
        return yAnimator;
    }

    public Animator getColorAnimator() {
        return colorAnimator;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDuration() {
        return duration;
    }
}
