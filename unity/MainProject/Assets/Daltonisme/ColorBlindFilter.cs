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

		/*
		 * Just for this basic class, attributs are in public so they can be changed in the editor
		 * And we set matrix to the shader in order to update the process
		 * 
		 * But in real script like CameraDaltonisme we use a setter
		 */
		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction, 1f));

		Graphics.Blit (src, dst, material);
	}
}
