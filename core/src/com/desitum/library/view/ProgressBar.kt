package com.desitum.library.view

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.drawing.Drawable
import com.desitum.library.game.World

/**
 * Created by kodyvanry on 5/1/17.
 */

open class ProgressBar @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null) : View(world, layoutConstraints) {

    var progress: Float = 0f
        set(value) {
            field = Math.max(Math.min(value, 1f), 0f)
        }
    var progressBarHeight: Float = 0f
    var progressDrawable: Drawable? = null
    var progressBackgroundDrawable: Drawable? = null

    init {
        this.progressBarHeight = DEFAULT_PROGRESS_BAR_HEIGHT
    }

    override fun onDraw(batch: Batch, viewport: Viewport) {
        super.onDraw(batch, viewport)
        progressBackgroundDrawable?.draw(
                batch,
                x,
                y + height / 2 - progressBarHeight / 2,
                width,
                progressBarHeight
        )
        progressDrawable?.draw(
                batch,
                x,
                y + height / 2 - progressBarHeight / 2,
                width * progress,
                progressBarHeight
        )
    }

    companion object {

        @JvmStatic protected val DEFAULT_PROGRESS_BAR_HEIGHT = 50f
    }
}
