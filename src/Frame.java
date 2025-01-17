import javax.swing.JFrame;

/**
 * Frame class extends JFrame to create a window for displaying the game.
 * This class initializes and configures the main application window.
 */
public class Frame extends JFrame {

    // A reference to the Game object, which contains the game's logic and visuals.
    public Panel panel;

    /**
     * Constructor for the Frame class.
     * Initializes the frame with the given Game instance and sets up its properties.
     *Important Note: User may need to diable upscaling in JVM because JVM is
     * stupid and likes to upscale window on displays with larger resoloutions.
     * enter this "-Dsun.java2d.uiScale=1"
     * @param panel the Game object to be displayed in the frame
     */
    public Frame(Panel panel) {

        // Assign the passed Game object to the local variable
        this.panel = panel;

        // Add the Game object to the frame as a component
        this.add(panel);

        // Pack the components within the frame to their preferred sizes
        this.pack();

        // Set the size of the frame based on the game's preferred size
        this.setSize(panel.getPreferredSize());

        // Ensure the program exits when the frame is closed
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Disable resizing of the frame to maintain consistent game layout
        this.setResizable(false);

        // Make the frame visible to the user
        this.setVisible(true);
    }
}

