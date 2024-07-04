package cosc202.andie;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 *
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions {
    /** Multilingual support */
    public ResourceBundle bundle = Andie.bundle;

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    /** A boolean for controlling the program */
    protected static boolean isOpened = false;
    // protected boolean isSaved = false;

    /** Action to open a file */
    public Action fileOpen;
    /** Action to save a file */
    public Action fileSave;
    /** Action to export a file */
    public Action fileExport;
    /** Action to print a file */
    public Action filePrint;
    /** Action to save as a file */
    private Action fileSaveAs;

    /**
     * To get the state of the isOpened variable
     *
     * @return isOpened the state of the isOpened variable
     */
    public boolean isOpenedGetter() {
        return isOpened;
    }

    /**
     * To set the state of the isOpened variable
     *
     * @param isOpened the state of the isOpened variable
     * @return isOpened the state of the isOpened variable
     */
    public boolean isOpenedSetter(boolean isOpened) {
        FileActions.isOpened = isOpened;
        return isOpened;
    }

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */

    public FileActions() {

        actions = new ArrayList<Action>();

        ImageIcon openFileIcon = new ImageIcon("src/cosc202/andie/icons/open-file.png");
        // Image downloaded from: <a href="https://www.flaticon.com/free-icons/folder"
        // title="folder icons">Folder icons created by stockes_02 - Flaticon</a>
        fileOpen = new FileOpenAction(Andie.bundle.getString("OpenAction"), openFileIcon,
                Andie.bundle.getString("OpenDesc"),
                Integer.valueOf(KeyEvent.VK_O));
        actions.add(fileOpen);
        CreateHotKey.createHotkey(fileOpen, KeyEvent.VK_O, Andie.controlOrCmd, "fileOpen");

        ImageIcon saveIcon = new ImageIcon("src/cosc202/andie/icons/diskette.png");
        // Downloaded from : <a href="https://www.flaticon.com/free-icons/folder"
        // title="folder icons">Folder icons created by stockes_02 - Flaticon</a>
        fileSave = new FileSaveAction(Andie.bundle.getString("SaveAction"), saveIcon,
                Andie.bundle.getString("SaveDesc"), Integer.valueOf(KeyEvent.VK_S));
        actions.add(fileSave);
        CreateHotKey.createHotkey(fileSave, KeyEvent.VK_S, Andie.controlOrCmd, "fileSave");

        fileSaveAs = new FileSaveAsAction(Andie.bundle.getString("SaveAsAction"), null,
                Andie.bundle.getString("SaveAsDesc"),
                Integer.valueOf(KeyEvent.VK_A));
        actions.add(fileSaveAs);
        CreateHotKey.createHotkey(fileSaveAs, KeyEvent.VK_S, Andie.controlOrCmd | InputEvent.SHIFT_DOWN_MASK,
                "fileSaveAs");

        ImageIcon exportIcon = new ImageIcon("src/cosc202/andie/icons/upload.png");
        // Downloaded from: <a href="https://www.flaticon.com/free-icons/output"
        // title="output icons">Output icons created by NajmunNahar - Flaticon</a>
        fileExport = new FileExportAction(Andie.bundle.getString("ExportAction"), exportIcon,
                Andie.bundle.getString("ExportDesc"),
                Integer.valueOf(KeyEvent.VK_E));
        actions.add(fileExport);
        CreateHotKey.createHotkey(fileExport, KeyEvent.VK_E, Andie.controlOrCmd, "fileExport");

        ImageIcon printIcon = new ImageIcon("src/cosc202/andie/icons/printing.png");
        // Downloaded from: <a href="https://www.flaticon.com/free-icons/print"
        // title="print icons">Print icons created by Freepik - Flaticon</a>
        filePrint = new FilePrintAction(Andie.bundle.getString("PrintAction"), printIcon,
                Andie.bundle.getString("PrintDesc"),
                Integer.valueOf(KeyEvent.VK_P));
        actions.add(filePrint);
        CreateHotKey.createHotkey(filePrint, KeyEvent.VK_P, Andie.controlOrCmd, "filePrint");

        ImageIcon languageIcon = new ImageIcon("src/cosc202/andie/icons/arrow.png");
        // Downloaded from: <a href="https://www.flaticon.com/free-icons/translate"
        // title="translate icons">Translate icons created by Roundicons Premium -
        // Flaticon</a>
        Action fileChangeLangue = new FileChangeLanguageAction(Andie.bundle.getString("ChangeLanguage"), languageIcon,
                Andie.bundle.getString("ChangeLanguage"), Integer.valueOf(KeyEvent.VK_L));
        actions.add(fileChangeLangue);
        // CreateHotKey.createHotkey(fileChangeLangue, 0, 0, "fileChangeLangue");

        ImageIcon exitIcon = new ImageIcon("src/cosc202/andie/icons/logout.png");
        // Downloaded from: <a href="https://www.flaticon.com/free-icons/mobile-app"
        // title="mobile app icons">Mobile app icons created by Andy Horvath -
        // Flaticon</a>
        Action fileExit = new FileExitAction(Andie.bundle.getString("ExitAction"), exitIcon,
                Andie.bundle.getString("ExitDesc"),
                Integer.valueOf(KeyEvent.VK_Q));
        actions.add(fileExit);
        CreateHotKey.createHotkey(fileExit, KeyEvent.VK_Q, Andie.controlOrCmd, "fileExit");
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     *
     * @return The File menu UI element.
     */
    public JMenu createMenu() {

        ArrayList<Integer> keystrokes = new ArrayList<Integer>();
        keystrokes.add(KeyEvent.VK_O);
        keystrokes.add(KeyEvent.VK_S);
        keystrokes.add(KeyEvent.VK_A);
        keystrokes.add(KeyEvent.VK_E);
        keystrokes.add(KeyEvent.VK_P);
        keystrokes.add(0);
        keystrokes.add(KeyEvent.VK_Q);

        int index = 0;

        JMenu fileMenu = new JMenu(Andie.bundle.getString("File"));

        // JMenuItem fileOpenMenuManual = new JMenuItem("Open");
        // fileOpenMenuManual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,0));
        // fileMenu.add(fileOpenMenuManual);

        for (Action action : actions) {
            // JMenuItem newItem = new JMenuItem(action);
            // newItem.setAccelerator(KeyStroke.getKeyStroke(keystrokes.get(index),0));
            // fileMenu.add(newItem);
            // index++;
            fileMenu.add(new JMenuItem(action));

        }

        return fileMenu;
    }

    /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     *
     * @param status the status of the menubar
     */
    public void changeCertainMenuStatus(boolean status) {
        // for (Action action : actions) {
        // action.setEnabled(status);
        // }
        actions.get(1).setEnabled(status);
        actions.get(2).setEnabled(status);
        actions.get(3).setEnabled(status);
        actions.get(4).setEnabled(status);
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     *
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (isOpened == true && EditableImage.isOpsNotEmptyStatus == true) {

                Object[] options = { Andie.bundle.getString("Yes"), Andie.bundle.getString("No"),
                        Andie.bundle.getString("Cancel") };

                int n = JOptionPane.showOptionDialog(
                        Andie.getFrame(),
                        Andie.bundle.getString("DoYouWantToSave"),
                        Andie.bundle.getString("Warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (n == 0) { // yes
                    // if user chose yes and there's no ops file saved
                    // then call "save"
                    actions.get(1).actionPerformed(e);

                    // then we treat it as saved so that next time we run the code, it will
                    // trigger the "else" statement outside of this loop
                    EditableImage.isOpsNotEmptyStatus = false;

                    // then open the openfile window
                    openFile();

                } else if (n == 1) { // no
                    openFile();
                } else {
                    // Should change this statement to case control cuz the "else" here is
                    // useless...
                }
            } else {
                openFile();
            }
        }

        /**
         * Method that opens a file
         */
        public void openFile() {

            JFileChooser fileChooser = new JFileChooser();

            // Cannot resolve .dYSM files
            FileNameExtensionFilter filterJPG = new FileNameExtensionFilter(
                    "JPG, JPEG", "jpg", "jpeg");
            fileChooser.setFileFilter(filterJPG);

            FileNameExtensionFilter filterGIF = new FileNameExtensionFilter(
                    "GIF", "gif");
            fileChooser.setFileFilter(filterGIF);

            FileNameExtensionFilter filterTIF = new FileNameExtensionFilter(
                    "TIF, TIFF", "tif", "tiff");
            fileChooser.setFileFilter(filterTIF);

            FileNameExtensionFilter filterPNG = new FileNameExtensionFilter(
                    "PNG", "png");
            fileChooser.setFileFilter(filterPNG);

            FileNameExtensionFilter filterBMP = new FileNameExtensionFilter(
                    "BMP", "bmp");
            fileChooser.setFileFilter(filterBMP);

            FileNameExtensionFilter filterWBEP = new FileNameExtensionFilter(
                    "WBEP", "wbep");
            fileChooser.setFileFilter(filterWBEP);

            FileNameExtensionFilter filterAllTypes = new FileNameExtensionFilter(
                    Andie.bundle.getString("AllSupportedFiletypes"), "jpg", "jpeg", "gif", "tif", "tiff", "png", "bmp",
                    "wbep");
            fileChooser.setFileFilter(filterAllTypes);

            int result = fileChooser.showOpenDialog(Andie.getFrame());

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    isOpened = true;
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();

                    // Finding image dimensions in order to be able to bound selection box
                    // Read the image file
                    // BufferedImage image = ImageIO.read(new File(imageFilepath));
                    // Get the dimensions of the image
                    // int width = image.getWidth();
                    // int height = image.getHeight();

                    target.getImage().open(imageFilepath);

                    // Removes any previous mouse listener instances
                    for (MouseListener listener : target.getMouseListeners()) {
                        target.removeMouseListener(listener);
                    }

                    // Adding mouse lisener to target panel
                    target.addMouseListener(new MouseSelection(target));

                    Andie.allCertainMenuStatus = true;
                    Andie.changeAllCertainMenuStatus(Andie.allCertainMenuStatus);
                    Andie.toolbar.changeCropStatus(false);
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("FileTypeErr"),
                            Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                }
            } else {
            }

        }

    }

    /**
     * <p>
     * Action to save an image without saving the .ops file.
     * i.e., to actually make changes to the image.
     * </p>
     *
     * @see EditableImage#open(String)
     */
    public class FileExportAction extends ImageAction {
        /**
         * <p>
         * Create a new file-open action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file export action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a format they wants to export and export
         * to an image without .ops file.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if (isOpened == true) { // The user doesn't have to save first when exporting images
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

                // Add file filters for different image formats
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", Andie.bundle.getString("JPG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", Andie.bundle.getString("TIFF")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", Andie.bundle.getString("PNG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", Andie.bundle.getString("BMP")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", Andie.bundle.getString("WBEP")));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", Andie.bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // Codes below are generated by AI - ChatGPT 4.0

                        // gather the filter user chose
                        FileFilter selectedFilter = fileChooser.getFileFilter();

                        // get the extension user chose
                        String selectedExtension = ((ImageFileFilter) selectedFilter).getExtension();

                        File selectedFile = fileChooser.getSelectedFile();
                        // check whether there is the extension in user's operation, if yes, then not
                        // add one more time
                        String selectedFilePath = selectedFile.getCanonicalPath();
                        if (!selectedFilePath.toLowerCase().endsWith(selectedExtension.toLowerCase())) {
                            selectedFilePath += "." + selectedExtension.toLowerCase();
                        }
                        // end of AI codes

                        // Check if the file already exists
                        File file = new File(selectedFilePath);
                        if (file.exists()) {
                            // Show a warning message
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Do you want to replace the existing file?",
                                    "Confirm Overwrite",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (response != JOptionPane.YES_OPTION) {
                                fileExport.actionPerformed(e);
                                return; // Do not overwrite the file
                            }
                        }

                        String format = ((ImageFileFilter) selectedFilter).getExtension();

                        target.getImage();
                        ImageIO.write(target.getImage().getCurrentImage(), format, new File(selectedFilePath));

                        // create a message box to tell user it's saved successfully
                        JOptionPane.showMessageDialog(null,
                                Andie.bundle.getString("ImageExportSaveSuccess"),
                                Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * <p>
         * A file filter for image files.
         * </p>
         */
        private class ImageFileFilter extends FileFilter {
            private String extension;
            private String description;

            /**
             * <p>
             * Create a new image file filter.
             * </p>
             *
             * @param extension   The extension of the image format.
             * @param description A description of the image format.
             */
            public ImageFileFilter(String extension, String description) {
                this.extension = extension.toLowerCase();
                this.description = description;
            }

            /**
             * <p>
             * Check whether a file is accepted by this filter.
             * </p>
             *
             * @param f The file to check.
             * @return True if the file is accepted, false otherwise.
             */
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith("." + extension);
            }

            /**
             * <p>
             * Get a description of this file filter.
             * </p>
             *
             * @return A description of this file filter.
             */
            public String getDescription() {
                return description + String.format(" (*.%s)", extension);
            }

            /**
             * <p>
             * Get the extension of this file filter.
             * </p>
             *
             * @return The extension of this file filter.
             */
            public String getExtension() {
                return extension;
            }
        }
    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     *
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // disestablished the codes below as it's a weird design
                // if (EditableImage.hasOpsFile == false) {
                // // these codes below don't need multi lingual support
                // FileSaveAsAction saveAsAction = new FileSaveAsAction("Save As", null, "Save a
                // copy",
                // Integer.valueOf(KeyEvent.VK_A));
                // saveAsAction.actionPerformed(e);
                // // No need for the command below because otherwise if the user clicked
                // // the button and closed the new window without saving anything,
                // // the command below will set the value to true and cause bugs!
                // // isSaved = true;
                // // then we must change the status to true
                // // otherwise it will ask the user to save a copy again
                // EditableImage.hasOpsFile = true;
                // } else {
                target.getImage().save();

                // then we treat it as saved so that next time we run the code, it will
                // trigger the "else" statement outside of this loop
                EditableImage.isOpsNotEmptyStatus = false;

                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                        Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);
                // }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image and the ops file to a new file location.
     * </p>
     *
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();

            if (isOpened == true) {

                fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" filter

                // Add file filters for different image formats
                fileChooser.addChoosableFileFilter(new ImageFileFilter("JPG", Andie.bundle.getString("JPG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("TIFF", Andie.bundle.getString("TIFF")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("PNG", Andie.bundle.getString("PNG")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("BMP", Andie.bundle.getString("BMP")));
                fileChooser.addChoosableFileFilter(new ImageFileFilter("WBEP", Andie.bundle.getString("WBEP")));
                fileChooser
                        .addChoosableFileFilter(
                                new ImageFileFilter("GIF", Andie.bundle.getString("Memetype")));

                int result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // gather the filter user chose
                        FileFilter selectedFilter = fileChooser.getFileFilter();

                        // get the extension user chose
                        String selectedExtension = ((ImageFileFilter) selectedFilter).getExtension();

                        File selectedFile = fileChooser.getSelectedFile();
                        // check whether there is the extension in user's operation, if yes, then not
                        // add one more time
                        String selectedFilePath = selectedFile.getCanonicalPath();
                        if (!selectedFilePath.toLowerCase().endsWith(selectedExtension.toLowerCase())) {
                            selectedFilePath += "." + selectedExtension.toLowerCase();
                        }

                        // Check if the file already exists
                        File file = new File(selectedFilePath);
                        if (file.exists()) {
                            // Show a warning message
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Do you want to replace the existing file?",
                                    "Confirm Overwrite",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (response != JOptionPane.YES_OPTION) {
                                // return; // Do not overwrite the file
                                fileSaveAs.actionPerformed(e);
                                return; // crucial but lovely statement
                            }
                        }

                        // Save the image
                        target.getImage().saveAs(selectedFilePath);

                        // then we treat it as saved so that next time we run the code, it will
                        // trigger the "else" statement outside of this loop
                        EditableImage.isOpsNotEmptyStatus = false;

                        // create a message box to tell user it's saved successfully
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                                Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("MyDearUser"),
                                Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * <p>
         * A file filter for image files.
         * </p>
         */
        private class ImageFileFilter extends FileFilter {
            private String extension;
            private String description;

            /**
             * Constructor of ImageFileFilter
             *
             * @param extension   the extension
             * @param description the description of the file filter
             */
            public ImageFileFilter(String extension, String description) {
                this.extension = extension.toLowerCase();
                this.description = description;
            }

            /**
             * <p>
             * Check whether a file is accepted by this filter.
             * </p>
             *
             * @param f The file to check.
             * @return True if the file is accepted, false otherwise.
             */
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith("." + extension);
            }

            /**
             * Gets the description of this file filter.
             *
             * @return The description of the file filter.
             */
            public String getDescription() {
                return description + String.format(" (*.%s)", extension);
            }

            /**
             * Gets the extension associated with this file filter.
             *
             * @return The extension of the file filter.
             */
            public String getExtension() {
                return extension;
            }
        }

    }

    /**
     * A class that represents the action of printing an image.
     */
    public class FilePrintAction extends AbstractAction {
        // private BufferedImage imageToPrint;

        /**
         * <p>
         * Create a new image-print action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FilePrintAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the image-print action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the image-print is triggered.
         * It call the system printing API to print the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (isOpened == true) {

                boolean b1Choose = false;
                boolean b2Choose = false;

                JPanel panel = new JPanel(new GridLayout(2, 2));

                JLabel l1 = new JLabel(Andie.bundle.getString("PrintMethodInstruction"));
                panel.add(l1);
                JLabel l2 = new JLabel();
                panel.add(l2);

                ButtonGroup group = new ButtonGroup();
                JRadioButton b1 = new JRadioButton("100%");
                JRadioButton b2 = new JRadioButton(Andie.bundle.getString("FitImage"));
                group.add(b1);
                group.add(b2);
                panel.add(b1);
                panel.add(b2);

                // Show the option dialog
                int option = JOptionPane.showOptionDialog(null, panel, Andie.bundle.getString("PrintMethod"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                    // Do nothing, but we keep this statement for possible future function expands.
                } else if (option == JOptionPane.OK_OPTION) {
                    if (b1.isSelected()) {
                        b1Choose = true;
                    } else if (b2.isSelected()) {
                        b2Choose = true;
                    }

                    if (!b1Choose && !b2Choose) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("PleaseChoose"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                        // Will educate the user if they didn't give any inputs and still wanna hit the
                        // OK button
                    } else {
                        PrintImage p = new PrintImage(b2Choose);
                        p.printImage();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }
        }// end of actionPerformed
    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (isOpened == true && EditableImage.isOpsNotEmptyStatus == true) {

                Object[] options = { Andie.bundle.getString("Yes"), Andie.bundle.getString("No"),
                        Andie.bundle.getString("Cancel") };

                int n = JOptionPane.showOptionDialog(
                        Andie.getFrame(),
                        Andie.bundle.getString("DoYouWantToSaveBeforeExit"),
                        Andie.bundle.getString("Warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (n == 0) { // yes
                    // if user chose yes and there's no ops file saved
                    // then call "save"
                    actions.get(1).actionPerformed(e);

                    // create a message box to tell user it's saved successfully
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("ImageSaveSuccess"),
                            Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                    // then we treat it as saved so that next time we run the code, it will
                    // trigger the "else" statement outside of this loop
                    EditableImage.isOpsNotEmptyStatus = false;

                    // then close window
                    System.exit(0);

                } else if (n == 1) { // no
                    System.exit(0);
                } else {
                    // Should change this statement to case control cuz the "else" here is
                    // useless...
                }
            } else {
                System.exit(0);
            }
        }// end of actionPerformed

    }

    /**
     * <p>
     * Action to change the language.
     * by Kevin Sathyanath, adapted from Yuxing's Resize Image work.
     * </p>
     *
     */
    public class FileChangeLanguageAction extends ImageAction {
        /** The height */
        int height;
        /** The width */
        int width;
        /** The labels */
        JLabel widthJLabel, heightLabel, titleLabel, blankLabel;
        /** The textfields containing language options */
        JTextField widthField, heightField;

        /**
         * <p>
         * Create a Language Change Action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileChangeLanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Change Language action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Change Language Action is triggered.
         * </p>
         *
         * @param e The event triggering this callback.
         */

        @Override
        public void actionPerformed(ActionEvent e) {
            Andie.exitFullScreen();// to prevent the window from resizing unexpectedly

            JFrame l = new JFrame();

            // Set behaviour of frame
            l.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            l.setBounds(200, 200, 300, 200);
            Container c = l.getContentPane();
            c.setLayout(new FlowLayout());

            JButton english = new JButton("English - NZ");
            JButton bahasa = new JButton("Bahasa Indonesia - ID");
            JButton traChinese = new JButton("繁體中文 - TW");

            english.setSize(500, 30);
            english.setLocation(100, 100);
            c.add(english);
            bahasa.setSize(400, 30);
            bahasa.setLocation(100, 250);
            c.add(bahasa);
            traChinese.setSize(400, 30);
            traChinese.setLocation(100, 400);
            c.add(traChinese);

            english.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Andie.exitFullScreen();
                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("en", "NZ"));
                    p.put("language", "en");
                    p.put("country", "NZ");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                    // System.out.println(p.get("language", "id"));
                    Andie.setLanguage();
                    // l.setExtendedState(JFrame.NORMAL);
                    l.dispose();
                    // l.setExtendedState(JFrame.NORMAL);
                }
            });

            bahasa.addActionListener(new ActionListener() {
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e) {
                    Andie.exitFullScreen();
                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("id", "ID"));
                    p.put("language", "id");
                    p.put("country", "ID");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");
                    // System.out.println(p.get("language", "en"));
                    Andie.getStatus();
                    Andie.setLanguage();
                    // l.setExtendedState(JFrame.NORMAL);
                    l.dispose();
                    // l.setExtendedState(JFrame.NORMAL);
                }
            });
            // test?
            traChinese.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Andie.exitFullScreen();
                    Preferences p = Preferences.userNodeForPackage(Andie.class);
                    Locale.setDefault(new Locale("zh", "TW"));
                    p.put("language", "zh");
                    p.put("country", "TW");
                    Andie.bundle = ResourceBundle.getBundle("cosc202/andie/MessageBundle");

                    // System.out.println(p.get("language", "zh"));
                    Andie.setLanguage();
                    // System.out.println(Andie.bundle.getString("EnterFilterRadius"));
                    // l.setExtendedState(JFrame.NORMAL);
                    l.dispose();

                }

            });
            l.setVisible(true);
        }
    }
}