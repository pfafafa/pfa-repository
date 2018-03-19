
/*
 * A static class that save value between scenes
 */
public static class SavedValue {

	static public ColorBlindMode mode;
	static public float alpha;
	static public bool correction;

	static SavedValue() {
		mode = ColorBlindMode.NoColorBlind;
		correction = false;
		alpha = 1f;
	}
}
