package com.disksoccer.disksoccermultiplayer.scenes;

import android.os.Handler;
import android.os.Looper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.disksoccer.disksoccermultiplayer.util.LogHelper;
import com.disksoccer.disksoccermultiplayer.util.PropertyHelper;
import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;
import com.disksoccer.disksoccermultiplayer.game.entities.wrappers.MagicAreaStatus;
import com.disksoccer.disksoccermultiplayer.wrappers.PhysicsEditorShapeLibrary;
import com.disksoccer.disksoccermultiplayer.wrappers.XY;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * @author Pranav
 *         <h1>Main Game Scene</h1>
 */
public class GameScene extends BaseScene implements IOnSceneTouchListener {
    private HUD hud;
    private Text scoreText;
    private PhysicsWorld physicsWorld;
    private AnalogOnScreenControl analogOnScreenControl;
    private PhysicsEditorShapeLibrary pEditor;

    private Sprite field;
    private Sprite ball;
    private Sprite leftGoalPost, rightGoalPost;
    private AnimatedSprite player;

    private Body playerBody;
    private Body ballBody;
    private Body leftGoalPostBody, rightGoalPostBody;

    private static final String PATH_FIELD_BODIES = "bodies/field_bodies.xml";
    private static final String BODY_ID_LG = "lgoal";
    private static final String BODY_ID_RG = "rgoal";

    private static final int TILE_PLAYER_POWER_APPLIED = 1;
    private static final int TILE_PLAYER_POWER_REVOKED = 0;

    private static final String DATA_BALL = "Ball";
    private static final String DATA_PLAYER = "Player";
    private static final String DATA_WALL_ROOF = "Roof";
    private static final String DATA_WALL_GROUND = "Ground";
    private static final String DATA_WALL_LEFT = "Left";
    private static final String DATA_WALL_RIGHT = "Right";
    private static final String DATA_LEFT_GP = "Left GP";
    private static final String DATA_RIGHT_GP = "Right GP";

    private static final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.0f, 0.5f);
    private static final FixtureDef ballFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.3f, 1.5f);
    private static final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
    private static final FixtureDef fieldFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 1.5f);

    // how much speed is lost after colliding with the wall (40% currently)
    private static final float wallDampeningFactor = 0.6f;
    // how much impulse is applied when ball enters magic area
    private static final float magicAreaImpulse = 5;
    // friction force between ball and field
    private static final float fieldBallFrictionMaxForce = 4.0f;
    // how fast player moves
    private static final float playerVelocityMultiplier = 5;
    // how heavy player and ball are
    private static final float playerMass = 30;
    private static final float ballMass = 2;
    // how hard does player hit the ball
    private static final float playerForceMultiplier = 0.35f;
    // for how long player can hit the ball once control is pressed
    private static final long forceTimeout = 300;
    // range for player power
    private static final float rangePlayerPower = 2;

    // offset distance for on screen control from bottom left corner
    private static final int OFFSET_CONTROL = 25;
    // offset distance for goal from left-right boundaries
    private static final int OFFSET_GOAL_POST = 10;

    // FPS in fallback mode, when property file value is not retrieved
    private int FPS_PHYSICS_WORLD = 60;

    @Override
    public void createScene() {
        // re-load FPS from property files
        FPS_PHYSICS_WORLD = PropertyHelper.getInstance().getPropertyInt(PropertyHelper.KEY_FPS_PHYSICS, activity);

        setOnSceneTouchListener(this);

        createPhysics();
        createBackground();
        createGoalPosts();
        createHUD();
        createPlayer();
        createBall();

        registerUpdateHandler(physicsWorld);
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().reCreateMenuScene();
    }

    @Override
    public void disposeScene() {
        field.detachSelf();
        field.dispose();
        player.detachSelf();
        player.dispose();
        scoreText.detachSelf();
        scoreText.dispose();
        hud.detachSelf();
        hud.dispose();
        camera.setHUD(null);
        camera.setCenter(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CENTER_HEIGHT);
        camera.setBoundsEnabled(false);
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_GAME;
    }

    private void createBackground() {
        field = new Sprite(0, 0, resourceHelper.gameBgTextureRegion, vbom);
        field.setPosition(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CENTER_HEIGHT);
        camera.setBounds(ResolutionHelper.CAMERA_X_MIN, ResolutionHelper.CAMERA_Y_MIN, ResolutionHelper.CAMERA_X_MAX, ResolutionHelper.CAMERA_Y_MAX);
        camera.setBoundsEnabled(true);
        setTouchAreaBindingOnActionDownEnabled(true);
        attachChild(field);
    }

    private void createHUD() {
        hud = new HUD();

        scoreText = new Text(ResolutionHelper.CENTER_WIDTH, 420, resourceHelper.fontHUD, "0 : 0", new TextOptions(HorizontalAlign.CENTER), vbom);
        scoreText.setAnchorCenter(0, 0);
        scoreText.setText("0 : 0");
        hud.attachChild(scoreText);

        camera.setHUD(hud);
    }

    private void createGoalPosts() {
        float offset_left_goal = resourceHelper.lgoal.getWidth() / 2 + OFFSET_GOAL_POST;
        float offset_right_goal = resourceHelper.rgoal.getWidth() / 2 + OFFSET_GOAL_POST;
        leftGoalPost = new Sprite(0, 0, resourceHelper.lgoal, vbom);
        rightGoalPost = new Sprite(0, 0, resourceHelper.rgoal, vbom);
        leftGoalPost.setPosition(ResolutionHelper.CAMERA_X_MIN + offset_left_goal, ResolutionHelper.CENTER_HEIGHT);
        rightGoalPost.setPosition(ResolutionHelper.CAMERA_X_MAX - offset_right_goal, ResolutionHelper.CENTER_HEIGHT);

        initiatePhysicsEditorExtension();
        rightGoalPostBody = pEditor.createBody(BODY_ID_RG, rightGoalPost, physicsWorld);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(rightGoalPost, rightGoalPostBody, true, true));
        leftGoalPostBody = pEditor.createBody(BODY_ID_LG, leftGoalPost, physicsWorld);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftGoalPost, leftGoalPostBody, true, true));
        leftGoalPostBody.setUserData(DATA_LEFT_GP);
        rightGoalPostBody.setUserData(DATA_RIGHT_GP);
        attachMultipleChilds(leftGoalPost, rightGoalPost);
    }

    /**
     * Initiates physics editor extension loader
     * Sets its path to bodies xml file
     */
    private void initiatePhysicsEditorExtension() {
        this.pEditor = new PhysicsEditorShapeLibrary();
        pEditor.open(activity, PATH_FIELD_BODIES);
    }

    private void createPlayer() {
        player = new AnimatedSprite(0, 0, resourceHelper.playerRegion, vbom);
        player.setCurrentTileIndex(TILE_PLAYER_POWER_REVOKED);
        player.setPosition(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CENTER_HEIGHT);
        playerBody = PhysicsFactory.createCircleBody(physicsWorld, player, BodyDef.BodyType.DynamicBody, playerFixtureDef);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody, true, true));
        MassData md = new MassData();
        md.mass = playerMass;
        playerBody.setMassData(md);
        playerBody.setUserData(DATA_PLAYER);
        attachChild(player);
        analogOnScreenControl = new AnalogOnScreenControl(
                resourceHelper.controlBaseRegion.getWidth() / 2 + OFFSET_CONTROL,
                resourceHelper.controlBaseRegion.getHeight() / 2 + OFFSET_CONTROL,
                camera,
                resourceHelper.controlBaseRegion,
                resourceHelper.controlKnobRegion,
                0.1f,
                200,
                vbom,
                new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
                    @Override
                    public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {

                    }

                    @Override
                    public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY) {
                        playerBody.setLinearVelocity(pValueX * playerVelocityMultiplier, pValueY * playerVelocityMultiplier);
                       // validateBoundary(player);
                    }
                }
        );
        setChildScene(analogOnScreenControl);
        camera.setChaseEntity(player);
    }

    private void createBall() {
        ball = new Sprite(0, 0, resourceHelper.ballRegion, vbom);
        ball.setPosition(ResolutionHelper.CENTER_WIDTH + 50, ResolutionHelper.CENTER_HEIGHT + 50);
        ballBody = PhysicsFactory.createCircleBody(physicsWorld, ball, BodyDef.BodyType.DynamicBody, ballFixtureDef);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, ballBody, true, true));
        MassData md = new MassData();
        md.mass = ballMass;
        ballBody.setMassData(md);
        ballBody.setUserData(DATA_BALL);
        attachChild(ball);

        // friction joint enables friction betweeen
        // ball and field making it more realistic
        Body fieldBody;
        fieldBody = PhysicsFactory.createBoxBody(physicsWorld, -1000, -1000, 0, 0, BodyDef.BodyType.StaticBody, fieldFixtureDef);
        FrictionJointDef fdef = new FrictionJointDef();
        fdef.bodyA = ballBody;
        fdef.bodyB = fieldBody;
        fdef.maxForce = fieldBallFrictionMaxForce;
        fdef.collideConnected = false;
        FrictionJoint fieldBallJoint = (FrictionJoint) physicsWorld.createJoint(fdef);
    }

    /**
     *
     * @param type
     */
    private void applyMagicForce(MagicAreaStatus.MagicAreaType type) {
        switch (type) {
            case UL:
            case LL:
                ballBody.applyLinearImpulse(magicAreaImpulse, 0, ball.getX(), ball.getY());
                break;
            case LR:
            case UR:
                ballBody.applyLinearImpulse(magicAreaImpulse*-1, 0, ball.getX(), ball.getY());
                break;
        }
    }

    /**
     * Creates physics world, adds borders
     */
    private void createPhysics() {
        // no gravity field
        physicsWorld = new FixedStepPhysicsWorld(FPS_PHYSICS_WORLD, new Vector2(0, 0), false) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                if(ball != null && ballBody != null) {
                    MagicAreaStatus status = isMagicAreaEntered();
                    if(status.entered)
                        applyMagicForce(status.whichArea);
                }
            }
        };
        physicsWorld.setContactListener(createContactListeners());

        final Rectangle ground = new Rectangle(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CAMERA_Y_MIN, ResolutionHelper.FIELD_WIDTH, 0.5f, vbom);
        final Rectangle roof = new Rectangle(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CAMERA_Y_MAX, ResolutionHelper.FIELD_WIDTH, 0.5f, vbom);
        final Rectangle left = new Rectangle(ResolutionHelper.CAMERA_X_MIN, ResolutionHelper.CENTER_HEIGHT, 0.5f, ResolutionHelper.FIELD_HEIGHT, vbom);
        final Rectangle right = new Rectangle(ResolutionHelper.CAMERA_X_MAX, ResolutionHelper.CENTER_HEIGHT, 0.5f, ResolutionHelper.FIELD_HEIGHT, vbom);

        // density, elasticity, friction for wall
        Body b;
        b = PhysicsFactory.createBoxBody(physicsWorld, ground, BodyDef.BodyType.StaticBody, wallFixtureDef);
        b.setUserData(DATA_WALL_GROUND);
        b = PhysicsFactory.createBoxBody(physicsWorld, roof, BodyDef.BodyType.StaticBody, wallFixtureDef);
        b.setUserData(DATA_WALL_ROOF);
        b = PhysicsFactory.createBoxBody(physicsWorld, left, BodyDef.BodyType.StaticBody, wallFixtureDef);
        b.setUserData(DATA_WALL_LEFT);
        b = PhysicsFactory.createBoxBody(physicsWorld, right, BodyDef.BodyType.StaticBody, wallFixtureDef);
        b.setUserData(DATA_WALL_RIGHT);

        attachMultipleChilds(ground, roof, left, right);
    }

    /**
     * Native attach child method modified to attach multiple entities
     * @param entities what is to be attached
     */
    private void attachMultipleChilds(IEntity ...entities) {
        for(IEntity entity : entities) {
            attachChild(entity);
        }
    }

    /**
     * Handles contact between two entities
     * @return
     */
    private ContactListener createContactListeners() {
        ContactListener contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                final Fixture a = contact.getFixtureA();
                final Fixture b = contact.getFixtureB();

                Body aBody = a.getBody(), bBody = b.getBody();

                setupDiskReboundVelocity(aBody, bBody, DATA_WALL_ROOF);
                setupDiskReboundVelocity(aBody, bBody, DATA_WALL_GROUND);
                setupDiskReboundVelocity(aBody, bBody, DATA_WALL_LEFT);
                setupDiskReboundVelocity(aBody, bBody, DATA_WALL_RIGHT);
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };
        return contactListener;
    }

    /**
     * After contact between ball and one of the boundaries, the ball
     * is set to rebound with certain velocity. This method takes care
     * of setting up such environment on the walls that are specified
     * using body data. It doesn't itself applies force rather it just
     * simplifies multiple condition checks in single method call.
     * @param a object b1
     * @param b object b2
     * @param whichWallData specifies which wall is hit
     */
    private void setupDiskReboundVelocity(Body a, Body b, String whichWallData) {
        if(hasCollisionOccurred(a, b, DATA_BALL, whichWallData)) {
            if(a.getUserData().equals(DATA_BALL)) applyDiskReboundVelocity(whichWallData);
            else applyDiskReboundVelocity(whichWallData);
        }
    }

    /**
     * After contact between ball and one of the boundaries, the ball
     * is set to rebound with certain velocity. This method takes care
     * of setting up such environment on all the walls.
     * It sets rebound velocity for the ball
     * @param whichWall specifies which wall is in contact with the ball
     */
    private void applyDiskReboundVelocity(String whichWall) {
        Vector2 diskVelocity;
        switch (whichWall) {
            case DATA_WALL_GROUND:
            case DATA_WALL_ROOF:
                diskVelocity = ballBody.getLinearVelocity();
                ballBody.setLinearVelocity(diskVelocity.x * wallDampeningFactor, diskVelocity.y * -1 * wallDampeningFactor);
                break;
            case DATA_WALL_LEFT:
            case DATA_WALL_RIGHT:
                diskVelocity = ballBody.getLinearVelocity();
                ballBody.setLinearVelocity(diskVelocity.x * -1 * wallDampeningFactor, diskVelocity.y * wallDampeningFactor);
                break;
        }
    }

    /**
     * Checks if two bodies have collided with each other
     * Note that this method doesn't <b>LISTEN</b> for
     * collisions. It is rather called from contact listener.
     * It simplifies two condition checks in single method call
     * @param a body 1
     * @param b body 2
     * @param aData identifier for body 1
     * @param bData identifier for body 2
     * @return
     */
    private boolean hasCollisionOccurred(Body a, Body b, String aData, String bData) {
        // when collision occurs between a and b,
        // we have to check if a collides with b,
        // or b collides with a
        if(a.getUserData().equals(aData) && b.getUserData().equals(bData)) {
            return true;
        }
        if(a.getUserData().equals(bData) && b.getUserData().equals(aData)) {
            return true;
        }
        return false;
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
        float max_x_allowed = ResolutionHelper.CAMERA_X_MAX - ball.getWidth();
        float min_x_allowed = ResolutionHelper.CAMERA_X_MIN + ball.getWidth();
        float max_y_allowed = ResolutionHelper.CAMERA_Y_MAX - ball.getHeight();
        float min_y_allowed = ResolutionHelper.CAMERA_Y_MIN + ball.getHeight();
        if(ball.getX() < min_x_allowed && ball.getY() < min_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.LL;
        }
        if(ball.getX() > max_x_allowed && ball.getY() < min_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.LR;
        }
        if(ball.getX() < min_x_allowed && ball.getY() > max_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.UL;
        }
        if(ball.getX() > max_x_allowed && ball.getY() > max_y_allowed) {
            ms.entered = true;
            ms.whichArea = MagicAreaStatus.MagicAreaType.UR;
        }
        return ms;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        switch(pSceneTouchEvent.getAction()) {
            case TouchEvent.ACTION_DOWN:
                player.setCurrentTileIndex(TILE_PLAYER_POWER_APPLIED);
                Thread thread = new Thread() {
                    public void run() {
                        Looper.prepare();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.setCurrentTileIndex(TILE_PLAYER_POWER_REVOKED);
                                handler.removeCallbacks(this);
                                Looper.myLooper().quit();
                            }
                        }, forceTimeout);
                        Looper.loop();
                    }
                };
                if(isBallInPowerRange()) applyPlayerForce();
                thread.start();
                break;

            case TouchEvent.ACTION_UP:
                player.setCurrentTileIndex(TILE_PLAYER_POWER_REVOKED);
                break;
        }
        return false;
    }

    /**
     * Applies force to the ball according to player location
     */
    private void applyPlayerForce() {
        XY ballXY = new XY(ball.getX(), ball.getY());
        XY playerXY = new XY(player.getX(), player.getY());
        XY distanceVector = playerXY.getVectorDistanceFrom(ballXY);
        ballBody.applyLinearImpulse(distanceVector.X * playerForceMultiplier, distanceVector.Y * playerForceMultiplier,
                ball.getX(), ball.getY());
    }

    /**
     * Checks if the ball is in range for
     * player's power to take effect
     * @return
     */
    private boolean isBallInPowerRange() {
        float max_distance_allowed = rangePlayerPower + player.getWidth() / 2 + ball.getWidth() / 2;
        LogHelper.d(LogHelper.T_GENERAL, String.valueOf(max_distance_allowed) + " ...allowed");
        LogHelper.d(LogHelper.T_GENERAL, String.valueOf(getDistanceBetweenSprites(player, ball)) + " ...actual");
        if(getDistanceBetweenSprites(player, ball) <= max_distance_allowed)
            return true;
        return false;
    }

    /**
     * Calculates and returns center to center distance
     * between two sprites
     * @return float value of distance
     */
    private float getDistanceBetweenSprites(Sprite a, Sprite b) {
        XY positionA = new XY(a.getX(), a.getY());
        XY positionB = new XY(b.getX(), b.getY());
        return positionA.getDistanceFrom(positionB);
    }
}
