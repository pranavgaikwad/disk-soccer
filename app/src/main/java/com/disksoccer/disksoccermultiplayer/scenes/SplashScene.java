package com.disksoccer.disksoccermultiplayer.scenes;

import com.disksoccer.disksoccermultiplayer.util.LogHelper;
import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * @author Pranav
 *         <h1>Splash Scene</h1>
 *         Displays splash screen
 */
public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene() {
        LogHelper.d(LogHelper.T_GENERAL, "created splash scene");
        splash = new Sprite(0, 0, ResourceHelper.getInstance().splashRegion, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // splash.setScale(1.5f);
        splash.setPosition(400, 240);
        attachChild(splash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_SPLASH;
    }
}
