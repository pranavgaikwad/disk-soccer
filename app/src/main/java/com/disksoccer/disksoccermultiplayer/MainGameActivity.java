package com.disksoccer.disksoccermultiplayer;

import android.view.KeyEvent;

import com.disksoccer.disksoccermultiplayer.scenes.SceneManager;
import com.disksoccer.disksoccermultiplayer.util.PropertyHelper;
import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;
import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

public class MainGameActivity extends BaseGameActivity implements ConnectionRequestListener {
    // Camera defines the part of the entire scene which is currently visible to the player
    // BoundCamera provides ways to limit boundaries
    private BoundCamera camera;
    // FPS for game engine
    private int FPS = 60;
    private ResourceHelper resourceHelper;

    @Override
    public EngineOptions onCreateEngineOptions() {
        // FPS value read from file
        FPS = PropertyHelper.getInstance().getPropertyInt(PropertyHelper.KEY_FPS_ENGINE, this);
        camera = new BoundCamera(0, 0, ResolutionHelper.SCREEN_WIDTH, ResolutionHelper.SCREEN_HEIGHT);
        // TODO: resolution policy currently set to fill !
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        resourceHelper = ResourceHelper.getInstance();
        // Resources need to be 'init' before used
        resourceHelper.init(mEngine, camera, getVertexBufferObjectManager(), this);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        return new LimitedFPSEngine(pEngineOptions, FPS);
    }

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {

    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {

    }

    @Override
    public void onInitUDPDone(byte b) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //System.exit(0); // kill activity on destroy
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }
}
