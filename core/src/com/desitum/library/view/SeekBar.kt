package com.desitum.library.view

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.drawing.Drawable
import com.desitum.library.game.World

/**
 * Created by kodyvanry on 5/1/17.
 */

open class SeekBar : ProgressBar {

    var seekerDrawable: Drawable? = null
    private var onSeekBarChangeListener: OnSeekBarChangeListener? = null
    private var seekerSize = SEEKER_SIZE

    constructor(world: World) : this(world, null)

    constructor(world: World, layoutConstraints: LayoutConstraints?) : super(world, layoutConstraints)

    override fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        when (touchEvent.action) {
            TouchEvent.Action.DOWN -> {
                progress = (touchEvent.x - x) / width
                onSeekBarChangeListener?.let {
                    it.onStartTrackingTouch(this)
                    it.onSeekChanged(this, progress)
                }
            }
            TouchEvent.Action.MOVE -> {
                progress = (touchEvent.x - x) / width
                onSeekBarChangeListener?.onSeekChanged(this, progress)
            }
            TouchEvent.Action.UP -> onSeekBarChangeListener?.onStopTrackingTouch(this)
            else -> {}
        }
        return super.onTouchEvent(touchEvent)
    }

    override fun onDraw(batch: Batch, viewport: Viewport) {
        super.onDraw(batch, viewport)
        seekerDrawable?.draw(
                    batch,
                    x + width * progress - seekerSize / 2,
                    y + height / 2 - seekerSize / 2,
                seekerSize,
                seekerSize
            )
    }

    interface OnSeekBarChangeListener {
        fun onSeekChanged(view: View, value: Float)

        fun onStartTrackingTouch(view: View)

        fun onStopTrackingTouch(view: View)
    }

    companion object {

        private val SEEKER_SIZE = ProgressBar.DEFAULT_PROGRESS_BAR_HEIGHT * 2
    }
}
