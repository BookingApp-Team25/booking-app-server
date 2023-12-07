package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;
import java.util.UUID;

public interface AccommodationService {
    public String createAccommodation(Accommodation accommodation);
    public Collection<AccommodationSummaryResponse> getAllAccommodations();
    public AccommodationResponse getAccommodation(UUID accommodationId);
    public Collection<AccommodationSummaryResponse> getHostAccommodations(int hostId);

    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, DatePeriod datePeriod, int guestNumber);

    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest);
    public boolean addFavoriteAccommodation(int accommodationId);
}
