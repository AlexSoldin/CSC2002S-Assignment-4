import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
    /**
     * Global Variables
     */
    public static volatile boolean done;
    private WordRecord[] words;
    private int noWords;
    private int maxY;
    private int count;
    public static AtomicBoolean paused;

    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0,0,width,height);
        g.setColor(Color.red);
        g.fillRect(0,maxY-10,width,height);

        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.PLAIN, 26));
       //draw the words
       //animation must be added
        for (int i=0;i<noWords;i++){
            //g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());
            g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words
        }

      }

    /**
     * Parameterised Constructor
     * @param words
     * @param maxY
     */
    WordPanel(WordRecord[] words, int maxY) {
        this.words = words; //will this work?
        noWords = words.length;
        done = false;
        this.maxY = maxY;
        paused = new AtomicBoolean(false);
    }

    /**
     * Run method
     */
    public void run() {
    //add in code to animate this
        WordRecord word = words[count];
        System.out.println(word.getWord());
        count++;

        while (!done) {
            if (!paused.get()) {
                System.out.println("Running");
                for (int i = 0; i < words.length; i++) {

                    words[i].drop(10);

                    try {
                        Thread.sleep(words[i].getSpeed() );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repaint();

                    if (word.dropped()==true){
                        word.resetWord();
                        WordApp.score.missedWord();
                        WordApp.updateScores();
                    }

                    String typed = WordApp.text;
                    if (typed.equals(word.getWord())){
                        WordApp.score.caughtWord(typed.length());
                        word.resetWord();
                        WordApp.updateScores();
                    }

                    if(done){
                        word.resetWord();
                        count = 0;
                    }

                }

            } else {
                System.out.println("Paused is: " + paused);
            }

        }
    }

}


