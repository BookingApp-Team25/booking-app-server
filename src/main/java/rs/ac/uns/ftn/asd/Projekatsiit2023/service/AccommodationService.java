package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AccommodationService {
    public String createAccommodation(Accommodation accommodation);
    public Collection<AccommodationSummaryResponse> getAllAccommodations() throws IOException;
    public AccommodationResponse getAccommodation(UUID accommodationId) throws IOException;
    public AccommodationSummaryCollectionResponse getAllApprovedAccommodations(int page, int numberOfElements) throws IOException;
    public AccommodationSummaryCollectionResponse getHostAccommodations(UUID hostId, int page, int numberOfElements) throws IOException;

    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, LocalDate dateStart, LocalDate dateEnd, int guestNumber);

    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest);
    public Boolean addFavouriteAccommodation(String guestUsername, UUID accommodationId);
    public Boolean removeFavouriteAccommodation(String guestUsername, UUID accommodationId);
    public Boolean isFavouriteAccommodation(UUID guestId, UUID accommodationId);

    public Collection<AccommodationSummaryResponse> getFavouriteAccommodations(String guestUsername);

    Collection<AccommodationSummaryResponse> filterAccommodations(String city, LocalDate startDate, LocalDate endDate, int guestNumber, List<String> amenities, AccommodationType accommodationType, double minPrice, double maxPrice);
    public ReservationSummaryCollectionResponse getGuestReservations(String guestUsername,int page, int numberOfElements) throws IOException;
}
