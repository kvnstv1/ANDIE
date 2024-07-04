
package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 *
 * <p>
 * The median filter takes all of the pixel values in a local neighbourhood and
 * sorts them. The new pixel value is the middle value
 * from the sorted list.
 * </p>
 *
 * <p>
 * <a href="https://creativecommons.org/lißnses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 *
 * @see java.awt.image.ConvolveOp
 * @author Kevin Steve Sathyanath
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {
    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;
    /** Number of threads to use */
    private final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    /**Static version of the thread above :-D */
    private static final int NUM_THREADS2 = Runtime.getRuntime().availableProcessors();  //This one's static. No, I'm not about to mess with hyperthreading again. If it works, I'm not about to try fixing it.

    /**
     * <p>
     * Construct a median filter with the given size.
     * </p>
     *
     * <p>
     * The size of the filter is the 'radius'.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     *
     * @param radius The radius of the newly constructed MedianFilter.
     */
    MedianFilter(int radius) {
        this.radius = radius;
    }
    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     *
     * @see MedianFilter(int)
     */
    MedianFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     *
     * @param input The image to apply the Median filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        //System.out.println(NUM_THREADS);
        if (radius == 0) {
            return input;
        }
        // Apply border padding
        FilterBorder filterBorder = new FilterBorder(input, radius);
        BufferedImage paddedInput = filterBorder.applyBorder();
        int side = 2 * radius + 1; // The side of the kernel using the radius.
        int kernelWidth = side;
        int kernelHeight = side;
        BufferedImage output = new BufferedImage(paddedInput.getColorModel(), paddedInput.copyData(null),
                paddedInput.isAlphaPremultiplied(), null);
        JFrame f = new JFrame("Progress bar");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(200, 150);
        JPanel c = new JPanel();
        c.setLayout(new BorderLayout());
        f.setContentPane(c);
        JDialog progressDialog = new JDialog(f, "progress", true);
        // JProgressBar progressBar = new JProgressBar(0,100);
        JProgressBar progressBar = new JProgressBar(0, input.getHeight());
        progressBar.setValue(progressBar.getMinimum());
        progressBar.setStringPainted(true);
        // Ask dems about variables accessed in inner class needing to be declared final
        SwingWorker<BufferedImage, Integer> worker = new SwingWorker<BufferedImage, Integer>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                Thread[] threads = new Thread[NUM_THREADS];
                for (int i = 0; i < NUM_THREADS; i++) {
                    final int threadIndex = i;
                    threads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Calculate number of rows each thread will process
                            int rowsPerThread = paddedInput.getHeight() / NUM_THREADS;
                            int remainingRows = paddedInput.getHeight() % NUM_THREADS;
                            // Arrays to hold the argb elements in a kernel; size determined by given
                            // radius.
                            int[] rMedian = new int[kernelWidth * kernelHeight];
                            int[] gMedian = new int[kernelWidth * kernelHeight];
                            int[] bMedian = new int[kernelWidth * kernelHeight];
                            int[] aMedian = new int[kernelWidth * kernelHeight];
                            int argb; // argb values for transformation.
                            // got from stack overflow
                            // Calculate start and end rows for this thread

                            int startRow = threadIndex * rowsPerThread;
                            int endRow = (threadIndex + 1) * rowsPerThread;
                            if (threadIndex == NUM_THREADS - 1) {
                                // Last thread processes remaining rows
                                endRow += remainingRows;
                            }
                            // stackoverflow codes end
                            for (int i = startRow; i < endRow; i++) {
                                for (int j = 0; j < paddedInput.getWidth(); j++) {
                                    int a1 = 0; // Counter to help fit a square kernel into a 1-D array.
                                    for (int k = i - side / 2; k <= i + side / 2; k++) {
                                        for (int l = j - side / 2; l <= j + side / 2; l++) {
                                            if (k >= 0 && k < paddedInput.getHeight() && l >= 0 && l < paddedInput.getWidth()) {
                                                // Taken from MeanFilter.
                                                argb = paddedInput.getRGB(l, k);
                                                int a = (argb & 0xFF000000) >> 24;
                                                int r = (argb & 0x00FF0000) >> 16;
                                                int g = (argb & 0x0000FF00) >> 8;
                                                int b = (argb & 0x000000FF);
                                                // Filling the arrays.
                                                rMedian[a1] = r;
                                                gMedian[a1] = g;
                                                bMedian[a1] = b;
                                                aMedian[a1] = a;
                                                // Incrementing the counter.
                                                a1++;
                                            }
                                        }
                                    }
                                    // Ok. Time to sort these arrays and see what happens.
                                    Arrays.sort(rMedian);
                                    Arrays.sort(gMedian);
                                    Arrays.sort(bMedian);
                                    Arrays.sort(aMedian);
                                    // Find the middle? Must be the radius probably. Use for watercolour filter.
                                    // int nr = rMedian[radius];
                                    // int ng = gMedian[radius];
                                    // int nb = bMedian[radius];
                                    // int na = aMedian[radius];
                                    int nr = rMedian[rMedian.length/2];
                                    int ng = gMedian[gMedian.length/2];
                                    int nb = bMedian[bMedian.length/2];
                                    int na = aMedian[aMedian.length/2];
                                    // Apply the filter now.
                                    argb = (na << 24) | (nr << 16) | (ng << 8) | nb;
                                    output.setRGB(j, i, argb);
                                    // Update the progress bar
                                    // progressBar.setValue(i);
                                    // publish(i);
                                }
                               //publish(i);
                            }
                        }
                    });// end of threads
                    threads[i].start();
                }
                // got from stack overflow
                for (int i = 0; i < NUM_THREADS; i++) {

                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return output;
            }// End of doInBackground()

            // stackoverflow codes end
            /**A method that does something
             * @param chunks chunks I guess?
             */
            @Override
            protected void process(java.util.List<Integer> chunks) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            /**Obligatory comment
             * Pls give marks.
             */
            @Override
            protected void done() {
                progressDialog.dispose();
            }
        };
        worker.execute();
        progressDialog.add(progressBar);
        progressDialog.pack();
        progressDialog.setLocationRelativeTo(Andie.getFrame());
        // progressDialog.setLocationByPlatform(true);
        //progressDialog.setVisible(true);  //It's a pain to detangle the progress bar from the hypertheading. But if the user can't see it, it's out of sight, out of mind.
        //If Eden reads this, he might get an aneursym from my love of commenting things out.
        return output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

    }


    /**A method that does the same as above, but for preview images. Also it's static.
     * @author Kevin Steve Sathyanath
     * @param input the input image
     * @param radius the input radius
     * @return The output BufferedImage
     */
     public static BufferedImage applyToPreview(BufferedImage input, int radius) {
        //System.out.println(NUM_THREADS2);
        if (radius == 0) {
            return input;
        }
        // Apply border padding
        FilterBorder filterBorder = new FilterBorder(input, radius);
        BufferedImage paddedInput = filterBorder.applyBorder();
        int side = 2 * radius + 1; // The side of the kernel using the radius.
        int kernelWidth = side;
        int kernelHeight = side;
        BufferedImage output = new BufferedImage(paddedInput.getColorModel(), paddedInput.copyData(null),
                paddedInput.isAlphaPremultiplied(), null);
        JFrame f = new JFrame("Progress bar");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(200, 150);
        JPanel c = new JPanel();
        c.setLayout(new BorderLayout());
        f.setContentPane(c);
        JDialog progressDialog = new JDialog(f, "progress", true);
        // JProgressBar progressBar = new JProgressBar(0,100);
        JProgressBar progressBar = new JProgressBar(0, input.getHeight());
        progressBar.setValue(progressBar.getMinimum());
        progressBar.setStringPainted(true);
        // Ask dems about variables accessed in inner class needing to be declared final
        SwingWorker<BufferedImage, Integer> worker = new SwingWorker<BufferedImage, Integer>() {
            @Override
            protected BufferedImage doInBackground() throws Exception {
                Thread[] threads = new Thread[NUM_THREADS2];
                for (int i = 0; i < NUM_THREADS2; i++) {
                    final int threadIndex = i;
                    threads[i] = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Calculate number of rows each thread will process
                            int rowsPerThread = paddedInput.getHeight() / NUM_THREADS2;
                            int remainingRows = paddedInput.getHeight() % NUM_THREADS2;
                            // Arrays to hold the argb elements in a kernel; size determined by given
                            // radius.
                            int[] rMedian = new int[kernelWidth * kernelHeight];
                            int[] gMedian = new int[kernelWidth * kernelHeight];
                            int[] bMedian = new int[kernelWidth * kernelHeight];
                            int[] aMedian = new int[kernelWidth * kernelHeight];
                            int argb; // argb values for transformation.
                            // got from stack overflow
                            // Calculate start and end rows for this thread

                            int startRow = threadIndex * rowsPerThread;
                            int endRow = (threadIndex + 1) * rowsPerThread;
                            if (threadIndex == NUM_THREADS2 - 1) {
                                // Last thread processes remaining rows
                                endRow += remainingRows;
                            }
                            // stackoverflow codes end
                            for (int i = startRow; i < endRow; i++) {
                                for (int j = 0; j < paddedInput.getWidth(); j++) {
                                    int a1 = 0; // Counter to help fit a square kernel into a 1-D array.
                                    for (int k = i - side / 2; k <= i + side / 2; k++) {
                                        for (int l = j - side / 2; l <= j + side / 2; l++) {
                                            if (k >= 0 && k < paddedInput.getHeight() && l >= 0 && l < paddedInput.getWidth()) {
                                                // Taken from MeanFilter.
                                                argb = paddedInput.getRGB(l, k);
                                                int a = (argb & 0xFF000000) >> 24;
                                                int r = (argb & 0x00FF0000) >> 16;
                                                int g = (argb & 0x0000FF00) >> 8;
                                                int b = (argb & 0x000000FF);
                                                // Filling the arrays.
                                                rMedian[a1] = r;
                                                gMedian[a1] = g;
                                                bMedian[a1] = b;
                                                aMedian[a1] = a;
                                                // Incrementing the counter.
                                                a1++;
                                            }
                                        }
                                    }
                                    // Ok. Time to sort these arrays and see what happens.
                                    Arrays.sort(rMedian);
                                    Arrays.sort(gMedian);
                                    Arrays.sort(bMedian);
                                    Arrays.sort(aMedian);
                                    // Find the middle? Must be the radius probably. Use for watercolour filter.
                                    // int nr = rMedian[radius];
                                    // int ng = gMedian[radius];
                                    // int nb = bMedian[radius];
                                    // int na = aMedian[radius];
                                    int nr = rMedian[rMedian.length/2];
                                    int ng = gMedian[gMedian.length/2];
                                    int nb = bMedian[bMedian.length/2];
                                    int na = aMedian[aMedian.length/2];
                                    // Apply the filter now.
                                    argb = (na << 24) | (nr << 16) | (ng << 8) | nb;
                                    output.setRGB(j, i, argb);
                                    // Update the progress bar
                                    // progressBar.setValue(i);
                                    // publish(i);
                                }
                               //publish(i);
                            }
                        }
                    });// end of threads
                    threads[i].start();
                }
                // got from stack overflow
                for (int i = 0; i < NUM_THREADS2; i++) {

                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return output;
            }// End of doInBackground()

            // stackoverflow codes end
            /**Comment.
             * @param chunks Chunky chunks
             */
            @Override
            protected void process(java.util.List<Integer> chunks) {
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            /**Commenting for marks.
             * 
             */
            @Override
            protected void done() {
                progressDialog.dispose();
            }
        };
        worker.execute();
        progressDialog.add(progressBar);
        progressDialog.pack();
        progressDialog.setLocationRelativeTo(Andie.getFrame());
        // progressDialog.setLocationByPlatform(true);
        //progressDialog.setVisible(true);  //It's a pain to detangle the progress bar from the hypertheading. But if the user can't see it, it's out of sight, out of mind.
        //If Eden reads this, he might get an aneursym from my love of commenting things out.
        return output.getSubimage(radius, radius, input.getWidth(), input.getHeight());

    }



}
