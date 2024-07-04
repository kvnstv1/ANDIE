package cosc202.andie;

import java.util.*;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

/**
 * HelpActions class manages a set of actions for the Help menu.
 */
public class HelpActions {

    /** A list of actions for the Help menu. */
    protected ArrayList<Action> actions;
    /**Bundle for multilingual support */
    public ResourceBundle bundle = Andie.bundle;

    /**
     * <p>
     * Create a set of Help menu actions.
     * </p>
     */
    public HelpActions() {
        actions = new ArrayList<Action>();
        ImageIcon aboutUsIcon = new ImageIcon("src/cosc202/andie/icons/About_us.png");
        //Icon downloaded from: <a href="https://www.flaticon.com/free-icons/user" title="user icons">User icons created by Freepik - Flaticon</a>
        Action about = new HelpActionsAboutUs(Andie.bundle.getString("AboutUs"), aboutUsIcon,
                Andie.bundle.getString("AboutUsMessage"), Integer.valueOf(KeyEvent.VK_A));
        actions.add(about);

        ImageIcon hotkeyIcon = new ImageIcon("src/cosc202/andie/icons/Hotkey.png");
        //Icon downloaded from: <a href="https://www.flaticon.com/free-icons/letter-w" title="letter-w icons">Letter-w icons created by Md Tanvirul Haque - Flaticon</a>
        Action hotKey = new HotKeyInstructions(Andie.bundle.getString("HotKey"), hotkeyIcon,
                Andie.bundle.getString("HotKeyMessage"), Integer.valueOf(KeyEvent.VK_H));
        actions.add(hotKey);
        CreateHotKey.createHotkey(hotKey, KeyEvent.VK_K, Andie.controlOrCmd | InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK, "hotKey");

    }

    /**
     * <p>
     * Create a menu containing the list of Help actions.
     * </p>
     *
     * @return The Help menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("Help"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }
    /**
     * <p>
     * Gives help and stuff
     * </p>
     *
     * <p>
     * Just some info about us and our app
     * </p>
     *

    *
    *
    * @author Kevin Steve Sathyanath
    * @version 1.0
    */
    public class HelpActionsAboutUs extends AbstractAction {

        /**
         * <p>
         * Create a new about-us action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        HelpActionsAboutUs(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the about-us action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the about-us-action is triggered.
         * It prints a message in a dialog box.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("TeamMessage"),
                    Andie.bundle.getString("AboutUs"), 1);
        }
    }

    /**
     * <p>
     * Info about hotkeys
     * </p>
     *
     * <p>
     * Info if you need it
     * </p>
     *
     * @author The Greatest Eden
     * @version 1.0
     */
    public class HotKeyInstructions extends AbstractAction {

        /**
         * <p>
         * Create a new Hotkey Instruction action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        HotKeyInstructions(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Hotkey Instruction action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Hotkey Instruction action is triggered.
         * It prints an HTML message in a dialog box.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            String message = Andie.bundle.getString("HotKeyHTMLMessage");

            JEditorPane jEditorPane = new JEditorPane();
            jEditorPane.setContentType("text/html");
            jEditorPane.setText(message);
            jEditorPane.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(jEditorPane);
            scrollPane.setPreferredSize(new Dimension(500, 500));

            JOptionPane.showMessageDialog(null, scrollPane, Andie.bundle.getString("HotKey"), JOptionPane.PLAIN_MESSAGE);
        }
    }
}