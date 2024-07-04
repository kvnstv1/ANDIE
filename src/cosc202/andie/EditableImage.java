package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 *
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * This is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 *
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
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
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /**
     * The current image, the result of applying {@link ops} to {@link original}.
     */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;

    // the data field below is for FileActions.FileOpenAction to ID whether we have
    // made a new change to the file or not; this will not look into the stack as
    // the user may open a new file which has been edited before.
    public static boolean isOpsNotEmptyStatus;

    // to help determine whether to "save" or "save as"
    public static boolean hasOpsFile;

    // a stack for saving all the ops done after click 'macro'
    public static Stack<ImageOperation> macroStack=new Stack<ImageOperation>();
   //a boolean variable for macro to manage the start and stop
    public static boolean recordingStart;

    /*
     * A method for determing whether to call "save as" or "save"
     * when user trying to save an image.
     *
     * @see FileActions
     */

    // public boolean isOpsNotEmpty() {

    // return !ops.empty();
    // }

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     *
     * <p>
     * ƒ
     * A new EditableImage has no image (it is a null reference), and an empty stack
     * of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
    }

    /**A method to make a copy of the EditableImage object. Mainly used in testing for now. Don't know if I'l commit to it.
     * @author Kevin Steve Sathyanath
     * @return a copy of the Editable image.
     */
    public EditableImage makeCopy(){

        final EditableImage copy = new EditableImage();
        copy.original = deepCopy(this.current);
        copy.current = deepCopy(this.current);
        copy.ops = this.ops;
        copy.redoOps = this.redoOps;
        copy.imageFilename = this.imageFilename;
        copy.opsFilename = this.opsFilename;

        return copy;

    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     *
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     *
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     *
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knowledge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     *
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     *
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     *
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     *
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code>
     * added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     *
     * @param filePath The file to open the image from.
     * @throws Exception If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);

        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
            hasOpsFile = true;
            //System.out.println("Opened");
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
            hasOpsFile = false;
            ops.clear();
            redoOps.clear();
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     *
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved
     * as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     *
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension
        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        //System.out.println("Save confirmed.");
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     *
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     *
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     *
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        // the command below is for FileActions.FileOpenAction to ID whether we have
        // made a new change to the file or not; this will not look into the stack as
        // the user may open a new file which has been edited before.
        isOpsNotEmptyStatus = true;
        if (recordingStart == true) {
            macroStack.add(op);
        }
        current = op.apply(current);
        ops.add(op);
        //System.out.println(ops.toString());  //Test line. Comment out later.

    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
        redoOps.push(ops.pop());
        //System.out.println("Undone");
        refresh();
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo() {
        apply(redoOps.pop());
        //System.out.println("Redone");
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     *
     * @return The result of applying all of the current operations to the
     *         {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     *
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }

    /**A method to, well, 'reset' the EditableImage to the original. Only of use in a preview image before applying some effect. Hopefully.
     * @author Kevin Steve Sathyanath
     * @date 27/04/2024
     */
    public void reset(){
        current = deepCopy(original);
    }

}