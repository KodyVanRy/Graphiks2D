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
        world.gameObjects.forEach { it.draw(batch, world.viewport) }
        batch.flush()
        world.particleEmitters.forEach { it.draw(batch) }
        batch.flush()
    }

    fun drawForeground(batch: Batch) {
        world.views.forEach { it.draw(batch, world.foregroundViewport) }
        batch.flush()
    }
}
