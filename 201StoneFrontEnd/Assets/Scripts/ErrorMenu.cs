using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class ErrorMenu : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void OkClicked () {
		GetComponent<CanvasGroup>().alpha = 0f;
		GetComponent<CanvasGroup>().blocksRaycasts = false;
		GetComponent<CanvasGroup>().interactable = false;
	}

	public void ShowError(string msg = null) {
		if(msg == null) msg = "Unable to login user, please try again later!";

		GetComponent<CanvasGroup>().alpha = 1f;
		GetComponent<CanvasGroup>().blocksRaycasts = true;
		GetComponent<CanvasGroup>().interactable = true;
	}
}
