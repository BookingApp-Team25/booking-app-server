package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationUpdateControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    AccommodationUpdateRepository accommodationUpdateRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    DatePeriodRepository datePeriodRepository;
    @Autowired
    UserRepository userRepository;

    //Creates accommodation
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

    //This method inserts data that needs to be prepared for test
    @BeforeAll
    private void insertData(){
        Host host = new Host("petar@gmail.com","$2a$12$XCFKI7HuFKl050mdW9lqN.D1Gy7hagekFVlKn8ZqsNIn2oDlJL09K","Petar","Petrovic","Belgrade","0654421252", Role.Host);
        Guest guest = new Guest("marko@gmail.com","$2a$12$XCFKI7HuFKl050mdW9lqN.D1Gy7hagekFVlKn8ZqsNIn2oDlJL09K","Marko","Markovic","Belgrade","0654421252", Role.Guest);
        host.setBlocked(false);
        userRepository.save(host);
        userRepository.save(guest);

        Accommodation accommodation=createAccommodation();
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        accommodation.setHost(host);
        accommodationRepository.save(accommodation);

        List<Reservation> reservations = new ArrayList<Reservation>();
        DatePeriod datePeriodOverlapsBefore=new DatePeriod(datePeriod.getStartDate().minusDays(5),datePeriod.getEndDate().minusDays(5));
        datePeriodRepository.save(datePeriodOverlapsBefore);
        Reservation reservation1=new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriodOverlapsBefore,200);
        DatePeriod datePeriodOverlapsAfter=new DatePeriod(datePeriod.getStartDate().plusDays(5),datePeriod.getEndDate().plusDays(5));
        datePeriodRepository.save(datePeriodOverlapsAfter);
        Reservation reservation2=new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriodOverlapsAfter,200);
        DatePeriod datePeriodOverlapsInside=new DatePeriod(datePeriod.getStartDate().plusDays(10),datePeriod.getEndDate().minusDays(10));
        datePeriodRepository.save(datePeriodOverlapsInside);
        Reservation reservation3=new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriodOverlapsInside,200);
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        reservationRepository.saveAll(reservations);
    }

    @Test
    @DisplayName("Should not edit accommodation when making POST request to /api/accommodation-request/{accommodationId}")
    public void shouldNotEditAccommodation(){
        //Retrieving entities from database
        Host host=hostRepository.findAll().get(0);
        Accommodation accommodation=accommodationRepository.findAll().get(0);

        //Retrieving jwt token for authorization
        HttpHeaders headersLogin = new HttpHeaders();
        LoginRequest loginRequest=new LoginRequest(host.getUsername(), "password");
        HttpEntity<LoginRequest> requestEntityLogin = new HttpEntity<>(loginRequest);
        ResponseEntity<LoginResponse> responseEntityLogin = restTemplate.exchange(
                "/api/auth/login",
                HttpMethod.POST,
                requestEntityLogin,
                LoginResponse.class
        );
        String token= Objects.requireNonNull(responseEntityLogin.getBody()).getJwt();

        //Creating AccommodationRequest DTO that is sent to endpoint
        UUID accommodationId=accommodation.getId();
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        AccommodationDatePeriod accommodationDatePeriod=new AccommodationDatePeriod(datePeriod.getStartDate(),datePeriod.getEndDate(),null);
        List<AccommodationDatePeriod> accommodationDatePeriods=new ArrayList<AccommodationDatePeriod>();
        accommodationDatePeriods.add(accommodationDatePeriod);
        Location location=new Location("USA","New York","7th Avenue",122);
        List<String> amenities=new ArrayList<>();
        List<String> photos=new ArrayList<>();
        AccommodationRequest accommodationRequest=new AccommodationRequest(host.getId(),
                "Hotel","Very good hotel",location,amenities,photos,1,4,AccommodationType.Apartment,50,accommodationDatePeriods,
                new AccommodationPricelist(),7,AccommodationReservationPolicy.Auto, PriceCalculationMethod.PER_GUEST);

        //Adding authorization header and request body
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        HttpEntity<AccommodationRequest> requestEntity = new HttpEntity<>(accommodationRequest, headers);

        //Sending request
        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                "/api/accommodation-request/{accommodationId}",
                HttpMethod.POST,
                requestEntity,
                MessageResponse.class,
                accommodationId
        );

        //Asserting received values
        MessageResponse messageResponse=responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(messageResponse.getSuccessful());
    }
}
