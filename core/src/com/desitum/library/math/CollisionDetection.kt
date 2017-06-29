package com.desitum.library.math

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.desitum.library.game_objects.GameObject
import com.desitum.library.widgets.Widget

/**
 * Created by Zmyth97 on 2/9/2015
 */
object CollisionDetection {

    @JvmStatic fun pointInWidget(widget: Widget, point: Vector3): Boolean {
        return pointInRectangle(widget.boundingRectangle, point)
    }

    @JvmStatic fun pointInRectangle(rect: Rectangle, point: Vector3): Boolean {
        val rw: Float = rect.getWidth()
        val rh: Float = rect.getHeight()
        val rx: Float = rect.getX()
        val ry: Float = rect.getY()
        val px: Float = point.x
        val py: Float = point.y

        return px >= rx && px <= rw + rx && py >= ry && py <= ry + rh
    }

    @JvmStatic fun overlapRectangles(r1: Rectangle, r2: Rectangle): Boolean {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y
    }

    @JvmStatic fun spritesTouching(s1: Sprite, s2: Sprite): Boolean {
        return overlapRectangles(s1.boundingRectangle, s2.boundingRectangle)
    }

    @JvmStatic fun gameObjectsTouching(object1: GameObject, object2: GameObject): Boolean {
        return overlapRectangles(object1.boundingRectangle, object2.boundingRectangle)
    }
}
