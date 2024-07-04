package cosc202.andie;

/**
 *
 * <p>
 * ImageOperation to apply drawing functions.
 * </p>
 *
 * <p>
 * The DrawingActions menu contains actions 3 drawing shapes actions.
 * Users are able to choose to draw rectangle, oval and line use different colors
 *
 * </p>
 *
 * </p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 */
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * <p>
 * You can draw stuff now
 * </p>
 *
 * <p>
 * This method draws stuff.
 * </p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 */
public class DrawingActions {
    /**
     * An arrayList of actions needed for this class. Ask Yuxing what it does I
     * guess.
     */
    protected ArrayList<Action> actions;
    /** The file where the operation sequence is stored. */
    public ResourceBundle bundle = Andie.bundle;

    /**
     * <p>
     * Create a set of Drawing menu actions.
     * </p>
     */
    public DrawingActions() {
        actions = new ArrayList<Action>();
        // Icon downloaded from: <a href="https://www.flaticon.com/free-icons/oval"
        // title="oval icons">Oval icons created by Freepik - Flaticon</a>
        ImageIcon ellipseIcon = new ImageIcon("src/cosc202/andie/icons/ellipse.png");
        // Icon taken from: <a href="https://www.flaticon.com/free-icons/rectangle"
        // title="rectangle icons">Rectangle icons created by Freepik - Flaticon</a>
        ImageIcon rectangleIcon = new ImageIcon("src/cosc202/andie/icons/rectangle.png");
        // Icon taken from: <a href="https://www.flaticon.com/free-icons/line"
        // title="line icons">Line icons created by Freepik - Flaticon</a>
        ImageIcon lineIcon = new ImageIcon("src/cosc202/andie/icons/diagonal-line.png");

        actions.add(new DrawOvalAction(Andie.bundle.getString("Oval"), ellipseIcon, Andie.bundle.getString("OvalDesc"),
                Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new DrawRectAction(Andie.bundle.getString("Rectangle"), rectangleIcon,
                Andie.bundle.getString("RectangleDesc"),
                Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new DrawLineAction(Andie.bundle.getString("Line"), lineIcon, Andie.bundle.getString("LineDesc"),
                Integer.valueOf(KeyEvent.VK_L)));
    }

    /**
     * <p>
     * Create a menu containing the list of drawing actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu drawMenu = new JMenu(Andie.bundle.getString("Drawing"));
        for (Action action : actions) {
            drawMenu.add(new JMenuItem(action));
        }

        return drawMenu;
    }

    /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     *
     * @param status The boolean value to control the behaviour of this method.
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawOvalAction extends ImageAction {

        /**
         * Constructor for the action
         *
         * @param name     the name
         * @param icon     the icon
         * @param desc     the desc
         * @param mnemonic the mnemonic
         */
        DrawOvalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    ColourWheel.pickColour();
                    DrawingOperations.zoom = target.getZoom();
                    DrawingOperations.isDrawingOval = true;

                    // target.getImage().apply(new DrawingOperations('o'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, err.toString(),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawRectAction extends ImageAction {

        /**
         * A method that requires comments
         *
         * @param name     name
         * @param icon     icon
         * @param desc     desc
         * @param mnemonic mnemonic
         */
        DrawRectAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    ColourWheel.pickColour();
                    DrawingOperations.zoom = target.getZoom();
                    DrawingOperations.isDrawingRect = true;
                    // target.getImage().apply(new DrawingOperations('r'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, err.toString(),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    /**
     * <p>
     * Create a drawing oval action.
     * </p>
     *
     */
    public class DrawLineAction extends ImageAction {

        DrawLineAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    ColourWheel.pickColour();
                    DrawingOperations.zoom = target.getZoom();
                    DrawingOperations.isDrawingLine = true;

                    // target.getImage().apply(new DrawingOperations('l'));
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, err.toString(),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                // System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }

    }
}
