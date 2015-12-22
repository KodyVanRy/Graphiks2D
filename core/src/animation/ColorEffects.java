package animation;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by dvan6234 on 2/17/2015.
 */
public class ColorEffects extends Animator {
    private float startRed;
    private float startGreen;
    private float startBlue;
    private float startAlpha;

    private float slopeRed;
    private float slopeGreen;
    private float slopeBlue;
    private float slopeAlpha;

    private float endRed;
    private float endGreen;
    private float endBlue;
    private float endAlpha;

    private float currentRed;
    private float currentGreen;
    private float currentBlue;
    private float currentAlpha;

    private boolean transforming;

    public ColorEffects(Color startColor, Color endColor, float duration) {
        super(duration, 0);
        transforming = false;

        setupColors(startColor, endColor, duration);
    }

    public ColorEffects(Color startColor, Color endColor, float duration, float delay) {
        super(duration, delay);
        setupColors(startColor, endColor, duration);
    }

    static public boolean colorsMatch(Color color1, Color color2, float marginOfError) {
        if (color1.equals(color2)) return true;

        float error = 0;

        error += Math.abs(color1.r - color2.r);
        error += Math.abs(color1.g - color2.g);
        error += Math.abs(color1.b - color2.b);
        error += Math.abs(color1.a - color2.a);

        if (error < marginOfError) {
            return true;
        }
        return false;
    }

    private void setupColors(Color startColor, Color endColor, float duration) {
        if (duration <= 0) {
            currentRed = endColor.r;
            endRed = (int) endColor.r;
            slopeRed = 0;
            currentGreen = endColor.r;
            endGreen = (int) endColor.g;
            slopeGreen = 0;
            currentBlue = endColor.b;
            endBlue = (int) endColor.b;
            slopeBlue = 0;
            currentAlpha = endColor.a;
            endAlpha = (int) endColor.a;
            slopeAlpha = 0;
            return;
        }

        startRed = startColor.r;
        startGreen = startColor.g;
        startBlue = startColor.b;
        startAlpha = startColor.a;

        slopeRed = (endColor.r - startColor.r);
        slopeGreen = (endColor.g - startColor.g);
        slopeBlue = (endColor.b - startColor.b);
        slopeAlpha = (endColor.a - startColor.a);

        currentRed = startColor.r;
        currentGreen = startColor.g;
        currentBlue = startColor.b;
        currentAlpha = startColor.a;

        endRed = endColor.r;
        endGreen = endColor.g;
        endBlue = endColor.b;
        endAlpha = endColor.a;
    }

    public Color getCurrentColor() {
        return new Color(currentRed, currentGreen, currentBlue, currentAlpha);
    }

    @Override
    public Animator duplicate() {
        return new ColorEffects(new Color(startRed, startGreen, startBlue, startAlpha), new Color(endRed, endGreen, endBlue, endAlpha), getDuration());
    }

    @Override
    public float getCurrentAmount() {
        return 0;
    }

    @Override
    protected void updateAnimation() {
        currentRed = slopeRed * getTimeInAnimation() + startRed;
        currentGreen = slopeGreen * getTimeInAnimation() + startGreen;
        currentBlue = slopeBlue * getTimeInAnimation() + startBlue;
        currentAlpha = slopeAlpha * getTimeInAnimation() + startAlpha;

        if (sprite != null) {
            sprite.setColor(getCurrentColor());
        }
    }
}

