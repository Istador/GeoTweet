/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geotweet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
/**
 *
 * @author R. C. Ladiges
 */
public class GeotweetMessage extends GeotweetObject {
    protected GeotweetUser user;
    protected GeotweetPosition position;
    protected String text;
    protected Timestamp time;
    public GeotweetUser getUser() {return user;}
    public GeotweetPosition getPosition() {return position;}
    public String getText() {return text;}
    public Timestamp getTime() {return time;}

    public GeotweetMessage(int id, GeotweetUser user, GeotweetPosition position, String text, Timestamp time){
        super(id);
        this.user = user;
        this.position = position;
        this.text = text;
        this.time = time;
//        this.addMouseListener(new MouseListener() {
//            public void mouseClicked(MouseEvent e){System.out.println("a");}
//            public void mouseEntered(MouseEvent e){};
//            public void mouseExited(MouseEvent e){};
//            public void mousePressed(MouseEvent e){};
//            public void mouseReleased(MouseEvent e){};
//        });
    }

    public String getUsername(){return user.getName();}    
    public String getDateTime(){return getTime().toGMTString();}
}
