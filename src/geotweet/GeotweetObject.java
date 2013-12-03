/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geotweet;

//import javax.swing.JButton;

/**
 *
 * @author R. C. Ladiges
 */
public abstract class GeotweetObject /*extends JButton*/{
    protected int id;
    public int getId() {return id;}

    protected GeotweetObject(int id){
        this.id = id;
    }

    @Override public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final GeotweetObject other = (GeotweetObject) obj;
        if (this.id != other.id) return false;
        return true;
    }

    @Override public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }
}
