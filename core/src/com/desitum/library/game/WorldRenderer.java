package com.desitum.library.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.game_objects.PolygonGameObject;
import com.desitum.library.logging.Log;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.widgets.Widget;

import sun.awt.AWTAccessor;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class WorldRenderer {
    private static final String _log = "WorldRenderer";

    private World world;
    private PolygonRegion polygonRegion;

    public WorldRenderer(World world) {
        this.world = world;
        // Construct and draw the polygon
        float[] vertices = new float[8];
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 10;
        vertices[3] = 0;
        vertices[4] = 10;
        vertices[5] = 10;
        vertices[6] = 0;
        vertices[7] = 5;
        short[] triangles = new short[] {0, 1, 2, 0, 2, 3};
        polygonRegion = new PolygonRegion(new TextureRegion(new Texture("badlogic.jpg")), vertices, triangles);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void draw(Batch batch, PolygonSpriteBatch polygonSpriteBatch) {
        for (GameObject gameObject : world.getGameObjects()) {
            gameObject.draw(batch);
        }
        batch.flush();
//        for (PolygonGameObject gameObject : world.getShatterPieces()) {
//            Log.d(_log, "gameObject.pos = " + gameObject.getX() + ", " + gameObject.getY());
//            Log.d(_log, "gameObject.size = " + gameObject.getWidth() + ", " + gameObject.getHeight());
//            gameObject.draw(polygonSpriteBatch);
//        }
//        polygonSpriteBatch.draw(polygonRegion, 0, 0);
//        batch.flush();
        for (ParticleEmitter particleEmitter: world.getParticleEmitters()) {
            particleEmitter.draw(batch);
        }
        batch.flush();
        for (Widget widget : world.getWidgets()) {
            widget.draw(batch, world.getViewport());
        }
        batch.flush();
    }

    public void draw(PolygonSpriteBatch polygonSpriteBatch) {
        for (PolygonGameObject gameObject : world.getShatterPieces()) {
            Log.d(_log, "gameObject.pos = " + gameObject.getX() + ", " + gameObject.getY());
            Log.d(_log, "gameObject.size = " + gameObject.getWidth() + ", " + gameObject.getHeight());
            gameObject.draw(polygonSpriteBatch);
        }
    }
}
