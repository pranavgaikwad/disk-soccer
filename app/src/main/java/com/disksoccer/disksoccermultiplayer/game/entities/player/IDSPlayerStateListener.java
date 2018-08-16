package com.disksoccer.disksoccermultiplayer.game.entities.player;

import com.disksoccer.disksoccermultiplayer.wrappers.XY;

/**
 * @author Pranav
 *         <h1>Listens to state transitions of player</h1>
 */
public interface IDSPlayerStateListener {
    void onPlayerLocationChanged(DSPlayer p, XY location);
}
