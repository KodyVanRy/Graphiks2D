package extras_to_deal_with;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 2/15/2015.
 */
public class Colors {
    public static final Color ACTIVE_CIRCLE = new Color(0.992f, 0.231f, 0.251f, 1);// 253, 59, 64
    public static final Color GAME_CIRCLE = new Color(0.173f, 0.635f, 0.702f, 1);// 44, 162, 179
    public static final Color DISABLED_CIRCLE = new Color(0.5f, 0.5f, 0.5f, 1);// 123, 123, 123
    public static final Color GREEN = new Color(0.2f, 0.9f, 0.2f, 1f);
    public static final Color WHITE = new Color(1f, 1f, 1f, 1f);

    public static ArrayList<Color> Colors;


    public static void load() {
        Colors = new ArrayList<Color>();

        Colors.add(ACTIVE_CIRCLE);
        Colors.add(GAME_CIRCLE);
        Colors.add(DISABLED_CIRCLE);
        Colors.add(GREEN);
        Colors.add(WHITE);
    }

}