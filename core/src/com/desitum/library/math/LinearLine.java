package com.desitum.library.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class LinearLine {

    public static float getSlope(float y2, float y1, float x2, float x1) {
        return (y2 - y1) / (x2 - x1);
    }

    public static float getSlope(float[] point1, float[] point2) {
        return getSlope(point2[0], point1[0], point2[1], point1[1]);
    }

    public static float getSlop(Vector2 point1, Vector2 point2) {
        return getSlope(point2.y, point1.y, point2.x, point1.x);
    }

    public static float getInverseSlope(float m) {
        return (1.0f / m);
    }

    public static float getSlopeIntercept(float pointX, float pointY, float slope) {
        return (pointY - (slope * pointX));
    }

    public static float[] getIntersection(float slope1, float b1, float slope2, float b2) {
        float x = (b2 - b1) / (slope1 - slope2);
        float y = (slope1 * x) + b1;

        return new float[]{x, y};
    }

    public static float distanceBetween(float x1, float y1, float x2, float y2) {
        // Pythagorean Theorem a^2 + b^2 = c^2

        return (float) Math.sqrt((Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2)));
    }
}
