package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.config.UniqueDates;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationDatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class DateManagementService {
    private boolean isHoliday(LocalDate date){
        for(LocalDate holiday : UniqueDates.HOLIDAYS){
            if(date.getDayOfMonth() == holiday.getDayOfMonth() && date.getMonth() == holiday.getMonth()){
                return true;
            }
        }
        return false;

    }
    private static boolean isInSeason(LocalDate date, LocalDate seasonStart, LocalDate seasonEnd) {
        int dayOfYear = date.getDayOfYear();
        int startDayOfYear = seasonStart.getDayOfYear();
        int endDayOfYear = seasonEnd.getDayOfYear();

        return dayOfYear >= startDayOfYear && dayOfYear <= endDayOfYear;
    }
    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
    public boolean isReservationPossible(DatePeriod reservationDatePeriod, List<AccommodationDatePeriod> availableDates){

        for( AccommodationDatePeriod accommodationDatePeriod : availableDates){
            if(!(reservationDatePeriod.getStartDate().isBefore(accommodationDatePeriod.getStartDate()) || reservationDatePeriod.getEndDate().isAfter(accommodationDatePeriod.getEndDate()))){
                return true;
            }
        }
        return false;
    }
    public long calculatePriceForPeriod(DatePeriod reservationDatePeriod, Accommodation accommodation){
        long price = 0;
        for( AccommodationDatePeriod accommodationDatePeriod : accommodation.availability){
            if(!(reservationDatePeriod.getStartDate().isBefore(accommodationDatePeriod.getStartDate()) || reservationDatePeriod.getEndDate().isAfter(accommodationDatePeriod.getEndDate()))){
                LocalDate currentDate = reservationDatePeriod.getStartDate();
                LocalDate endDate = reservationDatePeriod.getEndDate();
                while(!currentDate.isEqual(endDate)){
                    price += accommodation.getPricelist().getDailyPrice();

                    if(accommodationDatePeriod.isAppliedWeekend() && isWeekend(currentDate)){
                        price += accommodation.getPricelist().getWeekendPrice();
                    }
                    if(accommodationDatePeriod.isAppliedHoliday() && isHoliday(currentDate)){
                        price += accommodation.getPricelist().getDailyPrice();
                    }
                    if(accommodationDatePeriod.isAppliedSummer() && (isInSeason(currentDate,UniqueDates.SUMMER_BEGINNING,UniqueDates.SUMMER_ENDING) || isInSeason(currentDate,UniqueDates.WINTER_BEGINNING,UniqueDates.WINTER_ENDING))){
                        price += accommodation.getPricelist().getSummerPrice();
                    }
                    currentDate = currentDate.plusDays(1);
                }
            }
        }
        return price;
    }
    public List<AccommodationDatePeriod> calculateAvailabilityAfterReservation(DatePeriod reservationDatePeriod, List<AccommodationDatePeriod> availableDates){
        AccommodationDatePeriod beforeReservedDate;
        AccommodationDatePeriod afterReservedDate;
        List<AccommodationDatePeriod> newAvailableDates = new ArrayList<>(availableDates);
        for(AccommodationDatePeriod accommodationDatePeriod : availableDates){
            if(reservationDatePeriod.getStartDate().isEqual(accommodationDatePeriod.getStartDate()) && reservationDatePeriod.getEndDate().isBefore(accommodationDatePeriod.getEndDate())){
                afterReservedDate = new AccommodationDatePeriod(accommodationDatePeriod);
                afterReservedDate.setStartDate(reservationDatePeriod.getEndDate().plusDays(1));
                afterReservedDate.setEndDate(accommodationDatePeriod.getEndDate());

                int index = availableDates.indexOf(accommodationDatePeriod);
                newAvailableDates.set(index,afterReservedDate);

            }
            else if(reservationDatePeriod.getStartDate().isAfter(accommodationDatePeriod.getStartDate()) && reservationDatePeriod.getEndDate().isBefore(accommodationDatePeriod.getEndDate())){
                beforeReservedDate = new AccommodationDatePeriod(accommodationDatePeriod);
                afterReservedDate = new AccommodationDatePeriod(accommodationDatePeriod);

                beforeReservedDate.setStartDate(accommodationDatePeriod.getStartDate());
                beforeReservedDate.setEndDate(reservationDatePeriod.getStartDate().minusDays(1));
                afterReservedDate.setStartDate(reservationDatePeriod.getEndDate().plusDays(1));
                afterReservedDate.setEndDate(accommodationDatePeriod.getEndDate());

                int index = availableDates.indexOf(accommodationDatePeriod);
                newAvailableDates.remove(accommodationDatePeriod);
                newAvailableDates.add(index,afterReservedDate);
                newAvailableDates.add(index,beforeReservedDate);
            }
            else if(reservationDatePeriod.getStartDate().isAfter(accommodationDatePeriod.getStartDate()) && reservationDatePeriod.getEndDate().isEqual(accommodationDatePeriod.getEndDate())){
                beforeReservedDate = new AccommodationDatePeriod(accommodationDatePeriod);
                beforeReservedDate.setStartDate(accommodationDatePeriod.getStartDate());
                beforeReservedDate.setEndDate(reservationDatePeriod.getStartDate().minusDays(1));

                int index = availableDates.indexOf(accommodationDatePeriod);
                newAvailableDates.set(index,beforeReservedDate);
            }
            else{ // ceo period je uzet
                newAvailableDates.remove(accommodationDatePeriod);
            }
        }
        System.out.println(newAvailableDates);
        return newAvailableDates;

    }

}
