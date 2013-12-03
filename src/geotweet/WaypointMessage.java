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
public class WaypointMessage extends Waypoint{
    protected GeotweetMessage message;
    public GeotweetMessage getMessage() {return message;}

    public WaypointMessage(GeotweetMessage message){
        super(message.getPosition().getY(), message.getPosition().getX());
        this.message = message;
    }
}
