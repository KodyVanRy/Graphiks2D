package com.desitum.library.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.interpolation.Interpolation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kodyvanry on 7/3/17.
 */

public class AnimatorSetTest {

    private MovementAnimator movementAnimator;
    private MovementAnimator movementAnimator2;
    private AnimatorSet animatorSet;

    @Before
    public void before() {
        movementAnimator = new MovementAnimator(1, 2, 1, Interpolation.ACCELERATE_INTERPOLATOR);
        movementAnimator2 = new MovementAnimator(new Sprite(), 1, 2, 1, 1, Interpolation.ACCELERATE_INTERPOLATOR, false, false);

        animatorSet = new AnimatorSet(movementAnimator2, movementAnimator);
    }

    @Test
    public void testCreate() {
        AnimatorSet animatorSet = new AnimatorSet(movementAnimator, movementAnimator2);
        assertNotNull(animatorSet);
    }

    @Test
    public void testSize() {
        assertEquals(2, animatorSet.getSize());
    }

    @Test
    public void testTotalDuration() {
        assertEquals(2f, animatorSet.getTotalDuration(), Float.MIN_VALUE);
    }

    @Test
    public void testGetAnimators() {
        assertArrayEquals(animatorSet.getAnimators(), new Animator[] {movementAnimator2, movementAnimator});
    }

    @Test
    public void testUpdate() {
        animatorSet.start();
        animatorSet.update(0.5f);
        assertTrue(animatorSet.getAnimators()[0].isRunning());
        assertEquals(0.5f, animatorSet.getAnimators()[0].getCurrentDelay(), Float.MIN_VALUE);
        assertEquals(0.5f, animatorSet.getAnimators()[1].getTimeInAnimation(), Float.MIN_VALUE);
        assertNotEquals(0.5f, animatorSet.getAnimators()[0].getTimeInAnimation(), Float.MIN_VALUE);
    }

    @Test
    public void testDuplicate() {
        Animator animatorSet1 = animatorSet.duplicate();
        assertTrue(animatorSet1 instanceof AnimatorSet);
        assertNotSame(animatorSet, animatorSet1);
    }

}
