package com.desitum.library.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Zmyth97 on 2/9/2015.
 */
public class Drawing {

    public static Texture getTextureRoundedRectangle(int width, int height, int radius, Color color) {
        Pixmap pixmap;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // Horizontal Rectangle
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - 2 * radius);

        // Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2 * radius, pixmap.getHeight());


        // Bottom-left circle
        pixmap.fillCircle(radius, radius, radius);

        // Top-left circle
        pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);

        // Bottom-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);

        // Top-right circle
        pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);

        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }

    public static Texture getHollowRectangle(int width, int height, int lineWidth, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        //left rectangle
        pixmap.fillRectangle(0, 0, lineWidth, height);

        //right rectangle
        pixmap.fillRectangle(width - lineWidth, 0, lineWidth, height);

        //top rectangle
        pixmap.fillRectangle(lineWidth, height - lineWidth, width - lineWidth * 2, lineWidth);

        //bottom rectangle
        pixmap.fillRectangle(lineWidth, 0, width - lineWidth * 2, lineWidth);
        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }

    public static Texture getFilledRectangle(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // rectangle
        pixmap.fillRectangle(0, 0, width, height);

        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }

    public static Texture getFilledCircle(int radius, Color color) {
        Pixmap pixmap = new Pixmap(radius * 2, radius * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // rectangle
        pixmap.fillCircle(radius, radius, radius);

        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }

    public static Texture getHollowCircle(int radius, Color color) {
        Pixmap pixmap = new Pixmap(radius * 2, radius * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // rectangle
        pixmap.drawCircle(radius, radius, radius);

        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }

    public static Texture getDiamondFilled(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);

        // Diamond
        pixmap.fillTriangle(0, height / 2, width / 2, height, width, height / 2);
        pixmap.fillTriangle(0, height / 2, width / 2, 0, width, height / 2);

        Texture returnTexture = new Texture(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        returnTexture.draw(pixmap, 0, 0);
        return returnTexture;
    }
}
