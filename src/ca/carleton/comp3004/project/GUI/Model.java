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
	private ArrayList playertokens; //tokens that you have
	private ArrayList player1tokens; //tokens that player 1 has
	private ArrayList player2tokens;
	private ArrayList player3tokens;
	private ArrayList player4tokens;
	private ArrayList playerSpecials; //special cards that you have
	private ArrayList player1Specials; // special cards that player 1 has
	private ArrayList player2Specials;
	private ArrayList player3Specials;
	private ArrayList player4Specials;
	
	int playerNum = 0; //NOT PLAYER ID
	Game game;
	//private boolean chooseColor;
	//more?

	public Model(ArrayList hand, ArrayList area, ArrayList area1, ArrayList area2, ArrayList area3, ArrayList area4) {
		this.hand = hand;
		this.playerArea = area;
		this.playerArea1 = area1;
		this.playerArea2 = area2;
		this.playerArea3 = area3;
		this.playerArea4 = area4;
		this.playertokens = new ArrayList(0);
		this.player1tokens = new ArrayList(0);
		this.player2tokens = new ArrayList(0);
		this.player3tokens = new ArrayList(0);
		this.player4tokens = new ArrayList(0);
		this.playerSpecials = new ArrayList(0);
		this.player1Specials = new ArrayList(0);
		this.player2Specials = new ArrayList(0);
		this.player3Specials = new ArrayList(0);
		this.player4Specials = new ArrayList(0);
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

	public int getHandSize() {
		return this.hand.size();
	}
	
	public List<Card> getPlayerArea(int x) {
		return game.getPlayers().get(playerNum).getInDisplay();
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
	
	/*
	public void setPlayerTokens() {
		
		temp.add("P");
		temp.add("G");
		temp.add("B");
		temp.add("R");
		temp.add("Y");
		playertokens = temp;
		player1tokens = temp;
		player2tokens = temp;
		player3tokens = temp;
		player4tokens = temp;
	}
	*/
	
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