package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationDatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DateManagementServiceTest {

    @MockBean
    ReservationRepository reservationRepository;
    @MockBean
    AccommodationRepository accommodationRepository;
    @Autowired
    DateManagementService dateManagementService;
    private  List<AccommodationDatePeriod> availableDates;
    @BeforeEach
    public void initialization(){
        AccommodationDatePeriod accommodationDatePeriod = new AccommodationDatePeriod(LocalDate.of(2024,2,3),LocalDate.of(2024,2,20),new Accommodation());
        availableDates = new ArrayList<>();
        availableDates.add(accommodationDatePeriod);
    }
    @Test
    public void shouldRemoveMiddleOfAvailability() {
        DatePeriod reservationPeriod = new DatePeriod(LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 15));

        List<AccommodationDatePeriod> adjustedPeriods = dateManagementService.calculateAvailabilityAfterReservation(reservationPeriod,availableDates);

        assertEquals(adjustedPeriods.size(),2);
        assertEquals(adjustedPeriods.get(1).getStartDate(), LocalDate.of(2024,2,16));
        assertEquals(adjustedPeriods.get(0).getEndDate(), LocalDate.of(2024,2,4));
    }
    @Test
    public void shouldRemoveWholePeriod(){
        DatePeriod reservationPeriod = new DatePeriod(LocalDate.of(2024, 2, 3), LocalDate.of(2024, 2, 20));

        List<AccommodationDatePeriod> adjustedPeriods = dateManagementService.calculateAvailabilityAfterReservation(reservationPeriod,availableDates);

        assertEquals(adjustedPeriods.size(),0);
    }
    @Test
    public void shouldRemoveFirstPart(){
        DatePeriod reservationPeriod = new DatePeriod(LocalDate.of(2024, 2, 3), LocalDate.of(2024, 2, 15));

        List<AccommodationDatePeriod> adjustedPeriods = dateManagementService.calculateAvailabilityAfterReservation(reservationPeriod,availableDates);
        assertEquals(adjustedPeriods.size(),1);
        assertEquals(adjustedPeriods.get(0).getStartDate(),LocalDate.of(2024, 2, 16));

    }
    @Test
    public void shouldRemoveSecondPart(){
        DatePeriod reservationPeriod = new DatePeriod(LocalDate.of(2024, 2, 15), LocalDate.of(2024, 2, 20));

        List<AccommodationDatePeriod> adjustedPeriods = dateManagementService.calculateAvailabilityAfterReservation(reservationPeriod,availableDates);
        assertEquals(adjustedPeriods.size(),1);
        assertEquals(adjustedPeriods.get(0).getEndDate(),LocalDate.of(2024, 2, 14));
    }


}
