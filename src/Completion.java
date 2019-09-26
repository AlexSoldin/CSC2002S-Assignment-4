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
        done = false;
    }

    /**
     * Run method
     */
    public void run() {
        done = false;

        while (true) {
            if (done) {
                JOptionPane.showMessageDialog(null, "Game Over! \nYour score was "+WordApp.score.getScore()+
                        "\nPress play to try again");
                WordApp.score.resetScore();
                WordApp.updateScores();
                break;
            }
        }
    }

}
