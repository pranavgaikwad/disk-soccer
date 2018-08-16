package com.disksoccer.disksoccermultiplayer.scenes;

import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;

/**
 * @author Pranav
 *         <h1>Main Menu Scene</h1>
 *         Play button, options button, and a background ! (Simple af :P)
 */
public class MainMenuScene extends BaseScene implements MenuScene.IOnMenuItemClickListener{
    // in-built menu scene
    private MenuScene menuScene;
    private AnimatedSprite titleTextSprite;

    // ids for buttons
    private static final int ID_PLAY = 0;
    private static final int ID_OPTIONS = 1;

    @Override
    public void createScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        // close the app on back press
        System.exit(0);
    }

    @Override
    public void disposeScene() {
        titleTextSprite.detachSelf();
        titleTextSprite.dispose();
        this.detachSelf();
        this.dispose();
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_MENU;
    }

    /**
     * Creates background from Textures built using Resource Helper
     */
    private void createBackground() {
        //this.setBackground(new Background(Color.BLACK));
        titleTextSprite = new AnimatedSprite(0, 0, resourceHelper.titleRegion, vbom);
        titleTextSprite.setPosition(ResolutionHelper.SCREEN_WIDTH / 4 + 40, ResolutionHelper.SCREEN_HEIGHT / 2);
        titleTextSprite.animate(200);
        attachChild(titleTextSprite);
    }

    /**
     * Sets up menu buttons
     */
    private void createMenuChildScene() {
        menuScene = new MenuScene(camera);
        menuScene.setPosition(ResolutionHelper.SCREEN_WIDTH / 4 , 0);

        final IMenuItem playButton = new ScaleMenuItemDecorator(new SpriteMenuItem(ID_PLAY, resourceHelper.playButtonRegion, vbom), 1.2f, 1);
        final IMenuItem optionsButton = new ScaleMenuItemDecorator(new SpriteMenuItem(ID_OPTIONS, resourceHelper.optionsButtonRegion, vbom), 1.2f, 1);

        menuScene.addMenuItem(playButton);
        menuScene.addMenuItem(optionsButton);

        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        // considering dimensions of our buttons
        playButton.setPosition(playButton.getX(), (ResolutionHelper.CENTER_HEIGHT) + 40);
        optionsButton.setPosition(optionsButton.getX(), (ResolutionHelper.CENTER_HEIGHT) - 40);

        menuScene.setOnMenuItemClickListener(this);

        setChildScene(menuScene);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case ID_PLAY:
                SceneManager.getInstance().createGameScene();
                return true;
            case ID_OPTIONS:
                return true;
        }
        return false;
    }
}
