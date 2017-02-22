using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class EndGamePanel : MonoBehaviour {

	public Text titleText;
	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void ShowEnd(int state) {
		string endText = "";
		if(state == 0){
			endText = "You Win!";
		}
		else if(state == 1) {
			endText = "You Lose!";
		}
		else if(state == 2) {
			endText = "Draw!";
		}

		titleText.text = endText;

		GetComponent<CanvasGroup>().alpha = 1f;
		GetComponent<CanvasGroup>().interactable = true;
		GetComponent<CanvasGroup>().blocksRaycasts = true;
	}

	public void QuitClicked () {
		Application.Quit();
	}
}
