using UnityEngine;
using System.Collections;
using System;
using System.Text;
using System.Threading;
using UnityEngine.UI;
using SimpleJSON;

public class SocketConnector : MonoBehaviour {
	public delegate void ResponseDelegate(JSONNode response);

	public static SocketConnector instance;
	public WebSocket socket;
	private JSONNode response;

	void Awake() {
		DontDestroyOnLoad(this);

		if(instance != null) {
			Debug.LogError("More than one SocketConnector in Scene");
		}
		instance = this;
	}

	void Start () {
		socket = new WebSocket(new Uri("ws://45.55.80.69:6789/"));
		StartCoroutine(socket.Connect());
	}

	void Update () {
		SocketListener();
	}
		
	void SocketListener() {
		if(socket == null) {
			Debug.Log("Not connected");
			StartCoroutine(socket.Connect());
			return;
		}

		byte[] message = socket.Recv();
		if(message != null){
			String responseStr = Encoding.ASCII.GetString(message);
			responseStr = responseStr.Replace("\\","");

			Debug.Log("Received: " + responseStr);
			response = JSON.Parse(responseStr);
		}
	}

	public IEnumerator GetResponse(ResponseDelegate callback) {
		while(response == null) {
			yield return new WaitForSeconds(0.1f);
		}

		JSONNode toReturn = response;
		response = null;
		callback(toReturn);
	}
}
