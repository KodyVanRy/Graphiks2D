package com.desitum.library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.AssetManager;
import com.desitum.library.game.GameScreen;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.logging.Log;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.view.Button;
import com.desitum.library.view.EditText;
import com.desitum.library.view.LayoutConstraints;
import com.desitum.library.view.LinearLayout;
import com.desitum.library.view.ProgressBar;
import com.desitum.library.view.SeekBar;
import com.desitum.library.view.View;
import com.desitum.library.widgets.CircularProgressBar;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class MenuScreen extends GameScreen {

    public static final float SCREEN_WIDTH = 1920.0f;
    public static final float SCREEN_HEIGHT = 1080.0f;

    public static final int BUTTON_HOVER = 1;
    public static final int BUTTON_REST = 2;
    public static final int BUTTON_DOWN = 3;
    public static final int BADLOGIC = 4;
    public static final int CIRCLE_SHADOW = 5;
    public static final int PROGRESS_BAR = 6;
    public static final int PROGRESS_BG = 7;
    public static final int SLIDER = 8;
    public static final int GRAPHIKS2D = 9;
    public static final int CIRCULAR_PROGRESS = 10;
    public static final int CIRCULAR_PROGRESS_BAR = 11;
    public static final int PARTICLE = 12;


    private ProgressBar progressBar;
    private ProgressBar circularProgressBar;
    private SeekBar seekBar;
    private LinearLayout layout;

    public MenuScreen() {
        super(150, 100, SCREEN_WIDTH, SCREEN_HEIGHT, Companion.getASPECT_FILL());
//        super(getScreenWidth(), getScreenHeight());
        setClearColor(new Color(0.5f, 0, 0.5f, 1));

        setupWorld();
        Log.d(this, "Hello world");
        Log.d(this.getClass(), "Hello world");
        Log.d("Hello I am a muffin", "Hello world");
    }

    private void setupWorld() {
        AssetManager mAssetManager = AssetManager.getInstance();
        mAssetManager.addTexture("big_picture_a_1.png");
        mAssetManager.addDrawable(BUTTON_HOVER, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 0, 0, 1000, 100)));
        mAssetManager.addDrawable(BUTTON_REST, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 0, 100, 1000, 100)));
        mAssetManager.addDrawable(BUTTON_DOWN, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 0, 200, 500, 50)));
        mAssetManager.addDrawable(BADLOGIC, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 500, 200, 256, 256)));
        mAssetManager.addDrawable(CIRCLE_SHADOW, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 756, 200, 200, 200)));
        mAssetManager.addDrawable(PROGRESS_BAR, new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(0), 0, 250, 200, 200), 66, 66, 66, 66)));
        mAssetManager.addDrawable(PROGRESS_BG, new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(0), 200, 250, 200, 200), 66, 66, 66, 66)));
        mAssetManager.addDrawable(SLIDER, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 756, 400, 200, 200)));
        mAssetManager.addDrawable(GRAPHIKS2D, new Drawable(new TextureRegion(mAssetManager.getTexture(0), 0, 450, 128, 128)));
        mAssetManager.addDrawable(CIRCULAR_PROGRESS, new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(0), 400, 250, 100, 100), 48, 49, 49, 48)));
        mAssetManager.addDrawable(CIRCULAR_PROGRESS_BAR, new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(0), 956, 200, 46, 46), 21, 22, 21, 21)));
        mAssetManager.addDrawable(PARTICLE, new Drawable(new NinePatch(new TextureRegion(mAssetManager.getTexture(0), 1000, 0, 10, 10), 3, 3, 3, 3)));


//        getWorld().addGameObject(new GameObject(Drawing.getFilledRectangle(1, 1, Color.BLUE), 2000, 1500, -50, -50));

        getWorld().addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")));
        getWorld().getParticleEmitters().get(0).turnOn();

        Button button = new Button(getWorld());
        button.setSize(200, 200);
        button.setPosition(10, 10);
        button.setRestDrawable(mAssetManager.getDrawable(PARTICLE));
        button.setHoverDrawable(mAssetManager.getDrawable(BADLOGIC));
        button.setOriginCenter();
        getWorld().addView(button);

        final LayoutConstraints linearLayoutConstraints = new LayoutConstraints(100, 100, 800, 600);
        layout = new LinearLayout(getWorld(), linearLayoutConstraints);
        layout.setBackgroundDrawable(new Drawable(Drawing.getFilledRectangle(1, 1, Color.CHARTREUSE)));
        getWorld().addView(layout);

        seekBar = new SeekBar(getWorld());
        seekBar.setProgress(0.5f);
        seekBar.setProgressBackgroundDrawable(mAssetManager.getDrawable(PARTICLE));
        seekBar.setSeekerDrawable(new Drawable(Drawing.getFilledCircle(200, Color.RED)));
        seekBar.setProgressDrawable(new Drawable(Drawing.getFilledRectangle(1, 1, Color.CORAL)));
        seekBar.setSize(600, 200);
        seekBar.setProgressBarHeight(50);
        layout.addView(seekBar);


        progressBar = new ProgressBar(getWorld());
        progressBar.setProgress(0.5f);
        progressBar.setProgressBackgroundDrawable(mAssetManager.getDrawable(CIRCULAR_PROGRESS));
//        progressBar.getProgressBackgroundDrawable().setColor(Color.BLUE);
//        progressBar.setProgressDrawable(Drawable.loadDrawable("progress.png", true));
        progressBar.setProgressDrawable(mAssetManager.getDrawable(PROGRESS_BAR));
        progressBar.setProgressBarHeight(200);
        progressBar.setSize(800, 200);
//        layout.addView(progressBar);


        circularProgressBar = new CircularProgressBar(getWorld());
        circularProgressBar.setProgress(0.5f);
        circularProgressBar.setProgressBackgroundDrawable(mAssetManager.getDrawable(CIRCULAR_PROGRESS));
        circularProgressBar.setProgressDrawable(mAssetManager.getDrawable(CIRCULAR_PROGRESS_BAR));
        circularProgressBar.setProgressBarHeight(100);
        circularProgressBar.setSize(800, 100);
        layout.addView(circularProgressBar);

        EditText editText = new EditText(getWorld(), null,
                new BitmapFont(Gdx.files.internal("cartoon.fnt"), new TextureRegion(new Texture("cartoon.png"))));
        editText.setSize(View.MATCH_PARENT, 100);
        editText.setBackgroundDrawable(mAssetManager.getDrawable(PARTICLE));
        editText.setHint("Hello");
        layout.addView(editText);

        layout.startAnimator(new MovementAnimator(layout, 0, 400, 4, 0, Interpolation.DECELERATE_INTERPOLATOR, true, true));

        new Thread(new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 4000;
                while (System.currentTimeMillis() < endTime) {
                    progressBar.setProgress(1 - (endTime - System.currentTimeMillis()) / 4000.0f);
                    circularProgressBar.setProgress(1 - (endTime - System.currentTimeMillis()) / 4000.0f);
//                    layout.setPosition(layout.getX() + 1, layout.getY() + 1);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setProgress(1);
            }
        }).start();
    }

    @Override
    public void show() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        AssetManager.dispose();
    }
}
