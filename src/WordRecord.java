public class WordRecord {

    /**
     * Global Variables
     */
    private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	
	private int fallingSpeed;
	private static int maxWait=1500;
	private static int minWait=100;

	public static WordDictionary dict;

    /**
     * Default Constructor
     */
	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}

    /**
     * Parameterised Constructor 1
     */
	WordRecord(String text) {
		this();
		this.text=text;
	}

    /**
     * Parameterised Constructor 2
     */
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}

    /**
     * Accessor and Mutator Methods
     * All getters and setters must be synchronized
     */
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.text=text;
	}

	public synchronized  String getWord() {
		return text;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}

    /**
     * Reset Y-position
     */
	public synchronized void resetPos() {
		setY(0);
	}

    /**
     * Reset word and replace it with another word from the dictionary
	 * Word will be given new dropped status and falling speed
     */
	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());

	}

    /**
     * Checking that the typed word is equal to the word from the dictionary
     * @param typedText
     * @return boolean true (correct match) or false (incorrect match)
     */
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}

    /**
     * Dropping the word in the Y-direction according to the magnitude of increment
     * @param inc number by which the word should fall
     */
	public synchronized void drop(int inc) {
		setY(y+inc);
	}

    /**
     * Declaring that the dictionary word has fallen and therefore can't be caught any longer
     * @return boolean true (can't be caught) or false (can be caught)
     */
	public synchronized  boolean dropped() {
		return dropped;
	}

}
