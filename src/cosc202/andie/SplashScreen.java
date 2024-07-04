package cosc202.andie;

import javax.swing.*;
import java.awt.*;

/**A class to build a splash screen with cool image Eden made.
 * Code taken and adapted from Perplexity.ai
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class SplashScreen extends JWindow {
    /**A data field showing the time the splash screen will be active.  */
    private int duration;
     
    /**A constructor for the splash screen
     * @param duration the time for the splash screen to be active
     */
    public SplashScreen(int duration) {
        this.duration = duration;
        showSplash();
    }

    private void showSplash() {

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(255,255,255,200));
        //content.setOpaque(true);
        //content.setBackground(Color.RED);

        // Set the window's bounds, centering the window
        int width = 400;
        int height = 300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Build the splash screen
        
        ImageIcon originalImage = new ImageIcon("src/icon.png");
        Image image = originalImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledImage = new ImageIcon(image);
        JLabel label = new JLabel(scaledImage);

        JLabel copyrt = new JLabel("Copyright © 2024, CodeCrafters", JLabel.CENTER);
        content.add(label, BorderLayout.CENTER);
        Color oraRed = new Color(156, 20, 20);
        copyrt.setForeground(oraRed);
        content.add(copyrt, BorderLayout.SOUTH);


        setContentPane(content);

        // Display it
        setVisible(true);

        // Wait a little while, maybe while loading resources
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
        dispose();
    }
}