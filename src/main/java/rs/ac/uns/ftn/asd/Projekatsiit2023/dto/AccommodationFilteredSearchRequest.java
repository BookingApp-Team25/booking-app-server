package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Location;
import java.util.List;

public class AccommodationFilteredSearchRequest {
    private DatePeriod datePeriod;
    private Location location;
    private int numberOfGuests;

    private List<AccommodationType> types;
    private List<String> amenities;
    private double minPrice;
    private double maxPrice;

    public AccommodationFilteredSearchRequest() {
    }

    public AccommodationFilteredSearchRequest(DatePeriod datePeriod, Location location, int numberOfGuests, List<AccommodationType> types, List<String> amenities, double minPrice, double maxPrice) {
        this.datePeriod = datePeriod;
        this.location = location;
        this.numberOfGuests = numberOfGuests;
        this.types = types;
        this.amenities = amenities;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
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

    public List<AccommodationType> getTypes() {
        return types;
    }

    public void setTypes(List<AccommodationType> types) {
        this.types = types;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
