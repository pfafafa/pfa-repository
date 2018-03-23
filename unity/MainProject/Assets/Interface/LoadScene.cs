using UnityEngine;
using UnityEngine.SceneManagement;

public class LoadScene: MonoBehaviour {

	public void ChangeSceneTo (string sceneName) {
		Screen.orientation = ScreenOrientation.LandscapeLeft;
		SceneManager.LoadScene (sceneName);
	}
}
