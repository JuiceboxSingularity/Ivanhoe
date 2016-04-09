package ca.carleton.comp3004.project.cucumber.features;

import org.junit.Assert;

import ca.carleton.comp3004.project.gameobjects.Card;
import ca.carleton.comp3004.project.gameobjects.Card.CardColor;
import ca.carleton.comp3004.project.gameobjects.Card.CardType;
import ca.carleton.comp3004.project.gameobjects.Game;
import ca.carleton.comp3004.project.gameobjects.Player;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {

	public static Game game;
	Player one = new Player("Anduin", 1);
	Player two = new Player("Malfurion", 2);

	@Given("^A new game started$")
	public void a_new_game_started() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		game = new Game(3);
	}

	@Given("^Players join the game$")
	public void players_join_the_game() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		game.addPlayer(one);
		game.addPlayer(two);
	}

	@Given("^Cards and tokens get dealt$")
	public void cards_and_tokens_get_dealt() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		game.getDeck().shuffle();
		game.dealTokens();
		game.dealDeck();
	}

	@When("^It is a players turn to play$")
	public void it_is_a_players_turn_to_play() throws Throwable {
		game.startTurn();
	}

	@Then("^That player can play a yellow card$")
	public void that_player_can_play_a_yellow_card() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Yellow, 3));
		Assert.assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Yellow, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);

	}
	
	@Then("^The tournament color is now yelllow$")
	public void the_tournament_color_is_now_yelllow() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(game.getTournamentColor() == CardColor.Yellow);
	}
	
	@Then("^That player can play a green card$")
	public void that_player_can_play_a_green_card() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Green, 3));
		Assert.assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Green, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Then("^The tournament color is now green$")
	public void the_tournament_color_is_now_green() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(game.getTournamentColor() == CardColor.Green);
	}

	@Then("^That player can play a red card$")
	public void that_player_can_play_a_red_card() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Red, 3));
		Assert.assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Red, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Then("^The tournament color is now red$")
	public void the_tournament_color_is_now_red() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(game.getTournamentColor() == CardColor.Red);
	}
	
	@Then("^That player can play a purple card$")
	public void that_player_can_play_a_purple_card() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Purple, 3));
		Assert.assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Purple, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Then("^The tournament color is now purple$")
	public void the_tournament_color_is_now_purple() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(game.getTournamentColor() == CardColor.Purple);
	}

	@Then("^That player can play a blue card$")
	public void that_player_can_play_a_blue_card() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		game.getCurrentPlayer().getHand().add(new Card(CardType.Color, CardColor.Blue, 3));
		Assert.assertTrue(game.validatePlay(new Card(CardType.Color, CardColor.Blue, 3)));
		game.performPlay(game.getCurrentPlayer().getHand().size()-1);
	}

	@Then("^The tournament color is now blue$")
	public void the_tournament_color_is_now_blue() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(game.getTournamentColor() == CardColor.Blue);
	}


}
