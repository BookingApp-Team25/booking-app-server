package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationDatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.DatePeriodRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class ReservationServiceImplementationTest {

    @Autowired
    private ReservationServiceImplementation reservationService;

    @MockBean
    private DateManagementService dateManagementService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private AccommodationRepository accommodationRepository;

    @MockBean
    private DatePeriodRepository datePeriodRepository;

    public ReservationRequest generateReservationRequest() {
        return new ReservationRequest(
                UUID.randomUUID(), // random id
                UUID.randomUUID(), // random guestId
                UUID.randomUUID(),  // random hostId
                UUID.randomUUID(),  // random accommodationId
                ReservationStatus.ONGOING,
                new DatePeriod(LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 7)),
                (long)20.0  // valid price
        );
    }

    // test case for scenario 1
    @Test
    @DisplayName("Should invoke EntityNotFoundException at the line 47 of ReservationServiceImplementation")
    public void testCreateReservation_EntityNotFoundExceptionAtLine47() {
        when(accommodationRepository.findById(any())).thenReturn(Optional.empty()); // sets up the fail for search by Id

        ReservationRequest reservationRequest = generateReservationRequest();

        // chacking if exception is thrown based on the line 71 rules
        assertThrows(EntityNotFoundException.class, () -> reservationService.createReservation(reservationRequest));
        // testing that none of the other values that come after get saved
        verify(accommodationRepository, never()).save(any());
        verify(datePeriodRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }

    // test case for scenario 2
    @Test
    @DisplayName("Should invoke exception that reservation is not possible")
    public void testCreateReservation_ReservationNotPossible() {
        when(accommodationRepository.findById(any())).thenReturn(Optional.of(mock(Accommodation.class))); // mocking a valid accommodation

        ReservationRequest reservationRequest = generateReservationRequest();

        // mocking the behavior of dateManagementService.isReservationPossible to return false
        when(dateManagementService.isReservationPossible(any(), any())).thenReturn(false);

        MessageResponse response = reservationService.createReservation(reservationRequest);

        assertFalse(response.getSuccessful());
        assertEquals("Reservation at that period is not possible", response.getMessage());
        // testing that none of the other values that come after get saved
        verify(accommodationRepository, never()).save(any());
        verify(datePeriodRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }

    public Accommodation getAccommodationWithValidDates() {
        Accommodation accommodation = new Accommodation(
            UUID.fromString("f2689590-e7f9-4bb0-b280-2976e84d9220")
        );
        List<AccommodationDatePeriod> availableDates = new ArrayList<>();

        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        availableDates.add(new AccommodationDatePeriod(datePeriod.getStartDate(), datePeriod.getEndDate(), accommodation));

        accommodation.setAvailability(availableDates);

        return accommodation;
    }

    @Test
    @DisplayName("Should indicate that reservation is possible")
    public void testCreateReservation_ReservationPossible() {
        Accommodation accommodation = getAccommodationWithValidDates();
        accommodationRepository.save(accommodation);

        ReservationRequest reservationRequest = new ReservationRequest(
                UUID.randomUUID(), // random id
                UUID.randomUUID(), // random guestId
                UUID.randomUUID(),  // random hostId
                accommodation.getId(),
                ReservationStatus.ONGOING,
                new DatePeriod(LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 7)),
                (long)20.0  // valid price
        );

        // mock the behavior of dateManagementService.isReservationPossible to return true
       // when(dateManagementService.isReservationPossible(any(), any())).thenReturn(true);

        when(accommodationRepository.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));

        MessageResponse response = reservationService.createReservation(reservationRequest);

        assertTrue(response.getSuccessful());
        assertEquals("succesfully added new reservation", response.getMessage());
        // Verification that every repository method has been called
        verify(accommodationRepository, times(1)).findById(any());
        verify(accommodationRepository, times(1)).save(any());
        verify(datePeriodRepository, times(1)).save(any());
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Auto Approval Test")
    public void testCreateReservation_AutoApproval() {
        // Set up your test data and mocks
        Accommodation accommodation = getAccommodationWithValidDates();
        accommodation.setPolicy(AccommodationReservationPolicy.Auto);
        ReservationRequest reservationRequest = new ReservationRequest(
                UUID.randomUUID(), // random id
                UUID.randomUUID(), // random guestId
                UUID.randomUUID(),  // random hostId
                accommodation.getId(),
                ReservationStatus.ONGOING,
                new DatePeriod(LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 7)),
                (long)20.0  // valid price
        );
        Reservation reservation = new Reservation(
                accommodation,
                ReservationStatus.ACCEPTED,
                reservationRequest.getReservedDate(),
                reservationRequest.getPrice()
        );

        when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation));
     //   when(dateManagementService.isReservationPossible(any(), any())).thenReturn(true);
        when(reservationRepository.getReferenceById(any())).thenReturn(reservation);

        // Call the method to test
        MessageResponse response = reservationService.createReservation(reservationRequest);

        // Verify the results
        assertTrue(response.getSuccessful());
        assertEquals("succesfully added new reservation", response.getMessage());
        assertEquals(ReservationStatus.ACCEPTED, reservationRepository
                .getReferenceById(reservationRequest.getReservationId()).getReservationStatus());
        assertFalse(accommodation.getAvailability().stream()
                .anyMatch(datePeriod -> datePeriodsOverlap(datePeriod, reservationRequest.getReservedDate())));
    }

    private boolean datePeriodsOverlap(AccommodationDatePeriod period1, DatePeriod period2) {
        return !(period1.getEndDate().isBefore(period2.getStartDate()) || period2.getEndDate().isBefore(period1.getStartDate()));
    }

    @Test
    @DisplayName("Waiting for Approval Test")
    public void testCreateReservation_WaitingForApproval() {
        Accommodation accommodation = getAccommodationWithValidDates();
        accommodation.setPolicy(AccommodationReservationPolicy.Manually);
        ReservationRequest reservationRequest = new ReservationRequest(
                UUID.randomUUID(), // random id
                UUID.randomUUID(), // random guestId
                UUID.randomUUID(),  // random hostId
                accommodation.getId(),
                ReservationStatus.ONGOING,
                new DatePeriod(LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 7)),
                (long)20.0  // valid price
        );
        Reservation reservation = new Reservation(
                accommodation,
                ReservationStatus.WAITING_FOR_APPROVAL,
                reservationRequest.getReservedDate(),
                reservationRequest.getPrice()
        );

        when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation));
       // when(dateManagementService.isReservationPossible(any(), any())).thenReturn(true);
        when(reservationRepository.getReferenceById(any())).thenReturn(reservation);

        // Call the method to test
        MessageResponse response = reservationService.createReservation(reservationRequest);

        // Verify the results
        assertTrue(response.getSuccessful());
        assertEquals("succesfully added new reservation", response.getMessage());
        assertEquals(ReservationStatus.WAITING_FOR_APPROVAL, reservationRepository.
                getReferenceById(reservationRequest.getReservationId()).getReservationStatus());
        verify(accommodationRepository, never()).save(any());
    }
}
