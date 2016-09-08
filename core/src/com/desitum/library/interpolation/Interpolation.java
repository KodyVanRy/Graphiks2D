package com.desitum.library.interpolation;

/**
 * Created by kody on 2/24/2015
 */
public class Interpolation {
    public static final int OVERSHOOT_INTERPOLATOR = 0;
    public static final int ANTICIPATE_INTERPOLATOR = 1;
    public static final int DECELERATE_INTERPOLATOR = 2;
    public static final int ACCELERATE_INTERPOLATOR = 3;
    public static final int LINEAR_INTERPOLATOR = 4;
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 5;
    public static final int BOUNCE_INTERPOLATOR = 6;

    public static int getInterpolatorNum(Interpolator i) {
        int returnInt = 0;
        if (i.getClass().equals(OvershootInterpolator.class)) {
            returnInt = OVERSHOOT_INTERPOLATOR;
        } else if (i.getClass().equals(AnticipateInterpolator.class)) {
            returnInt = ANTICIPATE_INTERPOLATOR;
        } else if (i.getClass().equals(DecelerateInterpolator.class)) {
            returnInt = DECELERATE_INTERPOLATOR;
        } else if (i.getClass().equals(AccelerateInterpolator.class)) {
            returnInt = ACCELERATE_INTERPOLATOR;
        } else if (i.getClass().equals(LinearInterpolator.class)) {
            returnInt = LINEAR_INTERPOLATOR;
        } else if (i.getClass().equals(AccelerateDecelerateInterpolator.class)) {
            returnInt = ACCELERATE_DECELERATE_INTERPOLATOR;
        } else if (i.getClass().equals(BounceInterpolator.class)) {
            returnInt = BOUNCE_INTERPOLATOR;
        }

        return returnInt;
    }
}
