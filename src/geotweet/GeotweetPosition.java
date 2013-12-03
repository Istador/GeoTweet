/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geotweet;

/**
 *
 * @author R. C. Ladiges
 */
public class GeotweetPosition extends GeotweetObject {
    protected double x, y;
    protected String name;
    protected int anzahl;
    public double getX() {return x;}
    public double getY() {return y;}
    public String getName() {return name; }
    public int getAnzahl() {return anzahl; }

    public GeotweetPosition(int id, double x, double y, String name, int anzahl){
        super(id);
        this.x = x;
        this.y = y;
        this.name = name;
        this.anzahl = anzahl;
    }
}
