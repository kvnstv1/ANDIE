package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 *
 * <p>
 * The View menu contains actions that affect how the image is displayed in the
 *
 * application.
 * These actions do not affect the contents of the image itself, just the way it
 *
 * is displayed.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    /**The resource bundle for multilingual support */
    public ResourceBundle bundle = Andie.bundle;

    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**Actions representing zoom in,out and full */
    public Action zoomIn, zoomOut, zoomFull;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();


        zoomIn = new ZoomInAction(Andie.bundle.getString("ZoomInAction"), null,
                Andie.bundle.getString("ZoomInAction"),
                Integer.valueOf(KeyEvent.VK_I));
        actions.add(zoomIn);
        CreateHotKey.createHotkey(zoomIn, KeyEvent.VK_EQUALS, Andie.controlOrCmd, "zoomIn");

        zoomOut = new ZoomOutAction(Andie.bundle.getString("ZoomOutAction"), null,
                Andie.bundle.getString("ZoomOutAction"),
                Integer.valueOf(KeyEvent.VK_O));
        actions.add(zoomOut);
        CreateHotKey.createHotkey(zoomOut, KeyEvent.VK_MINUS, Andie.controlOrCmd, "zoomOut");

        zoomFull = new ZoomFullAction(Andie.bundle.getString("ZoomFullAction"), null,
                Andie.bundle.getString("ZoomFullAction"),
                Integer.valueOf(KeyEvent.VK_F));
        actions.add(zoomFull);
        CreateHotKey.createHotkey(zoomFull, KeyEvent.VK_1, Andie.controlOrCmd, "zoomFull");
        CreateHotKey.createHotkey(zoomFull, KeyEvent.VK_0, Andie.controlOrCmd, "zoomFull");

    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     *
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        // JMenu viewMenu = new JMenu("View");
        JMenu viewMenu = new JMenu(Andie.bundle.getString("View"));

        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

        /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     * @param status the status for the menu
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     *
     * actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // System.out.println(target.getZoom());
                target.setZoom(target.getZoom() + 10);
                target.repaint();
                target.getParent().revalidate();
                // to delete the current selection box
                target.setCrop(false);
                target.setIsSelecting(false);
                target.setSelectionRect(null);
                // TODO error messages catching
            } catch (Exception err) {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     *
     * actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // TODO error messages catching
            try {
                target.setZoom(target.getZoom() - 10);
                target.repaint();
                target.getParent().revalidate();
                // to delete the current selection box
                target.setCrop(false);
                target.setIsSelecting(false);
                target.setSelectionRect(null);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

            }

        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     *
     * <p>
     * Note that this action only affects the way the image is displayed, not its
     *
     * actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // TODO error messages catching
            try {
                target.setZoom(100);
                target.repaint();
                target.getParent().revalidate();

                // to delete the current selection box
                target.setCrop(false);
                target.setIsSelecting(false);
                target.setSelectionRect(null);
            } catch (Exception err) {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

    }

}
