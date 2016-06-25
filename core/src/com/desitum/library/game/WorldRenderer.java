package com.desitum.library.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.widgets.Widget;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class WorldRenderer {

    private World world;

    public WorldRenderer(World world) {
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void draw(Batch batch) {
        for (GameObject gameObject : world.getGameObjects()) {
            gameObject.draw(batch);
        }
        for (ParticleEmitter particleEmitter: world.getParticleEmitters()) {
            particleEmitter.draw(batch);
        }
        for (Widget widget : world.getWidgets()) {
            widget.draw(batch);
        }
    }
}
