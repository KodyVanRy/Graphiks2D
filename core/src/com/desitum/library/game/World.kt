package com.desitum.library.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.game_objects.GameObject
import com.desitum.library.particles.ParticleEmitter
import com.desitum.library.view.TouchEvent
import com.desitum.library.view.View

import java.util.ArrayList
import java.util.Collections

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
class World {

    var layerGameObjects = true
    private var clickDown = false
    private var foregroundClickDown = false

    var gameObjects: MutableList<GameObject>
        private set
    var sprites: List<G2DSprite>
        private set
    var views: MutableList<View>
        private set
    var particleEmitters: MutableList<ParticleEmitter>
        private set

    var camera: OrthographicCamera
    var foregroundCamera: OrthographicCamera? = null
    var viewport: Viewport
    var foregroundViewport: Viewport? = null
    private var viewFocus: View? = null
    private var touchEvent: TouchEvent

    /**
     * Create new [World]
     * @param camera camera from [GameScreen]
     */
    constructor(camera: OrthographicCamera, viewport: Viewport) {
        this.camera = camera
        this.viewport = viewport
        this.gameObjects = ArrayList<GameObject>()
        this.sprites = ArrayList<G2DSprite>()
        this.views = ArrayList<View>()
        this.particleEmitters = ArrayList<ParticleEmitter>()
        this.touchEvent = TouchEvent()
    }

    /**
     * Create new [World]
     * @param camera camera from [GameScreen]
     */
    constructor(camera: OrthographicCamera, viewport: Viewport, foregroundCamera: OrthographicCamera, foregroundViewport: Viewport) {
        this.camera = camera
        this.viewport = viewport
        this.foregroundCamera = foregroundCamera
        this.foregroundViewport = foregroundViewport
        this.gameObjects = ArrayList<GameObject>()
        this.sprites = ArrayList<G2DSprite>()
        this.views = ArrayList<View>()
        this.particleEmitters = ArrayList<ParticleEmitter>()
        this.touchEvent = TouchEvent()
    }

    /**
     * update the world every frame using this method
     * @param delta time since last update
     */
    fun update(delta: Float) {
        for (gameObject in gameObjects) {
            gameObject.update(delta)
        }
        for (particleEmitter in particleEmitters) {
            particleEmitter.update(delta)
        }
        for (g2DSprite in sprites) {
            g2DSprite.update(delta)
        }
        for (g2DSprite in views) {
            g2DSprite.update(delta)
        }
    }

    /**
     * updates the world based on touch input
     * for best results update before the master update
     * @param touchPos position of mouse or touch point if on mobile
     * *
     * @param touchDown is clicking or if currently touching screen
     */
    fun updateTouchInput(touchPos: Vector3, touchDown: Boolean): Boolean {
        var returnVal = false
        touchEvent.x = touchPos.x
        touchEvent.y = touchPos.y
        if (touchDown && !clickDown) {
            returnVal = onTouchDown(touchPos)
        } else if (!touchDown && clickDown) {
            returnVal = onTouchUp()
        } else if (touchDown && clickDown) {
            returnVal = onTouchMoved()
        }
        for (gameObject2D in gameObjects) {
            gameObject2D.updateTouchInput(touchPos, touchDown)
        }
        clickDown = touchDown
        return returnVal
    }

    /**
     * updates the world based on touch input
     * for best results update before the master update
     * @param touchPos position of mouse or touch point if on mobile
     * *
     * @param touchDown is clicking or if currently touching screen
     */
    fun updateForegroundTouchInput(touchPos: Vector3, touchDown: Boolean): Boolean {
        var returnValue = false
        if (!views.isEmpty()) {
            touchEvent.x = touchPos.x
            touchEvent.y = touchPos.y
            if (touchDown && !foregroundClickDown) {
                returnValue = onTouchDownForeground()
            } else if (!touchDown && foregroundClickDown) {
                returnValue = onTouchUpForeground()
            } else if (touchDown && foregroundClickDown) {
                returnValue = onTouchMovedForeground()
            }
            foregroundClickDown = touchDown
        }
        return returnValue
    }

    /**
     * Add a [GameObject] to be handled by [World]
     * @param gameObject2D [GameObject] to be handled
     */
    fun addGameObject(gameObject2D: GameObject) {
        this.gameObjects.add(gameObject2D)
        if (layerGameObjects) {
            Collections.sort(gameObjects)
        }
    }

    /**
     * Add a [ParticleEmitter] to be handled by [World]
     * @param particleEmitter [ParticleEmitter] to be handled
     */
    fun addParticleEmitter(particleEmitter: ParticleEmitter) {
        this.particleEmitters.add(particleEmitter)
    }

    /**
     * Add a [View] to be handled by [World]
     * @param view [View] to be handled
     */
    fun addView(view: View) {
        this.views.add(view)
        Collections.sort(views)
    }

    /**
     * Remove a game object from the screen
     * @param gameObject [GameObject] to remove
     */
    fun removeGameObject(gameObject: GameObject) {
        gameObjects.remove(gameObject)
    }

    /**
     * Remove a view from the screen
     * @param view [View] to remove
     */
    fun removeView(view: View) {
        views.remove(view)
    }

    fun onTouchDown(clickPos: Vector3): Boolean {
        touchEvent.action = TouchEvent.Action.DOWN
        // TODO Game Object Touch Events
        return false
    }

    fun onTouchUp(): Boolean {
        touchEvent.action = TouchEvent.Action.UP
        // TODO Game Object Touch Events
        return false
    }

    private fun onTouchMoved(): Boolean {
        touchEvent.action = TouchEvent.Action.MOVE
        // TODO Game Object Touch Events
        return false
    }

    fun onTouchDownForeground(): Boolean {
        touchEvent.action = TouchEvent.Action.DOWN
        for (view in views.reversed()) { // Touch needs to be backwards to touch top views first
            if (view.isTouching(touchEvent)) {
                view.dispatchTouchEvent(touchEvent)
                return true
            }
        }
        return false
    }

    fun onTouchUpForeground(): Boolean {
        touchEvent.action = TouchEvent.Action.UP
        if (viewFocus != null) {
            viewFocus!!.dispatchTouchEvent(touchEvent)
            viewFocus = null
            return true
        }
        return false
    }

    private fun onTouchMovedForeground(): Boolean {
        touchEvent.action = TouchEvent.Action.MOVE
        return viewFocus?.dispatchTouchEvent(touchEvent) ?: false
    }

    fun dispose() {
        for (particleEmitter in particleEmitters) {
            particleEmitter.dispose()
        }
        for (gameObject in gameObjects) {
            gameObject.dispose()
        }
    }

    fun requestFocus(view: View) {
        for (v in views) {
            v.clearFocus()
        }
        viewFocus = view
        viewFocus!!.setFocus(true)
    }
}
