// Inspired by a Long Qian's code
// Email: lqian8@jhu.edu

using UnityEngine;

/*
 * Try to correct deficiences
 */

public enum DeficiencieMode : int {
	Normal     = 0,
	Myopia     = 1,
	Presbyopia = 2,
}


public class ViewManager : MonoBehaviour {

	static private Material shaderAR;

	static private float pixelWidth; // in millimeter
	static private float punctumRemotum;
	static private float punctumProximum;


	void Start() {
		// Never turn off the screen
		Screen.sleepTimeout = SleepTimeout.NeverSleep;

		// set shaderAR material
		shaderAR = new Material (Shader.Find ("PFA/SideBySide"));
		shaderAR.SetFloat ("_FOV", 1.7f);

		// set pixelWidth
		float dpmm = Screen.dpi / 25.4f; // dot per mm
		if (dpmm == 0.0f)
			pixelWidth = 0.05991f; // default value : Nexus 5x pixel width
		else
			pixelWidth = 1.0f / dpmm;  // width of a pixel (in mm)

		punctumRemotum = 5000;
		punctumProximum = 250;

		SetImageDistance (punctumRemotum);
	}


	void OnRenderImage(RenderTexture src, RenderTexture dst) {
		Graphics.Blit(src, dst, shaderAR);
	}

	// Set the distance to the screen regarding the mode
	static public void SetView(DeficiencieMode mode, float leftDioptre, float rightDioptre) {
	
		switch (mode) {
		case DeficiencieMode.Normal:
		case DeficiencieMode.Presbyopia:
			// put the image at an infinit distance
			SetImageDistance(punctumRemotum);
			break;

		case DeficiencieMode.Myopia:
			// compute punctum remotum of each eye
			float leftPR = (1f / leftDioptre) * 100;
			float rightPR = (1f / rightDioptre) * 100;

			// average point of both
			float distance = (punctumProximum + Mathf.Min (leftPR, rightPR)) / 2f;
			SetImageDistance (distance);
			break;
		}
	}


	/*
	 * Set image at a D (in mm) distance from the user
	 */
	static private void SetImageDistance(float D) {
		float D2 = 76f; // distance eye's rotation center - screen's phone
		float d2 = 30f; // half the distance between cardboard lens

		float dx = D2 * d2 / D; // parallax needed on the image (in mm)
		SetParallax(dx / pixelWidth);
	}


	/*
	 * Set disparity parameter on the SideBySide shader
	 * in order to have a dp pixel parallax
	 */
	static private void SetParallax(float dp) {
		int dpMax = Screen.width / 4;

		// clamp value in [-dpMax, dpMax]
		if (dp > dpMax)
			dp = dpMax;
		else if (dp < -dpMax)
			dp = -dpMax;

		// [-dpMax, dpMax] -> [0.5, -0.5]
		float d = (dp / dpMax) * -0.5f;
		shaderAR.SetFloat ("_Disparity", d);
	}
}
