/**
 * @author Matthew Elkin
 * This class acts as the main loop and uses multithreading so performance isnt fucking awful
 * also includes core application loop
 */
public class Loop implements Runnable {
    private Frame f; // Frame that contains the game view
    private Panel p; // Game object that holds the logic of the game
    private Thread mainThread; // The thread that will run the game loop
    private final int FPS_SET = 60; // Target FPS (frames per second) for the game loop
    private final int TARGET_TIME = 1000 / FPS_SET; // Target time per frame in milliseconds

    public Loop() {
        p = new Panel();
        f = new Frame(p); // Create a new frame that holds the game object
        p.requestFocus(); // Request focus for the game so it can receive input
        startGameLoop(); // Start the game loop
    }

    @Override
    public void run() {
        long lastFrame = System.nanoTime(); // Record the time of the last frame
        long lastTime = System.nanoTime(); // Get the current time
        long timeDelta; // Variable to store time delta between frames
        long sleepTime = TARGET_TIME; // Default sleep time

        while (true) {
            long currentTime = System.nanoTime();
            timeDelta = currentTime - lastTime; // Get the time difference between frames

            if (timeDelta >= 1000000) { // Only update and repaint if enough time has passed
                lastTime = currentTime;
                p.repaint(); // Repaint the game view
                p.updateGridPos();
                for (Particle part: p.particles){
                    part.updatePosition();
                    part.handleCollisions();
                  //  System.out.println(part.x);
                }
                sleepTime = TARGET_TIME - (timeDelta / 1000000); // Calculate the remaining time to sleep
            }

            // Sleep to maintain the target FPS
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime); // Sleep for the remaining time in milliseconds
                } else {
                    Thread.sleep(1); // If the loop is running too fast, sleep for a short time
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the last frame time
            long timeElapsed = System.nanoTime() - lastFrame;
            if (timeElapsed >= 1000000000) {
                lastFrame = System.nanoTime(); // Reset the last frame time for FPS tracking
            }
        }
    }

    private void startGameLoop() {
        mainThread = new Thread(this); // Create a new thread that runs the game loop
        mainThread.start(); // Start the thread
    }
}
