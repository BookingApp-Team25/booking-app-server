package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccommodationReservationControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private DatePeriodRepository datePeriodRepository;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private GuestRepository guestRepository;

    @BeforeAll
    private void insertData(){
        Host host = new Host("petar@gmail.com","$2a$12$XCFKI7HuFKl050mdW9lqN.D1Gy7hagekFVlKn8ZqsNIn2oDlJL09K","Petar","Petrovic","Belgrade","0654421252", Role.Host);
        Guest guest = new Guest("marko@gmail.com","$2a$12$XCFKI7HuFKl050mdW9lqN.D1Gy7hagekFVlKn8ZqsNIn2oDlJL09K","Marko","Markovic","Belgrade","0654421252", Role.Guest);
        host.setBlocked(false);
        guest.setBlocked(false);
        userRepository.save(host);
        userRepository.save(guest);

        Accommodation accommodation=createAccommodation();
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        AccommodationDatePeriod accommodationDatePeriod=new AccommodationDatePeriod(datePeriod.getStartDate(),datePeriod.getEndDate(),accommodation);
        List<AccommodationDatePeriod> accommodationDatePeriods=new ArrayList<AccommodationDatePeriod>();
        accommodationDatePeriods.add(accommodationDatePeriod);
        accommodation.setAvailability(accommodationDatePeriods);
        datePeriodRepository.save(datePeriod);

        accommodation.setHost(host);
        accommodationRepository.save(accommodation);

        DatePeriod datePeriodOverlapsBefore=new DatePeriod(datePeriod.getStartDate().minusDays(5),datePeriod.getEndDate().minusDays(5));
        datePeriodRepository.save(datePeriodOverlapsBefore);
        Reservation reservation=new Reservation(guest,host,accommodation, ReservationStatus.ACCEPTED,datePeriodOverlapsBefore,200);
        reservationRepository.save(reservation);
    }

    public Accommodation createAccommodation(){
        String name = "Sample Accommodation";
        String description = "This is a sample accommodation description.";
        Location location = new Location("Serbia", "Belgrade","Bulevar Dr Zorana Djindjica",1);
        List<String> amenities = Arrays.asList("Wi-Fi", "Air Conditioning", "Parking");
        List<Image> photos = new ArrayList<Image>();
        int minGuests = 2;
        int maxGuests = 4;
        AccommodationType type = AccommodationType.Room;
        double price = 0.0;
        AccommodationPricelist pricelist = new AccommodationPricelist();
        int daysBefore = 7;
        AccommodationReservationPolicy policy = AccommodationReservationPolicy.Auto;
        Accommodation accommodation = new Accommodation(
                name, description, location, amenities, photos, minGuests, maxGuests,
                type, price, pricelist, daysBefore, policy
        );

        return accommodation;
    }

    public ReservationRequest createReservationRequest(Guest guest, Host host, Accommodation accommodation) {
        DatePeriod datePeriod = new DatePeriod(LocalDate.of(2024, 3, 5),LocalDate.of(2024, 3, 7));
        long price = (long)20.0;
        return new ReservationRequest(UUID.randomUUID(), guest.getId(),host.getId(),
                accommodation.getId(),ReservationStatus.ONGOING, datePeriod, price);
    }

    @Test
    @DisplayName("Create Reservation Endpoint Test (/api/reservation/create)")
    public void testCreateReservation() {
        Guest guest = guestRepository.findAll().get(0);
        Host host = hostRepository.findAll().get(0);
        Accommodation accommodation = accommodationRepository.findAll().get(0);

        // retrieving jwt token for authorization
        HttpHeaders headersLogin = new HttpHeaders();
        LoginRequest loginRequest=new LoginRequest(guest.getUsername(), "password");
        HttpEntity<LoginRequest> requestEntityLogin = new HttpEntity<>(loginRequest);
        ResponseEntity<LoginResponse> responseEntityLogin = restTemplate.exchange(
                "/api/auth/login",
                HttpMethod.POST,
                requestEntityLogin,
                LoginResponse.class
        );
        String token= Objects.requireNonNull(responseEntityLogin.getBody()).getJwt();

        ReservationRequest reservationRequest = createReservationRequest(guest, host, accommodation); // preparing object to send

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); // setting headers to json content type
        // authorization
        headers.set("Authorization", "Bearer " + token);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<ReservationRequest> requestEntity = new HttpEntity<>(reservationRequest, headers);

        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange( // POST request to the createReservation endpoint
                "/api/reservation/create",
                HttpMethod.POST,
                requestEntity,
                MessageResponse.class
        );

        assertEquals(200, responseEntity.getStatusCodeValue()); // verifying the response status

        // verifying the response content
        MessageResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(true, response.getSuccessful());
        assertEquals("succesfully added new reservation", response.getMessage());
    }
}
