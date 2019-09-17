import java.util.concurrent.atomic.AtomicBoolean;

public class FallingWords implements Runnable {

    /**
     * Global Variables
     */
    private WordRecord[] words;
    public static AtomicBoolean paused;

    /**
     * Parameterised Constructor
     *
     * @param words
     */
    public FallingWords(WordRecord[] words) {
        this.words = words;
        paused = new AtomicBoolean(false);
    }


    @Override
    public void run() {

        while (true) {
            if (!paused.get()) {
                System.out.println("Running");
            } else {
                System.out.println("Paused is: " + paused);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
