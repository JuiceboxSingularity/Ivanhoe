package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;
import java.util.List;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class Gamestate implements Serializable {

	private Game game;
	private static final long serialVersionUID = 1L;

	public Gamestate(Game game, boolean isNextPlayer) {
		this.game = game;
	}
}
