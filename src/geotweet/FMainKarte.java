/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FMainKarte.java
 *
 * Created on 22.06.2010, 23:04:03
 */

package geotweet;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import org.jdesktop.swingx.mapviewer.*;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;
import org.jdesktop.swingx.JXMapViewer;
/**
 *
 * @author R. C. Ladiges
 */
public class FMainKarte extends javax.swing.JFrame {
    public Connection jdbc_conn;
    protected WaypointPainter wppainter;
    public GeotweetUser myself;

    /** Creates new form FMainKarte */
    public FMainKarte() {
        initComponents();
        this.setLocationRelativeTo(null);
        GeoPosition haw = new GeoPosition(53.5570, 10.0235);
        jXMapKit1.setAddressLocation(haw);

        //JLabel label = new JLabel("openstreetmap.org");
        //label.setHorizontalAlignment(JLabel.RIGHT);
        //label.setVerticalAlignment(JLabel.BOTTOM);
        //jXMapKit1.getMainMap().add(label);
        
        updateMap();
        mouseClicks();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapKit1 = new org.jdesktop.swingx.JXMapKit();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GeoTweet");

        jXMapKit1.setToolTipText("Kartenquelle: OpenStreetMap.org");
        jXMapKit1.setAddressLocationShown(false);
        jXMapKit1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jXMapKit1.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        jXMapKit1.setMiniMapVisible(false);
        jXMapKit1.setZoom(1);
        jXMapKit1.setZoomButtonsVisible(false);
        jXMapKit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jXMapKit1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapKit1, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapKit1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jXMapKit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXMapKit1MouseClicked
        //Nothing but the rain.
    }//GEN-LAST:event_jXMapKit1MouseClicked

    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FMainKarte().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXMapKit jXMapKit1;
    // End of variables declaration//GEN-END:variables

    public boolean connect(){
        boolean result = true;
        try{
            if(jdbc_conn != null) this.disconnect();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = "blackpinguin.de";
            String user = "bai2dbp_geotweet";
            String pw = "trueblood";
            jdbc_conn = DriverManager.getConnection("jdbc:mysql://"+url+"/?user="+user+"&password="+pw);
            this.setTitle("Openstreetmap.org GeoTweet : " + user + "@" + url);
        }
        catch(Exception e){
            result = false;
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    public void disconnect(){
        try{
            jdbc_conn.close();
            jdbc_conn = null;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void updateMap(){
        Set<WaypointPosition> wp = new HashSet<WaypointPosition>();
        for(Iterator<GeotweetPosition> it = getAllPositions().iterator(); it.hasNext();){
            wp.add(new WaypointPosition(it.next()));
        }
        wppainter = new WaypointPainter();
        wppainter.setRenderer(new WaypointRenderer(){
            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp){
                g.setColor(Color.black);
                g.fillOval(-8, -8, 16, 16);
                g.setColor(Color.green);
                g.fillOval(-7, -7, 14, 14);
                g.setColor(Color.black);
                g.drawString(String.valueOf(((WaypointPosition)wp).getPos().getAnzahl()), -4, 4);
                return true;
            }
        });
        wppainter.setWaypoints(wp);
        jXMapKit1.getMainMap().setOverlayPainter(wppainter);
        
    }

    //Alle Messages
    protected Collection<GeotweetMessage> getAllMessages(){
        Collection<GeotweetMessage> result = new HashSet<GeotweetMessage>();
        if(!connect()) return result;
        try{
            //SELECT position_id, position_x, position_y, position_name, COUNT(*) position_anzahl FROM `position` NATURAL JOIN `message` GROUP BY `position_id`
            String db = "bai2dbp_geotweet";
            String query = "SELECT *, COUNT(*) `position_anzahl` FROM `"+db+"`.`message` " +
                "NATURAL JOIN `"+db+"`.`user` "+
                "NATURAL JOIN `"+db+"`.`position` "+
                "GROUP BY `position_id` ";
            Statement s = jdbc_conn.createStatement();
            if (s.execute(query)){
                ResultSet rs = s.getResultSet();
		while (rs.next()){
                    GeotweetUser user = new GeotweetUser(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            //rs.getString("user_pw"),
                            rs.getBlob("user_avatar")
                        );                    
                    
                    GeotweetPosition position = new GeotweetPosition(
                            rs.getInt("position_id"),
                            rs.getDouble("position_x"),
                            rs.getDouble("position_y"),
                            rs.getString("position_name"),
                            rs.getInt("position_anzahl")
                        );

                    GeotweetMessage msg = new GeotweetMessage(
                            rs.getInt("message_id"),
                            user,
                            position,
                            rs.getString("message_text"),
                            rs.getTimestamp("message_time")
                        );
                    result.add(msg);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        disconnect();
        return result;
    }

    //Alle Messages an einer bestimmten Position
    protected Collection<GeotweetMessage> getAllMessages(GeotweetPosition pos){
        //Collection<GeotweetMessage> result = new HashSet<GeotweetMessage>();
        Collection<GeotweetMessage> result = new LinkedList<GeotweetMessage>();
        if(!connect()) return result;
        try{
            //SELECT position_id, position_x, position_y, position_name, COUNT(*) position_anzahl FROM `position` NATURAL JOIN `message` GROUP BY `position_id`
            String db = "bai2dbp_geotweet";
            String query = "SELECT * FROM `"+db+"`.`message` " +
                "NATURAL JOIN `"+db+"`.`user` "+
                //"NATURAL JOIN `"+db+"`.`position` "+
                "WHERE `position_id` = '"+pos.getId()+"' " +
                "ORDER BY `message_time` ";
                //"GROUP BY `position_id` ";
            Statement s = jdbc_conn.createStatement();
            if (s.execute(query)){
                ResultSet rs = s.getResultSet();
		while (rs.next()){
                    GeotweetUser user = new GeotweetUser(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            //rs.getString("user_pw"),
                            rs.getBlob("user_avatar")
                        );

                    GeotweetPosition position = pos;

                    GeotweetMessage msg = new GeotweetMessage(
                            rs.getInt("message_id"),
                            user,
                            position,
                            rs.getString("message_text"),
                            rs.getTimestamp("message_time")
                        );
                    result.add(msg);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        disconnect();
        return result;
    }

    //Alle Positionen
    protected Collection<GeotweetPosition> getAllPositions(){
        Collection<GeotweetPosition> result = new HashSet<GeotweetPosition>();
        if(!connect()) return result;
        try{
            String db = "bai2dbp_geotweet";
            String query = "SELECT *, COUNT(*) `position_anzahl` FROM `"+db+"`.`position` " +
                "NATURAL JOIN `"+db+"`.`message`"+
                "GROUP BY `position_id` ";
            Statement s = jdbc_conn.createStatement();
            if (s.execute(query)){
                ResultSet rs = s.getResultSet();
		while (rs.next()){
                    GeotweetPosition position = new GeotweetPosition(
                            rs.getInt("position_id"),
                            rs.getDouble("position_x"),
                            rs.getDouble("position_y"),
                            rs.getString("position_name"),
                            rs.getInt("position_anzahl")
                        );
                    result.add(position);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        disconnect();
        return result;
    }




    public void newMessage(double y, double x){
        if(myself == null){
            login();
            return;
        }
        final FAddMessage frame = new FAddMessage(this,y,x);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void showMessage(Collection<GeotweetMessage> msgs){
        final FShowMessages frame = new FShowMessages(this, msgs);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    protected void login(){
        final FLogin frame = new FLogin(this);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected void mouseClicks(){
        //if(myself == null) return;
        jXMapKit1.getMainMap().addMouseListener(new MouseInputAdapter() {
        @Override public void mouseClicked(MouseEvent evt) {
        //Mausklick Event start
        boolean gefunden = false;
        Point pt = evt.getPoint();
        JXMapViewer map = jXMapKit1.getMainMap();

        for(Iterator<WaypointPosition> it = wppainter.getWaypoints().iterator();it.hasNext() && !gefunden;){
            WaypointPosition wp = it.next();
            Point2D point = map.getTileFactory().geoToPixel(new GeoPosition(wp.getPos().getY(), wp.getPos().getX()), map.getZoom());
            Rectangle bounds = map.getViewportBounds();
            int x = (int)(point.getX() - bounds.getX());
            int y = (int)(point.getY() - bounds.getY());
            Rectangle rect = new Rectangle(x - 7, y - 7, 16, 16);
            if (rect.contains(pt)){
                gefunden = true;
                LinkedList<GeotweetMessage> messages = (LinkedList<GeotweetMessage>) getAllMessages(wp.getPos());
                showMessage(messages);                
            }
        }
        if (!gefunden){
            GeoPosition pos = map.convertPointToGeoPosition(pt);
            newMessage(pos.getLatitude(), pos.getLongitude());
        }
        //Mausklick Event ende
        }} );
    }

}
