package ca.carleton.comp3004.project.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({	CardTest.class,
				DeckTest.class,
				GameTest.class,
				PlayerTest.class,
				GameLogicTest.class,
				TournamentTest.class})
public class AllGameTests {
	
}
