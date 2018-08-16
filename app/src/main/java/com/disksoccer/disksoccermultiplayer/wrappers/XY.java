package com.disksoccer.disksoccermultiplayer.wrappers;

/**
 * @author Pranav
 *         <h1>Wrapper Class for pixel values</h1>
 *         Describes standard ways to represent a pixel
 *         Provides methods to manipulate values
 */
public class XY {
    public float X;
    public float Y;

    public XY(float X, float Y) {
        this.X = X;
        this.Y = Y;
    }

    /**
     * Calculates distance between two points
     * @param point point from which distance has to be calculated
     * @return distance in float
     */
    public float getDistanceFrom(XY point) {
        float diff_x = this.X - point.X;
        float diff_y = this.Y - point.Y;
        float square_sum = diff_x*diff_x + diff_y*diff_y;
        return (float) Math.sqrt(square_sum);
    }

    /**
     * Calculates and returns distance in the form
     * of X and Y values
     * @param point point from which distance is to be calculated
     * @return XY object of vector
     */
    public XY getVectorDistanceFrom(XY point) {
        float diff_x = point.X - this.X;
        float diff_y = point.Y - this.Y;
        return new XY(diff_x, diff_y);
    }

    @Override
    public boolean equals(Object o) {
        XY object = (XY)o;
        if((object.X == this.X) && (object.Y == this.Y))
            return true;
        return false;
    }

    // setters
    public void setX(float x) {X = x;}
    public void setY(float y) {Y = y;}
}
