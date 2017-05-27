package com.desitum.library.game

import com.badlogic.gdx.graphics.g2d.Batch
import com.desitum.library.game_objects.GameObject
import com.desitum.library.particles.ParticleEmitter
import com.desitum.library.view.View
import com.desitum.library.widgets.Widget

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
class WorldRenderer(var world: World) {

    fun draw(batch: Batch) {
        for (gameObject in world!!.gameObjects!!) {
            gameObject.draw(batch)
        }
        batch.flush()
        for (particleEmitter in world!!.particleEmitters!!) {
            particleEmitter.draw(batch)
        }
        batch.flush()
    }

    fun drawForeground(batch: Batch) {
        for (view in world!!.views!!) {
            view.draw(batch, world!!.foregroundViewport)
        }
        batch.flush()
    }
}
