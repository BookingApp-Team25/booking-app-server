package e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HostAccommodationsPage {
    private WebDriver driver;
    private static String PAGE_URL="http://localhost:4200/host-accommodations";

    @FindBy(css = "button[aria-label='Example icon button with a menu icon']")
    WebElement editAccommodationButton;

    public HostAccommodationsPage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver,this);
    }

    public void clickEditButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(editAccommodationButton));
        editAccommodationButton.click();
    }
}
