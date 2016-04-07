package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Deck;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class DeckTest {

	static Deck aDeck = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void createDeck() {
		assertNotEquals(aDeck, null);
	}

	@Test
	public void assertDeckContents() {
		List<Card> list = aDeck.getCards();
		
		int blues = 0;
		int greens = 0;
		int yellows = 0;
		int purples = 0;
		int supporters = 0;
		int actions = 0;
		for (Card c : list) {
			if (c.getCardColor() == CardColor.Purple) {
				purples++;
			} else if (c.getCardColor() == CardColor.Blue) {
				blues++;
			} else if (c.getCardColor() == CardColor.Green) {
				greens++;
			} else if (c.getCardColor() == CardColor.Yellow) {
				yellows++;
			} else if (c.getCardType() == CardType.Supporter) {
				supporters++;
			} else if (c.getCardType() == CardType.Action) {
				actions++;
			}
		}
		
		assertEquals(blues, 14);
		assertEquals(greens, 14);
		assertEquals(purples, 14);
		assertEquals(yellows, 14);
		assertEquals(supporters, 20);
		assertEquals(actions, 20);
		
		assertEquals(list.size(), 110);
	}
	
	@Test
	public void assertDeckShuffle() {
		Deck aDeck2 = new Deck();
		aDeck2.shuffle();
		int index = 0;
		for(index = 0; index < aDeck.getCards().size(); index++) {
			if (aDeck.getCards().get(index) != aDeck.getCards().get(index)) break;
		}
		
		assertNotEquals(index, aDeck.getCards().size()-1);
	}
	
	@Test
	public void testEndOfDeck() {
		Deck aDeck2 = new Deck();
		
		for (int i = 0; i < 110; i++) {
			aDeck2.draw();
		}
		
		assertTrue(aDeck2.draw() == null);
	}
}
