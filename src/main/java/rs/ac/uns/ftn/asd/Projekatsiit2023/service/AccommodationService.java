package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;

public interface AccommodationService {
    public String createAccommodation(AccommodationRequest accommodationRequest);
    public Collection<AccommodationSummaryResponse> getAllAccommodations();
    public AccommodationResponse getAccommodation(int accommodationId);
    public Collection<AccommodationSummaryResponse> getHostAccommodations(int hostId);

    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, DatePeriod datePeriod, int guestNumber);

    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest);

}
