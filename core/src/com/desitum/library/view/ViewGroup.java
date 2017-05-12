package com.desitum.library.view;

import com.desitum.library.game.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class ViewGroup extends View {

    private List<View> mChildren;

    public ViewGroup(World world) {
        this(world, null);
    }

    public ViewGroup(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
        mChildren = new ArrayList<View>();
    }

    public List<View> getChildren() {
        return mChildren;
    }

    public void setChildren(List<View> children) {
        this.mChildren = children;
    }

    public void addView(View v) {
        mChildren.add(v);
    }

    public void removeView(View v) {
        mChildren.remove(v);
        getWorld().removeView(v);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        for (View v : mChildren) {
            v.invalidate();
        }
    }
}
