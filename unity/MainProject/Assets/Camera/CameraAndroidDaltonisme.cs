using System.Collections;
using UnityEngine;
using UnityEngine.UI;

/*
 * Replace the Unity camera view by the camera view of the device
 */
public class CameraAndroidDaltonisme: MonoBehaviour {

	private WebCamTexture webCameraTexture;

	public RawImage rawImage;
	public AspectRatioFitter fit;

	static Material staticMaterial;

	private float ratio;

	void Start() {

		rawImage.material = new Material(Shader.Find("PFA/ChannelMixer"));
		rawImage.material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(ColorBlindMode.NoColorBlind, false, 1f));

		staticMaterial = rawImage.material;

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
		//rawImage.texture = webCameraTexture;
		rawImage.material.mainTexture = webCameraTexture;
		webCameraTexture.Play();

	}

	void Update(){
		
		Graphics.Blit (rawImage.material.mainTexture, null as RenderTexture, rawImage.material);
		ratio = (float)webCameraTexture.width / (float)webCameraTexture.height;
		fit.aspectRatio = ratio;

		float scaleY = webCameraTexture.videoVerticallyMirrored ? -1f : 1f;
		rawImage.rectTransform.localScale = new Vector3 (1f, scaleY, 1f);

		int orientation = -webCameraTexture.videoRotationAngle;
		rawImage.rectTransform.localEulerAngles = new Vector3 (0, 0, orientation);
	}

	/*void OnRenderImage(RenderTexture src, RenderTexture dst) {
		Graphics.Blit (rawImage.material.mainTexture, null as RenderTexture, rawImage.material);
	}*/

	static public void SetFilter(ColorBlindMode mode, bool correction, float alpha){
		staticMaterial.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction, alpha));
	}

}

