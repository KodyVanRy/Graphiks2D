package com.desitum.library.animation;

import com.badlogic.gdx.graphics.Color;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kodyvanry on 7/3/17.
 */

public class ColorEffectsTest {

    private static final float EPSILON = 0.00001f;

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

    @Test
    public void updateTest() {
        ColorEffects colorEffects = new ColorEffects(red, green, 1);
        colorEffects.start();

        colorEffects.update(0.25f);
        assertEquals(0.75f, colorEffects.getCurrentRed(), EPSILON);
        assertEquals(0.25f, colorEffects.getCurrentGreen(), EPSILON);
        assertEquals(0f, colorEffects.getCurrentBlue(), EPSILON);
        assertEquals(1f, colorEffects.getCurrentAlpha(), EPSILON);

        colorEffects.update(0.330000000f);
        assertEquals(0.42f, colorEffects.getCurrentRed(), EPSILON);
        assertEquals(0.58f, colorEffects.getCurrentGreen(), EPSILON);
        assertEquals(0f, colorEffects.getCurrentBlue(), EPSILON);
        assertEquals(1f, colorEffects.getCurrentAlpha(), EPSILON);
    }

    @Test
    public void duplicateTest() {
        ColorEffects colorEffects = new ColorEffects(blue, green, 1);
        Animator colorEffects1 = colorEffects.duplicate();

        assertTrue(colorEffects1 instanceof ColorEffects);
        assertEquals(colorEffects, colorEffects1); // According to .equals they are the same
        assertFalse(colorEffects == colorEffects1); // According to their reference they are different
    }

    @Test
    public void colorsMatchTest() {
        assertTrue(ColorEffects.colorsMatch(Color.RED, Color.RED, 0.0f));
        assertTrue(ColorEffects.colorsMatch(Color.RED, new Color(1, 0, 0.999f, 1), 1.0f));
        assertFalse(ColorEffects.colorsMatch(Color.BLUE, new Color(1, 1, 0, 1), 1.0f));
    }

}
