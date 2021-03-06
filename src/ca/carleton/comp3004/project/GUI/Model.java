package ca.carleton.comp3004.project.GUI;

import java.util.ArrayList;
import java.util.List;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class Model {

	private ArrayList hand; //Array list of hand
	private ArrayList playerArea; //cards that are in play for you
	private ArrayList playerArea1; //cards that are in play for player 1
	private ArrayList playerArea2;
	private ArrayList playerArea3;
	private ArrayList playerArea4;
	private ArrayList playerSpecials; //special cards that you have
	private ArrayList player1Specials; // special cards that player 1 has
	private ArrayList player2Specials;
	private ArrayList player3Specials;
	private ArrayList player4Specials;
	
	int playerNum = 0; //NOT PLAYER ID
	int lastPlayerNum = 0;
	Game game;
	//private boolean chooseColor;
	//more?

	public Model(){
		
	}
	
	public Model(Game game){
		this.game = game;
	}
	
	public void setPlayer(int x){
		this.playerNum = x-1;
	}
	
	//TODO : TEMPORARY TILL FIXED
	public void setGame(Game game){
		this.game = game;
		//this.playerNum = playerNum;
	}

	public void setHand(ArrayList x) {
		this.hand = x;
	}

	public List<Card> getHand() {
		return game.getPlayers().get(playerNum).getHand();
	}
	
	public List<Card> getPlayerArea(int x) {
		return game.getPlayers().get(x).getInPlay();
	}
		
	public ArrayList getPlayerTokens(int x) {
		ArrayList temp = new ArrayList(5);
		java.util.Map<CardColor, Integer> tokens;
		tokens = game.getPlayers().get(x).getTokens();
		if (tokens.get(Card.CardColor.Red)>0){
			temp.add("R");
		}
		if (tokens.get(Card.CardColor.Blue)>0){
			temp.add("B");
		}
		if (tokens.get(Card.CardColor.Green)>0){
			temp.add("G");
		}
		if (tokens.get(Card.CardColor.Yellow)>0){
			temp.add("Y");
		}
		if (tokens.get(Card.CardColor.Purple)>0){
			temp.add("P");
		}
		return temp;
	}
	
	public ArrayList getPlayerSpecials() {
		return playerSpecials;
	}
	
	public ArrayList getPlayer1Specials() {
		return player1Specials;
	}
	
	public ArrayList getPlayer2Specials() {
		return player2Specials;
	}
	
	public ArrayList getPlayer3Specials() {
		return player3Specials;
	}
	
	public ArrayList getPlayer4Specials() {
		return player4Specials;
	}
	
	public void addPlayerSpecials(String special) {
		playerSpecials.add(special);
		player1Specials.add(special);
		player2Specials.add(special);
		player3Specials.add(special);
		player4Specials.add(special);
	}
}