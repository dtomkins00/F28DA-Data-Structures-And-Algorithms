package F28DA_CW1;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashWordMapTest {

	public void hashCodeTest() {
		int x = new int(24);
		assertEquals(x.hashCode() == "42442");
	}

	public void getTest(){
		String word = new String("test");
		assertEquals(word.get() == "test");

	}

	public void iteratorTest(){
		String word = new String("test");
		assertEquals(word.words() == "test");
	}

	@Test
	public void signatureTest() {
		try {
			IWordMap map = new HashWordMap(0.5f);
			String word1 = "test1";
			String word2 = "test2";
			IPosition pos1 = new WordPosition("test.txt", 4, word1);
			IPosition pos2 = new WordPosition("test.txt", 5, word2);      
			map.addPos(word1, pos1);
			map.addPos(word2, pos2);
			map.words();
			map.positions(word1);
			map.numberOfEntries();
			map.removePos(word1, pos1);
			map.removeWord(word2);
		} catch (Exception e) {
			fail("Signature of solution does not conform");
		}
	}

}
