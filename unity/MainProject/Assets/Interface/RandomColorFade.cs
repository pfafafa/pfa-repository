using UnityEngine;
using UnityEngine.UI;

/*
 * Easy script that randomly color fade an image
 */

public class RandomColorFade: MonoBehaviour {

	public Image img;
	public float time;  // time (in second) between to color

	void Start () {
		SetAndBeginFade ();
	}

	// public function in order to be call from outise
	public void SetAndBeginFade() {
		img.color = RandomColor ();
		CancelInvoke ();
		ChangeColor ();
	}

	private void ChangeColor() {
		img.CrossFadeColor (RandomColor (), time, true, false);
		Invoke ("ChangeColor", time);
	}

	private Color RandomColor() {
		return Random.ColorHSV (0f, 1f, 0.05f, 0.2f, 1f, 1f);
	}
}
