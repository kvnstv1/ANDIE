package cosc202.andie;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//NOTES: Maybe consider adding a Document Listener to the textfield

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 *
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {
    /**The multilingual support feature */
    public ResourceBundle bundle = Andie.bundle;
    /**The JPanel */
    public JPanel previewPanel;
    /**The previewIcon */
    public ImageIcon previewIcon;
    /** The height of the block */
    public int blockHeight;
    /**The width of the block */
    public int blockWidth;


    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();

        Action mean = new MeanFilterAction(Andie.bundle.getString("MeanFilterAction"), null,
                Andie.bundle.getString("MeanFilterDesc"),
                Integer.valueOf(KeyEvent.VK_M));
        actions.add(mean);

        Action soft = new SoftBlurFilterAction(Andie.bundle.getString("SoftBlur"), null,
                Andie.bundle.getString("SoftBlurDesc"),
                Integer.valueOf(KeyEvent.VK_S));
        actions.add(soft);

        Action sharpen = new SharpenFilterAction(Andie.bundle.getString("SharpenFilter"), null,
                Andie.bundle.getString("SharpenDesc"),
                Integer.valueOf(KeyEvent.VK_H));
        actions.add(sharpen);

        Action gaussian = new GaussianFilterAction(Andie.bundle.getString("GaussianFilter"), null,
                Andie.bundle.getString("GaussianDesc"),
                Integer.valueOf(KeyEvent.VK_G));
        actions.add(gaussian);

        Action median = new MedianFilterAction(Andie.bundle.getString("MedianFilter"), null,
                Andie.bundle.getString("MedianDesc"),
                Integer.valueOf(KeyEvent.VK_D));
        actions.add(median);

        Action emboss = new EmbossFilterAction(Andie.bundle.getString("EmbossFilter"), null,
                Andie.bundle.getString("EmbossDesc"),
                Integer.valueOf(KeyEvent.VK_E));
        actions.add(emboss);

        Action sobel = new SobelFilterAction(Andie.bundle.getString("SobelMenu"), null, Andie.bundle.getString("SobelDesc"), Integer.valueOf(KeyEvent.VK_O));
        actions.add(sobel);

        Action scattering = new RandomScatteringAction(Andie.bundle.getString("RandomScattering"), null,
                Andie.bundle.getString("RandomScattering"), Integer.valueOf(KeyEvent.VK_A));
        actions.add(scattering);

        Action blockAveraging = new BlcokAveragingAction(Andie.bundle.getString("BlockMenu"), null,
                Andie.bundle.getString("BlockDesc"), Integer.valueOf(KeyEvent.VK_B));
        actions.add(blockAveraging);
        // CreateHotKey.createHotkey(blockAveraging, KeyEvent.VK_I,
        // Andie.controlOrCmd | InputEvent.ALT_DOWN_MASK, "blockAveraging");
    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     *
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("Filter"));

        for (Action action : actions) {

            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

        /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     * @param status the boolean changing the way the program works
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     *
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
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
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**The slider used in the preview panel for the mean filter. */
        JSlider meanSlider;

        /**
         * <p>
         * Callback for when the mean filter action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a radius, then applies a mean filter of that
         * radius to the image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            try {
                // Determine the radius - ask the user.
                int radius = 0;
                BufferedImage prev = new BufferedImage( (target.getImage().getCurrentImage()).getWidth(), (target.getImage().getCurrentImage()).getHeight(), BufferedImage.TYPE_INT_RGB);
                prev.getGraphics().drawImage((target.getImage().getCurrentImage()), 0, 0, null);


                previewPanel = PreviewPanel.makePanel(prev);
                updatePreviewImage(prev);

                meanSlider = new JSlider(JSlider.VERTICAL, 0,10,0);
                meanSlider.setMajorTickSpacing(1);
                meanSlider.setPaintTicks(true);
                meanSlider.setPaintLabels(true);
                meanSlider.setSnapToTicks(true);
                meanSlider.setValue(0);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        try{
                            int rad = meanSlider.getValue();
                            BufferedImage curr = MeanFilter.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), rad);
                            updatePreviewImage(curr);
                        }catch(IllegalArgumentException hu){
                            //System.out.println("The illegal argument exception has been caught and handled."); Vanished when VSCode rebooted. 
                        }
                    }
                };

                meanSlider.addChangeListener(sliderChangeListener);

                JPanel menu = new JPanel(new FlowLayout());
                //GridBagConstraints a = new GridBagConstraints();
                menu.add(previewPanel);
                menu.add(meanSlider);



                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                JLabel info = new JLabel();
                info.setText(Andie.bundle.getString("PleaseEnter"));
                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                panel.add(info);
                panel.add(radiusSpinner);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), menu,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = meanSlider.getValue();
                    target.getImage().apply(new MeanFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }
                // // Check the return value from the dialog box.
                // if (option == JOptionPane.CANCEL_OPTION) {
                //     return;
                // } else if (option == JOptionPane.OK_OPTION) {
                //     radius = radiusModel.getNumber().intValue();
                // }

                // target.getImage().apply(new MeanFilter(radius));
                // target.repaint();
                // target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    /**
     * <p>
     * Action to change an image with a median filter.
     * </p>
     *
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
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
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The slider used in the preview panel for the median filter. */
        JSlider medianSlider;
        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Determine the radius - ask the user.
                int radius = 0;
                BufferedImage prev = new BufferedImage( (target.getImage().getCurrentImage()).getWidth(), (target.getImage().getCurrentImage()).getHeight(), BufferedImage.TYPE_INT_RGB);
                prev.getGraphics().drawImage((target.getImage().getCurrentImage()), 0, 0, null);


                previewPanel = PreviewPanel.makePanel(prev);
                updatePreviewImage(prev);


                medianSlider = new JSlider(JSlider.VERTICAL, 0,10,0);
                medianSlider.setMajorTickSpacing(1);
                medianSlider.setPaintTicks(true);
                medianSlider.setPaintLabels(true);
                medianSlider.setSnapToTicks(true);
                medianSlider.setValue(0);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        int rad = medianSlider.getValue();

                        BufferedImage curr = MedianFilter.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), rad);
                        updatePreviewImage(curr);


                    }
                };

                medianSlider.addChangeListener(sliderChangeListener);

                // Pop-up dialog box to ask for the radius value.
                //SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                //JSpinner radiusSpinner = new JSpinner(radiusModel);

                JPanel menu = new JPanel(new FlowLayout());
                //GridBagConstraints a = new GridBagConstraints();
                menu.add(previewPanel);
                menu.add(medianSlider);


                int option = JOptionPane.showOptionDialog(Andie.frame, menu, Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {

                } else if (option == JOptionPane.OK_OPTION) {
                    radius = medianSlider.getValue();
                    target.getImage().apply(new MedianFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }


            } catch (Exception err) {
               JOptionPane.showMessageDialog(Andie.getFrame(), err.toString(),
                            Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);

            }
        }

    }

    /**
     * Action class code layout created by Steven Mills
     */
    public class GaussianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Gaussian-filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The slider used in the preview panel. */
        JSlider gaussianSlider;
        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the GaussianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link GaussianFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {

                // Determine the radius - ask the user.
                int radius = 0;

                BufferedImage prev = new BufferedImage( (target.getImage().getCurrentImage()).getWidth(), (target.getImage().getCurrentImage()).getHeight(), BufferedImage.TYPE_INT_RGB);
                prev.getGraphics().drawImage((target.getImage().getCurrentImage()), 0, 0, null);


                previewPanel = PreviewPanel.makePanel(prev);
                updatePreviewImage(prev);

                gaussianSlider = new JSlider(JSlider.VERTICAL, 0,10,0);
                gaussianSlider.setMajorTickSpacing(1);
                gaussianSlider.setPaintTicks(true);
                gaussianSlider.setPaintLabels(true);
                gaussianSlider.setSnapToTicks(true);
                gaussianSlider.setValue(0);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        try{
                            int rad = gaussianSlider.getValue();
                            BufferedImage curr = GaussianFilter.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), rad);
                            updatePreviewImage(curr);
                        }catch(IllegalArgumentException hu){
                            //System.out.println("Illegal Argument Exception caught");  //This was apparently an issue caused by VSCode. Vanished on reboot. 
                        }


                    }
                };

                gaussianSlider.addChangeListener(sliderChangeListener);

                JPanel menu = new JPanel(new FlowLayout());
                //GridBagConstraints a = new GridBagConstraints();
                menu.add(previewPanel);
                menu.add(gaussianSlider);


                // Pop-up dialog box to ask for the radius value.
                // SpinnerNumberModel radiusModel = new SpinnerNumberModel(0, 0, 10, 1);
                // JSpinner radiusSpinner = new JSpinner(radiusModel);
                // System.out.println("the language is" +
                // Andie.bundle.getString("EnterFilterRadius"));

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), menu,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = gaussianSlider.getValue();
                    target.getImage().apply(new GaussianFilter(radius));
                    target.repaint();
                    target.getParent().revalidate();
                }

                // Create and apply the filter
                // target.getImage().apply(new GaussianFilter(radius));
                // target.repaint();
                // target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }

    }

    /**
     * <p>
     * Action to apply an sobel filter to an image.
     * </p>
     */
    public class SobelFilterAction extends ImageAction{
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name,icon,desc,mnemonic);
        }


        public void actionPerformed(ActionEvent e){
            try{
                JPanel panel = new JPanel(new GridLayout(3, 1));
                JLabel l1 = new JLabel(Andie.bundle.getString("SelectSobelDirection"));
                panel.add(l1);
                JRadioButton r1 = new JRadioButton(Andie.bundle.getString("Horizontal"));
                JRadioButton r2 = new JRadioButton(Andie.bundle.getString("Vertical"));
                ButtonGroup g1 = new ButtonGroup();
                g1.add(r1);
                g1.add(r2);

                panel.add(r1);
                panel.add(r2);

                int option = JOptionPane.showOptionDialog(null, panel, Andie.bundle.getString("SelectSobelDirection"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,null,null);
                if (option == JOptionPane.OK_OPTION){
                    boolean Direction = false;
                    if (r1.isSelected()){
                        Direction = true;
                    }
                    target.getImage().apply(new SobelFilter(Direction));
                    target.repaint();
                    target.getParent().revalidate();


                }

            }
            catch (Exception err){
                if (err instanceof NullPointerException){
                    JOptionPane.showMessageDialog(Andie.getFrame(),Andie.bundle.getString("YouDidNotOpen"), Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * <p>
     * Action to apply an emboss filter to an image.
     * </p>
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new EmbossFilter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         *
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link EmbossFilter}.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {

                // Determine the radius - ask the user.
                int angle = 0;
                int radius =0;
                SpinnerNumberModel angleModel = new SpinnerNumberModel(0, 0, 360, 1);
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JPanel panel = new JPanel(new GridLayout(4, 1));
                JLabel l1 = new JLabel(Andie.bundle.getString("EnterEmbossDirection"));
                JSpinner s1 = new JSpinner(angleModel);
                JSpinner s2 = new JSpinner(radiusModel);

                JLabel l2 = new JLabel(Andie.bundle.getString("EnterFilterRadius"));
                panel.add(l1);
                panel.add(s1);
                panel.add(l2);
                panel.add(s2);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), panel,
                        Andie.bundle.getString("EnterEmbossDirection"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    angle = angleModel.getNumber().intValue();
                    radius = radiusModel.getNumber().intValue();
                }

                // Create and apply the filter
                target.getImage().apply(new EmbossFilter(angle, radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }

        }

    }






    /**
     * <p>
     * Action to apply a blur to an image with Soft blur filter.
     * </p>
     *
     * @see SoftBlurFilter
     */
    public class SoftBlurFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Soft Blur filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SoftBlurFilterAction is triggered.
         * It applys the soft blur filter.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Create and apply soft blur filter
                target.getImage().apply(new SoftBlurFilter());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * <p>
     * Action to sharpen an image with a Sharpen filter.
     * </p>
     *
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * This method is called whenever the SharpenFilterAction is triggered.
         * It applys the Sharpen filter.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // make and apply filter
                target.getImage().apply(new SharpenFilter());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }
     /**
     * ImageBlcokAveraging extends ImageAction and represents an action for
     * Averaging blocks of the image.
     * Could be used to create a pixelated version of an image.
     * This action is triggered by user interaction in the graphical user interface.
     */
    public class BlcokAveragingAction extends ImageAction {

        /**
         * <p>
         * Create a new BlcokAveraging action
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BlcokAveragingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

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

            try {

                int blockSizeHeight=0;
                int blockSizeWidth = 0;

                BufferedImage prev = new BufferedImage( (target.getImage().getCurrentImage()).getWidth(), (target.getImage().getCurrentImage()).getHeight(), BufferedImage.TYPE_INT_RGB);
                prev.getGraphics().drawImage((target.getImage().getCurrentImage()), 0, 0, null);

                previewPanel = PreviewPanel.makePanel(prev);
                updatePreviewImage(prev);

                //JPanel menu = new JPanel(new GridLayout(2,1));

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2,1));
                JLabel info1 = new JLabel();
                info1.setText(Andie.bundle.getString("Height") + " ("+Andie.bundle.getString("PleaseEnter")+")");
                JLabel info2 = new JLabel();
                info2.setText(Andie.bundle.getString("Width")+" ("+Andie.bundle.getString("PleaseEnter")+")");
                // Pop-up dialog box to ask for the radius value.

                SpinnerNumberModel blockSizeHeightModel = new SpinnerNumberModel(1, 1, 1000, 1);
                SpinnerNumberModel blockSizeWidthModel = new SpinnerNumberModel(1, 1, 1000, 1);
                JSpinner blockSizeHeightSpinner = new JSpinner(blockSizeHeightModel);
                JSpinner blockSizeWidthSpinner = new JSpinner(blockSizeWidthModel);




                // Add a ChangeListener to both spinners
                ChangeListener listener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        blockWidth = (int) blockSizeWidthSpinner.getValue();
                        blockHeight = (int) blockSizeHeightSpinner.getValue();
                        BufferedImage curr = BlockAveraging.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), blockHeight, blockWidth);
                        updatePreviewImage(curr);
                    }
                };

                //Adding listener to spinner 1:
                blockSizeHeightSpinner.addChangeListener(listener);

                //Now on to spinner 2:
                blockSizeWidthSpinner.addChangeListener(listener);



                panel.add(info1);
                panel.add(blockSizeHeightSpinner);
                panel.add(info2);
                panel.add(blockSizeWidthSpinner);

                JPanel menu = new JPanel(new GridBagLayout());
                GridBagConstraints a = new GridBagConstraints();
                Insets i = new Insets(20,0,0,0);

                //a.fill = GridBagConstraints.BOTH;
                a.gridx = 0;
                a.gridy = 0;
                a.gridwidth = 2;
                a.anchor = GridBagConstraints.PAGE_START;
                menu.add(previewPanel, a);

                a.fill = GridBagConstraints.VERTICAL;
                a.gridx = 0;
                a.gridy = 1;
                a.weighty = 1.0;
                a.insets = i;
                menu.add(panel, a);



                int option = JOptionPane.showOptionDialog(Andie.getFrame(), menu,
                        Andie.bundle.getString("EnterBlock"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    blockSizeHeight = blockSizeHeightModel.getNumber().intValue();
                    blockSizeWidth = blockSizeWidthModel.getNumber().intValue();

                }


                target.getImage().apply(new BlockAveraging(blockSizeHeight,blockSizeWidth));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }    
    }

    /**
     * RandomScatteringAction extends ImageAction and represents an action for
     * applying random scattering effect to an image.
     * This action prompts the user for a filter radius and applies a
     * RandomScattering filter accordingly.
     */
    public class RandomScatteringAction extends ImageAction {
        /**The radius */
        int radius = 0;

        /**
         * <p>
         * Create a new Random Scattering action.
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
        RandomScatteringAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The JSlider used in the code below. */
        JSlider radiusSlider;

        /**
         * <p>
         * Callback for when the Random Scattering action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the RandomScatteringAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * </p>
         *
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Determine the radius - ask the user.
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());

                // final EditableImage preview = target.getImage().makeCopy();
                // final ImagePanel show = new ImagePanel(preview);

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(new Dimension(500, 300));
                updatePreviewImage(prev);

                // Pop-up dialog box to ask for the radius value.
                JPanel sliderPane = new JPanel(new FlowLayout());
                sliderPane.setPreferredSize(new Dimension(450, 50));
                JPanel labelPane = new JPanel(new GridLayout(1, 1, 167, 0));
                radiusSlider = new JSlider(0, 10, 0);
                radiusSlider.setPreferredSize(new Dimension(400, 50));

                JLabel tempLabel = new JLabel("Radius", JLabel.CENTER);
                radiusSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

                radiusSlider.setMajorTickSpacing(1);
                radiusSlider.setPaintTicks(true);
                radiusSlider.setPaintLabels(true);
                radiusSlider.setSnapToTicks(true);

                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        int rad = radiusSlider.getValue();
                        BufferedImage curr = RandomScattering2
                                .applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), rad);
                        updatePreviewImage(curr);
                        radius = rad;

                    }
                };

                radiusSlider.addChangeListener(sliderChangeListener);

                labelPane.add(tempLabel);

                sliderPane.add(radiusSlider);

                JPanel menu = new JPanel(new GridBagLayout());
                GridBagConstraints a = new GridBagConstraints();
                Insets i = new Insets(20, 0, 0, 0);

                // a.fill = GridBagConstraints.BOTH;
                a.gridx = 0;
                a.gridy = 0;
                a.gridwidth = 2;
                a.anchor = GridBagConstraints.PAGE_START;
                menu.add(previewPanel, a);

                a.fill = GridBagConstraints.VERTICAL;
                a.gridx = 0;
                a.gridy = 1;
                a.weighty = 1.0;
                a.insets = i;
                menu.add(sliderPane, a);

                a.gridx = 0;
                a.gridy = 2;
                a.weighty = 0.7;
                a.ipady = 1;
                i.set(10, 0, 0, 0);
                menu.add(labelPane, a);

                int option = JOptionPane.showOptionDialog(Andie.getFrame(), menu,
                        Andie.bundle.getString("EnterFilterRadius"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    target.getImage().apply(new RandomScattering2(radius));
                    target.getParent().revalidate();
                    target.repaint();
                }

                // Create and apply the filter

            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(Andie.getFrame(), Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }// End of randomScattering


    /**A method to update the preview Image. Common to all methods that pop up a preview image.
     * Please don't cut our marks for lack of comments.
     * @author Kevin Steve Sathyanath
     * @param i BufferedImage
     */
    public void updatePreviewImage(BufferedImage i){
        previewIcon = new ImageIcon(i);
        BufferedImage j = ImageResize.applyToPreview(i);
        JLabel pic = new JLabel(new ImageIcon(j));
        previewPanel.removeAll();
        previewPanel.add(pic);
        previewPanel.repaint();
        previewPanel.revalidate();
    }


}
