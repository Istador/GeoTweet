/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package geotweet;
import java.awt.Image;
import java.sql.Blob;
import javax.swing.ImageIcon;
import static java.awt.Image.*;
/**
 *
 * @author R. C. Ladiges
 */
public class GeotweetUser extends GeotweetObject {
    protected String name;
    protected Blob avatar;
    public String getName() {return name;}
    public Blob getAvatar() {return avatar;}

//    public InputStream getAvatarStream() {
//        try{
//            if(avatar != null) return avatar.getBinaryStream();
//        }
//        catch(Exception e){System.out.println(e.getMessage());}
//        return null;
//    }
//    public BufferedImage getAvatarBuffer() {
//        try{
//            if(avatar != null) return ImageIO.read(getAvatarStream());
//        }
//        catch(Exception e){System.out.println(e.getMessage());}
//        return null;
//    }

    public ImageIcon getAvatarImageIcon() {
        try{
            if(avatar == null) return null;
            ImageIcon img = new ImageIcon(avatar.getBytes(1, (int)(avatar.length())));
            Image scaled = img.getImage().getScaledInstance(60, 60, SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        catch(Exception e){System.out.println(e.getMessage());}
        return null;
    }



    public GeotweetUser(int id, String name, Blob avatar){
        super(id);
        this.name = name;
        this.avatar = avatar;
    }
}
