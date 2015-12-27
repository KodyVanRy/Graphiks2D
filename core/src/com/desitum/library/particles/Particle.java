package com.desitum.library.particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.animation.Animator;

import java.util.ArrayList;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
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

    private ArrayList<Animator> animators;

    private float lifetime;

    private ParticleEmitter particleEmitter;

    public Particle(Texture texture, ParticleEmitter pe, ParticleSettings ps) {
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());

        this.setSize(ps.getHeight(), ps.getWidth());
        this.setPosition(ps.getXAnimator().getCurrentAmount(), ps.getYAnimator().getCurrentAmount());

        this.setOriginCenter();

        this.animators = ps.getAnimators();

        for (Animator animator : animators) {
            animator.setSprite(this);
        }

        this.particleEmitter = pe;

        startAllAnimators();

        this.lifetime = ps.getDuration();
    }


    public void update(float delta) {
        for (Animator anim : animators) {
            anim.update(delta);
        }

        lifetime -= delta;

        if (lifetime < 0) {
            particleEmitter.remove(this);
        }
    }

    public float getLifetime() {
        return lifetime;
    }

    public void startAllAnimators() {
        for (Animator anim : animators) {
            anim.start(false);
        }
    }

    public void dispose() {
        animators.clear();
    }
}
