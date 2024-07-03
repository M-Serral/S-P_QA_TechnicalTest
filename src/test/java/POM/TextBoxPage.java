package POM;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.nio.file.Paths;

public class TextBoxPage {

    public WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(id = "userName")
    private WebElement inpt_userName;

    @FindBy(id = "userEmail")
    private WebElement inpt_userEmail;

    @FindBy(id = "currentAddress")
    private WebElement inpt_currentAddress;

    @FindBy(id = "permanentAddress")
    private WebElement inpt_permamentAddress;

    @FindBy(id = "submit")
    private WebElement btn_submit;

    @FindBy(id = "name")
    private WebElement txt_name;

    @FindBy(id = "email")
    private WebElement txt_email;

    @FindBy(xpath = "//*[@id=\"output\"]/div/p[4]")
    private WebElement txt_permanentAddress;

    @FindBy(xpath = "//*[@id=\"output\"]/div/p[3]")
    private WebElement txt_currentAddress;

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public static WebDriver initializeDriver() {
        // Define the path to your chromedriver
        String driverPath = Paths.get("drivers", "chromedriver").toString();
        System.setProperty("webdriver.chrome.driver", driverPath);

        // Set ChromeOptions to avoid WebSocket connection issues
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        // Initialize the ChromeDriver with options
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    public void loadPage(String url) {
        driver.get(url);
    }

    public void fillInputValues(String fullName, String email, String currentAddress, String permanentAddress) {
        inpt_userName.sendKeys(fullName);
        inpt_userEmail.sendKeys(email);
        inpt_currentAddress.sendKeys(currentAddress);
        inpt_permamentAddress.sendKeys(permanentAddress);
    }

    public void clickSubmit() {
        scrollToElement(btn_submit);
        wait.until(ExpectedConditions.elementToBeClickable(btn_submit));
        btn_submit.click();
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void checkOutputValues(String fullName, String email, String currentAddress, String permanentAddress) {
        Assert.assertEquals("name do not match", extractTextAfterColon(txt_name.getText()), fullName);
        Assert.assertEquals("mail do not match", extractTextAfterColon(txt_email.getText()), email);
        Assert.assertEquals("current address do not match", extractTextAfterColon(txt_currentAddress.getText()), currentAddress);
        Assert.assertEquals("permanent address do not match", extractTextAfterColon(txt_permanentAddress.getText()), permanentAddress);
    }

    public String extractTextAfterColon(String text) {
        if (text.contains(":")) {
            return text.split(":", 2)[1].trim();
        }
        return text;
    }

    public boolean isEmailErrorDisplayed() {
        return inpt_userEmail.getAttribute("class").contains("field-error");
    }

    public void deleteEmailInput() {
        inpt_userEmail.clear();
    }

    public void checkNoOutputForInvalidEmail() {
        // Wait for a short duration to ensure the page has time to update
        wait.withTimeout(Duration.ofSeconds(1));

        // Check that the output box does not contain the email field for invalid input
        boolean isEmailOutputPresent = !driver.findElements(By.id("email")).isEmpty();
        Assert.assertFalse("Invalid email output should not be present for invalid input", isEmailOutputPresent);
    }

    public String checkNoOutputChangeForInvalidEmail() {
        wait.withTimeout(Duration.ofSeconds(1));
        return extractTextAfterColon(txt_email.getText());
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void cleanUp() {
        if (driver != null) {
            driver.manage().deleteAllCookies(); // delete cookies
            driver.navigate().refresh(); // refresh page to apply changes
        }
    }


}
