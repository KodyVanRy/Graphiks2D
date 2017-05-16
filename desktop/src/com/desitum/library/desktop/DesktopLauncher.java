package com.desitum.library.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.desitum.library.BuildMenu;
import com.desitum.library.LibraryTest;
import com.desitum.library.game.AssetManager;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        Game buildMenu = new BuildMenu();
        new LwjglApplication(new LibraryTest(), config);
    }
}
