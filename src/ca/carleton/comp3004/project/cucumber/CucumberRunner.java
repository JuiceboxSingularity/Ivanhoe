package ca.carleton.comp3004.project.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "json:target/cucumber.json"}, features = {"src/ca/carleton/comp3004/project/cucumber/features/"})
public class CucumberRunner {

}
