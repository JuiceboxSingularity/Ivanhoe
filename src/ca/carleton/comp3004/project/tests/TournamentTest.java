package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import java.util.Map.Entry;

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
		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();
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
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		assertFalse(game.withdrawPlayer());
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

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		assertFalse(game.withdrawPlayer());
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

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		assertFalse(game.withdrawPlayer());
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

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		assertFalse(game.withdrawPlayer());
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

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		assertFalse(game.withdrawPlayer());
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
	
	//TODO: This doesn't check the condition that you can only change from R/Y/B to R/Y/B
	//TODO: Also, make sure you can't play this card if the current color != R/Y/B
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
	
	@Test
	public void testDropWeapon() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Red, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Dropweapon"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Dropweapon")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(CardColor.Green, game.getTournamentColor());
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 5)));
	}
	
	@Test
	public void testBreakLance() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		int targetId = game.getCurrentPlayer().getId();
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Breaklance"));
		game.setTargetPlayer(targetId);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Breaklance")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetId) {
				for (Card c : p.getHand()) {
					assertFalse(c.getCardColor() == CardColor.Purple);
				}
			}
		}
	}
	
	@Test
	public void testRiposte() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		int targetId = game.getCurrentPlayer().getId();
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Riposte"));
		game.setTargetPlayer(targetId);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Riposte")));
		Card takenCard = game.getTargetPlayer().getInPlay().get(0);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		assertTrue((takenCard.getCardColor() == CardColor.Purple) && ( takenCard.getCardType() == CardType.Color) && (takenCard.getCardValue() == 4));
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetId) {
				assertTrue(p.getInPlay().size() == 0);
			}
		}
	}
	
	@Test
	public void testDodge() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		int targetId = game.getCurrentPlayer().getId();
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Dodge"));
		game.setTargetPlayer(targetId);
		game.setTargetCard(0);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Dodge")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetId) {
				for (Card c : p.getInPlay()) {
					assertFalse((c.getCardColor() == CardColor.Purple) && (c.getCardValue() == 4));
				}
			}
		}
	}
	
	@Test
	public void testRetreat() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Retreat"));
		// We want to take back the supporter
		game.setTargetCard(1);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Retreat")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertFalse(c.equals(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		}
	}
	
	@Test
	public void testKnockdown() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		int targetPlayer = game.getCurrentPlayer().getId();
		int targetPlayerHandSize = game.getCurrentPlayer().getHand().size();
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Knockdown"));
		game.setTargetPlayer(targetPlayer);
		int currentPlayerHandSize = game.getCurrentPlayer().getHand().size();
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Knockdown")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetPlayer) {
				assertEquals(p.getHand().size(), targetPlayerHandSize - 1);
			}
		}
		assertEquals(game.getCurrentPlayer().getHand().size(), currentPlayerHandSize);
	}
	
	@Test
	public void testAdapt() {
		game.startTurn();
		game.getCurrentPlayer().getHand().clear();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().clear();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().clear();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Adapt"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Adapt")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			assertTrue(game.validateAdapt(p));
		}
		
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		
		assertFalse(game.validateAdapt(game.getCurrentPlayer()));
	}
	
	@Test
	public void testOutwit() {
		
	}
	
	@Test
	public void testShieldAgainstKnockdown() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Shield"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Shield")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		int targetPlayer = game.getCurrentPlayer().getId();
		int targetPlayerHandSize = game.getCurrentPlayer().getHand().size();
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Knockdown"));
		game.setTargetPlayer(targetPlayer);
		int currentPlayerHandSize = game.getCurrentPlayer().getHand().size();
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Knockdown")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetPlayer) {
				assertEquals(p.getHand().size(), targetPlayerHandSize);
			}
		}
		assertEquals(game.getCurrentPlayer().getHand().size(), currentPlayerHandSize - 1);
	}
	
	@Test
	public void testShieldAgainstDodge() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		int targetId = game.getCurrentPlayer().getId();
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Shield"));
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Shield")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Dodge"));
		game.setTargetPlayer(targetId);
		game.setTargetCard(0);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Dodge")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Player p : game.getPlayers()) {
			if (p.getId() == targetId) {
					assertTrue((p.getInPlay().get(0).getCardColor() == CardColor.Purple) && 
							((p.getInPlay().get(0).getCardValue() == 4)));
			}
		}
	}
	
	@Test
	public void testShieldAgainstDisgrace() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6));
		game.setTournamentColor(CardColor.Red);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Shield"));
		Player shieldedPlayer = game.getCurrentPlayer();
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Shield")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertTrue(c.getCardType().equals(CardType.Supporter));
		}
		game.endTurn();
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Disgrace"));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		//Make sure the card is still there
		for (Card c : shieldedPlayer.getInPlay()) {
			assertTrue(c.getCardType().equals(CardType.Supporter));
		}
	}
	
	@Test
	public void testShieldAgainstRetreat() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Shield"));
		Player shieldedPlayer = game.getCurrentPlayer();
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Shield")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Retreat"));
		// We want to take back the supporter
		game.setTargetCard(1);
		assertTrue(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Retreat")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		// Nothing should change
		for (Card c : game.getCurrentPlayer().getInPlay()) {
			assertFalse(c.equals(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		}
		Card cardInHand = shieldedPlayer.getHand().get(shieldedPlayer.getHand().size() - 1);
		assertTrue(cardInHand.getCardType() == CardType.Supporter && (cardInHand.getCardName() == "Maiden"));
	}
	
	@Test
	public void testShieldAgainstRiposte() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 5));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		int targetId = game.getCurrentPlayer().getId();
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		assertEquals(CardColor.Purple, game.getTournamentColor());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Action, CardColor.None, 0, "Riposte"));
		game.setTargetPlayer(targetId);
		assertFalse(game.validatePlay(new Card(CardType.Action, CardColor.None, 0, "Riposte")));
	}
	@Test
	public void testStunned() {
		
	}
	
	@Test
	public void testIvanhoe() {
		
	}
}
