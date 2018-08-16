package com.disksoccer.disksoccermultiplayer.util;

import com.disksoccer.disksoccermultiplayer.wrappers.XY;

/**
 * @author Pranav
 *         <h1>Resolution Helper</h1>
 *         Use this class to determine resolution for any entity that is to be drawn on screen
 *         Responsible for adapting different screen sizes. Performs calculcations for relative
 *         and absolute resolutions.
 */
public class ResolutionHelper {
    // global screen dimensions
    // use these values in future references
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 480;

    // soccer field size
    public static final int FIELD_WIDTH = 1250;
    public static final int FIELD_HEIGHT = 924;

    // camera bounds for the soccer field
    public static final int CAMERA_X_MIN = 0 - ((FIELD_WIDTH - SCREEN_WIDTH)/2) ;
    public static final int CAMERA_Y_MIN = 0 - ((FIELD_HEIGHT - SCREEN_HEIGHT)/2);
    public static final int CAMERA_X_MAX = SCREEN_WIDTH + ((FIELD_WIDTH - SCREEN_WIDTH)/2);
    public static final int CAMERA_Y_MAX = SCREEN_HEIGHT + ((FIELD_HEIGHT - SCREEN_HEIGHT)/2);

    // calculated center of the screen
    // don't re-calculate/use absolute numbers
    public static final int CENTER_WIDTH = SCREEN_WIDTH / 2;
    public static final int CENTER_HEIGHT = SCREEN_HEIGHT / 2;

    /**
     *
     * @param percentX
     * @param percentY
     * @return
     */
    public static XY getPixelByPercentage(int percentX, int percentY) {
        return new XY(SCREEN_WIDTH * percentX / 100, SCREEN_HEIGHT * percentY / 100);
    }
}
