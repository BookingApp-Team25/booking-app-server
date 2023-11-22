package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

public class AccommodationLogDataRequest {
    private DatePeriod period;

    public AccommodationLogDataRequest() {
    }

    public AccommodationLogDataRequest(DatePeriod period) {
        this.period = period;
    }

    public void setPeriod(DatePeriod period) {
        this.period = period;
    }

    public DatePeriod getPeriod() {
        return period;
    }
}
