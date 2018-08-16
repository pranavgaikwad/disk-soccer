package com.disksoccer.disksoccermultiplayer.scenes;

import android.app.Activity;

import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * @author Pranav
 *         <h1>Base Scene</h1>
 *         This class is the base class for all scenes to be displayed on screen.
 */
public abstract class BaseScene extends Scene {
    protected Engine engine;
    protected Activity activity;
    protected ResourceHelper resourceHelper;
    protected VertexBufferObjectManager vbom;
    protected BoundCamera camera;

    public BaseScene() {
        this.resourceHelper = ResourceHelper.getInstance();
        this.activity = resourceHelper.getMainGameActivity();
        this.camera = resourceHelper.getCamera();
        this.vbom = resourceHelper.getVbom();
        this.engine = resourceHelper.getEngine();
        createScene();
    }

    /**
     *
     */
    public abstract void createScene();

    /**
     *
     */
    public abstract void onBackKeyPressed();

    /**
     *
     */
    public abstract void disposeScene();

    /**
     *
     */
    public void destroyEntity(Entity e) {
        e.detachSelf();
        e.dispose();
    }

    /**
     *
     * @return
     */
    public abstract SceneType getSceneType();
}
