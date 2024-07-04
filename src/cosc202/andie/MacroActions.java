package cosc202.andie;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;


/**
 * <p>
 * Actions provided by the Action menu.
 * </p>
 *
 * <p>
 * The Macro menu contains actions that could save all
 * the operations performed between the start and stop actions.
 * Could also apply an ops file on another image to preform
 * the operations form the opened ops file
 *
 *</p>
 *
 * @author YUXING ZHANG
 * @version 1.0
 *
 */
public class MacroActions {
    /**An arrayList of actions to help in creatig the ops file */
    protected ArrayList<Action> actions;
    /** The file where the operation sequence is stored. */
    public ResourceBundle bundle = Andie.bundle;


    /**
     * <p>
     * Create a set of Macro menu actions.
     * </p>
     */
    public MacroActions() {

        actions = new ArrayList<Action>();
        ImageIcon startIcon = new ImageIcon("src/cosc202/andie/icons/start.png");
        //Image downloaded from: https://www.flaticon.com/free-icon/play_9581128?term=start&page=1&position=8&origin=search&related_id=9581128
        actions.add(new StartAction(Andie.bundle.getString("Start"), startIcon, Andie.bundle.getString("StartMacro"), Integer.valueOf(KeyEvent.VK_S)));

        ImageIcon stopIcon = new ImageIcon("src/cosc202/andie/icons/stop.png");
        //Image downloaded from: <a href="https://www.flaticon.com/free-icons/forbidden" title="forbidden icons">Forbidden icons created by Vitaly Gorbachev - Flaticon</a>
        actions.add(new StopAction(Andie.bundle.getString("Stop"), stopIcon, Andie.bundle.getString("Redo"),
        Integer.valueOf(KeyEvent.VK_T)));

        ImageIcon applyIcon = new ImageIcon("src/cosc202/andie/icons/Apply.png");
        //Icon downloaded from: <a href="https://www.flaticon.com/free-icons/finger" title="finger icons">Finger icons created by pojok d - Flaticon</a>
        actions.add(new ApplyPrevMacroAction(Andie.bundle.getString("Apply"), applyIcon, Andie.bundle.getString("Redo"),
        Integer.valueOf(KeyEvent.VK_A)));
    }

    /**
     * <p>
     * Create a menu containing the list of Macro actions.
     * </p>
     *
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu macroMenu = new JMenu(Andie.bundle.getString("Macro"));

        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

        /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     * @param status the boolean that decided whether the items in the menu are greyed out.
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Create a macro start recording action.
     * </p>
     *
     */
    public class StartAction extends ImageAction {

        StartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        public void actionPerformed(ActionEvent e) {
            if (FileActions.isOpened == true) {
                try {
                    EditableImage.recordingStart = true;
                    //System.out.println("Start Recording");
                    // target.getImage().recordOperations(op);
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception err) {
                    if (err instanceof EmptyStackException) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("NotOpenedOrFirstStep"),
                                Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                //System.out.println("Exception");
                JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
            }

        }
    }


     /**
     * <p>
     * Create a macro stop recording action.
     * </p>
     *
     * This class peforms a stop action of the macro action
     * and save the operations to a new file
     *
     */
    public class StopAction extends ImageAction {

        /**A constructor
         * @param name the name of the action
         * @param icon the icon
         * @param desc  the Description
         * @param mnemonic the mnemonic key
         */
        StopAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
          if(EditableImage.recordingStart == true||EditableImage.macroStack.empty()==true){
            try {
                // make a JOption to let user choose if they needs to
                // yes: save the oerations or
                // cancel: keep adding some other operations
                // no: delete all operations and starts again
                int opsSaveOrNot = JOptionPane.showConfirmDialog(Andie.getFrame(),
                        Andie.bundle.getString("WouldSaveOPS"), Andie.bundle.getString("SaveMacro"),
                        JOptionPane.YES_NO_CANCEL_OPTION);

                if (opsSaveOrNot == JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("OPSSaveCancel"),
                    "" , JOptionPane.WARNING_MESSAGE);

                }

                else if (opsSaveOrNot == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("DiscardMacro"),
                    "" , JOptionPane.WARNING_MESSAGE);
                     EditableImage.macroStack.clear();
                     EditableImage.recordingStart = false;

                }

                // if user click ok, then let the user choose a file to save the ops file
                else if (opsSaveOrNot == JOptionPane.OK_OPTION) {


                    JFileChooser fileChooser = new JFileChooser();

                    // Add file filters for OPS format only
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("OPS Files (*.ops)", "ops");

                    fileChooser.addChoosableFileFilter(filter);
                    fileChooser.setFileFilter(filter);

                    //show the save window
                    int chosed = fileChooser.showSaveDialog(Andie.getFrame());
                    if (chosed == JFileChooser.APPROVE_OPTION) {
                        try {
                             File fileToSave = fileChooser.getSelectedFile();

                            // Append ".ops" extension if not already present
                            String filePath = fileToSave.getAbsolutePath();
                            if (!filePath.toLowerCase().endsWith(".ops")) {
                                //System.out.println("no ops extension added");

                                filePath += ".ops";
                            }

                            // Rename the file with the enforced extension
                            File renamedFile = new File(filePath);
                            fileToSave.renameTo(renamedFile);
                            //System.out.println("File saved as: " + renamedFile.getAbsolutePath());


                            EditableImage.isOpsNotEmptyStatus = false;
                            FileOutputStream fileOut = new FileOutputStream(filePath);
                            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                            objOut.writeObject(EditableImage.macroStack);
                            objOut.close();
                            fileOut.close();

                            // create a message box to tell user it's saved successfully
                            JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("OPSFileSaved"),
                                    Andie.bundle.getString("Information"), JOptionPane.WARNING_MESSAGE);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("MyDearUser"),
                                    Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                        }

                        EditableImage.recordingStart = false;
                        EditableImage.macroStack.clear();// empty the stack after save the ops file
                        // }else if(chosed==JFileChooser.CANCEL_OPTION){

                        // }else if(chosed==JFileChooser.ERROR_OPTION ){

                        // }
                        // target.getImage().;
                        target.repaint();
                        target.getParent().revalidate();
                    }
                }
            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("NotOpenedOrFirstStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }else{
            JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("MacroInstruction"),
            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

        }

    }
    }
    /**A class that applies previously recorded macro actions on a new image.
     * @author Yuxing Zhang
     * @version 1.0
     */
    public class ApplyPrevMacroAction extends ImageAction {

        /**A constructor
         * @param name the name of the action
         * @param icon the icon
         * @param desc  the Description
         * @param mnemonic the mnemonic key
         */
        ApplyPrevMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if(FileActions.isOpened == true){

        try{

                // System.out.println("Select ops file");
                JFileChooser fileChooser = new JFileChooser();

                // Add file filters for OPS format only
                FileNameExtensionFilter filter = new FileNameExtensionFilter("OPS Files (*.ops)", "ops");

                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setFileFilter(filter);

                // Show open dialog
                int userSelection = fileChooser.showOpenDialog(Andie.getFrame());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    String opsFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    if (!opsFilepath.toLowerCase().endsWith(".ops")) {
                        JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("FileTypeErr"),
                        Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // try{
                    FileInputStream fileinput = new FileInputStream(opsFilepath);
                    try (ObjectInputStream obInput = new ObjectInputStream(fileinput)) {
                        @SuppressWarnings("unchecked")
                        Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) obInput.readObject();


                        for (int i = 0; i < opsFromFile.size(); i++) {
                            target.getImage().apply(opsFromFile.get(i));
                            target.repaint();
                            target.getParent().revalidate();
                        }
                        // }catch(){
                    }



                    // }

                    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                } else if (userSelection == JFileChooser.CANCEL_OPTION) {
                    //System.out.println("Operation canceled.");
                }

            } catch (Exception err) {
                if (err instanceof EmptyStackException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("NotOpenedOrFirstStep"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

    }else{
         //System.out.println("Exception");
         JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
         Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);

    }

    }
    }
}
