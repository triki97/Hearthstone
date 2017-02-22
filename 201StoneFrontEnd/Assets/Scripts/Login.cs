using UnityEngine;
using System.Collections;
using System;
using System.Text;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using SimpleJSON;

public class Login : MonoBehaviour {
	private SocketConnector connector;

	public InputField usernameText;
	public InputField passwordText;

	public ErrorMenu errorMenu;

	void Start () {
		connector = SocketConnector.instance;
	}

	public void createAccountAction(){
		Debug.Log("CreateAccount " + usernameText.text + " " + passwordText.text);
		connector.socket.SendString("CreateAccount " + usernameText.text + " " + passwordText.text);
		PlayerPrefs.SetInt("isGuest", 0);

		SocketConnector.ResponseDelegate callback = loginCallback;
		StartCoroutine(connector.GetResponse(callback));
	}

	public void loginButtonAction(){
		Debug.Log("Login " + usernameText.text + " " + passwordText.text);
		connector.socket.SendString("Login " + usernameText.text + " " + passwordText.text);
		PlayerPrefs.SetInt("isGuest", 0);

		SocketConnector.ResponseDelegate callback = loginCallback;
		StartCoroutine(connector.GetResponse(callback));
	}
		
	public void guestAction() {
		Debug.Log("Guest");
		connector.socket.SendString("Guest");
		PlayerPrefs.SetInt("isGuest", 1);

		SocketConnector.ResponseDelegate callback = loginCallback;
		StartCoroutine(connector.GetResponse(callback));
	}

	public void loginCallback(JSONNode response){
		if(response["success"].AsBool){
			SceneManager.LoadScene(1); // Main Screen
		}
		else {
			errorMenu.ShowError();
			Debug.Log("Login Error");
		}
	}

	
	// Update is called once per frame
	void Update () {
		
	}
}
