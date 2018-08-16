package com.disksoccer.disksoccermultiplayer.game;

/**
 * @author Pranav
 *         <h1>Base Game</h1>
 *         Offline and online modes are to be derived from the base game
 */
public abstract class DSBaseGame {
    /**
     * Initialize game
     */
    public abstract void init();
    /**
     * Method to finish the game
     */
    public abstract void finish();
}
