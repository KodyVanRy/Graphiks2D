package com.desitum.library;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by kodyvanry on 5/15/17.
 */

public class BuildMenu extends Game {
    @Override
    public void create() {
        Screen testMenuScreen = new BuildMenuScreen();
        this.setScreen(testMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        getScreen().dispose();
    }
}
