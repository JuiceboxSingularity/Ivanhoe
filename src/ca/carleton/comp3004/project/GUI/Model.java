package ca.carleton.comp3004.project.GUI;

import java.util.ArrayList;
import java.util.List;

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
	//private boolean chooseColor;
	//more?


	public Model() {
		hand = new ArrayList(0);
		playerArea = new ArrayList(0);
		playerArea1 = new ArrayList(0);
		playerArea2 = new ArrayList(0);
		playerArea3 = new ArrayList(0);
		playerArea4 = new ArrayList(0);
		playertokens = new ArrayList(0);
		player1tokens = new ArrayList(0);
		player2tokens = new ArrayList(0);
		player3tokens = new ArrayList(0);
		player4tokens = new ArrayList(0);
		playerSpecials = new ArrayList(0);
		player1Specials = new ArrayList(0);
		player2Specials = new ArrayList(0);
		player3Specials = new ArrayList(0);
		player4Specials = new ArrayList(0);
		
	}

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

	public void setHand(ArrayList x) {
		this.hand = x;
	}

	public ArrayList getHand() {
		return this.hand;
	}

	public int getHandSize() {
		return this.hand.size();
	}
	
	public ArrayList getPlayerArea() {
		return playerArea;
	}
	
	public ArrayList getPlayerArea1() {
		return playerArea1;
	}
	
	public ArrayList getPlayerArea2() {
		return playerArea2;
	}
	
	public ArrayList getPlayerArea3() {
		return playerArea3;
	}
	
	public ArrayList getPlayerArea4() {
		return playerArea4;
	}
	
	public ArrayList getPlayerTokens() {
		return playertokens;
	}
	
	public ArrayList getPlayerTokens1() {
		return player1tokens;
	}
	
	public ArrayList getPlayerTokens2() {
		return player2tokens;
	}
	
	public ArrayList getPlayerTokens3() {
		return player3tokens;
	}
	
	public ArrayList getPlayerTokens4() {
		return player4tokens;
	}
	
	public void setPlayerTokens() {
		ArrayList temp = new ArrayList(5);
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