package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;

public class GameLogicTest {

	static Game game;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		game = new Game(3);
		Player one = new Player("Joe", 1);
		Player two = new Player("Fred", 2);
		Player three = new Player("Jill", 3);

		game.addPlayer(one);
		game.addPlayer(two);
		game.addPlayer(three);

		game.dealTokens();
		System.out.println(game.getCurrentPlayer().getPlayerName());
		game.getDeck().shuffle();

		game.dealDeck();
		
		for (Player p : game.getPlayers()) {
			assertEquals(p.getHand().size(), 8);
		}
		
		game.startTurn();
		
		for (Player p : game.getPlayers()) {
			if (p.equals(game.getCurrentPlayer())) assertEquals(p.getHand().size(), 9);
			else assertEquals(p.getHand().size(), 8);
		}
		
		//while(!game.ended()) {
		// Get play from client
		System.out.println(game.validatePlay(game.getCurrentPlayer().viewCard(0)));
		System.out.println(game.getCurrentPlayer().viewCard(0).toString());
		game.getCurrentPlayer().addCardToPlay(game.getCurrentPlayer().removeCard(0));
		assertEquals(game.getCurrentPlayer().getInPlay().size(), 1);
		game.endTurn();
		System.out.println(game.getCurrentPlayer().getPlayerName());
		game.startTurn();
		System.out.println(game.getCurrentPlayer().getPlayerName());
		System.out.println(game.getCurrentPlayer().getHand().size());
		System.out.println(game.withdrawPlayer());
		game.startTurn();
		System.out.println(game.getCurrentPlayer().getPlayerName());
		System.out.println(game.withdrawPlayer());
		//}
	}

}
