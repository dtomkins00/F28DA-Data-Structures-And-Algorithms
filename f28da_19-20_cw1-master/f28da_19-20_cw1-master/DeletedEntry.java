package F28DA_CW1;

public class DeletedEntry extends Entry<Object, Object> {
	private static DeletedEntry entry = null;

	private DeletedEntry() {
		super(-1, -1);
	}

	public static DeletedEntry getUniqueDeletedEntry() {
		if (entry == null)
			entry = new DeletedEntry();
		return entry;
	}
}
