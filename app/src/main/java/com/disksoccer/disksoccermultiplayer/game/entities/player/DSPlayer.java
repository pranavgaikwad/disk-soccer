package com.disksoccer.disksoccermultiplayer.game.entities.player;

import android.os.Handler;
import android.os.Looper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.disksoccer.disksoccermultiplayer.game.entities.DSBaseEntity;
import com.disksoccer.disksoccermultiplayer.game.entities.ball.IDSBallStateListener;
import com.disksoccer.disksoccermultiplayer.game.util.DSEntityEngineConnector;
import com.disksoccer.disksoccermultiplayer.wrappers.XY;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

/**
 * @author Pranav
 *         <h1>Describes a player</h1>
 */
public class DSPlayer extends DSBaseEntity implements IDSBallStateListener {
    // who is playing
    private String name;
    private String userName;
    // is it a computer or human
    private PlayerType type;
    // listening player state
    private IDSPlayerStateListener l;

    // what are player's physical properties
    private static final FixtureDef fixtureDef = PhysicsFactory.createFixtureDef(1f, 0.0f, 0.5f);

    public static final String NAME_OFFLINE = "<offline>";
    public static final String DATA_PLAYER_BODY = "Player";

    public static final int PLAYER_TILE_INDEX_ON_FORCE_REMOVED = 0;
    public static final int PLAYER_TILE_INDEX_ON_FORCE_APPLIED = 1;
    private static final float MASS_PLAYER = 50;
    private static final long TIMEOUT_PLAYER_FORCE = 400;

    /**
     * What type of player is it
     */
    public enum PlayerType {
        PLAYER_HUMAN, PLAYER_COMP
    }

    public DSPlayer(DSEntityEngineConnector connector, String name, String userName, PlayerType type) {
        super(connector);
        this.name = name;
        this.userName = userName;
        this.type = type;
        setup();
    }

    /**
     *
     * @param connector
     * @param name
     * @param userName
     */
    public DSPlayer(DSEntityEngineConnector connector, String name, String userName, PlayerType type, IDSPlayerStateListener l) {
        super(connector);
        this.name = name;
        this.userName = userName;
        this.type = type;
        this.l = l;
        setup();
    }

    @Override
    public void setup() {
        animatedSprite = new AnimatedSprite(0, 0, connector.getResourceHelper().playerRegion, connector.getVbom());
        // set tile to no force applied (i.e. 0)
        animatedSprite.setCurrentTileIndex(PLAYER_TILE_INDEX_ON_FORCE_REMOVED);
        body = PhysicsFactory.createCircleBody(connector.getPhysicsWorld(), animatedSprite, BodyDef.BodyType.DynamicBody, fixtureDef);
        connector.getPhysicsWorld().registerPhysicsConnector(new PhysicsConnector(animatedSprite, body, true, true));
        MassData md = new MassData();
        md.mass = MASS_PLAYER;
        body.setMassData(md);
        body.setUserData(DATA_PLAYER_BODY);
    }

    public void hit(Body hitWhom, float impulseX, float impulseY, float pointX, float pointY) {
        hitWhom.applyLinearImpulse(impulseX, impulseY, pointX, pointY);
        animatedSprite.setCurrentTileIndex(PLAYER_TILE_INDEX_ON_FORCE_APPLIED);
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animatedSprite.setCurrentTileIndex(PLAYER_TILE_INDEX_ON_FORCE_REMOVED);
                        handler.removeCallbacks(this);
                        Looper.myLooper().quit();
                    }
                }, TIMEOUT_PLAYER_FORCE);
                Looper.loop();
            }
        };
        thread.start();
    }

    @Override
    public void safeDestroy() {
        name = null;
        userName = null;
        if(animatedSprite != null) {animatedSprite.detachSelf(); animatedSprite.dispose();}
        body = null;
        connector = null;
        animatedSprite = null;
        sprite = null;
        type = null;
        location = null;
        lastLocation = null;
        l = null;
    }

    // update and notify everyone that position has been changed
    @Override
    public void onUpdate(float pSecondsElapsed) {
        if(animatedSprite != null && body != null) {
            lastLocation = location;
            location.setX(animatedSprite.getX());
            location.setY(animatedSprite.getY());
            if((location != lastLocation) && (l != null)) l.onPlayerLocationChanged(this, location);
        }
    }

    @Override
    public boolean equals(Object o) {
        DSPlayer p = (DSPlayer) o;
        if(p.getUserName().equals(this.userName)) return true;
        return super.equals(o);
    }

    @Override
    public void reset() {

    }

    @Override
    public void onBallLocationChanged(XY location) {

    }

    // username
    public String getName() {return name;}
    public String getUserName() {return userName;}
}
