using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Code behind the intermediateScene, the only point is to have the time to turn the screen
 * Otherwise this screen is just two buttons
 */

public class IntermediateScene : LoadScene {

	void Start () {
		Screen.orientation = ScreenOrientation.LandscapeLeft;	
	}
}
