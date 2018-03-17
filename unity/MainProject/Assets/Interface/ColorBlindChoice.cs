
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ColorBlindChoice: MonoBehaviour {

	// Contoller on the GUI
	public Dropdown colorBlindChoices;
	public Toggle correctionToggle;
	public Material shader;


	void Start () {

		string[] colorBlindEnum = System.Enum.GetNames (typeof(ColorBlindMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < colorBlindEnum.Length; i++)
			optionList.Add (colorBlindEnum [i]);

		colorBlindChoices.ClearOptions ();
		colorBlindChoices.AddOptions (optionList);

		shader.SetMatrix ("_mat", Matrix4x4.identity);
	}


	/*
	 * Getter from the interface
	 */
	private ColorBlindMode ColorBlindMode() {
		return (ColorBlindMode) colorBlindChoices.value;
	}
		
	private bool CorrectionBool() {
		return correctionToggle.isOn;
	}

	public void UpdatePreview() {
		shader.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(ColorBlindMode(), CorrectionBool(), 1f));
	}
}
