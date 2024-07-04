package cosc202.andie;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 *
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * Eden has added the RGBSwapping function, now we have two functions
 * include greyscale as well, please MERGE WITH CARE.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @author Steven Mills
 * @version 1.0
 *          modified_by The Greatest Eden
 *          modified_date 10 Mar 2024
 */
public class ColourActions {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    /** The resource bundle for internationalization. */
    public ResourceBundle bundle = Andie.bundle;
    /** The image that will be previewed. */
    public BufferedImage previewImage;
    /** The icon of the preview image. */
    public ImageIcon previewIcon;
    /** The panel where the preview image will be displayed.  */
    public JPanel previewPanel;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();

        Action grey = new ConvertToGreyAction(Andie.bundle.getString("convertToGreyAction"), null,
                Andie.bundle.getString("GreyscaleDesc"), Integer.valueOf(KeyEvent.VK_G));
        actions.add(grey);

        Action invert = new ImageInvertAction(Andie.bundle.getString("invertColour"), null,
                Andie.bundle.getString("ImageInvertDesc"),
                Integer.valueOf(KeyEvent.VK_I));
        actions.add(invert);
        CreateHotKey.createHotkey(invert, KeyEvent.VK_I, Andie.controlOrCmd, "invert");

        Action rgbSwap = new RGBSwappingAction(Andie.bundle.getString("RGBSwappingAction"), null,
                Andie.bundle.getString("RGBSwapDesc"),
                Integer.valueOf(KeyEvent.VK_C));
        actions.add(rgbSwap);
        actions.add(new brightnessAndContrastAction(Andie.bundle.getString("BC"), null, Andie.bundle.getString("BCDesc"), Integer.valueOf(KeyEvent.VK_B)));

        Action sepia = new ConvertToSepiaAction(Andie.bundle.getString("Sepia"), null, Andie.bundle.getString("SepiaDesc"), Integer.valueOf(KeyEvent.VK_S));
        actions.add(sepia);

        Action temperature = new ChangeTemperatureAction(Andie.bundle.getString("Temp"), null, Andie.bundle.getString("TempDesc"), Integer.valueOf(KeyEvent.VK_T));
        actions.add(temperature);

        Action HSV = new ChangeHSVAction(Andie.bundle.getString("HSV"), null, Andie.bundle.getString("HSVDesc"), Integer.valueOf(KeyEvent.VK_H));
        actions.add(HSV);


    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     *
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(Andie.bundle.getString("Colour"));

        for (Action action : actions) {

            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

     /**
     * Change all the actions that require to change their availability before
     * and/or after opening an image.
     * @param status The status of the menu items.
     */
    public void changeCertainMenuStatus(boolean status) {
        for (Action action : actions) {
            action.setEnabled(status);
        }
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     *
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
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
     * Action to invert an image.
     *
     * Same as the above class, but edited to work for imageInvert().
     * </p>
     */
    public class ImageInvertAction extends ImageAction {

        /**
         * <p>
         * Create a new imageInvert action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ImageInvertAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the invert image command is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the Image Invert is triggered.
         * It changes the image.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new ImageInvert());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
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
     * Action to change an image's colour channel's order based on the user's taste.
     * </p>
     *
     * @see ConvertToGrey
     * @author The Greatest Eden
     */
    public class RGBSwappingAction extends ImageAction {

        /**
         * <p>
         * Create a new RGBSwapping action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RGBSwappingAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Callback for when the RGBSwapping action is triggered.
         *
         * <p>
         * This method is called whenever the RGBSwapping action is triggered.
         * It changes the order of the color channels in the image based on user preferences.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // Default value of the orders of R, G and B.
                int R = 0;
                int G = 0;
                int B = 0;

                // Create the dialog panel
                JPanel panel = new JPanel(new GridLayout(5, 3));

                JLabel l1 = new JLabel(Andie.bundle.getString("RGBSwappingInstruction"));
                JLabel l2 = new JLabel();
                JLabel l3 = new JLabel();

                panel.add(l1);
                panel.add(l2);
                panel.add(l3);

                JLabel l4 = new JLabel(Andie.bundle.getString("FirstChannel"));
                JLabel l5 = new JLabel(Andie.bundle.getString("SecondChannel"));
                JLabel l6 = new JLabel(Andie.bundle.getString("ThirdChannel"));

                panel.add(l4);
                panel.add(l5);
                panel.add(l6);


                JRadioButton r1 = new JRadioButton("R");
                JRadioButton r2 = new JRadioButton("G");
                JRadioButton r3 = new JRadioButton("B");

                ButtonGroup group1 = new ButtonGroup();
                group1.add(r1);
                group1.add(r2);
                group1.add(r3);

                JRadioButton g1 = new JRadioButton("R");
                JRadioButton g2 = new JRadioButton("G");
                JRadioButton g3 = new JRadioButton("B");

                ButtonGroup group2 = new ButtonGroup();
                group2.add(g1);
                group2.add(g2);
                group2.add(g3);

                JRadioButton b1 = new JRadioButton("R");
                JRadioButton b2 = new JRadioButton("G");
                JRadioButton b3 = new JRadioButton("B");

                ButtonGroup group3 = new ButtonGroup();
                group3.add(b1);
                group3.add(b2);
                group3.add(b3);

                panel.add(r1);
                panel.add(g1);
                panel.add(b1);
                panel.add(r2);
                panel.add(g2);
                panel.add(b2);
                panel.add(r3);
                panel.add(g3);
                panel.add(b3);

                // Show the option dialog
                int option = JOptionPane.showOptionDialog(null, panel, Andie.bundle.getString("ColourSelection"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);

                // If cancel or close the tab, do nothing
                // If click ok, run the function but with input testify.
                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                    // Do nothing, but we keep this statement for possible future function expands.
                } else if (option == JOptionPane.OK_OPTION) {
                    // Get selected values of R, G, and B
                    if (r1.isSelected()) {
                        R = 1;
                    } else if (r2.isSelected()) {
                        R = 2;
                    } else if (r3.isSelected()) {
                        R = 3;
                    }

                    if (g1.isSelected()) {
                        G = 1;
                    } else if (g2.isSelected()) {
                        G = 2;
                    } else if (g3.isSelected()) {
                        G = 3;
                    }

                    if (b1.isSelected()) {
                        B = 1;
                    } else if (b2.isSelected()) {
                        B = 2;
                    } else if (b3.isSelected()) {
                        B = 3;
                    }

                    // Fun fact: put the code below outside of the if statement to trick the
                    // users!!!
                    // Especially useful on 1st Apr

                    // If user gave half an input, will say we don't understand what you're tryna do
                    if (!(R == 0 && G == 0 && B == 0) && (R == B || B == G || R == B || R == 0 || G == 0 || B == 0)) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("CannotProcess"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                        // If user gave full input but with the same RGB order, will give them a
                        // friendly notice.
                    } else if (!(R == 0 && G == 0 && B == 0) && (R == 1 && G == 2 && B == 3)) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("WithRespect"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        // Will educate the user if they didn't give any inputs and still wanna hit the
                        // OK button
                    } else if (R == 0 && G == 0 && B == 0) {
                        JOptionPane.showMessageDialog(null, Andie.bundle.getString("PleaseChoose"),
                                Andie.bundle.getString("Warning"),
                                JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                        // If a legal input (i.e., passed all the ifs above), will apply the filter.
                    } else {
                        // Apply the filter
                        target.getImage().apply(new RGBSwapping(R, G, B));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                }
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }// End of RGBSwapping()

    /**
     * A class to implement the GUI for B and C manipulation.
     *
     * @author Kevin Steve Sathyanath
     * @since 19/04/2024
     */
    public class brightnessAndContrastAction extends ImageAction{
        /**The scaling for the brightness */
        int brightnessFactor = 0;
        /**The scaling for the contrast */
        int contrastFactor = 0;

        /**
         * <p>
         * Create a new brightnessAndContrast action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        brightnessAndContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The brightness slider used in the preview panel */
        JSlider brightnessSlider;
        /**The contrast slider used in the preview panel */
        JSlider contrastSlider;

        /**
         * <p>
         * Callback for when the brightnessAndContrast action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever brightnessAndContrast is triggered.
         * It changes the image's brightness and contrast depending on user input.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e){
            //Yeah this is a mess. But it took 3 weeks and 4 demonstrators to make this work. Please just leave it as it is.
            try{
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());

                final EditableImage preview = target.getImage().makeCopy();
                final ImagePanel show = new ImagePanel(preview);
                int prevHeight = prev.getHeight();
                int prevWidth = prev.getWidth();
                Dimension d;
                if(prevWidth<prevHeight*1.1){
                    d = new Dimension(300,300);
                }
                else if(prevHeight<prevWidth){
                    d = new Dimension(500,300);
                }
                else{
                    d = new Dimension(300,500);
                }

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(d);
                updatePreviewImage(prev);




                JPanel sliderPane = new JPanel(new GridLayout(1,2, 17, 0));
                JPanel labelPane = new JPanel(new GridLayout(1,2,167,0));
                brightnessSlider = new JSlider(-100,100);
                contrastSlider = new JSlider(-100,100);
                JLabel brightnessSliderLabel = new JLabel(Andie.bundle.getString("BrightnessLabel"), JLabel.CENTER);
                brightnessSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                JLabel contrastSliderLabel = new JLabel(Andie.bundle.getString("ContrastLabel"), JLabel.CENTER);
                contrastSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                contrastSlider.setMajorTickSpacing(50);
                contrastSlider.setPaintTicks(true);
                contrastSlider.setPaintLabels(true);
                contrastSlider.setValue(0);

                brightnessSlider.setMajorTickSpacing(50);
                brightnessSlider.setPaintTicks(true);
                brightnessSlider.setPaintLabels(true);
                brightnessSlider.setValue(0);



                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        brightnessFactor = brightnessSlider.getValue();
                        contrastFactor = contrastSlider.getValue();

                        //Setting a new target for the ImageActon
                        // setTarget(show);
                        // target.getImage().reset();
                        // target.getImage().apply(new BrightnessAndContrast(brightnessFactor,contrastFactor));
                        //target.getImage().repaint();
                        // target.getParent().revalidate();
                        //curr = BrightnessAndContrast.applyToPreview(prev, brightnessFactor, contrastFactor);
                        BufferedImage curr = BrightnessAndContrast.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), brightnessFactor, contrastFactor);
                        updatePreviewImage(curr);


                    }
                };

                brightnessSlider.addChangeListener(sliderChangeListener);
                contrastSlider.addChangeListener(sliderChangeListener);


                labelPane.add(brightnessSliderLabel);
                labelPane.add(contrastSliderLabel);

                sliderPane.add(brightnessSlider);
                sliderPane.add(contrastSlider);
                //p.add(show);

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
                menu.add(sliderPane, a);

                a.gridx = 0;
                a.gridy = 2;
                a.weighty = 0.7;
                a.ipady = 1;
                i.set(10,0,0,0);
                menu.add(labelPane, a);

                int option = JOptionPane.showOptionDialog(null, menu, Andie.bundle.getString("SetBC"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);  //Added ImageIcon here



                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {

                }
                else if (option == JOptionPane.OK_OPTION) {
                    //System.out.println(brightnessFactor + " " + contrastFactor);
                    //setTarget(Andie.getPanel());
                    target.getImage().apply(new BrightnessAndContrast(brightnessFactor, contrastFactor));
                    target.getParent().revalidate();
                    target.repaint();
                }
            } catch (Exception err){
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Error"), JOptionPane.WARNING_MESSAGE);
                }
            }


            } //End of actionPerformed()
        }//End of B&C()


        /**
     * <p>
     * Action to convert an image to Sepia.
     * </p>
     *
     */
    public class ConvertToSepiaAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToSepiaAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().apply(new Sepia());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                            Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }


    /**A class to implement the change temperature action
     * @author Kevin Steve Sathyanath
     */
    public class ChangeTemperatureAction extends ImageAction{
        /**The scaling for the temperature action */
        double dial = 0.0f;

        /**
         * <p>
         * Create a new changeTemperature action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChangeTemperatureAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The slider for getting values for the temperature. */
        JSlider tempSlider;

        /**
         * <p>
         * Callback for when the brightnessAndContrast action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever brightnessAndContrast is triggered.
         * It changes the image's brightness and contrast depending on user input.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e){
            try{
                /** A copy of the BufferedImage onn  the image panel to put on the preview panel.*/
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());
                /** An editableImage. I don't think this is used but I'm not going to delete it this close to the deadline. */
                final EditableImage preview = target.getImage().makeCopy();
                //final ImagePanel show = new ImagePanel(preview);

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(new Dimension(500,300));
                updatePreviewImage(prev);    //Method developed thanks to a lot of help from Niamh.




                JPanel sliderPane = new JPanel(new FlowLayout());
                sliderPane.setPreferredSize(new Dimension(450,50));
                JPanel labelPane = new JPanel(new GridLayout(1,1,167,0));
                tempSlider = new JSlider(1000,13000, 6500);
                tempSlider.setPreferredSize(new Dimension(400,50));
                //Numbers taken from GIMP

                JLabel tempLabel = new JLabel(Andie.bundle.getString("TempLabel"), JLabel.CENTER);
                tempSlider.setAlignmentX(Component.CENTER_ALIGNMENT);

                tempSlider.setMajorTickSpacing(4000);
                tempSlider.setPaintTicks(true);
                tempSlider.setPaintLabels(true);
                //tempSlider.setValue(0);



                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        double temp = tempSlider.getValue();
                        double di = 0;
                        //System.out.println(temp);
                        if(temp>=6500){
                            di = 1 + (Math.abs(6500-temp)/6500);
                            //System.out.println(di);
                            //System.out.println(Math.abs((6500-temp)/6500));
                        }
                        else{
                            di = 1 - ((6500-temp)/6500);
                            //System.out.println(di);
                            //System.out.println(Math.abs((6500-temp)/6500));


                        }

                        BufferedImage curr = Temperature.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), di);
                        updatePreviewImage(curr);
                        dial = di;
                    }
                };

                tempSlider.addChangeListener(sliderChangeListener);

                labelPane.add(tempLabel);

                sliderPane.add(tempSlider);

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
                menu.add(sliderPane, a);

                a.gridx = 0;
                a.gridy = 2;
                a.weighty = 0.7;
                a.ipady = 1;
                i.set(10,0,0,0);
                menu.add(labelPane, a);

                int option = JOptionPane.showOptionDialog(null, menu, Andie.bundle.getString("ChangeTemp"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);  //Added ImageIcon here



                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {

                }
                else if (option == JOptionPane.OK_OPTION) {
                    //System.out.println(brightnessFactor + " " + contrastFactor);
                    //setTarget(Andie.getPanel());
                    target.getImage().apply(new Temperature(dial));
                    target.getParent().revalidate();
                    target.repaint();
                }
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }


            } //End of actionPerformed()
        }//End of B&C()

        /**
         * A method to update the preview image.
         * @param i The BufferedImage input
         */
        public void updatePreviewImage(BufferedImage i){
            BufferedImage j = ImageResize.applyToPreview(i);
            JLabel pic = new JLabel(new ImageIcon(j));
            //previewIcon = new ImageIcon(j);
            previewPanel.removeAll();
            previewPanel.add(pic);
            previewPanel.repaint();
            previewPanel.revalidate();
        }



    /**A class to implement the GUI for B and C manipulation.
     * @author Kevin Steve Sathyanath
     */
    public class ChangeHSVAction extends ImageAction{
        /**The scaling for the Hue */
        float hueFactor = 0;
        /**The scaling for the saturation */
        float saturationFactor = 0;
        /**The scaling for the brightness */
        float brightnessFactor;

        /**
         * <p>
         * Create a new brightnessAndContrast action.
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChangeHSVAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**The slider for getting hue values */
        JSlider HueSlider;
        /**The slider for getting saturation values */
        JSlider SaturationSlider;
        /**the slider for getting brightness values */
        JSlider BrightnessSlider;


        /**
         * <p>
         * Callback for when the brightnessAndContrast action is triggered.
         * </p>
         *
         * <p>
         * This method is called whenever brightnessAndContrast is triggered.
         * It changes the image's brightness and contrast depending on user input.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e){
            try{
                BufferedImage prev = EditableImage.deepCopy(target.getImage().getCurrentImage());

                final EditableImage preview = target.getImage().makeCopy();
                final ImagePanel show = new ImagePanel(preview);

                previewPanel = new JPanel();
                previewPanel.setPreferredSize(new Dimension(500,300));
                updatePreviewImage(prev);




                JPanel HuePane = new JPanel(new GridLayout(2,1, 17, 0));
                JPanel SaturationPane = new JPanel(new GridLayout(2,1, 17, 0));
                JPanel BrightnessPane = new JPanel(new GridLayout(2,1, 17, 0));
                JPanel OptionsPane = new JPanel(new GridLayout(3,1,17,0));

                HueSlider = new JSlider(-360,360,0);
                SaturationSlider = new JSlider(-100,100,0);
                BrightnessSlider = new JSlider(-100,100,0);

                //Hue Slider labels
                JLabel HueSliderLabel = new JLabel(Andie.bundle.getString("Hue"), JLabel.CENTER);
                HueSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                //Saturation slider labels
                JLabel SaturationSliderLabel = new JLabel(Andie.bundle.getString("Saturation"), JLabel.CENTER);
                SaturationSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                //Brightness slider labels
                JLabel BrightnessSliderLabel = new JLabel(Andie.bundle.getString("Brightness"), JLabel.CENTER);
                BrightnessSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


                ChangeListener sliderChangeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {

                        hueFactor = HueSlider.getValue()/360.0f;

                        float sat = SaturationSlider.getValue();
                        saturationFactor = sat/100.0f;

                        brightnessFactor = BrightnessSlider.getValue()/100f;

                        BufferedImage curr = HSV.applyToPreview(EditableImage.deepCopy(target.getImage().getCurrentImage()), hueFactor, saturationFactor, brightnessFactor);
                        updatePreviewImage(curr);


                    }
                };

                HueSlider.addChangeListener(sliderChangeListener);
                SaturationSlider.addChangeListener(sliderChangeListener);
                BrightnessSlider.addChangeListener(sliderChangeListener);

                HuePane.add(HueSlider);
                HuePane.add(HueSliderLabel);

                SaturationPane.add(SaturationSlider);
                SaturationPane.add(SaturationSliderLabel);

                BrightnessPane.add(BrightnessSlider);
                BrightnessPane.add(BrightnessSliderLabel);

                OptionsPane.add(HuePane);
                OptionsPane.add(SaturationPane);
                OptionsPane.add(BrightnessPane);
                //p.add(show);

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
                menu.add(OptionsPane, a);


                int option = JOptionPane.showOptionDialog(null, menu, Andie.bundle.getString("EditHSV"),
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, null, null);  //Added ImageIcon here



                if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {

                }
                else if (option == JOptionPane.OK_OPTION) {
                    //System.out.println(brightnessFactor + " " + contrastFactor);
                    //setTarget(Andie.getPanel());
                    target.getImage().apply(new HSV(hueFactor, saturationFactor, brightnessFactor));
                    target.getParent().revalidate();
                    target.repaint();
                }
            } catch (Exception err) {
                if (err instanceof NullPointerException) {
                    JOptionPane.showMessageDialog(null, Andie.bundle.getString("YouDidNotOpen"),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, err.toString(),
                    Andie.bundle.getString("Warning"), JOptionPane.WARNING_MESSAGE);
                }
            }


            } //End of actionPerformed()
        }//End of B&C()


        /**
     * <p>
     * Action to pick a new colour.
     * </p>
     *
     * @see ConvertToGrey
     */
    public class ChooseAction extends ImageAction {

        /**
         * <p>
         * Create a new colour chooser action
         * </p>
         *
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ChooseAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for choosing a colour
         * </p>
         *
         * <p>
         * This method is called to chooe a colour from the colour wheel.
         * </p>
         *
         */
        public void actionPerformed(ActionEvent e) {
            try {

                ColourWheel.pickColour();
                //System.out.println(ColourWheel.getChosenColour());
                // JFrame f = new JFrame();
                // f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                // JPanel p = new JPanel();
                // p.setPreferredSize(new Dimension(100,100));
                // p.setBackground(ColourWheel.getChosenColour());
                // f.add(p);
                // f.pack();
                // f.setVisible(true);

            } catch (Exception err) {
                JOptionPane.showMessageDialog(null, err.toString(),
                Andie.bundle.getString("ColourErr"), JOptionPane.WARNING_MESSAGE);
            }
        }
    }



}//End of class
