package rs.ac.uns.ftn.asd.Projekatsiit2023.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class AccommodationRepositoryTest {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    DatePeriodRepository datePeriodRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    public Accommodation createAccommodation(){
        String name = "Sample Accommodation";
        String description = "This is a sample accommodation description.";
        Location location = new Location("Serbia", "Belgrade","Bulevar Dr Zorana Djindjica",1);
        List<String> amenities = Arrays.asList("Wi-Fi", "Air Conditioning", "Parking");
        List<Image> photos = new ArrayList<Image>();
        int minGuests = 2;
        int maxGuests = 4;
        AccommodationType type = AccommodationType.Room;
        double price = 100.0; // You mentioned not to use this, so it's here for illustration
        AccommodationPricelist pricelist = new AccommodationPricelist();
        int daysBefore = 7;
        AccommodationReservationPolicy policy = AccommodationReservationPolicy.Auto;
        Accommodation accommodation = new Accommodation(
                name, description, location, amenities, photos, minGuests, maxGuests,
                type, price, pricelist, daysBefore, policy
        );
        return accommodation;
    }

    @Test
    public void findAllByHostAndAccommodationIdTest(){
        // Arrange
        Host host = new Host("hostic@gmail.com","password","Petar","Petrovic","Belgrade","0654421252", Role.Guest);
        Guest guest = new Guest("gestic@gmail.com","password","Marko","Markovic","Belgrade","0654421252", Role.Guest);
        hostRepository.save(host);
        guestRepository.save(guest);

        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024,3,1),
                                             LocalDate.of(2024,3,10));
        datePeriodRepository.save(datePeriod);

        Accommodation accommodation = createAccommodation();
        accommodation.setHost(host);
        accommodationRepository.save(accommodation);
        UUID accommodationId=accommodation.getId();

        Reservation reservation=new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriod,200);
        reservationRepository.save(reservation);

        // Act
        List<Reservation> reservations = reservationRepository.findAllByHostAndAccommodationId(host.getId(), accommodationId);

        // Assert
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }
}
