package F28DA_CW1;

import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import F28DA_CW1.Entry;
import F28DA_CW1.List;
import F28DA_CW1.ListElem;


public class ListWordMap<W, P> extends List implements IWordMap{
	LinkedList<Entry> Map = new LinkedList<Entry>();
	public ListWordMap(LinkedList<Entry> M) {
		this.Map = M;
		number = new Entry[M];
	}

	// Holds a reference to the head and tail of the list

	//This method adds a new position to an entry of the Linked List based map. It creates the entry if word is not already present in the map.
	public void addPos (String word, IPosition pos) {
		ListElem p;
		IPosition old;

		for ( p = head; p != null; p = p.next )
		{
			if ( p.word.equals( word ) )
			{
				old = p.position;
				p.position = pos;         // Replace old value
				return;
			}
		}

		//Not found, insert new entry
		p = new ListElem(word, pos);
		insert(p);
		return;
	}

	//This method removes an entry of word in the Linked List based map. It will throw an exception if the word isn't in the map.
	public void removeWord(String word) throws WordException{
		ListElem p;
		IPosition old;

		for (p = head; p != null; p = p.next)
		{
			if (p.word.equals(word))
			{
				old = p.position;
				delete(p);         // Delete element
				return;       // Return old value
			}
		}
		return;             // Return "not found"
	}

	//This method removes a position for word from an entry of the Linked List based map. 
	//It will throw an exception if the word isn't in the map or if it isn't connected to the requested position.
	public void removePos(String word, IPosition pos) throws WordException{
		ListElem p = null;
		IPosition old;

		try {
			for (p = head; p != null; p = p.next)
			{
				if (p.word.equals(word))
				{
					old = p.position;
					p.position = pos;         // Replace old value
					return;
				}
			}
		}

		//Not found, insert new entry
		catch (WordException e){
			old = p.position;
			delete(p);
			return;
		}
	}

	//This method iterates over objects of String, and will do this for each word in the map.
	public Iterator<String> words(){
		Iterator<java.util.Map.Entry<W, P>> itr = ListWordMap.entrySet().iterator(); 
		while(itr.hasNext()) 
		{ 
			java.util.Map.Entry<W, P> entry = itr.next(); 
			System.out.println("Word = " + entry.getKey() + ", Position = " + entry.getValue()); 
		}
		return null;

	}

	//Similar to the above iterator but does it for all positions of word instead of just words. It throws an exception if the word isn't present in the map.
	public Iterator<IPosition> positions(String word) throws WordException{
		Iterator<java.util.Map.Entry<W, P>> itr = ListWordMap.entrySet().iterator(); 
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
		for (int i = 0; i < number.length; i++)
			if (number[i] != null)
				total++;
		return total;
	}
}