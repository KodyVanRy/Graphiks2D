package com.desitum.library.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.Animator;
import com.desitum.library.animation.MovementAnimator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class GameObject extends Sprite implements Comparable<GameObject> {

    public static final int DEFAULT_LEVEL = 0;
    private ArrayList<Animator> animators;
    private int level;

    public GameObject() {
        animators = new ArrayList<Animator>();
        level = DEFAULT_LEVEL;
    }

    public GameObject(int level) {
        animators = new ArrayList<Animator>();
        this.level = level;
    }

    public void update(float delta) {
        if (animators.size() > 0) {
            Iterator<Animator> iterator = animators.iterator();
            while (iterator.hasNext()) {
                Animator animator = iterator.next();
                animator.update(delta);
                if (animator.didFinish()) {
                    iterator.remove();
                }
            }
        }
    }

    public void updateTouchInput(Vector3 touchPos, boolean touchDown) {

    }

    public void moveTo(float x, float y, float duration, int interpolation) {
        MovementAnimator xAnimator = new MovementAnimator(this, getX(), x, duration, 0, interpolation, true, false);
        MovementAnimator yAnimator = new MovementAnimator(this, getY(), y, duration, 0, interpolation, false, true);
        xAnimator.start(true);
        yAnimator.start(true);
        this.animators.add(xAnimator);
        this.animators.add(yAnimator);
    }

    public void addAndStartAnimator(Animator anim) {
        anim.setSprite(this);
        animators.add(anim);
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int compareTo(GameObject gameObject) {
        return this.getLevel() - gameObject.getLevel();
    }
}
