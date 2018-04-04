using UnityEngine;
using UnityEngine.SceneManagement;

/*
 * Basic scene that all interface script inherit
 * 
 * It's just to avoid code duplication with ChangeScrene method
 * Also, Update method check the default return button on Android phone
 */

public class LoadScene: MonoBehaviour {

	public string previousScene;

	public void ChangeSceneTo (string sceneName) {
		SceneManager.LoadScene (sceneName);
	}

	void Update () {
		if (Input.GetKey(KeyCode.Escape)){
			if (Input.GetKey(KeyCode.Escape)){
				ChangeSceneTo (previousScene);
			}
		}
	}

}
