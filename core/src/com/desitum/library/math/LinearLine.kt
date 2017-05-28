package com.desitum.library.math

import com.badlogic.gdx.math.Vector2

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
object LinearLine {

    /**
     * Used to get slope between two points

     * @param y2 y value of the second point
     * *
     * @param y1 y value of the first point
     * *
     * @param x2 x value of the second point
     * *
     * @param x1 y value of the first point
     * *
     * @return slope of two points
     */
    @JvmStatic fun getSlope(y2: Float, y1: Float, x2: Float, x1: Float): Float {
        return (y2 - y1) / (x2 - x1)
    }

    /**
     * Used to get slope between two points
     * @param point1 first point
     * *
     * @param point2 second point
     * *
     * @return slope of two points
     */
    @JvmStatic fun getSlope(point1: FloatArray, point2: FloatArray): Float {
        return getSlope(point2[0], point1[0], point2[1], point1[1])
    }

    /**
     * Used to get slope between two points

     * @param point1 first point
     * *
     * @param point2 second point
     * *
     * @return slope of two points
     */
    @JvmStatic fun getSlope(point1: Vector2, point2: Vector2): Float {
        return getSlope(point2.y, point1.y, point2.x, point1.x)
    }

    /**
     * Used to get the slope perpendicular to the one provided
     * @param m original slop
     * *
     * @return slope perpendicular to one provided
     */
    @JvmStatic fun getInverseSlope(m: Float): Float {
        // solve for inverse of slope
        // flip the slope upside down
        return 1.0f / m
    }

    /**
     * Used to get the slopes intercept with the y axis (0, y)

     * @param referencePointX x value of the reference point
     * *
     * @param referencePointY y value of the reference point
     * *
     * @param slope           slope of line
     * *
     * @return where slope hits y axis
     */
    @JvmStatic fun getSlopeIntercept(referencePointX: Float, referencePointY: Float, slope: Float): Float {
        // put in slope intercept form
        // y = mx + b
        // solve for b
        // b = y - mx
        return referencePointY - slope * referencePointX
    }

    /**
     * Used to get intersection point between two lines
     * @param slope1 slope of line 1
     * *
     * @param b1 y intercept of line 1
     * *
     * @param slope2 slope of line 2
     * *
     * @param b2 y intercept of line 2
     * *
     * @return intersection point of the two lines
     */
    @JvmStatic fun getIntersection(slope1: Float, b1: Float, slope2: Float, b2: Float): FloatArray {
        // solve for x
        // {slope1}x + b1 = {slope2}x + b2
        val x = (b2 - b1) / (slope1 - slope2)
        // solve for y
        // y = mx + b
        val y = slope1 * x + b1

        return floatArrayOf(x, y)
    }

    /**
     * Used to get distance between two points on a plane
     * Pythagorean theorem
     * @param x1 x value of point 1
     * *
     * @param y1 y value of point 1
     * *
     * @param x2 x value of point 1
     * *
     * @param y2 y value of point 1
     * *
     * @return distance between two points
     */
    @JvmStatic fun distanceBetween(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        // Pythagorean Theorem a^2 + b^2 = c^2

        return Math.sqrt(Math.pow(Math.abs(x2 - x1).toDouble(), 2.0) + Math.pow(Math.abs(y2 - y1).toDouble(), 2.0)).toFloat()
    }

    /**
     * Used to get distance between two points on a plane
     * Pythagorean theorem
     * @param slope slope of the line
     * *
     * @param x x value
     * *
     * @param y y value
     * *
     * @return distance between two points
     */
    @JvmStatic fun getYIntercept(slope: Float, x: Float, y: Float): Float {
        // solve for b
        // y = mx + b
        // (y) = (m*x) +
        // (y) - (m*x) = b

        // 2 = 4 * 1 + b
        // y = 4x - 3
        return y - slope * x
    }

    /**
     * Used to get distance between two points on a plane
     * Pythagorean theorem
     * @param slope slope of the line
     * *
     * @param yIntercept b value
     * *
     * @return distance between two points
     */
    @JvmStatic fun getXIntercept(slope: Float, yIntercept: Float, y: Int): Float {
        // solve for y
        // 0 = mx + b
        // -b = (m*x)
        // -b - x
        // -b/m = x
        return -yIntercept / slope
    }
}
