package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Location;

public class AccommodationSearchRequest {
    private DatePeriod datePeriod;
    private Location location;
    private int numberOfGuests;

    public AccommodationSearchRequest(){

    }

    public AccommodationSearchRequest(DatePeriod datePeriod, Location location, int numberOfGuests) {
        this.datePeriod = datePeriod;
        this.location = location;
        this.numberOfGuests = numberOfGuests;
    }

    public DatePeriod getDatePeriod() {
        return datePeriod;
    }

    public void setDatePeriod(DatePeriod datePeriod) {
        this.datePeriod = datePeriod;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
