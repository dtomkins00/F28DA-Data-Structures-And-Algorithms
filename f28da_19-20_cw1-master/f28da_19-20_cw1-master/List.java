package F28DA_CW1;

public class List
{
   public ListElem  head;
   public int N;
 
   public List()
   {
	 head = null;   // Start with empty list
	 N = 0;
   }
 
   public int size()
   {
       return(N);
   }

   // *************************************************
   // delete(p): delete p
   // *************************************************
   public void delete(ListElem p)
   {
	 ListElem prevElem, nextElem;


      if ( N == 0 )
      {
         return ;                 // Nothing to delete
      }
      else if ( N == 1 )          // Delete last element ==> empty list
      {
         head = null;
      }
      else if ( p.prev == null )  // Delete FIRST element
      {
         System.out.println("Delete: " + p.word);

         nextElem = p.next;
         nextElem.prev = null;

         head = nextElem;  // The second is the new "first element"
      }
      else if ( p.next == null )  // Delete LAST element
      {
         System.out.println("Delete: " + p.word);

         prevElem = p.prev;
         prevElem.next = null;
      }
      else                              // Delete a "middle element"
      {
         System.out.println("Delete: " + p.word);

         prevElem = p.prev;
         nextElem = p.next;

         nextElem.prev = p.prev;
         prevElem.next = p.next;
      }

	 N--;
   }
 
   // -------------------------------
   // insert(v): insert at head
   // -------------------------------
   public void insert(ListElem p)
   {
 
	 if ( N == 0 )
	 {
	    p.next = null;
	    p.prev = null;
 
	    head = p;
	 }
	 else
	 {
	    p.next = head;
	    p.prev = null;
	    head.prev = p;
	    head = p;
	 }
 
	 N++;
   }
 
 
   public String toString()
   {
	 String r = "";
	 ListElem p;
 
	 p = head;    // Very important: start at first element
 
	 r = r + "{";
	 while ( p != null )
	 {
	    r = r + p + "  ";
	    p = p.next;
	 }
	 return(r);
   }
}