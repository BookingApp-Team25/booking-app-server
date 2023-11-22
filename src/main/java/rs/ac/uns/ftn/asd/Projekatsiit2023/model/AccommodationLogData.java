package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

public class AccommodationLogData {
    private DatePeriod period;

    public AccommodationLogData() {
    }

    public AccommodationLogData(DatePeriod period) {
        this.period = period;
    }

    public void setPeriod(DatePeriod period) {
        this.period = period;
    }

    public DatePeriod getPeriod() {
        return period;
    }
}
