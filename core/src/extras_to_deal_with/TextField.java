package extras_to_deal_with;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 4/10/15.
 * can be used by kody and people in []
 */
public class TextField {

    private Pixmap pm;
    private Texture tx;

    public TextField() {
        pm = new Pixmap(40, 40, Pixmap.Format.RGBA8888);
        tx = new Texture(pm);

    }
}
