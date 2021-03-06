﻿// Inspired by a Long Qian's code
// Email: lqian8@jhu.edu

using UnityEngine;
using UnityEngine.SceneManagement;

/*
 * Test class to test SideBySide shader on the editor
 */

public class SideBySide : MonoBehaviour {
   
	private Material shaderAR;

	[Range(1.0f, 2.0f)]
	public float FOV = 1.6f;
	[Range(-0.5f, 0.5f)]
	public float Disparity = 0.1f;

	private RenderTexture nullRenderTexture = null;

	// Use this for initialization
	void Start() {

		Screen.orientation = ScreenOrientation.LandscapeLeft;

		// Never turn off the screen
		Screen.sleepTimeout = SleepTimeout.NeverSleep;
		shaderAR = new Material (Shader.Find ("PFA/SideBySide"));
	}

	void OnRenderImage(RenderTexture src, RenderTexture dst) {

		// maybe there is better to do that update value at each time
		shaderAR.SetFloat("_Disparity", Disparity);
		shaderAR.SetFloat ("_FOV", FOV);

		Graphics.Blit(src, nullRenderTexture, shaderAR);
	}
}
