package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
@Entity
public class AccommodationDatePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    UUID id;
    @Column(name = "startDate",columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(name="endDate",columnDefinition = "DATE")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;

    private boolean appliedWeekend;
    private boolean appliedSummer;
    private boolean appliedHoliday;
    private boolean appliedWinter;

    private boolean isTaken; //da li je period rezervisan

    public AccommodationDatePeriod() {
    }

    public AccommodationDatePeriod(LocalDate startDate, LocalDate endDate, Accommodation accommodation) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
        this.appliedHoliday = this.appliedSummer = this.appliedWeekend = this.appliedWinter = false;
        this.isTaken = false;
    }

    public AccommodationDatePeriod(AccommodationDatePeriod accommodationDatePeriod){
        this.startDate = accommodationDatePeriod.startDate;
        this.endDate = accommodationDatePeriod.endDate;
        this.accommodation = accommodationDatePeriod.accommodation;
        this.isTaken = accommodationDatePeriod.isTaken;
        this.appliedHoliday = accommodationDatePeriod.appliedHoliday;
        this.appliedSummer = accommodationDatePeriod.appliedSummer;
        this.appliedWinter = accommodationDatePeriod.appliedWinter;
        this.appliedWeekend = accommodationDatePeriod.appliedWeekend;
    }

    public AccommodationDatePeriod(LocalDate startDate, LocalDate endDate, Accommodation accommodation, boolean appliedWeekend, boolean appliedSummer, boolean appliedHoliday, boolean appliedWinter) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
        this.appliedWeekend = appliedWeekend;
        this.appliedSummer = appliedSummer;
        this.appliedHoliday = appliedHoliday;
        this.appliedWinter = appliedWinter;
        this.isTaken = false;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public long calculateDurationInDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public boolean isAppliedWeekend() {
        return appliedWeekend;
    }

    public boolean isAppliedSummer() {
        return appliedSummer;
    }

    public boolean isAppliedHoliday() {
        return appliedHoliday;
    }

    public boolean isAppliedWinter() {
        return appliedWinter;
    }

    public void setAppliedWeekend(boolean appliedWeekend) {
        this.appliedWeekend = appliedWeekend;
    }

    public void setAppliedSummer(boolean appliedSummer) {
        this.appliedSummer = appliedSummer;
    }

    public void setAppliedHoliday(boolean appliedHoliday) {
        this.appliedHoliday = appliedHoliday;
    }

    public void setAppliedWinter(boolean appliedWinter) {
        this.appliedWinter = appliedWinter;
    }


}