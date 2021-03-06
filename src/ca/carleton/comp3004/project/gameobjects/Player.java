package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2949226602823583816L;
	private List<Card> hand;
	private List<Card> inPlay;
	private List<Card> inDisplay;
	private String playerName;
	private int id;
	private Map<CardColor, Integer> tokens;
	private boolean playing;
	private boolean turnPlayed;
	
	public Player(String name, int id) {
		this.playerName = name;
		this.id = id;
		this.inDisplay = new LinkedList<Card>();
		this.inPlay = new LinkedList<Card>();
		this.hand = new LinkedList<Card>();
		this.tokens = new HashMap<CardColor, Integer>();
		this.setTurnPlayed(false);
		for (CardColor c : CardColor.values()) {
			if (c == CardColor.None || c == CardColor.White) continue;
			tokens.put(c, 0);
		}
	}
	
	@Override
	public Player clone() {
		Player p = new Player(this.playerName, this.id);
		for (Card c : this.inDisplay) {
			p.inDisplay.add(c.clone());
		}
		
		for (Card c : this.inPlay) {
			p.inPlay.add(c.clone());
		}
		
		for (Card c : this.hand) {
			p.hand.add(c.clone());
		}
		return p;
	}
	
	public void addCard(Card c) {
		this.hand.add(c);
	}
	
	public Card removeCard(int index) {
		return hand.remove(index);
	}
	
	public Card viewCard(int index) {
		return hand.get(index);
	}

	public List<Card> getHand() {
		return hand;
	}
	
	public List<Card> getInDisplay() {
		return this.inDisplay;
	}
	
	public List<Card> getInPlay() {
		return this.inPlay;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getId() {
		return id;
	}

	public Map<CardColor, Integer> getTokens() {
		return tokens;
	}
	
	public void addCardToPlay(Card c) {
		inPlay.add(c);
	}
	
	public void addCardToDisplay(Card c) {
		inDisplay.add(c);
	}
	
	public boolean equals(Player p) {
		if (this.getId() == p.getId()) {
			return true;
		}
		return false;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}

	public void setInPlay(List<Card> inPlay) {
		this.inPlay = inPlay;
	}

	public void setInDisplay(List<Card> inDisplay) {
		this.inDisplay = inDisplay;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTokens(Map<CardColor, Integer> tokens) {
		this.tokens = tokens;
	}

	public void clearDisplay() {
		this.inDisplay.clear();
		this.inPlay.clear();
	}
	
	public boolean isShielded() {
		for (Card c : inDisplay) {
			if (c.getCardName() == "Shield") {
				return true;
			}
		}
		return false;
	}
	
	public boolean isStunned() {
		for (Card c : inDisplay) {
			if (c.getCardName() == "Stunned") {
				return true;
			}
		}
		return false;
	}

	public boolean isTurnPlayed() {
		return turnPlayed;
	}

	public void setTurnPlayed(boolean turnPlayed) {
		this.turnPlayed = turnPlayed;
	}
}
