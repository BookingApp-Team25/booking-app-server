package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationUpdateRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AccommodationUpdateServiceTest {
    @Autowired
    AccommodationUpdateService accommodationUpdateService;
    @MockBean
    AccommodationRepository accommodationRepository;
    @MockBean
    ReservationRepository reservationRepository;
    @MockBean
    AccommodationUpdateRepository accommodationUpdateRepository;

    public static List<Reservation> getReservations(){
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        List<Reservation> reservations = new ArrayList<Reservation>();
        Reservation reservation1=new Reservation();
        DatePeriod datePeriodOverlapsBefore=new DatePeriod(datePeriod.getStartDate().minusDays(5),datePeriod.getEndDate().minusDays(5));
        reservation1.setReservedDate(datePeriodOverlapsBefore);
        Reservation reservation2=new Reservation();
        DatePeriod datePeriodOverlapsAfter=new DatePeriod(datePeriod.getStartDate().plusDays(5),datePeriod.getEndDate().plusDays(5));
        reservation2.setReservedDate(datePeriodOverlapsAfter);
        Reservation reservation3=new Reservation();
        DatePeriod datePeriodOverlapsInside=new DatePeriod(datePeriod.getStartDate().plusDays(5),datePeriod.getEndDate().minusDays(5));
        reservation3.setReservedDate(datePeriodOverlapsInside);
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        return reservations;
    }

    @ParameterizedTest
    @MethodSource(value = "getReservations")
    @DisplayName("Should not successfully edit accommodation because of reserved periods")
    public void shouldNotEditAccommodation(Reservation reservation) throws IOException {
        //Arrange
        UUID hostId=UUID.randomUUID();
        UUID accommodationId=UUID.randomUUID();
        Host host=Mockito.mock(Host.class);
        when(host.getId()).thenReturn(hostId);
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        List<DatePeriod> datePeriods=new ArrayList<DatePeriod>();
        datePeriods.add(datePeriod);

        AccommodationRequest accommodationRequest= Mockito.mock(AccommodationRequest.class);
        Accommodation accommodation=Mockito.mock(Accommodation.class);
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodation.getAvailabilityDatePeriods()).thenReturn(datePeriods);
        when(accommodation.getHost()).thenReturn(host);

        List<Reservation> reservations=new ArrayList<Reservation>();
        reservations.add(reservation);
        when(reservationRepository.findAllByHostAndAccommodationId(hostId,accommodationId)).thenReturn(reservations);

        //Act
        MessageResponse messageResponse=accommodationUpdateService.createEditRequest(accommodationId,accommodationRequest);

        //Assert
        assertFalse(messageResponse.getSuccessful());
        verify(accommodationRepository, never()).save(any(Accommodation.class));
        verifyNoInteractions(accommodationUpdateRepository);
    }

    @Test
    @DisplayName("Should successfully edit accommodation")
    public void shouldEditAccommodation() throws IOException {
        //Arrange
        UUID hostId=UUID.randomUUID();
        UUID accommodationId=UUID.randomUUID();
        Host host=Mockito.mock(Host.class);
        when(host.getId()).thenReturn(hostId);
        DatePeriod datePeriod=new DatePeriod(LocalDate.of(2024, 3, 1),LocalDate.of(2024, 3, 31));
        List<DatePeriod> datePeriods=new ArrayList<DatePeriod>();
        datePeriods.add(datePeriod);

        AccommodationRequest accommodationRequest= Mockito.mock(AccommodationRequest.class);
        Accommodation accommodation=Mockito.mock(Accommodation.class);
        when(accommodationRepository.getReferenceById(accommodationId)).thenReturn(accommodation);
        when(accommodation.getAvailabilityDatePeriods()).thenReturn(datePeriods);
        when(accommodation.getHost()).thenReturn(host);

        LocalDate startDate=datePeriod.getEndDate().plusDays(5);
        DatePeriod reservedPeriod=new DatePeriod(startDate,startDate.plusDays(5));
        Reservation reservation=new Reservation();
        reservation.setReservedDate(reservedPeriod);
        List<Reservation> reservations=new ArrayList<Reservation>();
        reservations.add(reservation);
        when(reservationRepository.findAllByHostAndAccommodationId(hostId,accommodationId)).thenReturn(reservations);

        //Act
        MessageResponse messageResponse=accommodationUpdateService.createEditRequest(accommodationId,accommodationRequest);

        //Assert
        assertTrue(messageResponse.getSuccessful());
        verify(accommodationUpdateRepository).save(any(AccommodationUpdate.class));
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationUpdateRepository);
    }
}
