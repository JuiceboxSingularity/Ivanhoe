package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;

public class GameTest {

	static Game game;
	static Player one;
	static Player two;
	static Player three;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		game = new Game(2);
		one = new Player("Janna", 1);
		two = new Player("Garrosh", 2);
		three = new Player("Anduin", 3);
		
		game.addPlayer(one);
		game.addPlayer(two);
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
	public void addPlayersTest() {
		assertFalse(game.addPlayer(three));
	}

	@Test
	public void awardTokenTest() {
		CardColor color = CardColor.Blue;
		assertEquals(one.getTokens().get(color).intValue(), 0);
		assertEquals(game.getTokens().get(color).intValue(), 5);
		assertFalse(game.awardToken(one));
		game.setTournamentColor(color);
		assertTrue(game.awardToken(one));
		assertEquals(game.getTokens().get(color).intValue(), 4);
		assertEquals(one.getTokens().get(color).intValue(), 1);
	}
	
	@Test
	public void dealTokenTest() {
		game.dealTokens();
		assertNotEquals(game.getCurrentPlayer(), null);

	}
}
