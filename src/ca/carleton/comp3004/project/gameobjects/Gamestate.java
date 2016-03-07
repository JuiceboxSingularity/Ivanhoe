package ca.carleton.comp3004.project.gameobjects;

import java.io.Serializable;

public class Gamestate implements Serializable {

	private Game game;
	private static final long serialVersionUID = 1L;

	public Gamestate(Game game, boolean isNextPlayer) {
		this.game = game;
	}
}
