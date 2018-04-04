
/*
 * A static class that save value between scenes
 * 
 * This class doesn't inherit from MonoBehaviour so it's not reset at every new scene
 */

public static class SavedValue {

	static public ColorBlindMode colorBlindMode = ColorBlindMode.NoColorBlind;
	static public bool correction     = false;
	static public float alpha         = 1f;

	static public DeficiencieMode deficencieMode = DeficiencieMode.Normal;
	static public float leftDioptre   = 0f;
	static public float rightDioptre  = 0f;
}
