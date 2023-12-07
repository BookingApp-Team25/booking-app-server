package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
@Entity
public class DatePeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    UUID id;
    @Column(name = "startDate",columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(name="endDate",columnDefinition = "DATE")
    private LocalDate endDate;


    public DatePeriod() {
    }

    public DatePeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
