package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.listener.OnClickListener;
import com.desitum.library.math.CollisionDetection;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class Button extends Widget {

    private OnClickListener onClickListener;

    private Texture restTexture;
    private Texture hoverTexture;
    private Texture clickTexture;

    public Button(Texture text, String name, float width, float height, float X, float Y) {
        super(text, name, width, height, X, Y);
        restTexture = text;
    }

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
        if (isVisible()) {
            if (pointInWidget(mousePos) && isEnabled() && getClickIsDown() && !touchDown) {
                if (onClickListener != null) onClickListener.onClick(this);
            }

            if (CollisionDetection.pointInRectangle(getBoundingRectangle(), mousePos)) {
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
            super.updateTouchInput(mousePos, touchDown);
        }
    }

    public Texture getRestTexture() {
        return restTexture;
    }

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
