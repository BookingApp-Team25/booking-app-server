package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AccommodationService {
    public String createAccommodation(Accommodation accommodation);
    public Collection<AccommodationSummaryResponse> getAllAccommodations();
    public AccommodationResponse getAccommodation(UUID accommodationId);
    public AccommodationSummaryCollectionResponse getAllApprovedAccommodations(int page, int numberOfElements);
    public AccommodationSummaryCollectionResponse getHostAccommodations(UUID hostId, int page, int numberOfElements);

    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, LocalDate dateStart, LocalDate dateEnd, int guestNumber);

    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest);
    public boolean addFavoriteAccommodation(int accommodationId);

    Collection<AccommodationSummaryResponse> filterAccommodations(String city, LocalDate startDate, LocalDate endDate, int guestNumber, List<String> amenities, AccommodationType accommodationType, double minPrice, double maxPrice);
}
