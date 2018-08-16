package com.disksoccer.disksoccermultiplayer.game.util;

import com.disksoccer.disksoccermultiplayer.util.ResourceHelper;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * @author Pranav
 *         <h1>Class responsible for connecting game entities with the engine</h1>
 */
public class DSEntityEngineConnector {
    private PhysicsWorld physicsWorld;
    private ResourceHelper resourceHelper;
    private VertexBufferObjectManager vertexBufferObjectManager;

    public PhysicsWorld getPhysicsWorld() {return physicsWorld;}
    public ResourceHelper getResourceHelper() {return resourceHelper;}
    public VertexBufferObjectManager getVbom() {return vertexBufferObjectManager;}

    public DSEntityEngineConnector(PhysicsWorld p, ResourceHelper r, VertexBufferObjectManager v) {
        physicsWorld = p;
        resourceHelper = r;
        vertexBufferObjectManager = v;
    }
}
