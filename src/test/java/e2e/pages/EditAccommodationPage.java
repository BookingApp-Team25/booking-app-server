package e2e.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditAccommodationPage {

    private WebDriver driver;
    private static String PAGE_URL="localhost:4200/accommodation-creation";

    @FindBy(css="#name")
    WebElement nameInput;
    @FindBy(css="#description")
    WebElement descriptionInput;
    @FindBy(css="#minGuests")
    WebElement minGuestsInput;
    @FindBy(css="#maxGuests")
    WebElement maxGuestsInput;
    @FindBy(css="#daysBefore")
    WebElement daysBeforeInput;
    @FindBy(css="#dailyPrice")
    WebElement dailyPriceInput;
    @FindBy(css="#seasonPrice")
    WebElement seasonPriceInput;
    @FindBy(css="#weekendPrice")
    WebElement weekendPriceInput;
    @FindBy(css="#holidayPrice")
    WebElement holidayPriceInput;
    @FindBy(css="#country")
    WebElement countryInput;
    @FindBy(css="#city")
    WebElement cityInput;
    @FindBy(css="#street")
    WebElement streetInput;
    @FindBy(css="#streetNumber")
    WebElement streetNumberInput;
    @FindBy(css="input[formControlName='start']")
    WebElement startDateInput;
    @FindBy(css="input[formControlName='end']")
    WebElement endDateInput;
    @FindBy(css = "#submitButton")
    WebElement submitButton;

    public EditAccommodationPage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver,this);
    }

    public String editAccommodation(String startDate, String endDate){
        nameInput.clear();
        nameInput.sendKeys("Hotel Majestic");
        descriptionInput.clear();
        descriptionInput.sendKeys("Very good hotel");
        minGuestsInput.clear();
        minGuestsInput.sendKeys("1");
        maxGuestsInput.clear();
        maxGuestsInput.sendKeys("6");
        daysBeforeInput.clear();
        daysBeforeInput.sendKeys("7");
        dailyPriceInput.clear();
        dailyPriceInput.sendKeys("30");
        seasonPriceInput.clear();
        seasonPriceInput.sendKeys("40");
        weekendPriceInput.clear();
        weekendPriceInput.sendKeys("35");
        holidayPriceInput.clear();
        holidayPriceInput.sendKeys("40");
        countryInput.clear();
        countryInput.sendKeys("Serbia");
        cityInput.clear();
        cityInput.sendKeys("Belgrade");
        streetInput.clear();
        streetInput.sendKeys("Bulevar Kralja Aleksandra");
        streetInput.clear();
        streetNumberInput.sendKeys("56");
        startDateInput.sendKeys(startDate);
        endDateInput.sendKeys(endDate);
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        return alertText;
    }
}
