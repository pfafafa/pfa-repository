using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ARChoice : MonoBehaviour {

	public Dropdown choices;

	public Slider leftSlider;
	public Slider rightSlider;


	void Start () {
		Screen.orientation = ScreenOrientation.Portrait;

		OnChoiceChange ();
	}
		
	public void OnChoiceChange() {

		if (choices.value == 1) {
			leftSlider.enabled = true;
			rightSlider.enabled = true;
		} else {
			leftSlider.enabled = false;
			rightSlider.enabled = false;
		}
	}
		
	private void OnSliderChange(Text text, Slider slide) {
		if (slide.enabled) {
			text.text = (Mathf.Round (slide.value * 10) / 10).ToString();

		} else {
			text.text = "";
		}
	}

	public void OnLeftSlideChange(Text text) {
		OnSliderChange (text, leftSlider);
	}

	public void OnRightSlideChange(Text text) {
		OnSliderChange (text, rightSlider);
	}
}
