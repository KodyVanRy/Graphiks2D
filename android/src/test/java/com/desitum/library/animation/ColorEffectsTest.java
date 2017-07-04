package com.desitum.library.animation;

import com.badlogic.gdx.graphics.Color;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kodyvanry on 7/3/17.
 */

public class ColorEffectsTest {

    Color red;
    Color green;
    Color blue;

    @Before
    public void before() {
        red = Color.RED;
        green = Color.GREEN;
        blue = Color.BLUE;
    }

    @Test
    public void createTest() {
        ColorEffects colorEffects = new ColorEffects(red, green, 1);
        assertNotNull(colorEffects);
        ColorEffects colorEffects1 = new ColorEffects(red, green, 1, 1);
        assertNotNull(colorEffects1);
    }

    @Test
    public void slopeTest() {
        ColorEffects colorEffects = new ColorEffects(red, green, 1);
        colorEffects.setTimeInAnimation(1);
        colorEffects.updateAnimation();
        assertEquals(1.0f, colorEffects.getCurrentGreen(), Float.MIN_VALUE);
        assertEquals(0.0f, colorEffects.getCurrentRed(), Float.MIN_VALUE);
    }

}
