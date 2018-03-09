using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Manage wich filter apply on the image using ColorBlindMat
 * It gives the matrix to the ChannelMixer shader
 */
public class ColorBlindFilter : MonoBehaviour {

	public  ColorBlindMode mode = ColorBlindMode.NoColorBlind;
	private ColorBlindMode lastMode = ColorBlindMode.NoColorBlind;

	public  bool correction = false;
	private bool lastCorrection = false;

	private Material material;


	void Start () {		
		material = new Material(Shader.Find("Hidden/ChannelMixer"));
		material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction));
	}


	void OnRenderImage(RenderTexture src, RenderTexture dst) {

		// Change effect
		if (mode != lastMode || correction != lastCorrection) {
			material.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(mode, correction));
			lastMode = mode;
			lastCorrection = correction;
		}

		// Apply effect
		Graphics.Blit (src, dst, material);
	}
}
