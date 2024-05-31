package F28DA_CW1;

public class ListElem
{
   public String word;
   public IPosition position;
 
   public ListElem prev;        // Double linked list for easy deletion      
   public ListElem next;
 
   public ListElem(String w, IPosition pos)
   {
	 word = w;
	 position = pos;
	 prev = null;
	 next = null;
   }
 
   public String toString()
   {
	 return( "(" + word + "," + position + ")" );
   }
}