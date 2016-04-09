package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class Game implements Serializable {

	private static final long serialVersionUID = -6443549403268656779L;
	private final int NOCARD = -1;
	private List<Player> playerList;
	public CardColor tournamentColor;
	public CardColor prevColor;
	private Deck deck;
	private int maxPlayers;
	private Player currentPlayer;
	private Player targetPlayer = null;
	private int targetCard = NOCARD;
	private Map<CardColor, Integer> tokens;
	private Game backup = null;
	
	private boolean ended;
	
	public boolean purple = false, won = false;
	private CardColor customToken = CardColor.None;
	
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
	
	public Game (Game aGame) {
		this(aGame.maxPlayers);
		for (Player p : aGame.playerList) {
			this.playerList.add(p.clone());
		}
		this.backup = null;
		
		this.tournamentColor = aGame.tournamentColor;
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
		if (this.customToken != CardColor.None) {
			tournamentColor = customToken;
			customToken = CardColor.None;
		}
		if (this.tournamentColor == CardColor.None) {
			return false;
		}
		else if (p.getTokens().get(tournamentColor).intValue() == 1) {
			tournamentColor = CardColor.None;
			return false;
		}
		if (tokens.get(tournamentColor) > 0) {
			p.getTokens().put(tournamentColor, p.getTokens().get(tournamentColor)+1);
			tokens.put(tournamentColor, tokens.get(tournamentColor) - 1);
			tournamentColor = CardColor.None;
			if (this.gameWon()){
				won = true;
			}
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
		currentPlayer.setTurnPlayed(false);
		Card drawn = deck.draw();
		if (drawn == null) return;
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
			if (tscore >= score) {
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
		this.prevColor = tournamentColor;
		if (tournamentColor == CardColor.Purple){
			purple = true;
		} else {
			awardToken(currentPlayer);
		}
		for (Player p : playerList) {
			p.setPlaying(true);
			p.clearDisplay();
		}
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
		if (customToken != CardColor.None) {
			currentPlayer.getTokens().put(customToken, 0);
			tokens.put(tournamentColor, tokens.get(tournamentColor) + 1);
		}
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
			for (Player p : playerList) {
				if (currentPlayer.equals(p)) {
					do {
						currentPlayer = playerList.get((playerList.indexOf(p) + 1) % playerList.size());
					} while (!currentPlayer.isPlaying());
					break;
				}
			}
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
		if (tournamentColor == CardColor.None && c.getCardType() == CardType.Color) {
			if (this.prevColor == CardColor.Purple && c.getCardColor() == CardColor.Purple) {
				return false;
			}
			return true;
		} else if (c.getCardType() == CardType.Supporter){
			if (currentPlayer.isStunned() && (currentPlayer.isTurnPlayed())) {
				return false;
			} 
			if (c.getCardName() == "Squire") return true;
			for (Card card : currentPlayer.getInPlay()) {
				if (card.getCardName() == "Maiden") return false;
			}
			return true;
		} else if (tournamentColor == CardColor.None && c.getCardType() == CardType.Action) {
			return false;
		} else if (c.getCardType() == CardType.Action){
			if ((c.getCardName() == "Unhorse") && (tournamentColor == CardColor.Purple) && (customToken != CardColor.None)) {
				return true;
			} else if ((c.getCardName() == "Changeweapon") && ((tournamentColor == CardColor.Red) || 
					(tournamentColor == CardColor.Blue) || 
					(tournamentColor == CardColor.Yellow))) {
				if (((customToken == CardColor.Red) || 
						(customToken == CardColor.Blue) || 
						(customToken == CardColor.Yellow)))
					return true;
				else return false;
			} else if ((c.getCardName() == "Dropweapon") && ((tournamentColor == CardColor.Red) || 
					(tournamentColor == CardColor.Blue) || 
					(tournamentColor == CardColor.Yellow))) {
				return true;
			} else if ((c.getCardName() == "Breaklance")) {
				return true;
			} else if ((c.getCardName() == "Riposte")) {
				return true;
			} else if ((c.getCardName() == "Dodge")) {
				return true;
			} else if ((c.getCardName() == "Retreat")) {
				return true;
			} else if ((c.getCardName() == "Knockdown")) {
				return true;
			} else if (c.getCardName() == "Shield") {
				return true;
			} else if (c.getCardName() == "Adapt") {
				return true;
			} else if ((c.getCardName() == "Stunned")) {
				return true;
			} else if (c.getCardName() == "Outmaneuver") {
				return true;
			} else if (c.getCardName() == "Charge") {
				return true;
			} else if (c.getCardName() == "Countercharge") {
				return true;
			} else if (c.getCardName() == "Disgrace") {
				return true;
			}
			else {
				return false;
			}
		}
		else if (this.tournamentColor == c.getCardColor()) {
			if (currentPlayer.isStunned() && (currentPlayer.isTurnPlayed())) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public void performPlay(int index) {
		if (currentPlayer.viewCard(index).getCardType() == CardType.Color && tournamentColor == CardColor.None) {
			currentPlayer.setTurnPlayed(true);
			tournamentColor = currentPlayer.viewCard(index).getCardColor();
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
		} else if (currentPlayer.viewCard(index).getCardType() == CardType.Action) {

			if (currentPlayer.viewCard(index).getCardName() == "Disgrace") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performDisgrace();
			} else if (currentPlayer.viewCard(index).getCardName() == "Charge") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performCharge();
			} else if (currentPlayer.viewCard(index).getCardName() == "Outmaneuver") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performOutmaneuver();
			} else if (currentPlayer.viewCard(index).getCardName() == "Countercharge") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performCountercharge();
			} else if (currentPlayer.viewCard(index).getCardName() == "Unhorse") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				tournamentColor = customToken;
				customToken = CardColor.None;
			} else if (currentPlayer.viewCard(index).getCardName() == "Changeweapon") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				tournamentColor = customToken;
				customToken = CardColor.None;
			} else if (currentPlayer.viewCard(index).getCardName() == "Dropweapon") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				tournamentColor = CardColor.Green;
			} else if (currentPlayer.viewCard(index).getCardName() == "Breaklance") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performBreaklance();
			} else if (currentPlayer.viewCard(index).getCardName() == "Riposte") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performRiposte();
			} else if (currentPlayer.viewCard(index).getCardName() == "Dodge") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performDodge();
			} else if (currentPlayer.viewCard(index).getCardName() == "Retreat") {
				currentPlayer.removeCard(index);
				performRetreat();
			} else if (currentPlayer.viewCard(index).getCardName() == "Knockdown") {
				currentPlayer.removeCard(index);
				backup = new Game(this);
				performKnockdown();
			} else if (currentPlayer.viewCard(index).getCardName() == "Shield") {
				currentPlayer.addCardToDisplay(currentPlayer.removeCard(index));
			} else if (currentPlayer.viewCard(index).getCardName() == "Stunned") {
				for (Player p : playerList) {
					if (p.getId() == targetPlayer.getId()) {
						p.addCardToDisplay(currentPlayer.removeCard(index));
					}
				}
			}
		} else if (currentPlayer.viewCard(index).getCardColor() == tournamentColor) {
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
			currentPlayer.setTurnPlayed(true);
		} else if (currentPlayer.viewCard(index).getCardType() == CardType.Supporter) {
			currentPlayer.addCardToPlay(currentPlayer.removeCard(index));
			currentPlayer.setTurnPlayed(true);
		}
	}

	public CardColor getTournamentColor() {
		return tournamentColor;
	}

	public boolean gameWon() {
		if (playerList.size() <= 3) {
			int numTokens = 0;
			for (Entry<CardColor, Integer> entry : currentPlayer.getTokens().entrySet()) { 
				if (entry.getValue().intValue() == 1) {
					numTokens++;
				}
			}

			if (numTokens < 5) return false;
			return true;
		} else {
			int numTokens = 0;
			for (Entry<CardColor, Integer> entry : currentPlayer.getTokens().entrySet()) { 
				if (entry.getValue().intValue() == 1) {
					numTokens++;
				}
			}

			if (numTokens < 4) return false;
			return true;
		}
	}
	public void performDisgrace() {
		for (Player p : playerList) {
			if (p.isShielded()) continue;
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
			if (p.isShielded()) continue;
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
			if (p.isShielded()) continue;
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
			if (p.isShielded()) continue;
			for (Iterator<Card> it = p.getInPlay().iterator(); it.hasNext(); ) {
				it.next();
				if (!it.hasNext()) {
					it.remove();
				}
			}
		}
	}

	private void performBreaklance() {
		if (!targetPlayer.isShielded()) {
			for (Iterator<Card> it = targetPlayer.getHand().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (aCard.getCardColor() == CardColor.Purple) {
					it.remove();
				}
			}
		}
		this.targetPlayer = null;
	}

	public void performRiposte() {
		if (!targetPlayer.isShielded()) {
			for (Iterator<Card> it = targetPlayer.getInPlay().iterator(); it.hasNext(); ) {
				Card aCard = it.next();
				if (!it.hasNext()) {
					currentPlayer.getInPlay().add(aCard);
					it.remove();
				}
			}
		}
		this.targetPlayer = null;
	}
	public Player getTargetPlayer() {
		return targetPlayer;
	}

	public void performDodge() {
		if (!targetPlayer.isShielded()) {
			targetPlayer.getInPlay().remove(targetCard);
		}
		this.targetCard = NOCARD;
		this.targetPlayer = null;
	}

	public void performRetreat() {
		currentPlayer.getHand().add(currentPlayer.getInPlay().remove(targetCard));
		this.targetCard = NOCARD;
	}

	public void performKnockdown() {
		if (!targetPlayer.isShielded()) {
			Card randomCard = targetPlayer.getHand().remove(new Random().nextInt(targetPlayer.getHand().size()));
			this.currentPlayer.getHand().add(randomCard);
			
		}
		this.targetPlayer = null;
	}

	public void setTargetPlayer(int targetPlayer) {
		for (Player p : playerList) {
			if (p.getId() == targetPlayer) {
				this.targetPlayer = p;
			}
		}
	}

	public int getTargetCard() {
		return targetCard;
	}

	public void setTargetCard(int targetCard) {
		this.targetCard = targetCard;
	}

	public boolean validateAdapt(Player p) {
		Set<Integer> cardValues = new HashSet<Integer>();
		if (p.isShielded()) return true;
		for (Card c : p.getHand()) {
			if (c.getCardType() == CardType.Action) continue;
			if (!cardValues.add(c.getCardValue())) {
				return false;
			}
		}

		return true;
	}

	public void performIvanhoe(int playerId) {
		if (backup != null) {
			this.playerList = new LinkedList<Player>(backup.playerList);
			this.tournamentColor = backup.tournamentColor;
			backup = null;
		}
		
		for (Player p : playerList) {
			if (p.getId() == playerId) {
				for (Iterator<Card> it = p.getHand().iterator(); it.hasNext(); ) {
					Card aCard = it.next();
					if (aCard.getCardName() == "Ivanhoe") {
						it.remove();
					}
				}
			}
			
			if (p.getId() == currentPlayer.getId()) {
				this.currentPlayer = p;
			}
		}
	}
}
