public class Score {

    /**
     * Global Variables
     */
	private int missedWords;
	private int caughtWords;
	private int gameScore;

    /**
     * Default Constructor
     */
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}

    /**
     * Accessor and Mutator Methods
     * All getters and setters must be synchronized
     */
	public int getMissed() {
		return missedWords;
	}

	public int getCaught() {
		return caughtWords;
	}
	
	public int getTotal() {
		return (missedWords+caughtWords);
	}

	public int getScore() {
		return gameScore;
	}

    /**
     * Increment the number of words missed
     */
	public void missedWord() {
		missedWords++;
	}

    /**
     * Increment the number of words caught
     */
	public void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}

    /**
     * Reset score
     */
	public void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}
