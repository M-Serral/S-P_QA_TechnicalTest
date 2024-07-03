package steps;

import POM.TextBoxPage;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import stepDependencies.InputValues;

public class TextBoxSteps {

    private TextBoxPage textBoxPage;
    final InputValues inputValues = new InputValues();

    @Given("I access to the demoQA page")
    public void iAccessToDemoQAPage() {
        WebDriver driver = TextBoxPage.initializeDriver();
        textBoxPage = new TextBoxPage(driver);
        String provisionalBaseUrl = "https://demoqa.com/text-box";
        textBoxPage.loadPage(provisionalBaseUrl);
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
        textBoxPage.checkOutputValues(inputValues.getFullName(), inputValues.getEmail(), inputValues.getCurrentAddress(), inputValues.getPermanentAddress());
    }

    @When("I fill Email input with this value {string}")
    public void i_fill_Email_input_with(String email) {
        textBoxPage.fillInputValues("", email, "", "");
        inputValues.setEmail(email);

    }

    @And("I delete Email input")
    public void iDeleteEmailInput() {

        textBoxPage.deleteEmailInput();
    }

    @Then("Email input has some error indicator")
    public void email_input_has_some_error_indicator() {
        Assert.assertTrue("Email input does not have error indicator", textBoxPage.isEmailErrorDisplayed());
    }

    @Then("I check that invalid email is not shown")
    public void check_that_there_is_no_output_produced() {
        textBoxPage.checkNoOutputForInvalidEmail();
    }

    @Then("I check that the output email has not changed")
    public void i_check_that_the_output_email_has_not_changed() {
        textBoxPage.checkNoOutputChangeForInvalidEmail();
        Assert.assertNotEquals("Email output should not change for invalid input", textBoxPage.checkNoOutputChangeForInvalidEmail(), inputValues.getEmail());
    }



    @After
    public void closeBrowser() {
        textBoxPage.cleanUp();
        textBoxPage.quitDriver();
    }


}
