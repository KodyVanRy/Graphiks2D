package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kody mIsOn 5/25/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleEmitter {

    private float mX, mY, mWidth, mHeight, mParticlesPerSecond, mCurrentTime;

    private ArrayList<Particle> mParticles;
    private ArrayList<Particle> mParticlesToRemove;
    private ArrayList<Particle> mDeadParticles;
    private Texture mParticleTexture;
    private ArrayList<ParticleSettings> mParticleSettingsArrayList;
    private Random mRandomSettingsChooser;
    private Color mColor;

    private boolean mIsOn;

    public ParticleEmitter(float x, float y, float particlesPerSecond) {
        this.mParticles = new ArrayList<Particle>();
        this.mParticlesToRemove = new ArrayList<Particle>();
        this.mDeadParticles = new ArrayList<Particle>();
        this.mParticleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.mRandomSettingsChooser = new Random();

        this.mX = x;
        this.mY = y;

        this.mWidth = 1;
        this.mHeight = 1;
    }

    public ParticleEmitter() {
        this.mParticles = new ArrayList<Particle>();
        this.mParticlesToRemove = new ArrayList<Particle>();
        this.mDeadParticles = new ArrayList<Particle>();
        this.mParticleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.mRandomSettingsChooser = new Random();

        this.mX = 0;
        this.mY = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mColor = new Color(1, 1, 1, 1);
    }

    public void update(float delta) {
        updateParticles(delta);
        updateEmitter(delta);
    }

    private void updateParticles(float delta) {
        for (Particle particle : mParticles) {
            particle.update(delta);
            if (particle.needToRemove()) {
                mParticlesToRemove.add(particle);
            }
        }
        for (Particle particle : mParticlesToRemove) {
            mDeadParticles.add(particle);
            mParticles.remove(particle);
        }
        mParticlesToRemove.clear();
    }

    private void updateEmitter(float delta) {
        if (mIsOn) {
            mCurrentTime += delta;
            while (mCurrentTime >= 1.0f / mParticlesPerSecond) {
                addParticle(createNewParticle());
                mCurrentTime -= 1.0f / mParticlesPerSecond;
            }
        }
    }

    /**
     * Recycles old particle or if there are no spare mParticles, it creates a new one
     * @return {@link Particle}
     */
    public Particle createNewParticle() {
        ParticleSettings particleSettings = mParticleSettingsArrayList.get(mRandomSettingsChooser.nextInt(mParticleSettingsArrayList.size()));
        if (particleSettings == null) return null;
        Particle returnParticle = null;
        if (mDeadParticles.size() > 0) {
            returnParticle = mDeadParticles.get(0);
            mDeadParticles.remove(0);
            returnParticle.setup(particleSettings.getLifespan(),
                    particleSettings.getGravityX(),
                    particleSettings.getGravityY(),
                    particleSettings.getVelocityX(),
                    particleSettings.getVelocityY(),
                    particleSettings.getRotationAmount(),
                    particleSettings.getOpacity(),
                    particleSettings.isFadeOut());
            returnParticle.setPosition(getRandomParticleX(), getRandomParticleY());
        }
        if (returnParticle == null) {
            returnParticle = new Particle(mParticleTexture, getRandomParticleX(), getRandomParticleY(),
                    particleSettings.getWidth(), particleSettings.getHeight(), particleSettings.getLifespan(),
                    particleSettings.getGravityX(), particleSettings.getGravityY(),
                    particleSettings.getVelocityX(), particleSettings.getVelocityY(),
                    particleSettings.getRotationAmount(), particleSettings.getOpacity(),
                    particleSettings.isFadeOut());
        }

        return returnParticle;
    }

    public ArrayList<Particle> getParticles() {
        return mParticles;
    }

    public void draw(Batch batch) {
        for (Particle particle : mParticles) {
            particle.draw(batch);
        }
    }

    public void turnOn() {
        mIsOn = true;
    }

    public void turnOff() {
        mIsOn = false;
    }

    public void toggleOnOff() {
        mIsOn = !mIsOn;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        this.mX = x;
    }

    private float getRandomParticleX() {
        return ((float) Math.random() * getWidth()) + getX();
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        this.mY = y;
    }

    private float getRandomParticleY() {
        return (float) Math.random() * getHeight() + getY();
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float width) {
        this.mWidth = width;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float height) {
        this.mHeight = height;
    }

    public void addParticleSettings(ParticleSettings ps) {
        mParticleSettingsArrayList.add(ps);
    }

    public boolean isOn() {
        return mIsOn;
    }

    public void addParticle(Particle particle) {
        particle.setColor(mColor);
        mParticles.add(particle);
    }

    public ArrayList<ParticleSettings> getParticleSettingsArrayList() {
        return mParticleSettingsArrayList;
    }

    public void setParticleSettingsArrayList(ArrayList<ParticleSettings> particleSettingsArrayList) {
        this.mParticleSettingsArrayList = particleSettingsArrayList;
    }

    public float getParticlesPerSecond() {
        return mParticlesPerSecond;
    }

    public void setParticlesPerSecond(float particlesPerSecond) {
        this.mParticlesPerSecond = particlesPerSecond;
    }

    public Texture getParticleTexture() {
        return mParticleTexture;
    }

    public void setParticleTexture(Texture particleTexture) {
        this.mParticleTexture = particleTexture;
    }

    public void clearAndAddSettings(ParticleSettings particleSettings) {
        this.mParticleSettingsArrayList.clear();
        mParticleSettingsArrayList.add(particleSettings);
    }

    public void setColor(Color color) {
        this.mColor = color;
    }

    public void dispose() {
        try {
            mParticleTexture.dispose();
        } catch (Exception n) {
            // Particle has already been disposed elsewhere
        }
    }
}
