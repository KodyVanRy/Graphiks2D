package com.desitum.library.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
open class GameScreen : Screen {

    var viewportWidth: Float = 0f
        private set
    var viewportHeight: Float = 0f
        private set
    var foregroundViewportWidth: Float = 0f
        private set
    var foregroundViewportHeight: Float = 0f
        private set

    var cam: OrthographicCamera
    var foregroundCam: OrthographicCamera
    var viewport: Viewport
    var foregroundViewport: Viewport
    var spriteBatch: SpriteBatch = SpriteBatch()
    var worldRenderer: WorldRenderer
    var world: World
        set(world) {
            println(world)
            world.camera = cam
            world.viewport = viewport
            field = world
        }

    var touchPos: Vector3
    private var foregroundTouchPos: Vector3
    private var clearColor: Color

    // region constructors
    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     * *
     * @param world          mWorld class controller
     */
    constructor(viewportWidth: Float, viewportHeight: Float, foregroundViewportWidth: Float, foregroundViewportHeight: Float, world: World?, worldRenderer: WorldRenderer?, flags: Int) {

        cam = OrthographicCamera(getScreenWidth(viewportWidth, viewportHeight, flags),
                getScreenHeight(viewportWidth, viewportHeight, flags))
        foregroundCam = OrthographicCamera(
                getScreenWidth(foregroundViewportWidth, foregroundViewportHeight, flags),
                getScreenHeight(foregroundViewportWidth, foregroundViewportHeight, flags))

        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0f)
        foregroundCam.position.set(foregroundCam.viewportWidth / 2, foregroundCam.viewportHeight / 2, 0f)

        viewport = getViewport(cam, flags)
        foregroundViewport = getViewport(foregroundCam, flags)

        this.viewportWidth = cam.viewportWidth
        this.viewportHeight = cam.viewportHeight
        this.foregroundViewportWidth = foregroundCam.viewportWidth
        this.foregroundViewportHeight = foregroundCam.viewportHeight

        touchPos = Vector3(0f, 0f, 0f)
        foregroundTouchPos = Vector3(0f, 0f, 0f)

        this.world = world ?: World(cam, viewport, foregroundCam, foregroundViewport)
        this.worldRenderer = worldRenderer ?: WorldRenderer(this.world)

        clearColor = Color(0f, 0f, 0f, 1f)
    }

    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     * *
     * @param world          mWorld class controller
     */
    constructor(viewportWidth: Float, viewportHeight: Float, world: World, flags: Int) :
            this(viewportWidth, viewportHeight, viewportWidth, viewportHeight, world, null, flags)

    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     */
    constructor(viewportWidth: Float, viewportHeight: Float, flags: Int) :
            this(viewportWidth, viewportHeight, viewportWidth, viewportHeight, null, null, flags)


    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     */
    constructor(viewportWidth: Float, viewportHeight: Float,
                foregroundViewportWidth: Float, foregroundViewportHeight: Float, flags: Int) :
            this(viewportWidth, viewportHeight, foregroundViewportWidth, foregroundViewportHeight, null, null, flags)

    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     */
    constructor(viewportWidth: Float, viewportHeight: Float) :
            this(viewportWidth, viewportHeight, viewportWidth, viewportHeight, null, null, ASPECT_FIT)

    /**
     * Create a new [GameScreen] object

     * @param viewportWidth  Viewport width to fit to screen
     * *
     * @param viewportHeight Viewport height to fit to screen
     */
    constructor(viewportWidth: Float, viewportHeight: Float,
                foregroundViewportWidth: Float, foregroundViewportHeight: Float) :
            this(viewportWidth, viewportHeight, foregroundViewportWidth, foregroundViewportHeight, null, null, ASPECT_FIT)
    // endregion

    private fun getViewport(cam: OrthographicCamera, flags: Int): Viewport {
        if (flags and (ASPECT_FIT or ASPECT_FILL) != 0) {
            return FitViewport(cam.viewportWidth, cam.viewportHeight, cam)
        } else if (flags and ASPECT_STRETCH != 0) {
            return StretchViewport(cam.viewportWidth, cam.viewportHeight, cam)
        }
        return FitViewport(cam.viewportWidth, cam.viewportHeight, cam)
    }

    override fun render(delta: Float) {
        update(delta)
        draw()
    }

    /**
     * Update based on time since last frame

     * @param delta time since last frame
     */
    fun update(delta: Float) {
        foregroundTouchPos = foregroundViewport.unproject(foregroundTouchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
        if (!world.updateForegroundTouchInput(foregroundTouchPos, Gdx.input.isTouched)) {
            touchPos = viewport.unproject(touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
            world.updateTouchInput(touchPos, Gdx.input.isTouched)
        }
        world.update(delta)
    }

    /**
     * Draw the mWorld to the screen
     */
    fun draw() {
        cam.update()
        foregroundCam.update()
        spriteBatch.projectionMatrix = cam.combined
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)

        spriteBatch.begin()

        worldRenderer.draw(spriteBatch)

        spriteBatch.end()

        spriteBatch.projectionMatrix = foregroundCam.combined

        spriteBatch.begin()

        worldRenderer.drawForeground(spriteBatch)

        spriteBatch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        foregroundViewport.update(width, height)
    }

    override fun show() {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        try {
            spriteBatch.dispose()
            world.dispose()
        } catch (e: IllegalArgumentException) {
            // Never got initialized
        }

    }

    fun setClearColor(clearColor: Color) {
        this.clearColor = clearColor
    }

    companion object {

        val ASPECT_FILL = 1
        val ASPECT_FIT = 1 shl 1
        val ASPECT_STRETCH = 1 shl 2

        protected fun getScreenWidth(desiredWidth: Float, desiredHeight: Float,
                                     aspectFlags: Int): Float {

            if (aspectFlags and ASPECT_FIT != 0) {
                return desiredWidth
            } else if (aspectFlags and ASPECT_FILL != 0) {
                val viewWidth = Gdx.graphics.width.toFloat()
                val viewHeight = Gdx.graphics.height.toFloat()
                val viewAspect = viewWidth / viewHeight

                if (desiredWidth > desiredHeight * viewAspect) {
                    return desiredWidth
                } else {
                    return desiredHeight * viewAspect
                }
            }
            return desiredWidth
        }


        protected fun getScreenHeight(desiredWidth: Float, desiredHeight: Float,
                                      aspectFlags: Int): Float {
            if (aspectFlags and ASPECT_FIT != 0) {
                return desiredHeight
            } else if (aspectFlags and ASPECT_FILL != 0) {
                val viewWidth = Gdx.graphics.width.toFloat()
                val viewHeight = Gdx.graphics.height.toFloat()
                val viewAspect = viewWidth / viewHeight

                if (desiredWidth > desiredHeight * viewAspect) {
                    return desiredWidth / viewAspect
                } else {
                    return desiredHeight
                }
            }
            return desiredHeight
        }
    }
}
