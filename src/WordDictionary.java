public class WordDictionary {
    /**
     * Global Variables
     */
	int size;
	static String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary

    /**
     * Parameterised Constructor
     * @param tmp
     */
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}

    /**
     * Default Constructor
     */
	WordDictionary() {
		size=theDict.length;
		
	}

    /**
     * Method to find a new word to drop down
     */
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
