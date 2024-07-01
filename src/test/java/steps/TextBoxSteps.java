package steps;

import POM.TextBoxPage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import stepDependencies.InputValues;

public class TextBoxSteps {

    private WebDriver driver;
    private TextBoxPage textBoxPage;
    final InputValues inputValues = new InputValues();


    @Given("I access to the demoQA page")
    public void iAccessToDemoQAPage() {
        String provisionalBaseUrl = "https://demoqa.com/text-box";
        TextBoxPage.loadPage(provisionalBaseUrl);
        textBoxPage = new TextBoxPage(TextBoxPage.driver);
    }

    @When("I fill input fields with these values {string}, {string}, {string}, {string}")
    public void i_fill_input_fields_with_these_values(String fullName, String email, String currentAddress, String permanentAddress) {
        textBoxPage.fillInputValues(fullName, email, currentAddress, permanentAddress);
        inputValues.setFullName(fullName);
        inputValues.setEmail(email);
        inputValues.setCurrentAddress(currentAddress);
        inputValues.setPermanentAddress(permanentAddress);
    }

    @When("I click in Submit button")
    public void i_click_in_Submit_button() {
        textBoxPage.clickSubmit();
    }

    @Then("results match the input data")
    public void results_match_the_input_data() {
        textBoxPage.checkOutputValues(inputValues.getFullName(),inputValues.getEmail(),inputValues.getCurrentAddress(),inputValues.getPermanentAddress());
    }

    @When("I fill Email input with {string}")
    public void i_fill_Email_input_with(String email) {
        // Implementar llenado de campo de Email
        // textBoxPage.fillEmail(email);
    }

    @Then("Email input has some error indicator")
    public void email_input_has_some_error_indicator() {
        // Implementar verificación de indicador de error en Email
        // Assert.assertTrue("Email input no tiene indicador de error", textBoxPage.isEmailErrorDisplayed());
    }

    @Then("I check that there is no output produced")
    public void check_that_there_is_no_output_produced() {
        // Implementar verificación de que no hay resultado producido
        // Assert.assertTrue("Output no debería estar presente", textBoxPage.isOutputAbsent());
    }

    @After
    public void closeBrowser() {
        textBoxPage.cleanUp();
        textBoxPage.quitDriver();
    }
}
