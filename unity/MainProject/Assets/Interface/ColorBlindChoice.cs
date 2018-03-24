
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ColorBlindChoice: LoadScene {

	// Contoller on the GUI
	public Dropdown colorBlindChoices;
	public Toggle correctionToggle;
	public Material shader;


	void Awake () {
		Screen.orientation = ScreenOrientation.Portrait;

		// fill option with ColorBlindMode
		string[] colorBlindEnum = System.Enum.GetNames (typeof(ColorBlindMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < colorBlindEnum.Length; i++) {
			optionList.Add (colorBlindEnum [i]);
		}
		colorBlindChoices.AddOptions (optionList);
		colorBlindChoices.value = (int) SavedValue.colorBlindMode;

		/*
		 * The trigger ToggleGroup
		 * It appears that if SavedValue.mode != NoColorBlind
		 * then the toggle is on simulation regarless the value of SavedValue.correction
		 * That's really frustrating and unexplained
		 */
		correctionToggle.isOn = SavedValue.correction;

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
		SavedValue.colorBlindMode = ColorBlindMode ();
		SavedValue.correction = CorrectionBool ();
		
		// apply filter on the preview
		shader.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(SavedValue.colorBlindMode, SavedValue.correction, 1f));
	}
}
