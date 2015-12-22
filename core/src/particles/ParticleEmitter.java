package particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kody on 5/25/15.
 * can be used by kody and people in [kody}]
 */
public class ParticleEmitter {

    public static final String MIN_PARTICLES = "minParticles";
    public static final String MAX_PARTICLES = "maxParticles";
    public static final String EMIT_CIRCLE = "emitCircle";
    public static final String EMIT_RADIUS = "emitRadius";
    public static final String EMIT_WIDTH = "emitWidth";
    public static final String EMIT_HEIGHT = "emitHeight";
    public static final String IS_SQUARE = "isSquare";
    public static final String MIN_PARTICLE_WIDTH = "minWidth";
    public static final String MAX_PARTICLE_WIDTH = "maxWidth";
    public static final String MIN_PARTICLE_HEIGHT = "minHeight";
    public static final String MAX_PARTICLE_HEIGHT = "maxHeight";
    public static final String MIN_PARTICLE_ANGLE = "minDegree";
    public static final String MAX_PARTICLE_ANGLE = "maxDegree";
    public static final String MIN_PARTICLE_ROTATION = "minRotation";
    public static final String MAX_PARTICLE_ROTATION = "maxRotation";
    public static final String MIN_PARTICLE_DISTANCE = "minDistance";
    public static final String MAX_PARTICLE_DISTANCE = "maxDistance";
    public static final String MIN_PARTICLE_DURATION = "minDuration";
    public static final String MAX_PARTICLE_DURATION = "maxDuration";
    public static final String TEXTURE_LOCATION = "textureLocation";
    private ArrayList<Particle> particles;
    private ArrayList<Particle> particlesToRemove;
    private float x;
    private float y;

    private boolean isSquare;

    private float width;
    private float height;

    private int maxParticles;
    private int minParticles;

    private float particleMinAngle;
    private float particleMaxAngle;

    private float particleMinWidth;
    private float particleMaxWidth;

    private float particleMinHeight;
    private float particleMaxHeight;

    private float particleMinDistance;
    private float particleMaxDistance;

    private float particleMinDuration;
    private float particleMaxDuration;

    private float particleMinRotation;
    private float particleMaxRotation;

    private boolean emitCircle;
    private float emitRadius;

    private Texture emitterTexture;

    private boolean on;

    public ParticleEmitter(float x, float y, String particleFile) {

        this.particles = new ArrayList<Particle>();
        this.particlesToRemove = new ArrayList<Particle>();

        this.x = x;
        this.y = y;

        this.width = 1;
        this.height = 1;

        this.minParticles = 10;
        this.maxParticles = 50;

        this.particleMinAngle = 0;
        this.particleMaxAngle = 360;

        this.particleMinWidth = 1.0f;
        this.particleMaxWidth = 1.0f;

        this.particleMinHeight = 1.0f;
        this.particleMaxHeight = 1.0f;

        this.particleMinDistance = 1.0f;
        this.particleMaxDistance = 1.0f;

        this.particleMinRotation = 0;
        this.particleMaxRotation = 0;

        this.emitCircle = false;
        this.emitRadius = 1;

        openFile(particleFile);
    }

    private void openFile(String particleFile) {
        try {
            FileHandle file = Gdx.files.internal(particleFile);
            Scanner myScanner = new Scanner(file.reader());
            String line = "";
            while (myScanner.hasNext()) {
                line = myScanner.nextLine();
                if (line.equals("EMITTER")) continue;
                else if (line.equals("PARTICLE")) continue;
                String lineInfo = line.split(":")[0];
                String lineChange = line.split(":")[1];

                if (lineInfo.equals(MIN_PARTICLES)) {
                    this.minParticles = Integer.parseInt(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLES)) {
                    this.maxParticles = Integer.parseInt(lineChange);
                } else if (lineInfo.equals(EMIT_CIRCLE)) {
                    this.emitCircle = Boolean.parseBoolean(lineChange);
                } else if (lineInfo.equals(EMIT_RADIUS)) {
                    this.emitRadius = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(EMIT_WIDTH)) {
                    this.width = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(EMIT_HEIGHT)) {
                    this.height = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(IS_SQUARE)) {
                    this.isSquare = Boolean.parseBoolean(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_WIDTH)) {
                    this.particleMinWidth = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_WIDTH)) {
                    this.particleMaxWidth = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_HEIGHT)) {
                    this.particleMinHeight = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_HEIGHT)) {
                    this.particleMaxHeight = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_ANGLE)) {
                    this.particleMinAngle = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_ANGLE)) {
                    this.particleMaxAngle = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_DISTANCE)) {
                    this.particleMinDistance = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_DISTANCE)) {
                    this.particleMaxDistance = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_DURATION)) {
                    this.particleMinDuration = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_DURATION)) {
                    this.particleMaxDuration = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MIN_PARTICLE_ROTATION)) {
                    this.particleMinRotation = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(MAX_PARTICLE_ROTATION)) {
                    this.particleMaxRotation = Float.parseFloat(lineChange);
                } else if (lineInfo.equals(TEXTURE_LOCATION)) {
                    this.emitterTexture = new Texture(lineChange);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(float delta) {
        for (Particle particle : particles) {
            particle.update(delta);
        }

        for (Particle particle : particlesToRemove) {
            particles.remove(particle);
        }

        if ((particles.size() < maxParticles) && on) {
            for (int i = particles.size(); i < maxParticles; i++) {
                particles.add(createNewParticle());
            }
        }
    }

    protected Particle createNewParticle() {
        Particle returnParticle = null;
        ParticleSettings particleSettings = new ParticleSettings(this);
        returnParticle = new Particle(emitterTexture, this, particleSettings);
        return returnParticle;
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void draw(SpriteBatch batch) {
        for (Particle particle : particles) {
            particle.draw(batch);
        }
    }

    public void turnOn() {
        on = true;
    }

    public void turnOff() {
        on = false;
    }

    public void toggleOnOff() {
        on = !on;
    }
//EMITTER
//minParticles:10
//maxParticles:50
//emitCircle:false
//emitRadius:1.0
//emitWidth:1.0
//emitHeight:1.0
//PARTICLE
//isSquare:false
//minWidth:1.0
//minHeight:1.0
//maxWidth:1.0
//maxHeight:1.0
//minDegree:0.0
//maxDegree:360.0
//minDistance:1
//maxDistance:1
//accelerateX:false
//accelerateY:false

    public void remove(Particle p) {
        this.particlesToRemove.add(p);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getMaxParticles() {
        return maxParticles;
    }

    public int getMinParticles() {
        return minParticles;
    }

    public float getParticleMinAngle() {
        return particleMinAngle;
    }

    public float getParticleMaxAngle() {
        return particleMaxAngle;
    }

    public float getParticleMinWidth() {
        return particleMinWidth;
    }

    public float getParticleMaxWidth() {
        return particleMaxWidth;
    }

    public float getParticleMinHeight() {
        return particleMinHeight;
    }

    public float getParticleMaxHeight() {
        return particleMaxHeight;
    }

    public boolean isEmitCircle() {
        return emitCircle;
    }

    public float getEmitRadius() {
        return emitRadius;
    }

    public float getParticleMinDistance() {
        return particleMinDistance;
    }

    public float getParticleMaxDistance() {
        return particleMaxDistance;
    }

    public float getParticleMinDuration() {
        return particleMinDuration;
    }

    public float getParticleMaxDuration() {
        return particleMaxDuration;
    }

    public boolean isOn() {
        return on;
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
    }

    public float getParticleMinRotation() {
        return particleMinRotation;
    }

    public float getParticleMaxRotation() {
        return particleMaxRotation;
    }
}
