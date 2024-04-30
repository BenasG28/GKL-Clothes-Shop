package com.example.gkl.Cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import javafx.application.Platform;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/Features",
        glue = "com.example.gkl.Cucumber", // Change this to the package where your step definitions are located
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class BDDRunner {
    public class CucumberTestRunner {

        @BeforeClass
        public static void setUpJavaFX() {
            Platform.startup(() -> {
            });
        }

        @AfterClass
        public static void tearDownJavaFX() {
            Platform.exit();
        }
    }
}
