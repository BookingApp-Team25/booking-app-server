package e2e.tests;

import e2e.pages.EditAccommodationPage;
import e2e.pages.HomePage;
import e2e.pages.HostAccommodationsPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EditAccommodationTest extends TestBase {

    @Test
    public void test(){
        HomePage homePage=new HomePage(driver);
        homePage.login();
        homePage.openHostAccommodationsPage();
        HostAccommodationsPage hostAccommodationsPage=new HostAccommodationsPage(driver);
        hostAccommodationsPage.clickEditButton();
        EditAccommodationPage editAccommodationPage=new EditAccommodationPage(driver);
        editAccommodationPage.editAccommodation("2/1/2024","2/29/2024");
    }

}
