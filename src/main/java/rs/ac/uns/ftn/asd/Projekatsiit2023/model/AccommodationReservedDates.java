package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import ch.qos.logback.core.joran.sanity.Pair;

import java.util.ArrayList;
import java.util.List;

public class AccommodationReservedDates {

    private List<Pair<DatePeriod,Guest>> reservations;

    public AccommodationReservedDates(){
        this.reservations = new ArrayList<>();
    }
    public AccommodationReservedDates(List<Pair<DatePeriod,Guest>> reservedDates) {
        this.reservations = reservedDates;
    }

    public List<Pair<DatePeriod, Guest>> getReservations() {
        return reservations;
    }

    public void setReservations(List<Pair<DatePeriod, Guest>> reservations) {
        this.reservations = reservations;
    }
}

