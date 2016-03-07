package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;
import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class TournamentTest {

	static Game game;
	static Player one;
	static Player two;
	static Player three;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		game = new Game(3);
		one = new Player("Anduin", 1);
		two = new Player("Malfurion", 2);
		three = new Player("Garrosh", 3);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void startTournamentTest() {
		game.addPlayer(one);
		game.addPlayer(two);
		game.addPlayer(three);
		
		assertEquals(game.getTournamentColor(), CardColor.None);
		
		game.getDeck().shuffle();
		game.dealTokens();
		game.dealDeck();
		
		game.startTurn();
		int index = 0;
		boolean end = false;
		while(!end) {
			boolean hasColor = false;
			for (Card c : game.getCurrentPlayer().getHand()) {
				if (c.getCardType() == CardType.Color) {
					index = game.getCurrentPlayer().getHand().indexOf(c);
					hasColor = true;
					break;
				}
			}
			if (hasColor) break;
			end = game.withdrawPlayer();
		}
		
		if (game.validatePlay(game.getCurrentPlayer().getHand().get(index))) {
			CardColor color = game.getCurrentPlayer().viewCard(index).getCardColor();
			game.performPlay(index);
		
			//Assert that the tournament color is what the player played
			assertEquals(game.getTournamentColor(), color);
			System.out.println("Started " + color.toString() + " Tournament!");
		} else {
			fail("Did not receive a colored card");
		}
		
		// Test playing a supporter card
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
		if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
			game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		} else {
			fail("Could not play a supporter card!!");
		}
		
		//Test playing two maidens in one tournament
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
		if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
			fail("Should not be able to play two maidens!!");
		} 
		
		game.endTurn();
		game.startTurn();
		
		//Test player ending turn without playing a larger total
		assertFalse(game.endTurn());
		
		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());
		
		//Check that the player indeed wins a token of the tournament color
		CardColor tournamentColor = game.getTournamentColor();
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
		System.out.println(game.getCurrentPlayer().getPlayerName() + " Has Won The " + tournamentColor.toString() + " Tournament!!");
		//Make sure a player cannot win more than one token of the same color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
	}

}
