package com.desitum.library.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class LinearLine {

    /**
     * Used to get slope between two points
     *
     * @param y2 y value of the second point
     * @param y1 y value of the first point
     * @param x2 x value of the second point
     * @param x1 y value of the first point
     * @return slope of two points
     */
    public static float getSlope(float y2, float y1, float x2, float x1) {
        // m = (y2 - y1) / (x2 - x1)
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * Used to get slope between two points
     * @param point1 first point
     * @param point2 second point
     * @return slope of two points
     */
    public static float getSlope(float[] point1, float[] point2) {
        return getSlope(point2[0], point1[0], point2[1], point1[1]);
    }

    /**
     * Used to get slope between two points
     *
     * @param point1 first point
     * @param point2 second point
     * @return slope of two points
     */
    public static float getSlope(Vector2 point1, Vector2 point2) {
        return getSlope(point2.y, point1.y, point2.x, point1.x);
    }

    /**
     * Used to get the slope perpendicular to the one provided
     * @param m original slop
     * @return slope perpendicular to one provided
     */
    public static float getInverseSlope(float m) {
        // solve for inverse of slope
        // flip the slope upside down
        return (1.0f / m);
    }

    /**
     * Used to get the slopes intercept with the y axis (0, y)
     *
     * @param referencePointX x value of the reference point
     * @param referencePointY y value of the reference point
     * @param slope           slope of line
     * @return where slope hits y axis
     */
    public static float getSlopeIntercept(float referencePointX, float referencePointY, float slope) {
        // put in slope intercept form
        // y = mx + b
        // solve for b
        // b = y - mx
        return (referencePointY - (slope * referencePointX));
    }

    /**
     * Used to get intersection point between two lines
     * @param slope1 slope of line 1
     * @param b1 y intercept of line 1
     * @param slope2 slope of line 2
     * @param b2 y intercept of line 2
     * @return intersection point of the two lines
     */
    public static float[] getIntersection(float slope1, float b1, float slope2, float b2) {
        // solve for x
        // {slope1}x + b1 = {slope2}x + b2
        float x = (b2 - b1) / (slope1 - slope2);
        // solve for y
        // y = mx + b
        float y = (slope1 * x) + b1;

        return new float[]{x, y};
    }

    /**
     * Used to get distance between two points on a plane
     * Pythagorean theorem
     * @param x1 x value of point 1
     * @param y1 y value of point 1
     * @param x2 x value of point 1
     * @param y2 y value of point 1
     * @return distance between two points
     */
    public static float distanceBetween(float x1, float y1, float x2, float y2) {
        // Pythagorean Theorem a^2 + b^2 = c^2

        return (float) Math.sqrt((Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2)));
    }
}
