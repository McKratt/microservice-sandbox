package net.bakaar.sandbox.bootstrap.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", strict = true)
@Ignore("should be rewritten")
public class RunCucumberIT {

}
