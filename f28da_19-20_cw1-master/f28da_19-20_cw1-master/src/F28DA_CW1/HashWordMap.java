package F28DA_CW1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import F28DA_CW1.Entry;

public class HashWordMap<W, P> implements IHashMonitor, IWordMap{

	//Variables are declared, and an array called table is set up for later use in the program.
	private final static int INITIAL_CAPACITY = 13;
	public Entry[] table;
	public int capacity;
	public int nItems;
	private int prime;
	
    //This constructor sets up the max load factor as well as initialising the array with a for loop so the array can be used for hashing.
	public HashWordMap(int cap, int p, float maxLoadFactor) {
		table = new Entry[capacity];
		for (int i = 0; i < capacity; i++)
			table[i] = null;
		prime = p;
		capacity = cap;
		nItems = 0;
		maxLoadFactor = 0.5f;
	}

	//This method sets the max load factor to 0.5f.
	public HashWordMap (float maxLoadFactor) {
		maxLoadFactor = 0.5f;
	}

	//This creates a new map for the Iterators so they can go over the words.
	Map<W, P> HashWordMap = new HashMap<W,P>(); 

	//This get() method is designed to retrieve the word or position so that other methods don't have to use similar code when they are running as well as for readability.
	private Object get (int word) {
		int hash = (word % INITIAL_CAPACITY);
		while (table[hash] != null && table[hash].getWord() != word)
			hash = (hash + 1) % INITIAL_CAPACITY;
		if (table[hash] == null)
			return -1;
		else
			return table[hash].getPosition();
	}

	//This method adds a new position to an entry of the HashTable based map. It creates the entry if word is not already present in the map.
	public void addPos (String word, IPosition pos) {
		int hash = (word % INITIAL_CAPACITY);
		while (table[hash] != null && table[hash].getWord() != word)
			hash = (hash + 1) % INITIAL_CAPACITY;
		table[hash] = new Entry(word, pos);
		return;
	}

	//This method removes an entry of word in the HashTable based map. It will throw an exception if the word isn't in the map.
	public void removeWord(String word) throws WordException{
		int hash = (word % INITIAL_CAPACITY);
		int initialHash = -1;
		while (hash != initialHash
				&& (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
				&& table[hash].getWord() != word)) {
			if (initialHash == -1)
				initialHash = hash;
			hash = (hash + 1) % INITIAL_CAPACITY;
		}
		if (hash != initialHash && table[hash] != null)
			table[hash] = DeletedEntry.getUniqueDeletedEntry();

	}

	//This method removes a position for word from an entry of the HashTable based map. 
	//It will throw an exception if the word isn't in the map or if it isn't connected to the requested position.
	public void removePos (String word, IPosition pos) throws WordException{
		int hash = (word % INITIAL_CAPACITY);
		while (table[hash] != null && table[hash].getWord() != word)
			hash = (hash + 1) % INITIAL_CAPACITY;
		table[hash] = new Entry(word, pos);
		return;
	}

	//This method iterates over objects of String, and will do this for each word in the map.
	public Iterator<String>words(){
		Iterator<java.util.Map.Entry<W, P>> itr = HashWordMap.entrySet().iterator(); 
		while(itr.hasNext()) 
		{ 
			java.util.Map.Entry<W, P> entry = itr.next(); 
			System.out.println("Word = " + entry.getKey() + ", Position = " + entry.getValue()); 
		}
		return null;
	}

	//Similar to the above iterator but does it for all positions of word instead of just words. It throws an exception if the word isn't present in the map.
	public Iterator<IPosition> positions(String word) throws WordException{
		Iterator<java.util.Map.Entry<W, P>> itr = HashWordMap.entrySet().iterator(); 
		while(itr.hasNext()) 
		{ 
			java.util.Map.Entry<W, P> entry = itr.next(); 
			System.out.println("Word = " + entry.getKey() + ", Position = " + entry.getValue()); 
		}
		return null;

	}

	//This calculates how many entries are currently based within the map, and returns the total number of entries after finding the total amount.
	public int numberOfEntries() {
		int total = 0;
		for (int i = 0; i < table.length; i++)
			if (table[i] != null)
				total++;
		return total;
	}

	//This method returns the maximum allowed load factor.
	public float getMaxLoadFactor() {
		float maxLoadFactor = 0.5f;
		return maxLoadFactor;

	}

	//This method returns the current load factor.
	public float getLoadFactor() {
		float loadFactor = 0.5f;
		return loadFactor;

	}

	//This method returns the average number of probes that the HashTable implementation has done at this point in the program.
	public float averNumProbes() {
		return 0;
	}


	//This method returns the hash code as an integer of a provided string.
	public int hashCode(String s) {
		int sum = 0;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			sum = (int) c + 27*sum;
		}
		return sum;
	}
}