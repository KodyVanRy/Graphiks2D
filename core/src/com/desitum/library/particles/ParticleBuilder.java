package com.desitum.library.particles;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.desitum.library.drawing.Drawing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleBuilder {
    public static final String PARTICLES_PER_SECOND = "particles_per_second";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String MAKE_SQUARE = "make_square";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String PARTICLE_TEXTURE = "particle_texture";
    public static final String PARTICLE_SETTINGS = "particle_settings";

    /*
    particle settings :
            minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY,
            maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY,
            minRotationAmount, maxRotationAmount, opacity;
     */
    public static final String MIN_PARTICLE_WIDTH = "min_width";
    public static final String MAX_PARTICLE_WIDTH = "max_width";
    public static final String MIN_PARTICLE_HEIGHT = "min_height";
    public static final String MAX_PARTICLE_HEIGHT = "max_height";
    public static final String MIN_PARTICLE_GRAVITY_X = "min_gravity_x";
    public static final String MAX_PARTICLE_GRAVITY_X = "max_gravity_x";
    public static final String MIN_PARTICLE_GRAVITY_Y = "min_gravity_y";
    public static final String MAX_PARTICLE_GRAVITY_Y = "max_gravity_y";
    public static final String MIN_VELOCITY_X = "min_velocity_x";
    public static final String MAX_VELOCITY_X = "max_velocity_x";
    public static final String MIN_VELOCITY_Y = "min_velocity_y";
    public static final String MAX_VELOCITY_Y = "max_velocity_y";
    public static final String MIN_PARTICLE_ROTATION = "min_rotation";
    public static final String MAX_PARTICLE_ROTATION = "max_rotation";
    public static final String OPACITY = "opacity";
    public static final String LIFESPAN = "lifespan";
    public static final String FADE_OUT = "fade_out";

    public static ParticleEmitter buildParticleEmitter(FileHandle file) {
        ParticleEmitter returnEmitter = null;
        try {
            String json = file.readString();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            returnEmitter = getEmitter(jsonObject);
        } catch (ParseException e) {
//            Log.d("MenuBuilder", "Error parsing json file");
        }

        return returnEmitter;
    }

    private static ParticleEmitter getEmitter(JSONObject jsonObject) {
        ParticleEmitter particleEmitter = new ParticleEmitter();

        if (jsonObject.get(X) != null) {
            particleEmitter.setX(Float.parseFloat((String) jsonObject.get(X)));
        }
        if (jsonObject.get(Y) != null) {
            particleEmitter.setY(Float.parseFloat((String) jsonObject.get(Y)));
        }
        if (jsonObject.get(WIDTH) != null) {
            particleEmitter.setWidth(Float.parseFloat((String) jsonObject.get(WIDTH)));
        }
        if (jsonObject.get(HEIGHT) != null) {
            particleEmitter.setHeight(Float.parseFloat((String) jsonObject.get(HEIGHT)));
        }
        if (jsonObject.get(PARTICLES_PER_SECOND) != null) {
            particleEmitter.setParticlesPerSecond(Float.parseFloat((String) jsonObject.get(PARTICLES_PER_SECOND)));
        }
        if (jsonObject.get(PARTICLE_TEXTURE) != null) {
            String textureString = (String) jsonObject.get(PARTICLE_TEXTURE);

            if (textureString.startsWith("#")) {
                particleEmitter.setParticleTexture(Drawing.INSTANCE.getFilledRectangle(50, 50, Color.valueOf(textureString)));
            } else {
                particleEmitter.setParticleTexture(new Texture(textureString));
            }
        }

        JSONArray children = (JSONArray) jsonObject.get(PARTICLE_SETTINGS);
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                particleEmitter.addParticleSettings(getParticleSettings((JSONObject) children.get(i)));
            }
        }

        return particleEmitter;
    }

    private static ParticleSettings getParticleSettings(JSONObject jsonObject) {
        float minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY,
                maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY,
                minRotationAmount, maxRotationAmount, opacity, lifespan;
        boolean fadeOut, square;

        minWidth = 0;
        maxWidth = 0;
        minHeight = 0;
        maxHeight = 0;
        minGravityX = 0;
        maxGravityX = 0;
        minGravityY = 0;
        maxGravityY = 0;
        minVelocityX = 0;
        maxVelocityX = 0;
        minVelocityY = 0;
        maxVelocityY = 0;
        minRotationAmount = 0;
        maxRotationAmount = 0;
        opacity = 0;
        lifespan = 0;
        fadeOut = false;
        square = false;

        if (jsonObject.get(MIN_PARTICLE_WIDTH) != null) {
            minWidth = Float.parseFloat((String) jsonObject.get(MIN_PARTICLE_WIDTH));
        }
        if (jsonObject.get(MAX_PARTICLE_WIDTH) != null) {
            maxWidth = Float.parseFloat((String) jsonObject.get(MAX_PARTICLE_WIDTH));
        }
        if (jsonObject.get(MIN_PARTICLE_HEIGHT) != null) {
            minHeight = Float.parseFloat((String) jsonObject.get(MIN_PARTICLE_HEIGHT));
        }
        if (jsonObject.get(MIN_PARTICLE_HEIGHT) != null) {
            maxHeight = Float.parseFloat((String) jsonObject.get(MAX_PARTICLE_HEIGHT));
        }
        if (jsonObject.get(MIN_PARTICLE_GRAVITY_X) != null) {
            minGravityX = Float.parseFloat((String) jsonObject.get(MIN_PARTICLE_GRAVITY_X));
        }
        if (jsonObject.get(MAX_PARTICLE_GRAVITY_X) != null) {
            maxGravityX = Float.parseFloat((String) jsonObject.get(MAX_PARTICLE_GRAVITY_X));
        }
        if (jsonObject.get(MIN_PARTICLE_GRAVITY_Y) != null) {
            minGravityY = Float.parseFloat((String) jsonObject.get(MIN_PARTICLE_GRAVITY_Y));
        }
        if (jsonObject.get(MAX_PARTICLE_GRAVITY_Y) != null) {
            maxGravityY = Float.parseFloat((String) jsonObject.get(MAX_PARTICLE_GRAVITY_Y));
        }
        if (jsonObject.get(MIN_VELOCITY_X) != null) {
            minVelocityX = Float.parseFloat((String) jsonObject.get(MIN_VELOCITY_X));
        }
        if (jsonObject.get(MAX_VELOCITY_X) != null) {
            maxVelocityX = Float.parseFloat((String) jsonObject.get(MAX_VELOCITY_X));
        }
        if (jsonObject.get(MIN_VELOCITY_Y) != null) {
            minVelocityY = Float.parseFloat((String) jsonObject.get(MIN_VELOCITY_Y));
        }
        if (jsonObject.get(MAX_VELOCITY_Y) != null) {
            maxVelocityY = Float.parseFloat((String) jsonObject.get(MAX_VELOCITY_Y));
        }
        if (jsonObject.get(MIN_PARTICLE_ROTATION) != null) {
            minRotationAmount = Float.parseFloat((String) jsonObject.get(MIN_PARTICLE_ROTATION));
        }
        if (jsonObject.get(MAX_PARTICLE_ROTATION) != null) {
            maxRotationAmount = Float.parseFloat((String) jsonObject.get(MAX_PARTICLE_ROTATION));
        }
        if (jsonObject.get(OPACITY) != null) {
            opacity = Float.parseFloat((String) jsonObject.get(OPACITY));
        }
        if (jsonObject.get(LIFESPAN) != null) {
            lifespan = Float.parseFloat((String) jsonObject.get(LIFESPAN));
        }
        if (jsonObject.get(FADE_OUT) != null) {
            fadeOut = Boolean.parseBoolean((String) jsonObject.get(FADE_OUT));
        }
        if (jsonObject.get(MAKE_SQUARE) != null) {
            square = Boolean.parseBoolean((String) jsonObject.get(MAKE_SQUARE));
        }

        return new ParticleSettings(minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY, maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY, minRotationAmount, maxRotationAmount, opacity, lifespan, fadeOut, square);
    }
}
