package com.desitum.library.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.view.View;
import com.desitum.library.widgets.Widget;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class WorldRenderer {

    private World mWorld;

    public WorldRenderer(World world) {
        this.mWorld = world;
    }

    public void setWorld(World world) {
        this.mWorld = world;
    }

    public void draw(Batch batch) {
        for (GameObject gameObject : mWorld.getGameObjects()) {
            gameObject.draw(batch);
        }
        batch.flush();
        for (ParticleEmitter particleEmitter: mWorld.getParticleEmitters()) {
            particleEmitter.draw(batch);
        }
        batch.flush();
    }

    public void drawForeground(Batch batch) {
        for (View view : mWorld.getViews()) {
            view.draw(batch, mWorld.getForegroundViewport());
        }
        batch.flush();
    }
}
