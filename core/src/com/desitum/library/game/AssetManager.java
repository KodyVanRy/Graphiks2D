package com.desitum.library.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.drawing.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kodyvanry on 5/11/17.
 */

public class AssetManager {

    private static AssetManager instance;

    private List<Texture> mTextureList;
    private HashMap<Integer, Drawable> mDrawableMap;

    private AssetManager() {
        mTextureList = new ArrayList<Texture>();
        mDrawableMap = new HashMap<Integer, Drawable>();
//        new Texture(new FileHandle(Gdx.files.internal()))
    }

    public static AssetManager getInstance() {
        if (instance == null)
            instance = new AssetManager();
        return instance;
    }

    public void addTexture(Texture texture) {
        mTextureList.add(texture);
    }

    public void addTexture(String texture) {
        this.addTexture(new Texture(texture));
    }

    public Texture getTexture(int text) {
        return mTextureList.get(text);
    }

    public void addDrawable(int key, TextureRegion textureRegion) {
        mDrawableMap.put(key, new Drawable(textureRegion));
    }

    public void addDrawable(int key, Texture texture) {
        mDrawableMap.put(key, new Drawable(texture));
    }

    public void addDrawable(int key, NinePatch ninePatch) {
        mDrawableMap.put(key, new Drawable(ninePatch));
    }

    public void addDrawable(int key, Drawable drawable) {
        mDrawableMap.put(key, drawable);
    }

    public Drawable getDrawable(int key) {
        return mDrawableMap.get(key);
    }

    public static void dispose() {
        AssetManager assetManager = getInstance();
        for (Texture texture : assetManager.mTextureList) {
            texture.dispose();
        }
        assetManager.mTextureList = null;
        assetManager = null;
        instance = null;
    }
}
