package widgets;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import extras_to_deal_with.Drawing;


/**
 * Created by kody on 12/13/15.
 * can be used by kody and people in [kody}]
 */
public class MenuBuilder {

    public static final String MATCH_PARENT = "match parent";

    public static final String NAME = "name";
    public static final String WIDGET_TYPE = "widget";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String X = "x";
    public static final String Y = "y";

    // Layout specifics
    public static final String ORIENTATION = "orientation";
    public static final String CHILDREN = "children";
    public static final String VERTICAL = "vertical";
    public static final String HORIZONTAL = "horizontal";
    public static final String BACKGROUND = "background";
    public static final String SPACING = "spacing";
    public static final String ALIGNMENT = "alignment";
    public static final String ALIGNMENT_TOP = "top";
    public static final String ALIGNMENT_BOTTOM = "bottom";
    public static final String ALIGNMENT_CENTER = "center";
    public static final String ALIGNMENT_RIGHT = "right";
    public static final String ALIGNMENT_LEFT = "left";

    // Button specifics
    public static final String HOVER_TEXTURE = "texture_hover";
    public static final String CLICK_TEXTURE = "texture_click";

    //Edit Text
    public static final String FOCUS = "request focus";
    public static final String TEXT = "text";
    public static final String HINT = "hint";
    public static final String FONT = "font";
    public static final String BLINK_ON = "blink_on";
    public static final String BLINK_OFF = "blink_off";
    public static final String TEXT_COLOR = "text_color";
    public static final String HINT_COLOR = "hint_color";
    public static final String INPUT_TYPE = "input_type";

    //Slider
    public static final String VALUE = "value";
    public static final String SLIDER_WIDTH = "slider_width";
    public static final String SLIDER_HEIGHT = "slider_height";
    public static final String PADDING = "padding";
    public static final String BAR_HEIGHT = "slider_bar_height";
    public static final String SLIDER_TEXTURE = "slider_texture";
    public static final String BAR_TEXTURE = "bar_texture";

    public static Widget build(FileHandle file, OrthographicCamera cam) {
        Widget returnWidget = null;
        try {
            String json = file.readString();
            System.out.println(json);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            returnWidget = getWidget(jsonObject, null, cam);
        } catch (ParseException e) {
            Log.d("MenuBuilder", "Error parsing json file");
        } catch (JSONException e) {
            Log.d("MenuBuilder", "Error parsing json file");
        }
        return returnWidget;
    }

    private static Widget getWidget(JSONObject jsonObject, Widget parent, OrthographicCamera cam) throws JSONException {
        Widget returnWidget = null;

        // region Widget Basics
        String widgetType = (String) jsonObject.get(WIDGET_TYPE);
        String name = (String) jsonObject.get(NAME);
        String widthString = (String) jsonObject.get(WIDTH);
        String heightString = (String) jsonObject.get(HEIGHT);
        String background = (String) jsonObject.get(BACKGROUND);
        Texture backgroundTexture;

        float x = (float) (jsonObject.get(X) == null ? 0.0f : Float.parseFloat((String) jsonObject.get(X)));
        float y = (float) (jsonObject.get(Y) == null ? 0.0f : Float.parseFloat((String) jsonObject.get(Y)));
        float width = 0;
        float height = 0;

        if (widgetType == null) widgetType = Widget.WIDGET;
        if (name == null) name = "";
        if (widthString.equals(MATCH_PARENT)) {
            if (parent == null) {
                width = cam.viewportWidth;
            } else {
                width = parent.getWidth();
            }
        } else {
            width = Float.parseFloat(widthString);
        }
        if (heightString.equals(MATCH_PARENT)) {
            if (parent == null) {
                height = cam.viewportHeight;
            } else {
                height = parent.getHeight();
            }
        } else {
            height = Float.parseFloat(heightString);
        }

        if (background.startsWith("#")) {
            backgroundTexture = Drawing.getFilledRectangle(((int) width * (width < 1 ? 100 : 50)),
                    ((int) height * (height < 1 ? 100 : 50)), Color.valueOf(background));
        } else {
            backgroundTexture = new Texture(background);
        }
        // endregion

        //region LinearLayout
        if (widgetType.equals(Widget.LINEAR_LAYOUT)) {
            int orientation = jsonObject.get(ORIENTATION).equals(VERTICAL) ? LinearLayout.VERTICAL_ORIENTATION : LinearLayout.HORIZONTAL_ORIENTATION;
            float spacing = (float) (jsonObject.get(SPACING) == null ? 0 : Float.parseFloat((String) jsonObject.get(SPACING)));
            int alignment = jsonObject.get(ALIGNMENT) != null ? getAlignment((String) jsonObject.get(ALIGNMENT)) : LinearLayout.ALIGNMENT_LEFT;
            returnWidget = new LinearLayout(backgroundTexture, name, width, height, x, y, parent, orientation, cam);

            ((LinearLayout) returnWidget).setSpacing(spacing);
            ((LinearLayout) returnWidget).setAlignment(alignment);

            JSONArray children = (JSONArray) jsonObject.get(CHILDREN);
            if (children != null) {
                for (int i = 0; i < children.size(); i++) {
                    Widget child = getWidget((JSONObject) children.get(i), returnWidget, cam);
                }
            }
        }
        // endregion

        //region Button
        else if (widgetType.equals(Widget.BUTTON)) {
            returnWidget = new Button(backgroundTexture, name, width, height, x, y, cam);

            if (jsonObject.get(HOVER_TEXTURE) != null) {
                ((Button) returnWidget).setHoverTexture(new Texture((String) jsonObject.get(HOVER_TEXTURE)));
            }
            if (jsonObject.get(CLICK_TEXTURE) != null) {
                ((Button) returnWidget).setClickTexture(new Texture((String) jsonObject.get(CLICK_TEXTURE)));
            }
        }
        //endregion

        // region EditText
        else if (widgetType.equals(Widget.EDIT_TEXT)) {
            BitmapFont bitmapFont;
            if (jsonObject.get(FONT) != null) {
                String fontBasic = (String) jsonObject.get(FONT);
                bitmapFont = new BitmapFont(Gdx.files.internal(fontBasic + ".fnt"), new TextureRegion(new Texture(fontBasic + ".png")));
            } else {
                throw new JSONException("No font associated with Edit Text");
            }
            returnWidget = new EditText(backgroundTexture, name, width, height, x, y, cam, bitmapFont);

            if (jsonObject.get(FOCUS) != null) {
                ((EditText) returnWidget).setFocus(Boolean.parseBoolean((String) jsonObject.get(FOCUS)));
            }
            if (jsonObject.get(TEXT) != null) {
                ((EditText) returnWidget).setText((String) jsonObject.get(TEXT));
            }
            if (jsonObject.get(HINT) != null) {
                ((EditText) returnWidget).setText((String) jsonObject.get(HINT));
            }
            if (jsonObject.get(ALIGNMENT) != null) {
                ((EditText) returnWidget).setAlignment(getAlignment((String) jsonObject.get(ALIGNMENT)));
            }
            if (jsonObject.get(BLINK_ON) != null) {
                ((EditText) returnWidget).setBlinkOnTime(Float.parseFloat((String) jsonObject.get(BLINK_ON)));
            }
            if (jsonObject.get(BLINK_OFF) != null) {
                ((EditText) returnWidget).setBlinkOnTime(Float.parseFloat((String) jsonObject.get(BLINK_OFF)));
            }
            if (jsonObject.get(TEXT_COLOR) != null) {
                ((EditText) returnWidget).setTextColor(Color.valueOf((String) jsonObject.get(TEXT_COLOR)));
            }
            if (jsonObject.get(HINT_COLOR) != null) {
                ((EditText) returnWidget).setHintColor(Color.valueOf((String) jsonObject.get(HINT_COLOR)));
            }
            if (jsonObject.get(INPUT_TYPE) != null) {
                ((EditText) returnWidget).setInputType(EditText.parseInputType((String) jsonObject.get(INPUT_TYPE)));
            }
        }
        //endregion

        //region Slider
        else if (widgetType.equals(Widget.SLIDER)) {
            returnWidget = new Slider(backgroundTexture, name, width, height, x, y, cam);

            if (jsonObject.get(VALUE) != null)
                ((Slider) returnWidget).setValue(Float.parseFloat((String) jsonObject.get(VALUE)));
            if (jsonObject.get(SLIDER_WIDTH) != null)
                ((Slider) returnWidget).setSliderWidth(Float.parseFloat((String) jsonObject.get(SLIDER_WIDTH)));
            if (jsonObject.get(SLIDER_HEIGHT) != null)
                ((Slider) returnWidget).setSliderHeight(Float.parseFloat((String) jsonObject.get(SLIDER_HEIGHT)));
            if (jsonObject.get(PADDING) != null)
                ((Slider) returnWidget).setPadding(Float.parseFloat((String) jsonObject.get(PADDING)));
            if (jsonObject.get(BAR_HEIGHT) != null)
                ((Slider) returnWidget).setBarImageHeight(Float.parseFloat((String) jsonObject.get(BAR_HEIGHT)));
            if (jsonObject.get(SLIDER_TEXTURE) != null)
                ((Slider) returnWidget).setSliderImage(new Texture((String) jsonObject.get(SLIDER_TEXTURE)));
            if (jsonObject.get(BAR_TEXTURE) != null)
                ((Slider) returnWidget).setBarImage(new Texture((String) jsonObject.get(BAR_TEXTURE)));
        }
        //endregion


        if (parent != null && parent instanceof Layout) ((Layout) parent).addWidget(returnWidget);

        return returnWidget;
    }

    private static int getAlignment(String alignment) {
        int returnAlignment = LinearLayout.ALIGNMENT_CENTER; // default is left/top >>

        if (alignment.equals(ALIGNMENT_CENTER)) { // less if and or statements = faster computational time
            returnAlignment = LinearLayout.ALIGNMENT_CENTER;
        } else if (alignment.equals(ALIGNMENT_RIGHT) || alignment.equals(ALIGNMENT_BOTTOM)) {
            returnAlignment = LinearLayout.ALIGNMENT_BOTTOM;
        }

        return returnAlignment;
    }
}
