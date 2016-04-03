package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class Game implements Serializable {

	private static final long serialVersionUID = -6443549403268656779L;
	private List<Player> playerList;
	private CardColor tournamentColor;
	private CardColor prevColor;
	private Deck deck;
	private int maxPlayers;
	private Player currentPlayer;
	private Map<CardColor, Integer> tokens;
	private CardColor customToken = CardColor.None;
	private boolean ended;

	public Game(int maxPlayers) {
		this.playerList = new LinkedList<Player>();
		this.tournamentColor = CardColor.None;
		this.prevColor = CardColor.None;
		this.deck = new Deck();
		this.maxPlayers = maxPlayers;
		this.currentPlayer = null;
		this.tokens = new HashMap<CardColor, Integer>();
		this.ended = false;
		for(CardColor c : CardColor.values()) {
			tokens.put(c, 5);
		}
	}

	public boolean addPlayer(Player aPlayer) {
		if (playerList.size() < maxPlayers) {
			aPlayer.setPlaying(true);
			playerList.add(aPlayer);
			return true;
		}
		return false;
	}

	public boolean awardToken(Player p) {
		if (this.customToken != CardColor.None) tournamentColor = customToken;
		if (this.tournamentColor == CardColor.None) return false;
		else if (p.getTokens().get(tournamentColor).intValue() == 1) {
			return false;
		}
		if (tokens.get(tournamentColor) > 0) {
			p.getTokens().put(tournamentColor, p.getTokens().get(tournamentColor)+1);
			tokens.put(tournamentColor, tokens.get(tournamentColor) - 1);
			return true;
		} else {
			return false;
		}

	}

	public void dealDeck() {
		for (int i = 0; i < 8; i++) {
			for (Player p : playerList) {
				p.addCard(deck.draw());
			}
		}
	}

	public void dealTokens() {
		while(true) {
			for (Player p : playerList) {
				int rand = (int) Math.floor(Math.random() * 5);
				if (CardColor.values()[rand].toString().equals(CardColor.Purple.toString())) {
					this.currentPlayer = p;
					return;
				}
			}
		}
	}
	public Map<CardColor, Integer> getTokens() {
		return this.tokens;
	}

	public void setTournamentColor(CardColor c) {
		this.tournamentColor = c;
	}

	public void setCustomColor(CardColor c) {
		this.customToken = c;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void startTurn() {
		currentPlayer.addCard(deck.draw());
	}
	public boolean endTurn() {
		int score = 0;
		for (Card c : currentPlayer.getInPlay()) {
			if (tournamentColor == CardColor.Green) {
				score++;
			} else {
				score += c.getCardValue();
			}
		}

		for (Player p : playerList) {
			if (p.equals(currentPlayer)) continue;
			int tscore = 0;
			for (Card c : p.getInPlay()) {
				tscore += c.getCardValue();
			}
			if (tscore > score) {
				return false;
			}
		}

		for (Player p : playerList) {
			if (currentPlayer.equals(p)) {
				do {
					currentPlayer = playerList.get((playerList.indexOf(p) + 1) % playerList.size());
				} while (!currentPlayer.isPlaying());
				break;
			}
		}
		return true;
	}

	public void endTournament() {
		awardToken(currentPlayer);
		for (Player p : playerList) {
			p.setPlaying(true);
			p.clearDisplay();
		}
		this.prevColor = tournamentColor;
		this.tournamentColor = CardColor.None;
		this.customToken = CardColor.None;
	}

	public List<Player> getPlayers() {
		return this.playerList;
	}

	public Deck getDeck() {
		return this.deck;
	}

	public boolean ended() {
		return this.ended;
	}

	public boolean withdrawPlayer() {
		for (Player p : playerList) {
			if (currentPlayer.equals(p)) {
				p.setPlaying(false);
			}
		}
		int playing = 0;
		for (Player p : playerList) {
			if (p.isPlaying()) {
				playing++;
			}
		}

		if (playing == 1) {
			return true;
		} else {
			for (Player p : playerList) {
				if (currentPlayer.equals(p)) {
					do {
						currentPlayer = playerList.get((playerList.indexOf(p) + 1) % playerList.size());
					} while (!currentPlayer.isPlaying());
					break;
				}
			}
			this.endTurn();
			return false;
		}
	}

	public boolean validatePlay(Card c) {
		if (this.tournamentColor == CardColor.None && c.getCardType() == CardType.Color) {
			if (this.prevColor == CardColor.Purple && c.getCardColor() == CardColor.Purple) return false;
			return true;
		} else if (c.getCardType() == CardType.Supporter){
			if (c.getCardName() == "Squire") return true;
			for (Card card : currentPlayer.getInPlay()) {
				if (card.getCardName() == "Maiden") return false;
			}
			return true;
		} else if (tournamentColor == CardColor.None && c.getCardType() == CardType.Action) {
			return false;
		} else if (this.tournamentColor == c.getCardColor()) {
			return true;
		} else {
			return false;
		}
	}

	public void performPlay(int index) {
		if (currentPlayer.viewCard(index).getCardType() == CardType.Color && tournamentColor == CardColor.None) {
			tournamentColor = currentPlayer.viewCard(index).getCardColor();
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
		} else if (currentPlayer.viewCard(index).getCardType() == CardType.Action) {

			if (currentPlayer.viewCard(index).getCardName() == "Disgrace") {
				performDisgrace();
			} else if (currentPlayer.viewCard(index).getCardName() == "Charge") {
				performCharge();
			} else if (currentPlayer.viewCard(index).getCardName() == "Outmaneuver") {
				performOutmaneuver();
			} else if (currentPlayer.viewCard(index).getCardName() == "Countercharge") {
				performCountercharge();
			} else if (currentPlayer.viewCard(index).getCardName() == "Unhorse") {
				tournamentColor = CardColor.None;
			}

			currentPlayer.addCardToDisplay(currentPlayer.removeCard(index));
		} else if (currentPlayer.viewCard(index).getCardColor() == tournamentColor) {
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
		} else if (currentPlayer.viewCard(index).getCardType() == CardType.Supporter) {
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
		}
	}

	public CardColor getTournamentColor() {
		return tournamentColor;
	}

	public boolean gameWon() {
		for (Player p : playerList) {
			for (Entry<CardColor, Integer> entry : p.getTokens().entrySet()) { 
				if (entry.getValue().intValue() != 1) {
					continue;
				}
			}
			return true;
		}
		return false;
	}

	public void performDisgrace() {
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardType() == CardType.Supporter) {
					it.remove();
				}
			}
		}
	}
	
	public void performCharge() {
		int smallestVal = 10;
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardValue() < smallestVal) {
					smallestVal = aCard.getCardValue();
				}
			}
		}
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardValue() == smallestVal) {
					it.remove();
				}
			}
		}
	}
	
	public void performCountercharge() {
		int biggestVal = 0;
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardValue() > biggestVal) {
					biggestVal = aCard.getCardValue();
				}
			}
		}
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardValue() == biggestVal) {
					it.remove();
				}
			}
		}
	}
	
	private void performOutmaneuver() {
		for (Player p : playerList) {
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (!it.hasNext()) {
					it.remove();
				}
			}
		}
	}
}
