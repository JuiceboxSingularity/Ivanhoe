package ca.carleton.comp3004.project.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;

public class Iteration2Tests {

	static Game game;
	static Player one;
	static Player two;
	static Player three;
	static Player four;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		game = new Game(4);
		one = new Player("Anduin", 1);
		two = new Player("Malfurion", 2);
		three = new Player("Garrosh", 3);
		four = new Player("Rexxar", 4);
		game.addPlayer(one);
		game.addPlayer(two);
		game.addPlayer(three);
		game.addPlayer(four);
		assertEquals(game.getTournamentColor(), CardColor.None);

		game.getDeck().shuffle();
		game.dealDeck();
		game.dealTokens();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGreenBulletB1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testGreenBulletB2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletB1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testYellowBulletB2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testRedBulletB1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testRedBulletB2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 5));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 5)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testBlueBulletB1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testBlueBulletB2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testPurpleBulletB1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testPurpleBulletB2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testGreenBulletC1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertFalse(game.endTurn());
	}

	@Test
	public void testGreenBulletC2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletC1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletC2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testRedBulletC1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testRedBulletC2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testBlueBulletC1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testBlueBulletC2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testPurpleBulletC1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testPurpleBulletC2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testGreenBulletD1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertFalse(game.endTurn());
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertFalse(game.endTurn());
	}

	@Test
	public void testGreenBulletD2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertFalse(game.endTurn());
	}

	@Test
	public void testYellowBulletD1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 5));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 5)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletD2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testRedBulletD1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 5));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 5)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testRedBulletD2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testBlueBulletD1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 5));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 5)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testBlueBulletD2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testPurpleBulletD1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testPurpleBulletD2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 4));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 4)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testGreenBulletE1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
	}

	@Test
	public void testGreenBulletE2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
	}

	@Test
	public void testGreenBulletE3() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		// Cannot play more than 1 maiden per tournament
		assertFalse(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

	}

	@Test
	public void testYellowBulletE1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletE2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
	}

	@Test
	public void testYellowBulletE3() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		// Cannot play more than 1 maiden per tournament
		assertFalse(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testRedBulletE1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
	}

	@Test
	public void testRedBulletE2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
	}

	@Test
	public void testRedBulletE3() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		// Cannot play more than 1 maiden per tournament
		assertFalse(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testBlueBulletE1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
	}

	@Test
	public void testBlueBulletE2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
	}

	@Test
	public void testBlueBulletE3() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		// Cannot play more than 1 maiden per tournament
		assertFalse(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testPurpleBulletE1() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
	}

	@Test
	public void testPurpleBulletE2() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
	}

	@Test
	public void testPurpleBulletE3() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		// Cannot play more than 1 maiden per tournament
		assertFalse(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 2, "Squire"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 2, "Squire")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Test
	public void testGreenBulletG() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 1)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testYellowBulletG() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Yellow);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 2));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 2)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testRedBulletG() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testBlueBulletG() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void testPurpleBulletG() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);
		assertTrue(game.endTurn());
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		//Cannot end turn because did not play enough cards
		assertFalse(game.endTurn());
	}

	@Test
	public void greenTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 1));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Green);
		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Green).intValue(), 1);
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
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Yellow).intValue(), 1);
	}

	@Test
	public void redTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Red);

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Red).intValue(), 1);
	}

	@Test
	public void blueTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Blue);

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Blue).intValue(), 1);
	}

	@Test
	public void purpleTournamentTest() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());

		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Purple).intValue(), 1);
	}

	@Test
	public void testBulletJ() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));

		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

		assertEquals(game.getTournamentColor(), CardColor.Purple);

		assertTrue(game.endTurn());
		//Withdraw other two players
		game.startTurn();		
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		assertFalse(game.withdrawPlayer());
		game.startTurn();
		//This returns true if the tournament has ended
		assertTrue(game.withdrawPlayer());
		//Player wants a red token
		game.setCustomColor(CardColor.Red);
		//Check that the player indeed wins a token of the tournament color
		game.endTournament();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Red).intValue(), 1);
	}
	
	@Test
	public void testGreenBulletK() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.awardToken(game.getCurrentPlayer());
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Green).intValue(), 1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Green);
		game.withdrawPlayer();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Green).intValue(), 0);
	}
	
	@Test
	public void testYellowBulletK() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.awardToken(game.getCurrentPlayer());
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Yellow).intValue(), 1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Yellow);
		game.withdrawPlayer();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Yellow).intValue(), 0);
	}
	
	@Test
	public void testRedBulletK() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.awardToken(game.getCurrentPlayer());
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Red).intValue(), 1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Red);
		game.withdrawPlayer();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Red).intValue(), 0);
	}
	
	@Test
	public void testBlueBulletK() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.awardToken(game.getCurrentPlayer());
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Blue).intValue(), 1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Blue);
		game.withdrawPlayer();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Blue).intValue(), 0);
	}
	
	@Test
	public void testPurpleBulletK() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.awardToken(game.getCurrentPlayer());
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Purple).intValue(), 1);
		game.getCurrentPlayer().getHand().add(new Card(CardType.Supporter, CardColor.White, 6, "Maiden"));
		assertTrue(game.validatePlay(new Card(CardType.Supporter, CardColor.White, 6, "Maiden")));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Purple);
		game.withdrawPlayer();
		assertEquals(game.getCurrentPlayer().getTokens().get(CardColor.Purple).intValue(), 0);
	}
	
	@Test
	public void testAwardTokenTwice() {
		game.startTurn();
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
		game.setCustomColor(CardColor.Green);
		// If true, token was awarded
		assertTrue(game.awardToken(game.getCurrentPlayer()));
		// If false, ask user to change his selection for token
		assertFalse(game.awardToken(game.getCurrentPlayer()));
	}
}
