package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class CardTest {


	@Test
	public void testCreateCard() {
		Card aCard = new Card(CardType.Color, CardColor.Blue, 4);
		
		assertTrue(aCard.getCardColor() == CardColor.Blue);
		assertTrue(aCard.getCardType() == CardType.Color);
		assertTrue(aCard.getValue() == 4);
	}

}
