package F28DA_CW1;

public class WordException extends Exception {
	private static final long serialVersionUID = 1L;

	public WordException(String message) {
		super("WordIndex: " +message);
	}
}