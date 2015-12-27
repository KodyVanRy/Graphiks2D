package com.desitum.library.math;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Zmyth97 on 2/9/2015.
 */
public class CollisionDetection {

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


}
