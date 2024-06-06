package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.exceptions.ReservationNotPossibleException;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ReservationServiceTest {
    @MockBean
    AccommodationRepository accommodationRepository;
    @MockBean
    ReservationRepository reservationRepository;
    Accommodation accommodation;

    @Autowired
    private ReservationService reservationService;
    @BeforeEach
    public void initialization(){
        accommodation = new Accommodation();
        AccommodationDatePeriod accommodationDatePeriod = new AccommodationDatePeriod(LocalDate.of(2024,2,3),LocalDate.of(2024,2,20),accommodation);
        AccommodationDatePeriod accommodationDatePeriod1 = new AccommodationDatePeriod(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),accommodation);
        accommodation.setAvailability(new ArrayList<>());
        accommodation.availability.add(accommodationDatePeriod);
        accommodation.availability.add(accommodationDatePeriod1);
    }
    @Test
    public void shouldAcceptReservation(){

        Reservation reservation = new Reservation(new Guest(),new Host(),accommodation, ReservationStatus.ACCEPTED,new DatePeriod(LocalDate.of(2024,2,5),LocalDate.of(2024,2,16)),10);

        reservationService.acceptReservation(reservation,accommodation);

        verify(reservationRepository,times(1)).save(reservation);
    }
    @Test
    public void invalidReservationDateTest(){
        Reservation reservation = new Reservation(new Guest(),new Host(),accommodation, ReservationStatus.ACCEPTED,new DatePeriod(LocalDate.of(2024,2,2),LocalDate.of(2024,2,21)),10);

        assertThrows(ReservationNotPossibleException.class, () -> reservationService.acceptReservation(reservation,accommodation));

    }
    @Test
    public void reservationDateExpiredTest(){
        Reservation reservation = new Reservation(new Guest(),new Host(),accommodation, ReservationStatus.ACCEPTED,new DatePeriod(LocalDate.now().minusDays(2),LocalDate.now().plusDays(2)),10);

        assertThrows(ReservationNotPossibleException.class, () -> reservationService.acceptReservation(reservation,accommodation));
    }






}
