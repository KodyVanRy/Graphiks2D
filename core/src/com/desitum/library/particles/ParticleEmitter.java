package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by kody on 5/25/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleEmitter {

    private float x, y, width, height, particlesPerSecond, currentTime;

    private ArrayList<Particle> particles;
    private Texture particleTexture;
    private ArrayList<ParticleSettings> particleSettingsArrayList;
    private Random randomSettingsChooser;

    private boolean on;

    public ParticleEmitter(float x, float y, float particlesPerSecond) {
        this.particles = new ArrayList<Particle>();
        this.particleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.randomSettingsChooser = new Random();

        this.x = x;
        this.y = y;

        this.width = 1;
        this.height = 1;
    }

    public ParticleEmitter() {
        this.particles = new ArrayList<Particle>();
        this.particleSettingsArrayList = new ArrayList<ParticleSettings>();
        this.randomSettingsChooser = new Random();

        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public void update(float delta) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(delta);
            if (particle.needToRemove()) {
                iterator.remove();
            }
        }

        if (on) {
            currentTime += delta;
            while (currentTime >= 1.0f / particlesPerSecond) {
                createNewParticle();
                currentTime -= 1.0f / particlesPerSecond;
            }
        }
    }

    protected Particle createNewParticle() {
        ParticleSettings particleSettings = particleSettingsArrayList.get(randomSettingsChooser.nextInt(particleSettingsArrayList.size()));
        if (particleSettings == null) return null;
        Particle returnParticle = null;

        returnParticle = new Particle(particleTexture, particleSettings.getWidth(),
                particleSettings.getHeight(), particleSettings.getLifespan(),
                particleSettings.getGravityX(), particleSettings.getGravityY(),
                particleSettings.getVelocityX(), particleSettings.getVelocityY(),
                particleSettings.getRotationAmount(), particleSettings.getOpacity(),
                particleSettings.isFadeOut());
        return returnParticle;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void draw(SpriteBatch batch) {
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

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
}
