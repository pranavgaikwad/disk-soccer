package com.disksoccer.disksoccermultiplayer.util;

import android.graphics.Color;

import com.disksoccer.disksoccermultiplayer.MainGameActivity;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * @author Pranav
 *         <h1>Resourse helper</h1>
 *         Loads resources viz. audio, images, sprites, buffers, fonts, etc.
 *         Single instance = global entry/exit for shared resources
 */
public class ResourceHelper {
    // single instance
    private static final ResourceHelper instance = new ResourceHelper();

    private Engine engine;
    private BoundCamera camera;
    private VertexBufferObjectManager vbom;
    private MainGameActivity mainGameActivity;
    private WarpClient warpClient;

    // file paths
    private static final String PATH_MAIN_GFX = "gfx/";
    private static final String PATH_MAIN_FONTS = "fonts/";
    // backgrounds
    private static final String BG_SPLASH = "splash.png";
    private static final String BG_FIELD = "field2.png";
    // entities
    private static final String BODY_BALL = "disk.png";
    private static final String BODY_PLAYER = "sprite_player.png";
    private static final String BODY_CONTROL_KNOB = "control_knob.png";
    private static final String BODY_CONTROL_BASE = "control_base.png";
    private static final String BODY_LEFT_GOAL = "left_goal.png";
    private static final String BODY_RIGHT_GOAL = "right_goal.png";
    // buttons
    private static final String BUTTON_PLAY = "play_button.png";
    private static final String BUTTON_OPTIONS = "options_button.png";
    // sprites
    private static final String SPRITE_LOADING = "sprite_loading.png";
    private static final String SPRITE_TITLE_TEXT = "sprite_title_text.png";
    // fonts
    private static final String FONT_HUD = "font_hud.ttf";

    // splash screen resources
    public ITextureRegion splashRegion;
    private BitmapTextureAtlas splashTextureAtlas;

    // menu screen resources
    public ITiledTextureRegion titleRegion;
    public ITextureRegion playButtonRegion; // play button region
    public ITextureRegion optionsButtonRegion; // options button region
    private BuildableBitmapTextureAtlas menuTextureAtlas;

    // Loading Scene resources
    public TiledTextureRegion loadingTextRegion;
    private BuildableBitmapTextureAtlas loadingTextureAtlas;
    private final int ROWS_LOADING_SPRITE = 1;
    private final int COLS_LOADING_SPRITE = 6;
    private final int ROWS_PLAYER_SPRITE = 1;
    private final int COLS_PLAYER_SPRITE = 2;

    // main game scene resources
    public ITiledTextureRegion playerRegion;
    public ITextureRegion gameBgTextureRegion;
    public ITextureRegion ballRegion;
    public ITextureRegion lgoal, rgoal;
    public ITextureRegion controlBaseRegion, controlKnobRegion;
    private BuildableBitmapTextureAtlas gameTextureAtlas;

    // fonts
    public Font fontHUD;

    /**
     * instantiation banned
     */
    private ResourceHelper() {}

    /**
     * @return single instance
     */
    public static ResourceHelper getInstance() {
        return instance;
    }

    /**
     * Initialize game resources
     * @param engine
     * @param camera
     * @param vbom
     * @param mainGameActivity
     * @return
     */
    public ResourceHelper init(Engine engine, BoundCamera camera, VertexBufferObjectManager vbom, MainGameActivity mainGameActivity) {
        this.engine = engine;
        this.camera = camera;
        this.vbom = vbom;
        this.mainGameActivity = mainGameActivity;
        return this;
    }

    private void loadWarpClientResources() {
        // init warp client with api and secret key
        WarpClient.initialize(PropertyHelper.getInstance().getPropertyString(PropertyHelper.KEY_WARP_API, mainGameActivity), PropertyHelper.getInstance().getPropertyString(PropertyHelper.KEY_WARP_SECRET, mainGameActivity));
        try {
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            LogHelper.e(LogHelper.T_EXCEPTION, e.toString());
        }
        warpClient.addConnectionRequestListener(mainGameActivity);
    }

    public void loadSplashResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(PATH_MAIN_GFX);
        splashTextureAtlas = new BitmapTextureAtlas(mainGameActivity.getTextureManager(), 1200, 720, TextureOptions.BILINEAR);
        splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, mainGameActivity, BG_SPLASH, 0, 0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen() {
        splashTextureAtlas.unload();
        splashRegion = null;
    }

    public void loadMenuResources(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(PATH_MAIN_GFX);
        menuTextureAtlas = new BuildableBitmapTextureAtlas(mainGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        titleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuTextureAtlas, mainGameActivity, SPRITE_TITLE_TEXT, 1, 6);
        playButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, mainGameActivity, BUTTON_PLAY);
        optionsButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, mainGameActivity, BUTTON_OPTIONS);
        try
        {
            this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.menuTextureAtlas.load();
        }
        catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e)
        {
            LogHelper.d(LogHelper.T_EXCEPTION, "exception caught in loadMenuResources() : " + e.toString());
        }
    }

    public void loadMenuTextures() {
        menuTextureAtlas.load();
    }

    public void unloadMenuTextures() {
        menuTextureAtlas.unload();
    }

    public void loadLoadingSceneResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(PATH_MAIN_GFX);
        loadingTextureAtlas = new BuildableBitmapTextureAtlas(mainGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        loadingTextRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(loadingTextureAtlas, mainGameActivity, SPRITE_LOADING, COLS_LOADING_SPRITE, ROWS_LOADING_SPRITE);
        try {
            this.loadingTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.loadingTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            LogHelper.d(LogHelper.T_EXCEPTION, "exception in loadLoadingSceneResources() : " + e.toString());
        }
    }

    public void loadGameResources() {
        // create fonts
        FontFactory.setAssetBasePath(PATH_MAIN_FONTS);
        final ITexture fontHUDTexture = new BitmapTextureAtlas(mainGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        fontHUD = FontFactory.createStrokeFromAsset(mainGameActivity.getFontManager(), fontHUDTexture, mainGameActivity.getAssets(), FONT_HUD, 50, true, Color.WHITE, 2, Color.BLACK);
        fontHUD.load();

        // game background textures
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(PATH_MAIN_GFX);
        gameTextureAtlas = new BuildableBitmapTextureAtlas(mainGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
        gameBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BG_FIELD);
        playerRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, mainGameActivity, BODY_PLAYER, COLS_PLAYER_SPRITE, ROWS_PLAYER_SPRITE);
        ballRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BODY_BALL);
        lgoal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BODY_LEFT_GOAL);
        rgoal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BODY_RIGHT_GOAL);
        controlKnobRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BODY_CONTROL_KNOB);
        controlBaseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, mainGameActivity, BODY_CONTROL_BASE);
        try {
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            LogHelper.d(LogHelper.T_EXCEPTION, "exception in loadGameTextures() : " + e.toString());
        }
    }

    public void loadGameTextures() {
        gameTextureAtlas.load();
    }

    public void unloadGameTextures() {
        gameTextureAtlas.unload();
    }

    public WarpClient getWarpClient() {
        return warpClient;
    }

    public BoundCamera getCamera() {
        return camera;
    }

    public Engine getEngine() {
        return engine;
    }

    public MainGameActivity getMainGameActivity() {
        return mainGameActivity;
    }

    public VertexBufferObjectManager getVbom() {
        return vbom;
    }
}
