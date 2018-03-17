using UnityEngine;

/*
 * Manage wich filter apply on the image using ColorBlindMat
 * that gives the matrix to the ChannelMixer shader
 */
public class ColorBlindFilter : MonoBehaviour {

	public  ColorBlindMode mode = ColorBlindMode.NoColorBlind;
	public  bool correction = false;

	private Material material;


	void Start () {		
		material = new Material(Shader.Find("PFA/ChannelMixer"));
		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(ColorBlindMode.NoColorBlind, false, 1f));
	}

	void OnRenderImage(RenderTexture src, RenderTexture dst) {

		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction, 1f));

		Graphics.Blit (src, dst, material);
	}

	//	public void SetFilter(ColorBlindMode m, bool correction, float alpha) {
	//		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(m, correction, alpha));	
	//	}
}
