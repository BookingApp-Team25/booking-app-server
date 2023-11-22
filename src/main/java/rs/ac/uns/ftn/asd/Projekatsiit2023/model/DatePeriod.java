package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DatePeriod {
    private LocalDate startDate;
    private LocalDate endDate;

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
