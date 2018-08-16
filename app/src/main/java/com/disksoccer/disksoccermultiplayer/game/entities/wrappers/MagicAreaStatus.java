package com.disksoccer.disksoccermultiplayer.game.entities.wrappers;

/**
 * @author Pranav
 *         <h1>Wrapper class for magic area status</h1>
 *         Wraps updates about body entering a magic zone
 *         inside an object
 */
public class MagicAreaStatus {
    public boolean entered;
    public MagicAreaType whichArea;
    // Types - upper left, upper right, lower left, lower right
    public enum MagicAreaType {
        UL, UR, LL, LR
    }
}
