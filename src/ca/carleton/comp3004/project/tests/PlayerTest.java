package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Deck;
import ca.carleton.comp3004.project.gameobjects.Player;

public class PlayerTest {

	static Player player = null;
	static Deck aDeck = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		player = new Player("Tester", 1);
		aDeck = new Deck();
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
	public void testCreatePlayer() {
		assertEquals(player.getPlayerName(), "Tester");
		
		
	}
	
	@Test
	public void testPlayerCards() {
		aDeck.shuffle();
		assertEquals(player.getHand().size(), 0);
		player.addCard(aDeck.draw());
		assertEquals(player.getHand().size(), 1);
		
		Card aCard = player.viewCard(0);
		player.addCardToPlay(aCard);
		assertTrue(aCard.equals(player.getInPlay().get(0)));
		
		player.addCardToDisplay(aCard);
		assertTrue(aCard.equals(player.getInDisplay().get(0)));
	}
	
	@Test
	public void testPlayerTokens() {
		assertEquals(player.getTokens().size(), 5);
		for (Map.Entry<CardColor, Integer> entry : player.getTokens().entrySet()) {
		    assertEquals(entry.getValue().intValue(), 0);
		}
	}

}
