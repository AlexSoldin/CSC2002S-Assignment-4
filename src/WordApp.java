import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
//model is separate from the view.

public class WordApp {
    /**
     * Global Variables
     */
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();

	static WordPanel w;
	static Completion completed = new Completion();

	static JLabel caught;
	static JLabel missed;
	static JLabel scr;

	public static volatile String text = "";

    /**
     * Setup GUI screen
     * @param frameX
     * @param frameY
     * @param yLimit
     */
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setSize(frameX,frameY);
 
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	    g.add(w);
	    
	    
	    JPanel txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
	    caught = new JLabel("Caught:" + score.getCaught()+ "    ");
	    missed = new JLabel("Missed:" + score.getMissed()+ "    ");
	    scr = new JLabel("Score:" + score.getScore()+ "    ");
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);
  
	    final JTextField textEntry = new JTextField("",20);
	    textEntry.addActionListener(new ActionListener()
		{
	      public void actionPerformed(ActionEvent evt) {
	          text = textEntry.getText();
	          textEntry.setText("");
	          textEntry.requestFocus();
	      }
	    });
	   
	    txt.add(textEntry);
	    txt.setMaximumSize( txt.getPreferredSize() );
	    g.add(txt);
	    
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

		JButton resetB = new JButton("Reset");
		resetB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Reset();
			}
		});

		JButton pauseB = new JButton("Pause");
		pauseB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				w.paused.set(true);
			}
		});

		JButton playB = new JButton("Play");
		playB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Start();
                w.paused.set(false);
			}
		});

		JButton quitB = new JButton("Quit");
		quitB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				w.done = true;
				System.out.println("Quit Successful");
				System.exit(0);
			}
		});

		b.add(resetB);
		b.add(pauseB);
		b.add(playB);
		b.add(quitB);
		
		g.add(b);
    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
       	//frame.pack();  // don't do this - packs it into small space
        frame.setVisible(true);
	}

    /**
     * Main Method
     * @param args
     */
	public static void main(String[] args) {
		//String[] argsT = {"10", "5", "example_dict.txt"};
		//deal with command line arguments
		totalWords = Integer.parseInt(args[0]);  //total words to fall
		noWords = Integer.parseInt(args[1]); // total words falling at any point
		assert (totalWords >= noWords); // this could be done more neatly
		String[] tmpDict = getDictFromFile(args[2]); //file of words
		if (tmpDict != null)
			dict = new WordDictionary(tmpDict);

		WordRecord.dict = dict; //set the class dictionary for the words.

		words = new WordRecord[noWords];  //shared array of current words

		setupGUI(frameX, frameY, yLimit);

		int x_inc = (int) frameX / noWords;

		for (int i = 0; i < noWords; i++) {
			words[i] = new WordRecord(dict.getNewWord(), i * x_inc, yLimit);
		}

	}

	/**
	 * Method to reset the game
	 * Clears the GUI screen and resets the scores
	 */
	public static void Reset(){
        w.paused.set(false);
		w.done = true;
		score.resetScore();
		updateScores();
	}

	/**
	 * Method to start the game by calling multiple WordPanel threads
	 * This calls the run method in the WordPanel class
	 */
	public static void Start(){
		done = false;

		Thread completion = new Thread(completed);
		completion.start();

		Thread wt;

		for (int i = 0; i < noWords; i++) {
			wt = new Thread(w);
			wt.start();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Updates the value of the JLabels keeping the caught, missed and score variables
	 */
	public static void updateScores(){
		caught.setText("Caught:" + score.getCaught()+ "    ");
		missed.setText("Missed:" + score.getMissed()+ "    ");
		scr.setText("Score:" + score.getScore()+ "    ");
	}

	/**
	 * Reads in dictionary from input file
     * If there is an error with input file, default dictionary will be used
     * @param filename
     * @return
	 */
	public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
			System.err.println("Problem reading file " + filename + " default dictionary will be used");
		}
		return dictStr;

	}

}
