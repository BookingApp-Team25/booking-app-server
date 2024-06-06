package e2e.testing;


import e2e.pages.GuestReservationPage;
import e2e.pages.HomePage;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReservationCancellingTest extends TestBase {

    @Test
    public void test() throws InterruptedException {
        HomePage homePage=new HomePage(driver);
        homePage.login();
        homePage.openGuestReservationsPage();
        GuestReservationPage guestReservationPage = new GuestReservationPage(driver);
        guestReservationPage.viewAllReservations();
        guestReservationPage.cancelReservation();
    }

}