package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.config.UniqueDates;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationLog;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationMonthlyLog;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationDatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class DateManagementService {
    ReservationRepository reservationRepository;
    AccommodationRepository accommodationRepository;
    public DateManagementService(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository){
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }

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
    public boolean isPeriodInside(DatePeriod insidePeriod, DatePeriod outsidePeriod){
        if(insidePeriod.getStartDate().isBefore(outsidePeriod.getStartDate()) || insidePeriod.getEndDate().isAfter(outsidePeriod.getEndDate())){
            return false;
        }
        return true;
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
            else if(reservationDatePeriod.getStartDate().isEqual(accommodationDatePeriod.getStartDate()) && reservationDatePeriod.getEndDate().isEqual(accommodationDatePeriod.getEndDate())){ // ceo period je uzet
                newAvailableDates.remove(accommodationDatePeriod);
            }
        }
        System.out.println(newAvailableDates);
        return newAvailableDates;
    }
    Map<UUID, AccommodationLog> getHostLogsForPeriod(String hostUsername, DatePeriod reportPeriod) throws IOException {
        List<Reservation> hostReservations = reservationRepository.findAllHostReservations(hostUsername);
        Map<UUID, AccommodationLog> accommodationLogMap = new HashMap<>();

        for(Accommodation accommodation : accommodationRepository.findAll()){
            accommodationLogMap.put(accommodation.getId(),null);
        }

        for(Reservation reservation : hostReservations){
            if(isPeriodInside(reservation.getReservedDate(),reportPeriod) && reservation.getReservationStatus() != ReservationStatus.REJECTED){ // OBAVEZNO PROMENITI NA rezervation status == finished!!!!!!!!!!
                if(accommodationLogMap.get(reservation.getAccommodation().getId()) == null){ //ako za ovaj smestaj jos nije nadjena akomodacija prvo inicijalizujemo izvestaj
                    AccommodationLog log = new AccommodationLog(reservation.getAccommodation().getId(),reservation.getAccommodation().getName(),reservation.getAccommodation().getPhotosEncoded().get(0),1,reservation.getPrice());
                    accommodationLogMap.put(reservation.getAccommodation().getId(),log);
                }
                else{ // log vec postoji sad se samo povecava broj rezervacija i para
                    accommodationLogMap.get(reservation.getAccommodation().getId()).increaseTotalProfit(reservation.getPrice());
                    accommodationLogMap.get(reservation.getAccommodation().getId()).incrementNumberOfReservations();
                }
            }
        }
        return accommodationLogMap;
    }
    ArrayList<DatePeriod> generateMonths(){
        LocalDate currentDate = LocalDate.now();
        ArrayList<DatePeriod> months = new ArrayList<>();
        for(int i=0;i<11;i++){
            LocalDate firstDayOfMonth = currentDate.minusMonths(i).withDayOfMonth(1);

            LocalDate lastDayOfMonth = currentDate.minusMonths(i).withDayOfMonth(
                    currentDate.minusMonths(i).lengthOfMonth());
            months.add(new DatePeriod(firstDayOfMonth,lastDayOfMonth));
        }
        return months;
    }
    public boolean doPeriodsOverlap(DatePeriod datePeriod1, DatePeriod datePeriod2){
        LocalDate startDate1 = datePeriod1.getStartDate();
        LocalDate endDate1 = datePeriod1.getEndDate();
        LocalDate startDate2 = datePeriod2.getStartDate();
        LocalDate endDate2 = datePeriod2.getEndDate();
        return !(startDate1.isAfter(endDate2) || startDate2.isAfter(endDate1));
    }
    public int numberOfOverlappingDays(DatePeriod datePeriod1, DatePeriod datePeriod2){
        if(!doPeriodsOverlap(datePeriod1,datePeriod2)){
            return 0;
        }
        else{
            LocalDate maxStartDate = datePeriod1.getStartDate().isAfter(datePeriod2.getStartDate()) ? datePeriod1.getStartDate() : datePeriod2.getStartDate();
            LocalDate minEndDate = datePeriod1.getEndDate().isBefore(datePeriod2.getEndDate()) ? datePeriod1.getEndDate() : datePeriod2.getEndDate();
            return (int) maxStartDate.until(minEndDate).getDays() + 1;
        }
    }
    List<AccommodationMonthlyLog> getAnnualStatistics(UUID accommodationId){
        List<AccommodationMonthlyLog> monthlyLogs = new ArrayList<>();
        ArrayList<DatePeriod> months = generateMonths();
        List<Reservation> accommodationReservations = reservationRepository.findAllByAccommodationId(accommodationId);
        for(DatePeriod month : months){
            String monthName = month.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int monthReservations = 0;
            double monthProfit = 0;
            for(Reservation reservation : accommodationReservations){
                double reservationProfit = numberOfOverlappingDays(month, reservation.getReservedDate())*((double) reservation.getPrice() /reservation.getReservedDate().getDuration());
                if(reservationProfit > 0){
                    monthReservations +=1;
                }
                monthProfit += reservationProfit;
            }
            AccommodationMonthlyLog monthlyLog = new AccommodationMonthlyLog(monthName,monthProfit,monthReservations);
            monthlyLogs.add(monthlyLog);
        }
        return monthlyLogs;
    }

}
