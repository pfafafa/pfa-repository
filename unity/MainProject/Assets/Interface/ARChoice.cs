using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ARChoice : LoadScene {

	public Dropdown choices;

	public Slider leftSlider;
	public Slider rightSlider;


	void Start () {
		Screen.orientation = ScreenOrientation.Portrait;

		// fill option with ColorBlindMode
		string[] deficiencieEnum = System.Enum.GetNames (typeof(DeficiencieMode));
		List<string> optionList = new List<string>();
		for (int i = 0; i < deficiencieEnum.Length; i++) {
			optionList.Add (deficiencieEnum [i]);
		}
		choices.AddOptions (optionList);
		choices.value = (int) SavedValue.deficencieMode;

		OnChoiceChange ();
	}
		
	public void OnChoiceChange() {

		SavedValue.deficencieMode = (DeficiencieMode) choices.value;

		if (choices.value == (int) DeficiencieMode.Myopia) {
			leftSlider.enabled = true;
			rightSlider.enabled = true;
		} else {
			leftSlider.enabled = false;
			rightSlider.enabled = false;
		}
	}

	public void OnLeftSlideChange(Text text) {
		OnSliderChange (text, leftSlider);
		SavedValue.leftDioptre = leftSlider.value;
	}

	public void OnRightSlideChange(Text text) {
		OnSliderChange (text, rightSlider);
		SavedValue.rightDioptre = rightSlider.value;
	}

	private void OnSliderChange(Text text, Slider slide) {
		if (slide.enabled) {
			text.text = (Mathf.Round (slide.value * 10) / 10).ToString();

		} else {
			text.text = "";
		}
	}
}
