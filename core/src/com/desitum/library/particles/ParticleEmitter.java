package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kody on 5/25/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleEmitter {

    private float x, y, width, height, particlesPerSecond, currentTime;

    private ArrayList<Particle> particles;
    private ArrayList<Particle> particlesToRemove;
    private ArrayList<Particle> deadParticles;
    private Texture particleTexture;
    private ArrayList<ParticleSettings> particleSettingsArrayList;
    private Random randomSettingsChooser;
    private Color color;

    private boolean on;

    public ParticleEmitter(float x, float y, float particlesPerSecond) {
        this.particles = new ArrayList<Particle>();
        this.particlesToRemove = new ArrayList<Particle>();
        this.deadParticles = new ArrayList<Particle>();
        this.particleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.randomSettingsChooser = new Random();

        this.x = x;
        this.y = y;

        this.width = 1;
        this.height = 1;
    }

    public ParticleEmitter() {
        this.particles = new ArrayList<Particle>();
        this.particlesToRemove = new ArrayList<Particle>();
        this.deadParticles = new ArrayList<Particle>();
        this.particleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.randomSettingsChooser = new Random();

        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.color = new Color(1, 1, 1, 1);
    }

    public void update(float delta) {
        updateParticles(delta);
        updateEmitter(delta);
    }

    private void updateParticles(float delta) {
        for (Particle particle : particles) {
            particle.update(delta);
            if (particle.needToRemove()) {
                deadParticles.add(particle);
            }
        }
        for (Particle particle : particlesToRemove) {
            deadParticles.add(particle);
            particles.remove(particle);
        }
    }

    private void updateEmitter(float delta) {
        if (on) {
            currentTime += delta;
            while (currentTime >= 1.0f / particlesPerSecond) {
                addParticle(createNewParticle());
                currentTime -= 1.0f / particlesPerSecond;
            }
        }
    }

    /**
     * Recycles old particle or if there are no spare particles, it creates a new one
     * @return {@link Particle}
     */
    public Particle createNewParticle() {
        ParticleSettings particleSettings = particleSettingsArrayList.get(randomSettingsChooser.nextInt(particleSettingsArrayList.size()));
        if (particleSettings == null) return null;
        Particle returnParticle = null;
        if (deadParticles.size() > 0) {
            returnParticle = deadParticles.get(0);
            deadParticles.remove(0);
            returnParticle.setCurrentLife(particleSettings.getLifespan());
            returnParticle.setRotationAmount(particleSettings.getRotationAmount());
            returnParticle.setOpacity(particleSettings.getOpacity());
            returnParticle.setVelocityX(particleSettings.getVelocityX());
            returnParticle.setVelocityY(particleSettings.getVelocityY());
        }
        if (returnParticle == null) {
            returnParticle = new Particle(particleTexture, getRandomParticleX(), getRandomParticleY(),
                    particleSettings.getWidth(), particleSettings.getHeight(), particleSettings.getLifespan(),
                    particleSettings.getGravityX(), particleSettings.getGravityY(),
                    particleSettings.getVelocityX(), particleSettings.getVelocityY(),
                    particleSettings.getRotationAmount(), particleSettings.getOpacity(),
                    particleSettings.isFadeOut());
        }
        return returnParticle;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void draw(Batch batch) {
        for (Particle particle : particles) {
            particle.draw(batch);
        }
    }

    public void turnOn() {
        on = true;
    }

    public void turnOff() {
        on = false;
    }

    public void toggleOnOff() {
        on = !on;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    private float getRandomParticleX() {
        return ((float) Math.random() * getWidth()) + getX();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float getRandomParticleY() {
        return (float) Math.random() * getHeight() + getY();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void addParticleSettings(ParticleSettings ps) {
        particleSettingsArrayList.add(ps);
    }

    public boolean isOn() {
        return on;
    }

    public void addParticle(Particle particle) {
        particle.setColor(color);
        particles.add(particle);
    }

    public ArrayList<ParticleSettings> getParticleSettingsArrayList() {
        return particleSettingsArrayList;
    }

    public void setParticleSettingsArrayList(ArrayList<ParticleSettings> particleSettingsArrayList) {
        this.particleSettingsArrayList = particleSettingsArrayList;
    }

    public float getParticlesPerSecond() {
        return particlesPerSecond;
    }

    public void setParticlesPerSecond(float particlesPerSecond) {
        this.particlesPerSecond = particlesPerSecond;
    }

    public Texture getParticleTexture() {
        return particleTexture;
    }

    public void setParticleTexture(Texture particleTexture) {
        this.particleTexture = particleTexture;
    }

    public void clearAndAddSettings(ParticleSettings particleSettings) {
        this.particleSettingsArrayList.clear();
        particleSettingsArrayList.add(particleSettings);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void dispose() {
        try {
            particleTexture.dispose();
        } catch (Exception n) {
            // Particle has already been disposed elsewhere
        }
    }
}
