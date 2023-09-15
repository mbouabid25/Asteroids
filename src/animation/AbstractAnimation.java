package animation;
import javax.swing.JComponent;

/**
 * Animation provides the basic functionality to run an animation. Subclasses
 * should define the nextFrame method. This method is called every 30
 * milliseconds, giving the animation an opportunity to update the display and
 * do whatever operations it needs to during the animation.
 *
 */
public abstract class AbstractAnimation extends JComponent implements Runnable {
    // Default amount of time between frame updates.
    private static final int DEFAULT_MILLIS_BETWEEN_FRAMES = 30;

    // The thread in which the animation is running.
    private Thread animationThread;

    // Amount of time paused between frames
    private int pauseTime;

    /**
     * Creates an animation with the default frame rate.
     */
    public AbstractAnimation() {
        this(DEFAULT_MILLIS_BETWEEN_FRAMES);
    }

    /**
     * Creates an animation with the specified frame rate.
     * 
     * @param pauseTime the length of time to pause between frames, measured in
     *                  milliseconds. Should be > 0.
     */
    public AbstractAnimation(int pauseTime) {
        assert pauseTime > 0;
        this.pauseTime = pauseTime;
    }

    /**
     * Starts the animation
     */
    public void start() {
        if (animationThread == null) {
            animationThread = new Thread(this);
            animationThread.start();
        }
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }

    /**
     * Runs the animation. This method should not be called directly. Instead,
     * to start the animation, call the start() method.
     */
    public void run() {
        // Update the display periodically.
        try {
            while (Thread.currentThread() == animationThread) {
                nextFrame();
                repaint();

                Thread.sleep(pauseTime);
            }
        } catch (InterruptedException e) {
            // Stop the animation if interrupted.
        }
    }

    /**
     * Update the state of the animation to represent the next frame in the
     * animation. Classes that extend Animation must define this method.
     */
    protected abstract void nextFrame();
}
