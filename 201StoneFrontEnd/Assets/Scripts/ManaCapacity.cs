using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class ManaCapacity : MonoBehaviour {

	public int manaCapacity = 10;
	private int _currentMana = 10;
	public int CurrentMana
	{
		get { return _currentMana; }
		set { 
			if(value <= manaCapacity) {
				_currentMana = value; 
				UpdateManaCapacity(); 
			}
		}
	}

	private int _usedMana = 0;
	public int UsedMana
	{
		get { return _usedMana; }
		set { 
			if(value <= _currentMana) {
				_usedMana = value; 
				UpdateManaCapacity(); 
			}
		}
	}

	private Image[] manaObjects;
	public Transform manaCrystalPrefab;

	const int MANA_SIZE = 40;

	// Use this for initialization
	void Start () {
		manaObjects = new Image[manaCapacity];
		for(int k = 0; k < manaObjects.Length; ++k){
			Transform manaCrystal = Instantiate(manaCrystalPrefab, GetComponent<Transform>()) as Transform;
			RectTransform rect = manaCrystal.GetComponent<RectTransform>();
			rect.sizeDelta = new Vector2(MANA_SIZE, rect.sizeDelta.y);

			manaCrystal.localPosition = new Vector3(k * (rect.sizeDelta.x * 0.4f) * -1f, 0);
			manaObjects[k] = manaCrystal.GetComponent<Image>();
		}

		UpdateManaCapacity();
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	public void UpdateManaCapacity() {
		for(int k = 1; k <= manaCapacity; ++k){
			if(k <= _currentMana){
				manaObjects[k-1].color = Color.white;
			}
			else {
				manaObjects[k-1].color = Color.clear;
			}

			if(k <= _usedMana){
				manaObjects[k-1].color = Color.gray;
			}
		}
	}

	public int AvailableMana() {
		return _currentMana - _usedMana < 0 ? 0 : _currentMana - _usedMana;
	}
}
