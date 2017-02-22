using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class GameBoard : MonoBehaviour {

	public const int MAX_MINION_SIZE = 7;

	private List<Minion>[] _minions; //2D ArrayList of Minions
	public List<Minion>[] Minions
	{
		get { return _minions; }
		set { Minions = _minions; ReorganizeMinions(); }
	}

	public Hero[] heroes;
	public Transform[] heroPositions;

	public Transform playerMinionPoint;
	public Transform opponentMinionPoint;

	public Transform minionPrefab;
	public Transform explosionPrefab;

	// Use this for initialization
	void Start () {
		// Init Minion Array
		List<Minion>[] minions = new List<Minion>[2];
		minions[0] = new List<Minion>();
		minions[1] = new List<Minion>();
		_minions = minions;

		// Make Heroes not interactable
		SetHeroInteractable(0, false);
		SetHeroInteractable(1, false);
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	// Check if Full on Players Side (index = 0)
	public bool isFull() {
		return _minions[0].Count == MAX_MINION_SIZE;
	}

	public void UpdateBoard(int playerIndex, List<Minion> minionData) {
		List<Minion> playerMinions = _minions[playerIndex];

		// Update Stats of currently existing minions
		for(int k = 0; k < playerMinions.Count; ++k){
			if(minionData.Count >= k+1 && minionData[k] != null){
				playerMinions[k].UpdateMinion(minionData[k]);
			}
		}

		// Add Extra Minions
		for(int k = playerMinions.Count; k < minionData.Count; ++k){
			AddMinion(playerIndex, minionData[k]);
		}

		// Check for Minions with 0 Health and Remove them
		for(int k = playerMinions.Count - 1; k >= 0; --k){
			if(playerMinions[k].health <= 0){
				Debug.Log("Minion at " + k + " died");
				RemoveMinion(playerIndex, k);
			}
		}
		playerMinions.RemoveAll(minion => minion.health <= 0);

		ReorganizeMinions();
	}

	public void AddMinion(int playerIndex, Minion minionData) {
		if(isFull()) return;

		Transform transform = Instantiate(minionPrefab, GetComponent<Transform>()) as Transform;
		transform.SetParent(playerIndex == 0 ? playerMinionPoint : opponentMinionPoint); // Based on playerIndex set the Spawn Point
		Minion minion = transform.GetComponent<Minion>();
		minion.UpdateMinion(minionData);

		// Case: Minion is on Opponent side, then set its interactability to false
		if(playerIndex == 1){
			minion.GetComponent<CanvasGroup>().blocksRaycasts = false;
			minion.GetComponent<CanvasGroup>().interactable = false;
		}

		_minions[playerIndex].Add(minion);
		//ReorganizeMinions();
	}

	public void RemoveMinion(int playerIndex, int minionIndex) {
		Minion toRemove = _minions[playerIndex][minionIndex];
		Destroy(toRemove.gameObject);

		//_minions[playerIndex].RemoveAt(minionIndex);
		//ReorganizeMinions();
	}

	public int GetMinionIndex(int playerIndex, Minion minionToGet) {
		List<Minion> playerMinions = _minions[playerIndex];
		int minionIndex = 0;
		foreach(Minion minion in playerMinions){
			if(minion == minionToGet) break;
			++minionIndex;
		}

		return minionIndex;
	}

	public void SetInteractable(int playerIndex, bool interactable) {
		List<Minion> playerMinions = _minions[playerIndex];
		foreach(Minion minion in playerMinions){
			if(!minion.hasAttacked || playerIndex == 1){
				minion.GetComponent<CanvasGroup>().blocksRaycasts = interactable;
				minion.GetComponent<CanvasGroup>().interactable = interactable;
			}
		}
	}

	public void SetHeroInteractable(int playerIndex, bool interactable) {
		heroes[playerIndex].GetComponent<CanvasGroup>().blocksRaycasts = interactable;
		heroes[playerIndex].GetComponent<CanvasGroup>().interactable = interactable;
	}

	void ReorganizeMinions() {
		ReorganizeMinions(0);
		ReorganizeMinions(1);
	}

	// Only call this method after adding a new minion
	void ReorganizeMinions(int playerIndex) {
		List<Minion> playerMinions = _minions[playerIndex];
		int minionCount = playerMinions.Count;

		for(int k = 0; k < minionCount; ++k){
			Minion m = playerMinions[k];

			Transform transform = m.GetComponent<Transform>();
			Vector3 position = new Vector3(transform.localPosition.x, transform.localPosition.y);

			position.x = (k - (minionCount/2)) * (100 * 0.75f);
			position.y = 0;

			transform.localPosition = position;
		}
	}

	public IEnumerator handleDeath(int playerIndex){
		Transform heroPos = heroPositions[playerIndex];
		for(int k = 0; k < Random.Range(10, 20); ++k){
			int xCoord = Random.Range(-1, 1);
			int yCoord = Random.Range(-1, 1);

			yield return new WaitForSeconds(Random.Range(0.2f, 1f));
			Vector3 newPosition = new Vector3(heroPos.position.x + xCoord, heroPos.position.y + yCoord, -6);
			Transform explosion = Instantiate(explosionPrefab, heroPos) as Transform;
			explosion.GetComponent<AudioSource>().pitch = Random.Range(-0.25f, 1.25f);
			explosion.position = newPosition;
			Destroy(explosion.gameObject, 2f);
		}

		Destroy(heroes[playerIndex].gameObject);
	}
}
