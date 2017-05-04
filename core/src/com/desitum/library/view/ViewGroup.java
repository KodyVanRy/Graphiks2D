package com.desitum.library.view;

import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class ViewGroup extends View {
    public ViewGroup(World world) {
        super(world);
    }

    public ViewGroup(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
    }
}
