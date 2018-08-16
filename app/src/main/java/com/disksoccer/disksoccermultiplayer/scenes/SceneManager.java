package com.disksoccer.disksoccermultiplayer.scenes;

import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;

/**
 * @author Pranav
 *         <h1>Scene Manager</h1>
 *         Responsible for manipulating scenes.
 *         Create new scene, switch scene, dispose scene, etc.
 */
public class SceneManager {
    // all available scenes
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;

    private static final SceneManager instance = new SceneManager();

    private SceneManager(){}

    public static SceneManager getInstance() {
        return instance;
    }

    private Engine engine = ResourceHelper.getInstance().getEngine();

    // which scene is currently getting displayed
    private BaseScene currentScene;

    public void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }

    /**
     * Toggles scenes
     * @param sceneType provide type of the scene to be displayed
     */
    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            default:
                break;
        }
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourceHelper.getInstance().loadSplashResources();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    public void createMenuScene() {
        ResourceHelper.getInstance().loadMenuResources();
        ResourceHelper.getInstance().loadLoadingSceneResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        setScene(menuScene);

        ResourceHelper.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createGameScene() {
        setScene(loadingScene);
        ResourceHelper.getInstance().unloadMenuTextures();
        menuScene.disposeScene();
        menuScene = null;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourceHelper.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
            }
        }));
    }

    public void reCreateMenuScene() {
        setScene(loadingScene);
        ResourceHelper.getInstance().unloadGameTextures();
        gameScene.disposeScene();
        gameScene = null;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourceHelper.getInstance().loadMenuTextures();
                menuScene = new MainMenuScene();
                setScene(menuScene);
            }
        }));
    }

}

