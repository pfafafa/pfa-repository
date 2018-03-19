
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class ColorBlindChoice: MonoBehaviour {

	// Contoller on the GUI
	public Dropdown colorBlindChoices;
	public Toggle correctionToggle;
	public Material shader;


	void Update () {
		Screen.orientation = ScreenOrientation.Portrait;

		// trigger ToggleGroup
		correctionToggle.isOn = SavedValue.correction;

		// fill option with ColorBlindMode
		ColorBlindMode tmp = SavedValue.mode;
		Debug.Log (SavedValue.correction);
		Debug.Log (tmp);

		string[] colorBlindEnum = System.Enum.GetNames (typeof(ColorBlindMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < colorBlindEnum.Length; i++)
			optionList.Add (colorBlindEnum [i]);

		colorBlindChoices.ClearOptions ();
		colorBlindChoices.AddOptions (optionList);
		colorBlindChoices.value = (int) tmp;

		Debug.Log (colorBlindChoices.value);

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
		// apply filter on the preview
		shader.SetMatrix ("_mat", ColorBlindMatrix.GetColorBlindnessMat(SavedValue.mode, SavedValue.correction, 1f));
	}


	public void ChangeSceneTo (string sceneName) {
		Screen.orientation = ScreenOrientation.LandscapeLeft;
		SceneManager.LoadScene (sceneName);
	}
}
