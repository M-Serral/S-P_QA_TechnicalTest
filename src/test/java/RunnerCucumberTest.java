import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/TextBox.feature",
        glue = "steps",
        plugin = {"json:target/cucumber-reports/cucumber.json"}
)
public class RunnerCucumberTest {
    //HERE WE RUN FEATURES WE CHOOSE IN @CucumberOptions
}