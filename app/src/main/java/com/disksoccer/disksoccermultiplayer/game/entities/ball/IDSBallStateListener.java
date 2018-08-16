package com.disksoccer.disksoccermultiplayer.game.entities.ball;

import com.disksoccer.disksoccermultiplayer.wrappers.XY;

/**
 * @author Pranav
 *         <h1>Title goes here...</h1>
 */
public interface IDSBallStateListener {
    void onBallLocationChanged(XY location);
}
