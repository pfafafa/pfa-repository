using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ARView : LoadScene {

	void Start () {
		Screen.orientation = ScreenOrientation.LandscapeLeft;

		CameraDaltonisme.SetFilter(SavedValue.colorBlindMode, SavedValue.correction, SavedValue.alpha);
		ViewManager.SetView (SavedValue.deficencieMode, SavedValue.leftDioptre, SavedValue.rightDioptre);
	}
}
