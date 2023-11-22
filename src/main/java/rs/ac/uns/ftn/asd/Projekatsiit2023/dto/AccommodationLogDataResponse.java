package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

public class AccommodationLogDataResponse {
    private DatePeriod period;

    public AccommodationLogDataResponse() {
    }

    public AccommodationLogDataResponse(DatePeriod period) {
        this.period = period;
    }

    public void setPeriod(DatePeriod period) {
        this.period = period;
    }

    public DatePeriod getPeriod() {
        return period;
    }
}
