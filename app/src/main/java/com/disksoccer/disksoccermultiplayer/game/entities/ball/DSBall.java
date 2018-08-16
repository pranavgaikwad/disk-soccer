package com.disksoccer.disksoccermultiplayer.game.entities.ball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.disksoccer.disksoccermultiplayer.game.entities.DSBaseEntity;
import com.disksoccer.disksoccermultiplayer.game.entities.wrappers.MagicAreaStatus;
import com.disksoccer.disksoccermultiplayer.game.util.DSEntityEngineConnector;
import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;
import com.disksoccer.disksoccermultiplayer.wrappers.XY;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Pranav
 *         <h1>Describes Ball</h1>
 */
public class DSBall extends DSBaseEntity {
    // to whom location updates are to be sent
    private List <IDSBallStateListener> observers;

    // what are player's physical properties
    private static final FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1f, 0.0f, 0.5f);
    private static final FixtureDef fieldFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 1.5f);

    private static final int MASS_BALL = 3;
    // friction force between ball and field
    private static final float FORCE_MAX_FRICTION_FIELD_BALL = 4.0f;
    private static final float MAGIC_AREA_IMPULSE = 5;

    public static final String DATA_BALL_BODY = "Ball";

    /**
     * @param connector
     */
    public DSBall(DSEntityEngineConnector connector) {
        super(connector);
        observers = new ArrayList<>();
        setup();
    }

    /**
     *
     * @param observer
     */
    public void attachObserver(IDSBallStateListener observer) {
        observers.add(observer);
    }

    /**
     *
     * @param observer
     */
    public void detachObserver(IDSBallStateListener observer) {
        Iterator<IDSBallStateListener> it = observers.iterator();
        int i = 0;
        while(it.hasNext()) {
            IDSBallStateListener obs = it.next();
            if(obs.equals(observer)) observers.remove(i);
            i++;
        }
    }

    /**
     *
     * @param location
     */
    public void notifyAllObservers(XY location) {
        for(IDSBallStateListener observer : observers) {
            observer.onBallLocationChanged(location);
        }
    }

    @Override
    public void setup() {
        sprite = new Sprite(0, 0, connector.getResourceHelper().ballRegion, connector.getVbom());
        sprite.setPosition(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CENTER_HEIGHT);
        body = PhysicsFactory.createCircleBody(connector.getPhysicsWorld(), sprite, BodyDef.BodyType.DynamicBody, fixtureDef);
        connector.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));
        MassData md = new MassData();
        md.mass = MASS_BALL;
        body.setMassData(md);
        body.setUserData(DATA_BALL_BODY);

        // friction joint enables friction betweeen
        // ball and field making it more realistic
        Body fieldBody;
        fieldBody = PhysicsFactory.createBoxBody(connector.getPhysicsWorld(), -1000, -1000, 0, 0, BodyDef.BodyType.StaticBody, fieldFixtureDef);
        FrictionJointDef fdef = new FrictionJointDef();
        fdef.bodyA = body;
        fdef.bodyB = fieldBody;
        fdef.maxForce = FORCE_MAX_FRICTION_FIELD_BALL;
        fdef.collideConnected = false;
        FrictionJoint fieldBallJoint = (FrictionJoint) connector.getPhysicsWorld().createJoint(fdef);
    }

    @Override
    public void safeDestroy() {
        sprite.detachSelf();
        sprite.dispose();
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if(body != null && sprite != null) {
            lastLocation = location;
            location.X = sprite.getX();
            location.Y = sprite.getY();
            if(lastLocation != location) notifyAllObservers(location);
            // if(isMagicAreaEntered().entered) body.applyLinearImpulse();
        }
    }

    /**
     * This method checks if ball has entered the magic area
     * or not. This method doesn't take any parameters to determine
     * area coordinates. But, it calculates them on it's own. Because
     * if ball size changes, magic area coordinates need to be changed
     * Thus, this method should adapt to any entity size
     * @return status object
     */
    private MagicAreaStatus isMagicAreaEntered() {
        MagicAreaStatus ms = new MagicAreaStatus();
        ms.entered = false;
        float max_x_allowed = ResolutionHelper.CAMERA_X_MAX - sprite.getWidth();
        float min_x_allowed = ResolutionHelper.CAMERA_X_MIN + sprite.getWidth();
        float max_y_allowed = ResolutionHelper.CAMERA_Y_MAX - sprite.getHeight();
        float min_y_allowed = ResolutionHelper.CAMERA_Y_MIN + sprite.getHeight();
        if(sprite.getX() < min_x_allowed && sprite.getY() < min_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.LL;
        }
        if(sprite.getX() > max_x_allowed && sprite.getY() < min_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.LR;
        }
        if(sprite.getX() < min_x_allowed && sprite.getY() > max_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.UL;
        }
        if(sprite.getX() > max_x_allowed && sprite.getY() > max_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.UR;
        }
        return ms;
    }

    /**
     *
     * @param type
     */
    private void applyMagicForce(MagicAreaStatus.MagicAreaType type) {
        switch (type) {
            case UL:
            case LL:
                body.applyLinearImpulse(MAGIC_AREA_IMPULSE, 0, sprite.getX(), sprite.getY());
                break;
            case LR:
            case UR:
                body.applyLinearImpulse(MAGIC_AREA_IMPULSE*-1, 0, sprite.getX(), sprite.getY());
                break;
        }
    }


    @Override
    public void reset() {

    }
}
