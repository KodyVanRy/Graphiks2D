package widgets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class LinearLayout extends Layout {

    public static final int VERTICAL_ORIENTATION = 0;
    public static final int HORIZONTAL_ORIENTATION = 1;
    public static final int ALIGNMENT_TOP = 0;
    public static final int ALIGNMENT_BOTTOM = 1;
    public static final int ALIGNMENT_CENTER = 2;
    public static final int ALIGNMENT_RIGHT = 1;
    public static final int ALIGNMENT_LEFT = 0;

    private int orientation;
    private float spacing;
    private int alignment;

    public LinearLayout(Texture text, String name, float width, float height, float X, float Y, Widget parent, Camera cam) {
        super(text, name, width, height, X, Y, parent, cam);
        orientation = HORIZONTAL_ORIENTATION;
        this.spacing = 0;
    }

    public LinearLayout(Texture text, String name, float width, float height, float X, float Y, Widget parent, int orientation, Camera cam) {
        super(text, name, width, height, X, Y, parent, cam);
        this.orientation = orientation;
        this.spacing = 0;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    @Override
    public void addWidget(Widget widget) {
        widget.setParent(this);
        float posX = 0;
        float posY = getHeight() - (getWidgets().size() >= 1 ? getWidgets().get(0).getHeight() : widget.getHeight());
        for (Widget preWidget : getWidgets()) {
            posX += preWidget.getWidth() + spacing;
            posY -= preWidget.getHeight() + spacing;
        }
        if (orientation == HORIZONTAL_ORIENTATION) {
            if (alignment == ALIGNMENT_TOP) {
                posY = this.getHeight() - widget.getHeight();
            } else if (alignment == ALIGNMENT_CENTER) {
                posY = this.getHeight() / 2 - widget.getHeight() / 2;
            } else if (alignment == ALIGNMENT_BOTTOM) {
                posY = 0;
            }
        } else if (orientation == VERTICAL_ORIENTATION) {
            if (alignment == ALIGNMENT_LEFT) {
                posX = 0;
            } else if (alignment == ALIGNMENT_CENTER) {
                posX = this.getWidth() / 2 - widget.getWidth() / 2;
            } else if (alignment == ALIGNMENT_RIGHT) {
                posX = this.getWidth() - widget.getWidth();
            }
        }
        widget.setMyX(posX);
        widget.setMyY(posY);
        super.addWidget(widget);
    }
}
