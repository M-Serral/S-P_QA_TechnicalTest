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

    public static WebDriver driver;
    protected static WebDriverWait wait;

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

    @FindBy(xpath = "//*[@id=\"output\"]/div")
    private WebElement box_values;

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
        PageFactory.initElements(driver, this);
    }

    public static void loadPage(String url) {
        // Define the path to your chromedriver
        String driverPath = Paths.get("drivers", "chromedriver").toString();

        // Set the system property for chromedriver
        System.setProperty("webdriver.chrome.driver", driverPath);

        // Set ChromeOptions to avoid WebSocket connection issues
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        // Initialize the ChromeDriver with options
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
