package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
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

    private OnClickListener onClickListener;

    private Texture restTexture;
    private Texture hoverTexture;
    private Texture clickTexture;

    public Button(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);
        restTexture = text;
    }

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
        if (isVisible()) {
            if (isPointInWidget(mousePos) && isEnabled() && isClickDown() && !touchDown) {
                if (onClickListener != null) onClickListener.onClick(this);
            } else if (isPointInWidget(mousePos) && touchDown && !isClickDown()) {
                if (onClickListener != null) onClickListener.onClickDown(this);
            }

            if (isPointInWidget(mousePos)) {
                if (touchDown && getClickTexture() != null) {
                    setTexture(getClickTexture());
                } else if (getHoverTexture() != null) {
                    setTexture(getHoverTexture());
                } else {
                    setTexture(getRestTexture());
                }
            } else {
                setTexture(getRestTexture());
            }
        }
        super.updateTouchInput(mousePos, touchDown);
    }

    public Texture getRestTexture() {
        return restTexture;
    }

    /**
     * @param restTexture texture when button is neither being hovered or clicked
     */
    public void setRestTexture(Texture restTexture) {
        this.restTexture = restTexture;
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Texture getHoverTexture() {
        return hoverTexture;
    }

    public void setHoverTexture(Texture hoverTexture) {
        this.hoverTexture = hoverTexture;
    }

    public Texture getClickTexture() {
        return clickTexture;
    }

    public void setClickTexture(Texture clickTexture) {
        this.clickTexture = clickTexture;
    }
}
