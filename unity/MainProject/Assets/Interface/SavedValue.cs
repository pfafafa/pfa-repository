
/*
 * A static class that save value between scenes
 */
public class SavedValue {

	static public ColorBlindMode mode;
	static public bool correction;
	static public float alpha;


	static SavedValue() {
		mode = ColorBlindMode.NoColorBlind;
		correction = false;
		alpha = 1f;
	}

	private SavedValue() {}
}
