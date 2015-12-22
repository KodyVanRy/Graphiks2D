package widgets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public abstract class Layout extends Widget {

    private ArrayList<Widget> widgets;

    private float paddingLeft, paddingRight, paddingBottom, paddingTop, padding;

    public Layout(Texture text, String name, float width, float height, float X, float Y, Widget parent, Camera camera) {
        super(text, name, width, height, X, Y, camera);

        widgets = new ArrayList<Widget>();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        for (Widget widget : widgets) {
            widget.draw(batch);
        }

    }

    public float getBaseX() {
        return getX() + getPaddingLeft();
    }

    public float getBaseY() {
        return getY() + getPaddingBottom();
    }

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public float getPaddingBottom() {
        return paddingBottom;
    }

    public float getPaddingRight() {
        return paddingRight;
    }

    public float getPaddingTop() {
        return paddingTop;
    }

    public float getPadding() {
        return padding;
    }

    public void addWidget(Widget widget) {
        widget.setParent(this);
        widgets.add(widget);
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        for (Widget widget : widgets) {
            widget.update(delta);
        }
    }

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
        super.updateTouchInput(mousePos, clickDown);

        for (Widget widget : widgets) {
            widget.updateTouchInput(mousePos, clickDown);
        }
    }

    @Override
    public ArrayList<Widget> tree(ArrayList<Widget> widgets1) {
        if (widgets1 == null) widgets1 = new ArrayList<Widget>();
        for (Widget widget : widgets) widgets1 = widget.tree(widgets1);
        return super.tree(widgets);
    }
}
