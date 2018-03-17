
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ColorBlindChoice: MonoBehaviour {

	// Contoller on the GUI
	public Dropdown colorBlindChoices;
	public Toggle correctionToggle;
	public Image preview;


	void Start () {

		string[] colorBlindEnum = System.Enum.GetNames (typeof(ColorBlindMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < colorBlindEnum.Length; i++)
			optionList.Add (colorBlindEnum [i]);

		colorBlindChoices.ClearOptions ();
		colorBlindChoices.AddOptions (optionList);
	}


	/*
	 * Getter from the interface
	 */
	private ColorBlindMode GetColorBlindChoice() {
		return (ColorBlindMode) colorBlindChoices.value;
	}
		
	private bool GetCorrection() {
		return correctionToggle.isOn;
	}


	//TODO: apply color filter on the image at each event
}
