package e2e.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuestReservationPage {
    private WebDriver driver;
    private static String PAGE_URL="http://localhost:4200/guest-reservations-view";
    @FindBy(css = "#reservation-mat-list app-reservation-guest-card")
    List<WebElement> reservations;

    public GuestReservationPage(WebDriver driver){
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver,this);
    }
    public void viewAllReservations(){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to the bottom of the page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Add a pause to see the effect (optional)
        try {
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Scroll back to the top of the page
        js.executeScript("window.scrollTo(0, 0);");
    }
    public void cancelReservation() throws InterruptedException {

            WebElement cancelButton = driver.findElement(By.cssSelector("#reservation-mat-list app-reservation-guest-card:nth-child(1) #cancel-button"));
            cancelButton.click();
            Thread.sleep(5000);

    }
}
