package com.disksoccer.disksoccermultiplayer.scenes;

import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;
import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;

import org.andengine.entity.sprite.AnimatedSprite;

/**
 * @author Pranav
 *         <h1>Filler Loading Scene</h1>
 *         This class describes the scene that is to be used while resources are loaded before
 *         the main game begins.
 *         It loads during transition from menu to game scene and from game scene to menu scene. 
 */
public class LoadingScene extends BaseScene {
    private AnimatedSprite loadingText;

    @Override
    public void createScene() {
        loadingText = new AnimatedSprite(0, 0, ResourceHelper.getInstance().loadingTextRegion, vbom);
        loadingText.setPosition(ResolutionHelper.SCREEN_WIDTH / 2, ResolutionHelper.SCREEN_HEIGHT / 2);
        loadingText.animate(200);
        attachChild(loadingText);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_LOADING;
    }

    private void createBackground() {

    }

    private void createLoadingText() {

    }
}
