package com.desitum.library.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class Drawable {

    private TextureRegion mTextureRegion;
    private NinePatch mNinePatch;
    private Color mColor;

    public Drawable(Texture texture) {
        this.mTextureRegion = new TextureRegion(texture);
    }

    public Drawable(TextureRegion textureRegion) {
        this.mTextureRegion = textureRegion;
    }

    public Drawable(NinePatch ninePatch) {
        this.mNinePatch = ninePatch;
    }

    public static Drawable loadDrawable(String filePath) {
        return loadDrawable(new Texture(filePath), false);
    }

    public static Drawable loadDrawable(String filePath, boolean isNinePatch) {
        return loadDrawable(new Texture(filePath), isNinePatch);
    }

    public static Drawable loadDrawable(Texture texture, boolean isNinePatch) {
        if (isNinePatch) {
            int width = texture.getWidth();
            int height = texture.getHeight();
            return new Drawable(
                    new NinePatch(
                            texture,
                            width / 3,
                            width / 3,
                            height / 3,
                            height / 3
                    )
            );
        }
        return new Drawable(texture);
    }

    public void setColor(Color color) {
        this.mColor = color;
        if (mNinePatch != null) {
            mNinePatch = new NinePatch(mNinePatch, color);
        }
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        if (mNinePatch != null) {
            mNinePatch.draw(batch, x, y, Math.max(width, mNinePatch.getTotalWidth()), Math.max(height, mNinePatch.getTotalHeight()));
        } else if (mColor != null && mTextureRegion != null) {
            Color originalColor = batch.getColor();
            batch.setColor(mColor);
            batch.draw(mTextureRegion, x, y, width, height);
            batch.setColor(originalColor);
        } else {
            batch.draw(mTextureRegion, x, y, width, height);
        }
    }
}
