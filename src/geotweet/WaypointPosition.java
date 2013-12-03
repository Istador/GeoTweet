/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geotweet;

import org.jdesktop.swingx.mapviewer.Waypoint;

/**
 *
 * @author R. C. Ladiges
 */
public class WaypointPosition extends Waypoint{
    protected GeotweetPosition pos;
    public GeotweetPosition getPos() {return pos;}

    public WaypointPosition(GeotweetPosition pos){
        super(pos.getY(), pos.getX());
        this.pos = pos;
    }
}
