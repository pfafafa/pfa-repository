using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using UnityEngine.SceneManagement;


/*
 * Replace the Unity camera view by the camera of the device
 */
public class CameraDaltonism: MonoBehaviour {

	private WebCamTexture mCamera;

	static private Material material;


	private void Start() {

		Screen.orientation = ScreenOrientation.Portrait;

		WebCamDevice[] devices = WebCamTexture.devices;

		if (devices.Length == 0) {
			Debug.Log ("No camera detected");
			return;
		}

		material = new Material(Shader.Find("PFA/ChannelMixer"));
		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(ColorBlindMode.NoColorBlind, false, 1f));

		// Checks how many and which cameras are available on the device
		for (int cameraIndex = 0; cameraIndex < devices.Length; cameraIndex++) {
			#if UNITY_EDITOR
			// In editor mode, the camera is usually front facing
			// grab any camera is fine for testing
			mCamera = new WebCamTexture(cameraIndex, Screen.width*10, Screen.height*10);
			#else
			// grab the back facing camera on Android device
			if (!WebCamTexture.devices[cameraIndex].isFrontFacing) {
			mCamera = new WebCamTexture(devices[cameraIndex].name, Screen.width, Screen.height);                
			}
			#endif
		}

		if (mCamera == null) {
			Debug.Log ("Unable to find camera");
			return;
		}
		material.mainTexture = mCamera;
		mCamera.Play();

	}

	void OnRenderImage(RenderTexture src, RenderTexture dst) {
		Graphics.Blit (material.mainTexture, dst, material);
	}

	static public void SetFilter(ColorBlindMode mode, bool correction, float alpha){
		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction, alpha));
	}
}
