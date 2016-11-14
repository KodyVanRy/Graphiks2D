package com.desitum.library.math;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.widgets.Widget;

/**
 * Created by Zmyth97 on 2/9/2015
 */
public class CollisionDetection {

    public static boolean pointInWidget(Widget widget, Vector3 point) {
        return pointInRectangle(widget.getBoundingRectangle(), point);
    }

    public static boolean pointInGameObject(GameObject gameObject, Vector3 point) {
        return pointInRectangle(gameObject.getBoundingRectangle(), point);
    }

    public static boolean pointInRectangle(Rectangle rect, Vector3 point) {
        float rw, rh, rx, ry, px, py;
        rw = rect.getWidth();
        rh = rect.getHeight();
        rx = rect.getX();
        ry = rect.getY();
        px = point.x;
        py = point.y;

        return px >= rx && px <= rw + rx && py >= ry && py <= ry + rh;
    }

    public static boolean overlapRectangles(Rectangle r1, Rectangle r2) {
        if (r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y)
            return true;
        else
            return false;
    }

    public static boolean spritesTouching(Sprite s1, Sprite s2) {
        return overlapRectangles(s1.getBoundingRectangle(), s2.getBoundingRectangle());
    }

    public static boolean gameObjectsTouching(GameObject object1, GameObject object2) {
        return overlapRectangles(object1.getBoundingRectangle(), object2.getBoundingRectangle());
    }
}
