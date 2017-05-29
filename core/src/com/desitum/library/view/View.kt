package com.desitum.library.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.animation.Animator
import com.desitum.library.drawing.Drawable
import com.desitum.library.game.G2DSprite
import com.desitum.library.game.World

import java.util.ArrayList

/**
 * Created by kodyvanry on 5/1/17.
 */

open class View(var world: World?, layoutConstraints: LayoutConstraints?) : G2DSprite() {

    var layoutConstraints: LayoutConstraints


    // TOUCH
    var focus = false
        @JvmName("hasFocus") get
    var enabled = true
        @JvmName("isEnabled") get
    var focusable = true
        @JvmName("isFocusable") get
    var clickable = false
        @JvmName("isClickable") get
    var onTouchListener: OnTouchListener? = null
    var onClickListener: OnClickListener? = null

    // DRAWING
    var backgroundDrawable: Drawable? = null
    var visibility: Int = 0
    private var dirty = true
    var parent: ViewGroup? = null

    var id: Int = 0
    var name: String? = null
    private var animators: MutableList<Animator>

    init {
        animators = ArrayList<Animator>()
        this.layoutConstraints = layoutConstraints ?: LayoutConstraints(0f, 0f, 0f, 0f)
    }

    // -----------------------------
    // region Animation methods
    // -----------------------------
    override fun update(delta: Float) {
        if (dirty)
            dispatchLayout()
        animators.forEach { it.update(delta) }
    }

    fun addAnimation(animator: Animator) {
        animators.add(animator)
    }

    fun startAnimator(animator: Animator) {
        addAnimation(animator)
        animator.start(false)
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Layout methods
    // -----------------------------
//    fun onMeasure(): LayoutConstraints {
//        // TODO this needs to actually measure things and work correctly :D
//        return layoutConstraints
//    }

    protected open fun dispatchLayout() {
        if ((visibility and GONE) != 0) { // VISIBILITY IS SET TO GONE
            super.setX(0f);
            super.setY(0f);
            super.setSize(0f, 0f);
        } else {
            layoutConstraints.let {
                super.setSize(
                        if (it.width == MATCH_PARENT) parent!!.width else it.width,
                        if (it.height == MATCH_PARENT) parent!!.width else it.height
                )
                super.setX(
                        if (it.x == LayoutConstraints.CENTER_HORIZONTAL)
                            (parent!!.x + parent!!.width - width / 2)
                        else if (parent != null)
                            parent!!.x + it.x
                        else
                            it.x
                )
                super.setY(
                        if (it.y == LayoutConstraints.CENTER_VERTICAL)
                            (parent!!.y + parent!!.height - height / 2)
                        else if (parent != null)
                            (parent!!.y + it.y)
                        else
                            it.y
                )
            }
        }
        dirty = false;
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Drawing methods
    // -----------------------------
    open fun draw(batch: Batch, viewport: Viewport) {
        /*
        Need to draw in the following order

        1. Background
        2. Draw view itself
        3. Draw any possible children
        4. Draw foreground
         */
        // 1. Draw background
        backgroundDrawable?.draw(batch, x, y, width, height)

        // 2. Draw view itself
        onDraw(batch, viewport)

        // 3. Draw any possible children
        dispatchDraw(batch, viewport)

        // 4. Draw the foreground / view decorations
        drawForeground(batch, viewport)
    }

    open fun onDraw(spriteBatch: Batch, viewport: Viewport) {

    }

    fun dispatchDraw(spriteBatch: Batch, viewport: Viewport) {

    }

    fun drawForeground(spriteBatch: Batch, viewport: Viewport) {

    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Input methods
    // -----------------------------
    fun dispatchTouchEvent(touchEvent: TouchEvent): Boolean {
        onTouchListener?.onTouchEvent(this, touchEvent)
        if (focus) {
            if (onClickListener != null
                    && clickable
                    && touchEvent.action === TouchEvent.Action.UP
                    && isTouching(touchEvent)) {
                onClickListener!!.onClick(this)
            }
        }
        if (enabled || this is EditText) {
            if (touchEvent.action === TouchEvent.Action.DOWN) {
                world!!.requestFocus(this)
            }
            return onTouchEvent(touchEvent)
        }
        return true
    }

    open fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        return true
    }

    fun isTouching(touchEvent: TouchEvent): Boolean {
        return boundingRectangle.contains(touchEvent.x, touchEvent.y)
    }
    // -----------------------------
    // endregion
    // -----------------------------

    // -----------------------------
    // region Getters & Setters
    // -----------------------------

    fun requestFocus(touchEvent: TouchEvent): View? {
        if (!focusable || visibility and INVISIBLE != 0) {
            return null
        }
        if (!isTouching(touchEvent)) {
            return null
        }
        focus = true
        return this
    }

    fun clearFocus() {
        focus = false
    }

    override fun setSize(width: Float, height: Float) {
        layoutConstraints.width = width
        layoutConstraints.height = height
        invalidate()
    }

    override fun setX(x: Float) {
        layoutConstraints.x = x
        invalidate()
    }

    override fun setY(y: Float) {
        layoutConstraints.y = y
        invalidate()
    }

    override fun setPosition(x: Float, y: Float) {
        layoutConstraints.x = x
        layoutConstraints.y = y
        invalidate()
    }

    open fun invalidate() {
        dirty = true
    }
// -----------------------------
// endregion
// -----------------------------

    // -----------------------------
// region Listeners
// -----------------------------
    interface OnTouchListener {
        fun onTouchEvent(view: View, touchEvent: TouchEvent)
    }

    interface OnClickListener {
        fun onClick(view: View)
    }

    companion object {

        protected val VERTEX_SIZE = 2 + 1 + 2
        protected val VIEW_SIZE = 4 * VERTEX_SIZE

        val VISIBLE = 1
        val INVISIBLE = 1 shl 1
        val GONE = 1 shl 2

        val MATCH_PARENT = LayoutConstraints.MATCH_PARENT
        val WRAP_CONTENT = LayoutConstraints.WRAP_CONTENT
    }

    // -----------------------------
// endregion
// -----------------------------
    init {
        this.layoutConstraints = layoutConstraints ?: LayoutConstraints(0f, 0f, 0f, 0f)
    }
}
