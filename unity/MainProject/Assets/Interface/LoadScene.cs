using UnityEngine;
using UnityEngine.SceneManagement;

public class LoadScene: MonoBehaviour {

	public void ChangeSceneTo (string sceneName) {
		SceneManager.LoadScene (sceneName);
	}
}
