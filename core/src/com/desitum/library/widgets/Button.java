package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.desitum.library.listener.OnClickListener;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class Button extends Widget {

    /* TODO If possible add back in material buttons (Not sure if this will be possible.
    I can easily do the material click, but keeping it within the exact bounds of the view will be
    the difficult part. Square is easy, rounded corners and such... not so much. Especially considering
    drawing a circle on the view using the Drawing class is both expensive to use and doesn't look the prettiest
    Should material buttons even be a thing? These are for games after all. Maybe the best option is adding an
    animation on Click? Then again if you override the OnClickListener can't each game handle
    that individually? Interesting. I'll bring it up with the Amigo!
     */

    private OnClickListener mOnClickListener;

    private Texture mRestTexture;
    private Texture mHoverTexture;
    private Texture mClickTexture;

    public Button(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(name, width, height, x, y);
        mRestTexture = text;
    }

//    @Override
//    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
//        if (isVisible()) {
//            if (isPointInWidget(mousePos) && isEnabled() && isClickDown() && !touchDown) {
//                if (mOnClickListener != null) mOnClickListener.onClick(this);
//            } else if (isPointInWidget(mousePos) && touchDown && !isClickDown()) {
//                if (mOnClickListener != null) mOnClickListener.onClickDown(this);
//            }
//
//            if (isPointInWidget(mousePos)) {
//                if (touchDown && getClickTexture() != null) {
//                    setTexture(getClickTexture());
//                } else if (getHoverTexture() != null) {
//                    setTexture(getHoverTexture());
//                } else {
//                    setTexture(getRestTexture());
//                }
//            } else {
//                setTexture(getRestTexture());
//            }
//        }
//        super.updateTouchInput(mousePos, touchDown);
//    }

    @Override
    public boolean onTouchEvent(com.desitum.library.view.TouchEvent touchEvent) {
        super.onTouchEvent(touchEvent);
        if (isVisible()) {
            switch (touchEvent.getAction()) {
                case DOWN:
                    if (mClickTexture != null)
                        setTexture(mClickTexture);
                    return true;
                case MOVE:
                    if (isPointInWidget(touchEvent.getX(), touchEvent.getY()) && mClickTexture != null)
                        setTexture(mClickTexture);
                    else
                        setTexture(mRestTexture);
                    return true;
                case UP:
                    if (mOnClickListener != null)
                        mOnClickListener.onClick(this);
                    setTexture(mRestTexture);
                    return true;
            }
        }
        return super.onTouchEvent(touchEvent);
    }

    public Texture getRestTexture() {
        return mRestTexture;
    }

    /**
     * @param restTexture texture when button is neither being hovered or clicked
     */
    public void setRestTexture(Texture restTexture) {
        this.mRestTexture = restTexture;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public Texture getHoverTexture() {
        return mHoverTexture;
    }

    public void setHoverTexture(Texture hoverTexture) {
        this.mHoverTexture = hoverTexture;
    }

    public Texture getClickTexture() {
        return mClickTexture;
    }

    public void setClickTexture(Texture clickTexture) {
        this.mClickTexture = clickTexture;
    }
}
