package com.desitum.library

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.desitum.library.drawing.Drawable
import com.desitum.library.drawing.Drawing
import com.desitum.library.game.AssetManager
import com.desitum.library.game.GameScreen
import com.desitum.library.particles.ParticleBuilder
import com.desitum.library.view.Button
import com.desitum.library.view.EditText
import com.desitum.library.view.LayoutConstraints
import com.desitum.library.view.LinearLayout
import com.desitum.library.view.ProgressBar
import com.desitum.library.view.SeekBar
import com.desitum.library.view.TextView
import com.desitum.library.view.View

/**
 * Created by kodyvanry on 5/15/17.
 */

class BuildMenuScreen : GameScreen(150f, 100f, BuildMenuScreen.SCREEN_WIDTH, BuildMenuScreen.SCREEN_HEIGHT, GameScreen.ASPECT_FILL) {

    private var progressBar: ProgressBar? = null
    private var circularProgressBar: ProgressBar? = null
    private var seekBar: SeekBar? = null
    private var layout: LinearLayout? = null

    init {
        //        super(getScreenWidth(), getScreenHeight());
        setClearColor(Color(0.5f, 0f, 0.5f, 1f))

        setupWorld()
    }

    private fun setupWorld() {
        val mAssetManager = AssetManager.instance
        mAssetManager.addTexture("big_picture_a_1.png")
        mAssetManager.addDrawable(BUTTON_HOVER, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 0, 1000, 100)))
        mAssetManager.addDrawable(BUTTON_REST, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 100, 1000, 100)))
        mAssetManager.addDrawable(BUTTON_DOWN, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 200, 500, 50)))
        mAssetManager.addDrawable(BADLOGIC, Drawable(TextureRegion(mAssetManager.getTexture(0), 500, 200, 256, 256)))
        mAssetManager.addDrawable(CIRCLE_SHADOW, Drawable(TextureRegion(mAssetManager.getTexture(0), 756, 200, 200, 200)))
        mAssetManager.addDrawable(PROGRESS_BAR, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 0, 250, 200, 200), 66, 66, 66, 66)))
        mAssetManager.addDrawable(PROGRESS_BG, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 200, 250, 200, 200), 66, 66, 66, 66)))
        mAssetManager.addDrawable(SLIDER, Drawable(TextureRegion(mAssetManager.getTexture(0), 756, 400, 200, 200)))
        mAssetManager.addDrawable(GRAPHIKS2D, Drawable(TextureRegion(mAssetManager.getTexture(0), 0, 450, 128, 128)))
        mAssetManager.addDrawable(CIRCULAR_PROGRESS, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 400, 250, 100, 100), 48, 49, 49, 48)))
        mAssetManager.addDrawable(CIRCULAR_PROGRESS_BAR, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 956, 200, 46, 46), 21, 22, 21, 21)))
        mAssetManager.addDrawable(PARTICLE, Drawable(NinePatch(TextureRegion(mAssetManager.getTexture(0), 1000, 0, 10, 10), 3, 3, 3, 3)))


        //        getWorld().addGameObject(new GameObject(Drawing.getFilledRectangle(1, 1, Color.BLUE), 2000, 1500, -50, -50));

        world.addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")))
        world.particleEmitters[0].turnOn()

        val button = Button(world)
        button.setSize(200f, 200f)
        button.setPosition(10f, 10f)
        button.restDrawable = mAssetManager.getDrawable(PARTICLE)
        button.hoverDrawable = mAssetManager.getDrawable(BADLOGIC)
        button.setOriginCenter()
        world.addView(button)

        val linearLayoutConstraints = LayoutConstraints(100f, 100f, 800f, 600f)
        layout = LinearLayout(world, linearLayoutConstraints)
        layout!!.backgroundDrawable = Drawable(Drawing.getFilledRectangle(1, 1, Color.CHARTREUSE))
        world.addView(layout!!)

        seekBar = SeekBar(world)
        seekBar!!.progress = 0.5f
        seekBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(PARTICLE)
        seekBar!!.seekerDrawable = Drawable(Drawing.getFilledCircle(200, Color.RED))
        seekBar!!.progressDrawable = Drawable(Drawing.getFilledRectangle(1, 1, Color.CORAL))
        seekBar!!.setSize(600f, 200f)
        seekBar!!.progressBarHeight = 50f
        layout!!.addView(seekBar!!)


        progressBar = ProgressBar(world)
        progressBar!!.progress = 0.5f
        progressBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS)
        //        progressBar.getProgressBackgroundDrawable().setColor(Color.BLUE);
        //        progressBar.setProgressDrawable(Drawable.loadDrawable("progress.png", true));
        progressBar!!.progressDrawable = mAssetManager.getDrawable(PROGRESS_BAR)
        progressBar!!.progressBarHeight = 200f
        progressBar!!.setSize(800f, 200f)
        //        layout.addView(progressBar);


        circularProgressBar = CircularProgressBar(world)
        circularProgressBar!!.progress = 0.5f
        circularProgressBar!!.progressBackgroundDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS)
        //        progressBar.getProgressBackgroundDrawable().setColor(Color.BLUE);
        //        progressBar.setProgressDrawable(Drawable.loadDrawable("progress.png", true));
        circularProgressBar!!.progressDrawable = mAssetManager.getDrawable(CIRCULAR_PROGRESS_BAR)
        circularProgressBar!!.progressBarHeight = 100f
        circularProgressBar!!.setSize(800f, 100f)
        layout!!.addView(circularProgressBar!!)

        val textView = TextView(world, null,
                BitmapFont(Gdx.files.internal("cartoon.fnt"), TextureRegion(Texture("cartoon.png"))))
        textView.setSize(View.MATCH_PARENT, 100f)
        textView.backgroundDrawable = mAssetManager.getDrawable(PARTICLE)
        layout!!.addView(textView)

        val editText = EditText(world, null,
                BitmapFont(Gdx.files.internal("cartoon.fnt"), TextureRegion(Texture("cartoon.png"))))
        editText.setSize(View.MATCH_PARENT, 100f)
        editText.backgroundDrawable = mAssetManager.getDrawable(PARTICLE)
        editText.hint = "Hello"
        layout!!.addView(editText)

        Thread(Runnable {
            val endTime = System.currentTimeMillis() + 4000
            while (System.currentTimeMillis() < endTime) {
                progressBar!!.progress = 1 - (endTime - System.currentTimeMillis()) / 4000.0f
                circularProgressBar!!.progress = 1 - (endTime - System.currentTimeMillis()) / 4000.0f
                layout!!.setPosition(layout!!.x + 1, layout!!.y + 1)
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
            progressBar!!.progress = 1f
        }).start()
    }

    companion object {

        val SCREEN_WIDTH = 1920.0f
        val SCREEN_HEIGHT = 1080.0f

        val BUTTON_HOVER = 1
        val BUTTON_REST = 2
        val BUTTON_DOWN = 3
        val BADLOGIC = 4
        val CIRCLE_SHADOW = 5
        val PROGRESS_BAR = 6
        val PROGRESS_BG = 7
        val SLIDER = 8
        val GRAPHIKS2D = 9
        val CIRCULAR_PROGRESS = 10
        val CIRCULAR_PROGRESS_BAR = 11
        val PARTICLE = 12
    }
}
