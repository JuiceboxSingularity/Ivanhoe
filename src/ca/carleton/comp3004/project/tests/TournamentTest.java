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

		game.addPlayer(one);
		game.addPlayer(two);
		game.addPlayer(three);

		assertEquals(game.getTournamentColor(), CardColor.None);

		game.getDeck().shuffle();
		game.dealTokens();
		game.dealDeck();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void yellowTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);

		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Yellow).intValue(), 1);
	}

	@Test
	public void greenTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Green).intValue(), 1);
	}

	@Test
	public void blueTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);

		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Blue).intValue(), 1);
	}

	@Test
	public void redTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);

		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Red).intValue(), 1);
	}

	@Test
	public void purpleTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);

		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Purple).intValue(), 1);
	}

	@Test
	public void whiteTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.None);
	}

	@Test
	public void consecutivePurpleTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);

		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Purple).intValue(), 1);

		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		assertFalse(game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1)));

		assertEquals(game.getTournamentColor(), CardColor.None);
	}

	@Test
	public void tournamentPlaySupporter() {
		// Test playing a supporter card
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
		if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
			game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		} else {
			fail("Could not play a supporter card!!");
		}
	}

	@Test
	public void tournamentSupporterRestriction() {
		// Test playing a Maiden card
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
			game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		} else {
			fail("Could not play a supporter card!!");
		}
		//Test playing two maidens in one tournament
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		if (game.validatePlay(game.getCurrentPlayer().viewCard(game.getCurrentPlayer().getHand().size()-1))) {
			fail("Should not be able to play two maidens!!");
		} 
	}

	@Test
	public void testForceWithdrawPlayer() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		game.endTurn();
		//Start the next player's turn
		game.startTurn();

		//Test player ending turn without playing a larger total
		assertFalse(game.endTurn());
	}

	@Test
	public void tournamentRewardToken() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		game.endTurn();
		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		CardColor tournamentColor = game.getTournamentColor();
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
	}

	@Test
	public void tournamentTokenRestrictions() {
		game.setTournamentColor(CardColor.Green);
		game.awardToken(one);
		assertEquals(one.getTokens().get(CardColor.Green).intValue(), 1);
		game.awardToken(one);
		assertEquals(one.getTokens().get(CardColor.Green).intValue(), 1);
	}

	@Test
	public void multipleTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		game.endTurn();
		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		CardColor tournamentColor = game.getTournamentColor();
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		game.endTurn();
		//Withdraw other two players
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());
		tournamentColor = game.getTournamentColor();
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(tournamentColor).intValue(), 1);
	}

	@Test
	public void testDisgrace() {
		game.startTurn();

		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);


		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertTrue(c.getCardType().equals(CardType.Supporter));
		}

		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Disgrace"));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertFalse(c.getCardType().equals(CardType.Supporter));
		}
	}

	@Test
	public void testCharge() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 5));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 6));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();

		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Charge"));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertFalse(c.getCardValue() == 3);
		}
	}
	
	@Test
	public void testOutmaneuver() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 5));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 6));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();

		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Outmaneuver"));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		for (Player p : game.getPlayers()) {
			for (Card c : p.getInPlay()) {
				assertTrue(c.getCardValue() == 3);
			}
		}
	}

	@Test
	public void testCountercharge() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();

		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Countercharge"));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertFalse(c.getCardValue() == 4);
		}
	}
	
	@Test
	public void testUnhorse() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Unhorse"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Unhorse")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 5));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		assertEquals(CardColor.Red, game.getTournamentColor());
		assertFalse(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Unhorse")));
		
	}
	
	//TODO: This doesn't check the condition that you can only change from R/G/B to R/G/B
	@Test
	public void testChangeWeapon() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Red, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Changeweapon"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Changeweapon")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 5));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());

		game.startTurn();
		assertEquals(CardColor.Blue, game.getTournamentColor());
	}
}
