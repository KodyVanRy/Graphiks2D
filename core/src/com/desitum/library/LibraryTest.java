package com.desitum.library;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class LibraryTest extends Game {
    @Override
    public void create() {
        Screen testMenuScreen = new MenuScreen();
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
