package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/9/17.
 */

public class LinearLayout extends ViewGroup {

    enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private Orientation mOrientation = Orientation.VERTICAL;

    public LinearLayout(World world) {
        this(world, null);
    }

    public LinearLayout(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
    }

    @Override
    public void addView(View v) {
        super.addView(v);
        v.setLayoutConstraints(getNewLayoutConstraints(v));
        v.setLayer(getLayer() + 1);
        v.setParent(this);
        getWorld().addView(v);
    }

    private LayoutConstraints getNewLayoutConstraints(View v) {
        LayoutConstraints layoutConstraints;
        if (v.getLayoutConstraints() == null)
            layoutConstraints = new LayoutConstraints(0, 0, 0, 0);
        else
            layoutConstraints = v.getLayoutConstraints();

        if (mOrientation == Orientation.HORIZONTAL) {
            if (layoutConstraints.width == LayoutConstraints.MATCH_PARENT)
                throw new RuntimeException(v + " : " + v.getName() + " - Parent orientation is HORIZONTAL. View cannot have width of MATCH_PARENT");
            if (layoutConstraints.width == 0)
                layoutConstraints.width = v.getWidth();
            if (layoutConstraints.height == 0)
                layoutConstraints.height = LayoutConstraints.MATCH_PARENT;
            if (layoutConstraints.x == LayoutConstraints.CENTER_HORIZONTAL)
                throw new RuntimeException(v + " : " + v.getName() + " - Parent orientation is HORIZONTAL. View cannot center horizontally");
            else
                layoutConstraints.x = getNewX();
            layoutConstraints.y = 0;
        } else if (mOrientation == Orientation.VERTICAL) {
            if (layoutConstraints.height == LayoutConstraints.MATCH_PARENT)
                throw new RuntimeException(v + " : " + v.getName() + " - Parent orientation is VERTICAL. View cannot have height of MATCH_PARENT");
            if (layoutConstraints.height == 0)
                layoutConstraints.height = v.getHeight();
            if (layoutConstraints.width == 0)
                layoutConstraints.width = LayoutConstraints.MATCH_PARENT;
            if (layoutConstraints.y == LayoutConstraints.CENTER_VERTICAL)
                throw new RuntimeException(v + " : " + v.getName() + " - Parent orientation is VERTICAL. View cannot center vertically");
            else
                layoutConstraints.y = getNewY();
        }
        return layoutConstraints;
    }

    private float getNewX() {
        float returnX = 0;
        for (View v : getChildren()) {
            returnX += v.getLayoutConstraints().width;
        }
        return returnX;
    }

    private float getNewY() {
        float returnY = getHeight();
        for (View v : getChildren()) {
            returnY -= v.getLayoutConstraints().height;
        }
        returnY = returnY + getLayoutConstraints().height + getLayoutConstraints().y;
        return returnY;
    }
}
