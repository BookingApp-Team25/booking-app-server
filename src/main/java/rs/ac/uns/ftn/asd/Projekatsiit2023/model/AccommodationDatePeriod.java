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

    public AccommodationDatePeriod() {
    }

    public AccommodationDatePeriod(LocalDate startDate, LocalDate endDate, Accommodation accommodation) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodation = accommodation;
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
}