package com.disksoccer.disksoccermultiplayer.game.entities.field;

import com.disksoccer.disksoccermultiplayer.game.entities.DSBaseEntity;
import com.disksoccer.disksoccermultiplayer.game.util.DSEntityEngineConnector;
import com.disksoccer.disksoccermultiplayer.util.ResolutionHelper;

import org.andengine.entity.sprite.Sprite;

/**
 * @author Pranav
 *         <h1>Field</h1>
 */
public class DSField extends DSBaseEntity {

    public DSField(DSEntityEngineConnector connector) {
        super(connector);
    }

    @Override
    public void setup() {
        sprite = new Sprite(0, 0, connector.getResourceHelper().gameBgTextureRegion, connector.getVbom());
        sprite.setPosition(ResolutionHelper.CENTER_WIDTH, ResolutionHelper.CENTER_HEIGHT);
    }

    @Override
    public void safeDestroy() {
        sprite.detachSelf();
        sprite.dispose();
    }

    // do nothing here
    @Override
    public void onUpdate(float pSecondsElapsed) {}
    @Override
    public void reset() {}
}
