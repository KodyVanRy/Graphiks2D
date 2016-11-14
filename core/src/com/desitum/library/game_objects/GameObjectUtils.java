package com.desitum.library.game_objects;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.math.LinearLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dakota Van Ry on 11/9/2016. Use it
 */
public class GameObjectUtils {

    public static List<GameObject> getPiecesFromGameObject(GameObject gameObject, float intensity, int pieces, Vector3 centerOfShatter) {
        int rows = 1;
        if (intensity > 0.3 && intensity < 0.5) {
            rows = 2;
        } else if (intensity < 0.7) {
            rows = 3;
        } else if (intensity < 0.9) {
            rows = 4;
        } else {
            rows = 5;
        }
        pieces = pieces / rows;

        if (pieces < 6)
            pieces = 6;

        ArrayList<GameObject> _shatteredPieces = new ArrayList<GameObject>();

        float[][] edgePoints = getShatterEdgePoints(new Rectangle(0, 0, gameObject.getTexture().getWidth(), gameObject.getTexture().getHeight()), pieces);
        float scaleX = gameObject.getTexture().getWidth() / gameObject.getWidth();
        float scaleY = gameObject.getTexture().getHeight() / gameObject.getHeight();
        for (int i = 0; i < edgePoints.length; i++) {
            float[] firstPoint = edgePoints[i];
            float[] secondPoint = (i == edgePoints.length - 1) ? edgePoints[0] : edgePoints[i+1];
            float[] vertices = new float[] {
                    firstPoint[0], firstPoint[1],
                    secondPoint[0], secondPoint[1],
                    centerOfShatter.x * scaleX, centerOfShatter.y * scaleY};
            PolygonRegion region = new PolygonRegion(new TextureRegion(gameObject.getTexture()), vertices, null);
            GameObject g = new GameObject(region.getRegion(), getWidth(region, gameObject), getHeight(region, gameObject), getX(vertices, gameObject), getY(vertices, gameObject));
            g.setRotationSpeed((float) Math.random() * 2);
            g.setRotationResistance(1);
            _shatteredPieces.add(g);
        }
        return _shatteredPieces;
    }

    private static float getWidth(PolygonRegion polygonRegion, GameObject gameObject) {
        // x1 / x2 = y1 / y2
        float scale = gameObject.getWidth() / gameObject.getTexture().getWidth();
        return scale * polygonRegion.getRegion().getRegionWidth();
    }

    private static float getHeight(PolygonRegion polygonRegion, GameObject gameObject) {
        // x1 / x2 = y1 / y2
        float scale = gameObject.getHeight() / gameObject.getTexture().getHeight();
        return scale * polygonRegion.getRegion().getRegionHeight();
    }

    private static float getX(float[] vertices, GameObject gameObject) {
        float maxX = 0;
        for (int i = 0; i < vertices.length; i += 3) {
            if (vertices[i] > maxX)
                maxX = vertices[i];
        }
        float minX = maxX;
        for (int i = 0; i < vertices.length; i += 3) {
            if (vertices[i] < minX)
                minX = vertices[i];
        }
        float scale = gameObject.getWidth() /gameObject.getTexture().getWidth();
        return minX * scale + gameObject.getX();
    }

    private static float getY(float[] vertices, GameObject gameObject) {
        float maxY = 0;
        for (int i = 1; i < vertices.length; i += 3) {
            if (vertices[i] > maxY)
                maxY = vertices[i];
        }
        float minY = maxY;
        for (int i = 1; i < vertices.length; i += 3) {
            if (vertices[i] < minY)
                minY = vertices[i];
        }
        float scale = gameObject.getHeight() / gameObject.getTexture().getHeight();
        return minY * scale + gameObject.getY();
    }

    private static float[][] getShatterEdgePoints(Rectangle rectangle, int pieces) {
        float[][] array = new float[pieces][2];
        float perimeter = rectangle.width * 2 + rectangle.height * 2;
        for (int i = 0; i < pieces; i++) {
            float section = perimeter / pieces;
            float point = (section * i) - (section / 2) + (section * (float) Math.random());
            array[i] = new float[]{getX(rectangle, point), getY(rectangle, point)};
        }
        return array;
    }

    private static float getX(Rectangle rectangle, float point) {
        if (point >= 0 && point < rectangle.width) {
            return point;
        } else if (point >= rectangle.width + rectangle.height && point < rectangle.width * 2 + rectangle.height) {
            return point - (rectangle.width + rectangle.height);
        } else if (point >= rectangle.width && point < rectangle.width + rectangle.height) {
            return rectangle.width;
        } else {
            return 0;
        }
    }

    private static float getY(Rectangle rectangle, float point) {
        if (point >= rectangle.width && point < rectangle.width + rectangle.height) {
            return point - rectangle.width;
        } else if (point >= rectangle.width * 2 + rectangle.height && point < rectangle.height * 2 + rectangle.width * 2) {
            return point - (rectangle.width * 2 + rectangle.height);
        } else if (point >= rectangle.width + rectangle.height && point < rectangle.width * 2 + rectangle.height) {
            return rectangle.height;
        } else {
            return 0;
        }
    }

    private static float[] getShatterAngles(int pieces) {
        float[] piecesAngles = new float[pieces];
        for (int i = 0; i < pieces; i++) {
            piecesAngles[i] = (float) Math.toRadians((360.0f / pieces) - (360.0f / pieces)/2 + Math.random() * (360.0f / pieces));
        }
        return piecesAngles;
    }

    public static Vector3 convertPointToLocal(GameObject gameObject, Vector3 touchPoint) {
        return new Vector3(touchPoint.x - gameObject.getX(), touchPoint.y - gameObject.getY(), 0);
    }
}
