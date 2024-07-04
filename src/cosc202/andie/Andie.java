package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.InputEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 *
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {
    /**
     * The main application frame.
     */
    public static JFrame frame;

    /**
     * The panel that displays the image.
     */
    private static ImagePanel imagePanel;
    /**
     * The resource bundle for internationalization.
     */
    public static ResourceBundle bundle;
    /**
     * The main menu bar of the application.
     */
    public static JMenuBar menuBar;
    /**
     * The tabbed pane that holds the image tabs.
     */
    public static JTabbedPane tabs;
    /**
     * The actions associated with the toolbar.
     */
    public static TooIbarActions toolbar;
    /**
     * The actions associated with file operations.
     */
    public static FileActions fileActions;
    /**
     * The actions associated with edit operations.
     */
    public static EditActions editActions;
    /**
     * The actions associated with view operations.
     */
    public static ViewActions viewActions;
    /**
     * The actions associated with filter operations.
     */
    private static FilterActions filterActions;
    /**
     * The menu bar associated with the image.
     */
    public static ImageMenuBar imageMenuBar;
    /**
     * The actions associated with colour operations.
     */
    private static ColourActions colourActions;
    /**
     * The actions associated with help operations.
     */
    private static HelpActions helpActions;
    /**
     * The actions associated with macro operations.
     */
    private static MacroActions macroActions;
    /** The actions associated with drawing operations. */
    private static DrawingActions drawingActions;
    /**
     * The status of all certain menu items.
     */
    public static boolean allCertainMenuStatus;

    /**
     * The relative control or command key.
     */
    public static int controlOrCmd;

    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     *
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggered via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     *
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see MacroActions
     *
     * @throws Exception if something goes wrong.
     */
    public static void createAndShowGUI() throws Exception {
        changeHotKey();

        // Set up the main GUI frame
        frame = new JFrame("ANDIE: CodeCrafters");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 600));

        // The main content area is an ImagePanel
        imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        // tabs = new JTabbedPane();
        // tabs.addTab("Welcome", null, null, "Does nothing yet");
        JScrollPane scrollPane = new JScrollPane(imagePanel);

        scrollPane.getViewport().getView().addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int verticalScrollAmount = e.getUnitsToScroll();
                // int horizontalScrollAmount = e.getUnitsToScroll();

                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                // JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();

                verticalScrollBar.setValue(verticalScrollBar.getValue() - verticalScrollAmount);
                // horizontalScrollBar.setValue(horizontalScrollBar.getValue() -
                // horizontalScrollAmount);
            }

        });

        frame.add(scrollPane, BorderLayout.CENTER);

        // We hereby beget toolbar.
        toolbar = new TooIbarActions();
        frame.getContentPane().add(toolbar.createToolBar(), BorderLayout.NORTH);

        createMenuBar();

        frame.repaint();

    }

    /**
     * <p>
     * Exits full screen mode.
     * </p>
     */
    public static void exitFullScreen() {
        frame.setExtendedState(JFrame.NORMAL);
    }

    /**
     * <p>
     * Gets the status of the frame.
     * </p>
     */
    public static void getStatus() {
        System.out.println(frame.getExtendedState());
    }

    /**
     * Method to change all the related "grey buttons'" status
     * before/after the image get imported.
     *
     * @param status updates the status of all of the menu buttons.
     */
    public static void changeAllCertainMenuStatus(boolean status) {
        fileActions.changeCertainMenuStatus(status);
        editActions.changeCertainMenuStatus(status);
        viewActions.changeCertainMenuStatus(status);
        filterActions.changeCertainMenuStatus(status);
        imageMenuBar.changeCertainMenuStatus(status);
        colourActions.changeCertainMenuStatus(status);
        macroActions.changeCertainMenuStatus(status);
        drawingActions.changeCertainMenuStatus(status);
        toolbar.changeCertainToolbarStatus(status);
    }

    /**
     * <p>
     * Creates the menu bar for the GUI with a whole hellofa bunch of menus inside.
     * </p>
     */
    private static void createMenuBar() {
        // Add in menus for various types of action the user may perform.
        JMenuBar newMenuBar = new JMenuBar();

        fileActions = new FileActions();
        editActions = new EditActions();
        viewActions = new ViewActions();
        filterActions = new FilterActions();
        imageMenuBar = new ImageMenuBar();
        colourActions = new ColourActions();
        macroActions = new MacroActions();
        drawingActions = new DrawingActions();
        helpActions = new HelpActions();
        // File menus are pretty standard, so things that usually go in File menus go
        // here.

        JMenu fileMenu = fileActions.createMenu();
        newMenuBar.add(fileMenu);

        // Likewise Edit menus are very common, so should be clear what might go here.
        JMenu editMenu = editActions.createMenu();
        newMenuBar.add(editMenu);

        // View actions control how the image is displayed, but do not alter its actual
        // content
        JMenu viewMenu = viewActions.createMenu();
        newMenuBar.add(viewMenu);

        // Actions that alter the image such as image flip/rotate
        JMenu imageMenu = imageMenuBar.createMenu();
        newMenuBar.add(imageMenu);

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window
        JMenu filterMenu = filterActions.createMenu();
        newMenuBar.add(filterMenu);

        // Actions that affect the representation of colour in the image
        JMenu colourMenu = colourActions.createMenu();
        newMenuBar.add(colourMenu);

        // actions that apply a macro funtion of the operations
        JMenu macroMenu = macroActions.createMenu();
        newMenuBar.add(macroMenu);

        JMenu drawMenu = drawingActions.createMenu();
        newMenuBar.add(drawMenu);

        // Provides an about page and link to online docs
        JMenu helpMenu = helpActions.createMenu();
        newMenuBar.add(helpMenu);

        frame.setJMenuBar(newMenuBar);

        CreateHotKey.createHotkey(fileMenu, KeyEvent.VK_F, 0, "filemenu");
        CreateHotKey.createHotkey(editMenu, KeyEvent.VK_E, 0, "editmenu");
        CreateHotKey.createHotkey(viewMenu, KeyEvent.VK_V, 0, "viewmenu");
        CreateHotKey.createHotkey(filterMenu, KeyEvent.VK_L, 0, "filtermenu");
        CreateHotKey.createHotkey(colourMenu, KeyEvent.VK_C, 0, "colourmenu");
        CreateHotKey.createHotkey(imageMenu, KeyEvent.VK_I, 0, "imagemenu");
        CreateHotKey.createHotkey(helpMenu, KeyEvent.VK_H, 0, "helpmenu");
        CreateHotKey.createHotkey(macroMenu, KeyEvent.VK_M, 0, "macroMenu");
        CreateHotKey.createHotkey(drawMenu, KeyEvent.VK_D, 0, "drawMenu");

        frame.repaint();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        changeAllCertainMenuStatus(allCertainMenuStatus);
        toolbar.changeCropStatus(false);
    }

    /**
     * <p>
     * Gets the frame.
     * </p>
     *
     * @return The frame.
     */
    public static JFrame getFrame() {
        return frame;
    }

    /**
     * Yes it's calling createMenuBar(), we'rejust trying to make it with more
     * sense.
     * Makes more sense than using a carrier pigeon for teammate communication.
     */
    public static void setLanguage() {
        boolean prevIsOpenedStatus = fileActions.isOpenedGetter();
        createMenuBar();
        fileActions.isOpenedSetter(prevIsOpenedStatus);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     *
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     *
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                // new SplashScreen(2000);

                Locale.setDefault(new Locale("en", "NZ"));

                // Now making the ResourceBundle
                bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

                // Line below is for testing the bundle
                // System.out.println(bundle.getString("convertToGreyAction"));

                try {
                    // calling ThemeConfig for changing themes
                    createTheme();
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

    /**
     * Method to create the theme for the GUI.
     */
    public static void createTheme() {
        LookAndFeel laf = ThemeConfig.CreateTheme();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "FATAL: " + ex.toString(),
                    Andie.bundle.getString("FailedLAF"), JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * An accessor for the imagePanel that is used in the preview panels for
     * Brightness and Contrast, among others.
     *
     * @author Kevin Steve Sathyanath
     * @return the ImagePanel in question
     * @since 27/04/2024
     */
    public static ImagePanel getPanel() {
        return imagePanel;
    }

    /**
     * A method to add an EditableImage to a tabPane.
     *
     * @param i The EditableImage to be added to the tabPane.
     * @author Kevin Steve Sathyanath
     * @since 5/5/2024
     */
    public static void addTab(EditableImage i) {

    }

    /**
     * A method to identify the current OS.
     * return 1 if OS is Windows.
     * return 2 if OS is Mac.
     * return 0 if either.
     *
     * @return the OS
     */
    public static int whatsTheOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return 1;
        if (os.contains("mac"))
            return 2;
        else
            return 0; // Sincerely Eden: sorry Linux, cry myself to sleep... Wait, programmers should
                      // have no sleeps.
    }

    /**
     * A method that determine whether to use control or command based on the
     * current OS.
     */
    public static void changeHotKey() {
        if (whatsTheOS() == 1) {
            controlOrCmd = 128;// CTRL_DOWN_MASK
        } else if (whatsTheOS() == 2) {
            controlOrCmd = 256;// META_DOWN_MASK
        } else {
            controlOrCmd = 128;// set it to control
        }
    }
}