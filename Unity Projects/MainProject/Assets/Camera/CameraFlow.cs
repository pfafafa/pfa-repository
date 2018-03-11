// Inspired by a Long Qian's code
// Email: lqian8@jhu.edu

using UnityEngine;
using System.Collections;


/*
 * Replace the Unity camera view by the camera view of the device
 */
public class CameraFlow: MonoBehaviour {
    
	private WebCamTexture webCameraTexture;
	private Material camTextureHolder;


	void Start() {

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
		camTextureHolder = new Material(Shader.Find("Standard"));
		camTextureHolder.mainTexture = webCameraTexture;
		webCameraTexture.Play();
	}

	void OnRenderImage(RenderTexture src, RenderTexture dst) {
		Graphics.Blit(camTextureHolder.mainTexture, dst);
	}
}
