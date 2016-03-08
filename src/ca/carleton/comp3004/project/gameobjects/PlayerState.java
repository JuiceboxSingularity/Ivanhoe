package ca.carleton.comp3004.project.gameobjects;

import java.util.List;

public class PlayerState {

	private List<Card> inPlay;
	private List<Card> inDisplay;
	private int handSize;
	private int id;
	
	public PlayerState(Player aPlayer) {
		this.inPlay = aPlayer.getInPlay();
		this.inDisplay = aPlayer.getInDisplay();
		this.handSize = aPlayer.getHand().size();
		this.id = aPlayer.getId();
	}

	public List<Card> getInPlay() {
		return inPlay;
	}

	public void setInPlay(List<Card> inPlay) {
		this.inPlay = inPlay;
	}

	public List<Card> getInDisplay() {
		return inDisplay;
	}

	public void setInDisplay(List<Card> inDisplay) {
		this.inDisplay = inDisplay;
	}

	public int getHandSize() {
		return handSize;
	}

	public void setHandSize(int handSize) {
		this.handSize = handSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
