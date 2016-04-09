package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class Deck implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551003649221963293L;
	List<Card> cards;

	//110 cards:
	//70 color cards (14 cards each in five colors)
	//20 supporter cards (16 squires and four maidens, all in white)
	//20 action cards (as described below)

	public Deck() {
		cards = new ArrayList<Card>();

		for (CardColor c : Card.CardColor.values()) {
			for (int i = 0; i < 14; i++) {

				int val;
				if (c == CardColor.Purple) {
					if (i < 4) val = 3;
					else if (i < 8) val = 4;
					else if (i < 12) val = 5;
					else val = 7;
				} else if (c == CardColor.Red) {
					if (i < 6) val = 3;
					else if (i < 12) val = 4;
					else val = 5;
				} else if (c == CardColor.Blue) {
					if (i < 4) val = 2;
					else if (i < 8) val = 3;
					else if (i < 12) val = 4;
					else val = 5;
				} else if (c == CardColor.Yellow) {
					if (i < 4) val = 2;
					else if (i < 12) val = 3;
					else val = 4;
				} else if (c == CardColor.Green) {
					val = 1;
				} else {
					continue;
				}
				cards.add(new Card(CardType.Color, c, val));
			}
		}

		// Add Supporters

		for (int i = 0;i < 20; i++) {
			if (i < 8) {
				cards.add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
			} else if (i < 16) {
				cards.add(new Card(CardType.Supporter, CardColor.White, 3, "Squire"));
			} else {
				cards.add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
			}
		}

		// Add special cards
		
		/*
		String[] actionCardName = {"Retreat", "KnockDown", "KnockDown", "Outmaneuver", "Charge", "Countercharge", "Disgrace", "Adapt",

				"Outwit", "Shield", "Stunned", "Ivanhoe", "Unhorse", "Changeweapon", "Dropweapon", "Breaklance", "Riposte", "Riposte", 
				"Riposte", "Dodge"};
		*/
	
		Card card;
		
		//NOTHING
		String[] needNothing = {"Outmaneuver","Charge","Countercharge","Disgrace","Shield","Ivanhoe","Dropweapon"};
		for (int i = 0; i < needNothing.length; i++) {
			card = new Card(CardType.Action, CardColor.None, 0, needNothing[i]);
			cards.add(card);
		}
		
		//NEED CARD
		//actionCardName = {};
		
		//NEED COLOR
		String[] needColor = {"Retreat","Unhorse","Changeweapon",};
		for (int i = 0; i < needColor.length; i++) {
			card = new Card(CardType.Action, CardColor.None, 0, needColor[i]);
			cards.add(card);
		}
		
		//NEED PLAYER
		String[] needPlayer = {"Knockdown","Knockdown","Stunned","Breaklance","Riposte","Riposte","Riposte"};
		for (int i = 0; i < needPlayer.length; i++) {
			card = new Card(CardType.Action, CardColor.None, 0, needPlayer[i]);
			card.targetPlayer = true;
			cards.add(card);
		}
		
		//NEED PLAYER CARD
		String[] needPlayerCard = {"Dodge", "Adapt", "Outwit"};
		for (int i = 0; i < needPlayerCard.length; i++) {
			card = new Card(CardType.Action, CardColor.None, 0, needPlayerCard[i]);
			card.targetPlayer = true;
			card.targetCard = true;
			cards.add(card);
		}	
	}

	public List<Card> getCards() {
		return this.cards;
	}
	
	public Card draw() {
		if (cards.size() == 0) return null;
		return cards.remove(cards.size()-1);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
}
