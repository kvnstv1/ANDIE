package cosc202.andie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


/**A class that simulates a pencil drawing on a canvas 
 * @author Kevin Steve Sathyanath
 * @version 1.0
*/
public class Pencil extends JPanel implements ImageOperation, java.io.Serializable, MouseListener, MouseMotionListener {

    /**Graphics object */
    private Graphics2D g;
    /**The bufferedImage to return */
    private BufferedImage out;

    /**The constructor */
    public Pencil(){

    }
    
    /**The apply method. 
     * @param in the input BufferedImage.
    */
    @Override
    public BufferedImage apply(BufferedImage in){

        out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
        g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(2f));

        if(ColourWheel.getChosenColour()!=null){
            g.setColor(ColourWheel.getChosenColour());
        }
        else{
            g.setColor(Color.BLACK);
        }
        return in;
    }

    /**A method to show what to do when the mouse is pressed.
     * @param e the MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent e) {
        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
        repaint();
    }
    /**A method to show what to do when the mouse is exited.
     * @param e the MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e){
    }
    /**A method to show what to do when the mouse is released.
     * @param e the MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e){
    }
    /**A method to show what to do when the mouse is clicked.
     * @param e the MouseEvent
     */
    @Override 
    public void mouseClicked(MouseEvent e){
    }
    /**A method to show what to do when the mouse is entered.
     * @param e the MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e){

    }
    /**A method to show what to do when the mouse is dragged.
     * @param e the MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e){
        g.drawLine(e.getX(), e.getY(), e.getX(), e.getY());
            repaint();
    }
    /**A method to show what to do when the mouse is moved.
     * @param e the MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e){
    }
    /**Paint component
     * @param g the graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(out, 0, 0, this);
    }


}//End of class Body
