
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ColorBlindChoice: MonoBehaviour {

	// Contoller on the GUI
	public Dropdown colorBlindChoices;
	public Toggle correctionToggle;
	public Material shader;


	void Start () {

		// fill option with ColorBlindMode
		string[] colorBlindEnum = System.Enum.GetNames (typeof(ColorBlindMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < colorBlindEnum.Length; i++)
			optionList.Add (colorBlindEnum [i]);

		colorBlindChoices.ClearOptions ();
		colorBlindChoices.AddOptions (optionList);

		// Init interface
		colorBlindChoices.value = (int) SavedValue.mode;
		correctionToggle.isOn = SavedValue.correction; // doesn't work so far
		OnChange ();
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

	/*
	 * Update the preview on each change and save values
	 */
	public void OnChange() {
		SavedValue.mode = ColorBlindMode ();
		SavedValue.correction = CorrectionBool ();

		shader.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(SavedValue.mode, SavedValue.correction, 1f));
	}
}
