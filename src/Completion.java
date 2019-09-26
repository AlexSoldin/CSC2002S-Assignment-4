import javax.swing.*;

public class Completion implements Runnable{

    /**
     * Global Variables
     */
    public static volatile boolean done;

    /**
     * Default Constructor
     */
    public Completion(){
        done=false;
    }

    /**
     * Run method
     */
    public void run() {
        done=false;

        while (true) {
            if (done) {
                JOptionPane.showMessageDialog(null, "Unlucky son");
                WordApp.score.resetScore();
                WordApp.updateScores();
                break;
            }
        }
    }

}
