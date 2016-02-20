package com.desitum.library.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.widgets.Widget;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class World {

    private ArrayList<Widget> widgets;
    private ArrayList<GameObject> gameObjects;
    private OrthographicCamera camera;

    public World(OrthographicCamera camera) {
        this.camera = camera;
        this.widgets = new ArrayList<Widget>();
        this.gameObjects = new ArrayList<GameObject>();
    }

    public void update(float delta) {
        for (Widget widget : widgets) {
            widget.update(delta);
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.update(delta);
        }
    }

    public void updateTouchInput(Vector3 touchPos, boolean touchDown) {
        for (Widget widget : widgets) {
            widget.updateTouchInput(touchPos, touchDown);
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updateTouchInput(touchPos, touchDown);
        }
    }

    public void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
        if (gameObject.getLevel() != GameObject.DEFAULT_LEVEL) {
            Collections.sort(gameObjects);
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
