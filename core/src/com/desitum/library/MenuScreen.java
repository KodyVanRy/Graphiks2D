package com.desitum.library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.GameScreen;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.logging.Log;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.view.Button;
import com.desitum.library.view.EditText;
import com.desitum.library.view.ProgressBar;
import com.desitum.library.view.SeekBar;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class MenuScreen extends GameScreen {

    public static final float SCREEN_WIDTH = 1920.0f;
    public static final float SCREEN_HEIGHT = 1080.0f;

    private ProgressBar progressBar;

    public MenuScreen() {
        super(150, 100, SCREEN_WIDTH, SCREEN_HEIGHT, ASPECT_FILL);
//        super(getScreenWidth(), getScreenHeight());
        setClearColor(new Color(0.5f, 0, 0.5f, 1));

        setupWorld();
        Log.d(this, "Hello world");
        Log.d(this.getClass(), "Hello world");
        Log.d("Hello I am a muffin", "Hello world");
    }

    private void setupWorld() {
//        Widget widget = MenuBuilder.build(Gdx.files.internal("layout.json"), getCam());
//        LinearLayout ll = (LinearLayout) widget.findByName("myLayout");
//
//        MovementAnimator animator = new MovementAnimator(-100, 90, 0.9f, Interpolation.DECELERATE_INTERPOLATOR);
//        animator.setControllingY(true);
//
//        Slider mSlider = new Slider(Drawing.getFilledRectangle(1, 1, new Color(0, 0, 0, 0)), new Texture(Gdx.files.internal("slider.png")), Drawing.getHollowRectangle(100, 3, 3, Color.WHITE), "", 100, 10, 0, 0, null);
//        ll.addWidget(mSlider);

//        getWorld().addWidget(ll);
        getWorld().addGameObject(new GameObject(Drawing.getFilledRectangle(1, 1, Color.BLUE), 2000, 1500, -50, -50));
//        getCam().position.set(0, 0, 0);

        getWorld().addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")));
        getWorld().getParticleEmitters().get(0).turnOn();

        ProgressBar seekBar = new SeekBar(getWorld());
        seekBar.setProgress(0.5f);
        seekBar.setProgressBackgroundDrawable(Drawable.loadDrawable("progress_bg.png", true));
        ((SeekBar) seekBar).setSeekerDrawable(new Drawable(Drawing.getFilledCircle(200, Color.RED)));
        seekBar.setProgressDrawable(new Drawable(Drawing.getFilledRectangle(1, 1, Color.CORAL)));
        getWorld().addView(seekBar);
        seekBar.setSize(800, 100);
        seekBar.setPosition(0, 0);
        seekBar.setProgressBarHeight(100);

        progressBar = new ProgressBar(getWorld());
        progressBar.setProgress(0.5f);
//        progressBar.setProgressBackgroundDrawable(new Drawable(Drawing.getFilledRectangle(1, 1, Color.BLUE)));
        progressBar.setProgressBackgroundDrawable(Drawable.loadDrawable("progress_bg.png", true));
//        progressBar.getProgressBackgroundDrawable().setColor(Color.BLUE);
//        progressBar.setProgressDrawable(Drawable.loadDrawable("progress.png", true));
        progressBar.setProgressDrawable(new Drawable(
                new NinePatch(new Texture("progress_bar.png"), 99, 99, 99, 99)));
//        progressBar.setProgressDrawable(Drawable.loadDrawable(Drawing.getFilledCircle(20, Color.RED), true));
        progressBar.setProgressBarHeight(200);
        getWorld().addView(progressBar);
        progressBar.setSize(800, 200);
        progressBar.setPosition(200, 200);

        Button button = new Button(getWorld());
        button.setSize(200, 200);
        button.setPosition(10, 10);
        button.setRestDrawable(Drawable.loadDrawable("particle.png", true));
        button.setHoverDrawable(new Drawable(Drawing.getFilledRectangle(1, 1, Color.PINK)));
        button.setOriginCenter();
        getWorld().addView(button);

        EditText editText = new EditText(getWorld(), null,
                new BitmapFont(Gdx.files.internal("cartoon.fnt"), new TextureRegion(new Texture("cartoon.png"))));
        editText.setSize(400, 100);
        editText.setPosition(400, 400);
        editText.setBackgroundDrawable(Drawable.loadDrawable("particle.png", true));
        editText.setHint("Hello");
        editText.setOriginCenter();
        getWorld().addView(editText);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 4000;
                while (System.currentTimeMillis() < endTime) {
                    progressBar.setProgress(1 - (endTime - System.currentTimeMillis()) / 4000.0f);
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

    }
}
