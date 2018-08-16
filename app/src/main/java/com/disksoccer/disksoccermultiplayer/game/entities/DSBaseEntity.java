package com.disksoccer.disksoccermultiplayer.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.disksoccer.disksoccermultiplayer.game.util.DSEntityEngineConnector;
import com.disksoccer.disksoccermultiplayer.wrappers.XY;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;

/**
 * @author Pranav
 *         <h1>Base class for entity</h1>
 *         Each object associated with the game is referred to as entity
 *         This forms a base class for entity
 */
public abstract class DSBaseEntity implements IUpdateHandler{
    // connector for engine and entity
    protected DSEntityEngineConnector connector;
    // sprite (may or may not be present)
    protected Sprite sprite = null;
    // animated sprite (may or may not be applicable)
    protected AnimatedSprite animatedSprite = null;
    // body
    protected Body body = null;
    // every entity must not have an active location
    protected XY location, lastLocation;

    /**
     *
     * @param connector
     */
    public DSBaseEntity(DSEntityEngineConnector connector) {
        this.connector = connector;
        this.location = new XY(0, 0);
        this.lastLocation = location;
    }

    /**
     * Enforce setup on every object creation
     */
    public abstract void setup();

    /**
     * Safely destroys entity without affecting game play
     */
    public abstract void safeDestroy();

    // getters
    public AnimatedSprite getAnimatedSprite() {return animatedSprite;}
    public Body getBody() {return body;}
    public Sprite getSprite() {return sprite;}
    public XY getLocation() {return location;}
    public DSEntityEngineConnector getConnector() {return connector;}
}
