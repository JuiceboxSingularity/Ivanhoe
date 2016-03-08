package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class Token implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496446119416052447L;
	public CardColor tokenColor;
	
	public Token(CardColor c) {
		this.tokenColor = c;
	}
}
