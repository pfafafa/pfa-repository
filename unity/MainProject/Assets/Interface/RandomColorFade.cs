using UnityEngine;
using UnityEngine.UI;

public class RandomColorFade: MonoBehaviour {

	public Image img;
	public float time;  // time (in second) between to color

	void Start () {
		img.color = RandomColor ();
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
