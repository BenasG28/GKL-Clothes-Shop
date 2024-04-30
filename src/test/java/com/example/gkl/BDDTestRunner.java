package com.example.gkl;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/hellocucumber",
        glue = "com.example.gkl.steps"
)
public class BDDTestRunner {
}