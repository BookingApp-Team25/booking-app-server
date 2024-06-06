package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private static String PAGE_URL="http://localhost:4200/home";
    @FindBy(css = ".account-button")
    WebElement accountMenu;
    @FindBy(css = ".account-menu button:nth-child(1)")
    WebElement loginMenuButton;
    @FindBy(css = ".log-in-pop-up")
    WebElement loginPopup;
    @FindBy(css="input[name='username']")
    WebElement emailInput;
    @FindBy(css="input[name='password']")
    WebElement passwordInput;
    @FindBy(css="button[type='submit']")
    WebElement loginSubmitButton;
    @FindBy(css = ".account-menu button:nth-child(5)")
    WebElement yourAccommodationsButton;
    @FindBy(css = ".account-menu button:nth-child(2)")
    WebElement guestReservationsButton;

    public HomePage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver,this);
    }

    public void login(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(accountMenu));
        accountMenu.click();
        wait.until(ExpectedConditions.visibilityOf(loginMenuButton));
        loginMenuButton.click();
        wait.until(ExpectedConditions.visibilityOf(loginPopup));
        emailInput.sendKeys("guest@gmail.com");
        passwordInput.sendKeys("password1");
        loginSubmitButton.click();
    }
    public void openGuestReservationsPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(loginPopup));
        accountMenu.click();
        wait.until(ExpectedConditions.visibilityOf(guestReservationsButton));
        guestReservationsButton.click();
    }
    public void openHostAccommodationsPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(loginPopup));
        accountMenu.click();
        wait.until(ExpectedConditions.visibilityOf(yourAccommodationsButton));
        yourAccommodationsButton.click();
    }
}