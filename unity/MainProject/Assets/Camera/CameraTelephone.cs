﻿// Inspired by a Long Qian's code
// Email: lqian8@jhu.edu

using System.Collections;
using UnityEngine;
using UnityEngine.UI;

/*
 * Replace the Unity camera view by the camera view of the device
 */
public class CameraTelephone: MonoBehaviour {

	private WebCamTexture webCameraTexture;

	public RawImage rawImage;
	public AspectRatioFitter fit;

	private float ratio;

	void Start() {

		Screen.orientation = ScreenOrientation.LandscapeLeft;

		// Checks how many and which cameras are available on the device
		for (int cameraIndex = 0; cameraIndex < WebCamTexture.devices.Length; cameraIndex++) {
			#if UNITY_EDITOR
			// In editor mode, the camera is usually front facing
			// grab any camera is fine for testing
			webCameraTexture = new WebCamTexture(cameraIndex, Screen.width * 10, Screen.height * 10);
			#else
			// grab the back facing camera on Android device
			if (!WebCamTexture.devices[cameraIndex].isFrontFacing) {
			webCameraTexture = new WebCamTexture(cameraIndex, Screen.width, Screen.height);                
			}
			#endif
		}
		rawImage.texture = webCameraTexture;
		rawImage.material.mainTexture = webCameraTexture;
		webCameraTexture.Play();

		ratio = (float)webCameraTexture.width / (float)webCameraTexture.height;
		fit.aspectRatio = ratio;
	}

	void Update(){
		ratio = (float)webCameraTexture.width / (float)webCameraTexture.height;
		fit.aspectRatio = ratio;
	}
}
