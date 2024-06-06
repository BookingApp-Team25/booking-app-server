package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.LoginResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.DateManagementService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTest {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    AccommodationRepository accommodationRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    DatePeriodRepository datePeriodRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    DateManagementService dateManagementService;
    @BeforeAll
    private void setup(){
        Host host = new Host("hostTest@gmail.com","$2a$12$Jx/ZmCRb40tj/.pfuPZhJ.H7wkQRQpCG73iOE5qPEdZaZ4rFOy9qm","Ime","Prezime","Adresa","0611111111", Role.Host);
        Guest guest = new Guest("guestTest@gmail.com","$2a$12$33fD8O/6fct4Zz3begssle1NM2j0UX0JLW3shHLXTc459xpgiM1DK","Ime","Prezime","Adresa","0611111111", Role.Guest);
        host.setBlocked(false);
        hostRepository.save(host);
        guestRepository.save(guest);
        Accommodation accommodation = new Accommodation("testAccommodation",
                "Anything",
                new Location("Drzava","Grad","Ulica",5),
                new ArrayList<String>(),
                new ArrayList<Image>(),
                0,
                10,
                AccommodationType.House,
                20,new AccommodationPricelist(),
                0, AccommodationReservationPolicy.Auto
        );
        AccommodationDatePeriod accommodationDatePeriod = new AccommodationDatePeriod(LocalDate.now(),LocalDate.now().plusDays(15),accommodation);
        accommodation.availability.add(accommodationDatePeriod);
        accommodationRepository.save(accommodation);
    }
    @Test
    @DisplayName("Should accept reservation with proper request(base case)")
    public void shouldAcceptReservation(){
        Host host = hostRepository.findAll().get(0);
        Guest guest = guestRepository.findAll().get(0);
        Accommodation accommodation = accommodationRepository.findAll().get(0);
        DatePeriod datePeriod=new DatePeriod(LocalDate.now().plusDays(2),LocalDate.now().plusDays(5));
        datePeriodRepository.save(datePeriod);
        Reservation reservation = new Reservation(guest,host,accommodation, ReservationStatus.WAITING_FOR_APPROVAL,datePeriod,10);
        reservationRepository.save(reservation);

        HttpHeaders headersLogin = new HttpHeaders();
        LoginRequest loginRequest=new LoginRequest(host.getUsername(), "password1");
        HttpEntity<LoginRequest> requestEntityLogin = new HttpEntity<>(loginRequest);
        ResponseEntity<LoginResponse> responseEntityLogin = restTemplate.exchange(
                "/api/auth/login",
                HttpMethod.POST,
                requestEntityLogin,
                LoginResponse.class
        );
        String token = responseEntityLogin.getBody().getJwt();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        String url = "http://localhost:" + port +"/api/reservation/{reservationId}/resolve";

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("isAccepted",true).buildAndExpand(reservation.getId());

        //Sending request
        ResponseEntity<MessageResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                MessageResponse.class
        );

        MessageResponse messageResponse=responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(messageResponse.getSuccessful());
        Reservation acceptedReservation = reservationRepository.findAll().get(0);
        assertEquals(ReservationStatus.ACCEPTED,acceptedReservation.getReservationStatus());
    }

}
