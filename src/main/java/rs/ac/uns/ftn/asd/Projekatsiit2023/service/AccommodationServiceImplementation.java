package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImplementation implements AccommodationService{
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String createAccommodation(Accommodation accommodation){
        accommodationRepository.save(accommodation);
        return "Accommodation saved";
    }
    @Override
    public List<AccommodationSummaryResponse> getAllAccommodations() throws IOException {
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAll());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
            summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                    accommodation.getPhotos().get(0).getEncodedImage(),accommodation.getDescription(),accommodation.getPrice(),
                    5,accommodation.getOnHoldStatus()));
        }
       return summary;
    }

    @Override
    public AccommodationResponse getAccommodation(UUID accommodationId) throws IOException {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        return new AccommodationResponse(accommodation.getId(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.getAmenities(),
                accommodation.getPhotosEncoded(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests(),
                accommodation.getType(),
                accommodation.getAvailabilityDatePeriods(),
                accommodation.getPricelist(),
                accommodation.getPrice(),
                accommodation.getDaysBefore(),
                accommodation.getPolicy(),
                accommodation.getOnHoldStatus(),
                accommodation.getHost().getId(),
                accommodation.getHost().getUsername(),
                accommodation.getRating());
    }

    @Override
    public AccommodationSummaryCollectionResponse getAllApprovedAccommodations(int page, int numberOfElements) throws IOException {
        long totalNumberOfAccommodations = accommodationRepository.count();
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAll(pageRequest).getContent());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
            if(accommodation.getOnHoldStatus() == AccommodationOnHoldStatus.APPROVED){
                summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                        accommodation.getPhotos().get(0).getEncodedImage(),accommodation.getDescription(),accommodation.getPricelist().getDailyPrice(),
                        5,accommodation.getOnHoldStatus()));
            }
        }
        return new AccommodationSummaryCollectionResponse(summary,totalNumberOfAccommodations);
    }


    @Override
    public AccommodationSummaryCollectionResponse getHostAccommodations(UUID hostId,int page, int numberOfElements) throws IOException {
        long totalNumberOfAccommodations = accommodationRepository.countAllHostAccommodations(hostId);
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAllHostAccommodations(hostId,pageRequest).getContent());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
                summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                        accommodation.getPhotos().get(0).getEncodedImage(),accommodation.getDescription(),accommodation.getPrice(),
                        5,accommodation.getOnHoldStatus()));
        }
        return new AccommodationSummaryCollectionResponse(summary,totalNumberOfAccommodations);
    }
    public AccommodationResponse getAccommodation(int accommodationId){
        return new AccommodationResponse();
    }

    @Override
    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, LocalDate dateStart, LocalDate dateEnd, int guestNumber) {
//        LocalDate startDate = datePeriod.getStartDate();
//        LocalDate endDate = datePeriod.getEndDate();

        List<Accommodation> accommodations =
                accommodationRepository.searchAccommodations(city, guestNumber, dateStart, dateEnd);

        return mapToSummaryResponse(accommodations);
    }

    @Override
    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest) {
        return null;
    }
    @Override
    public Boolean addFavouriteAccommodation(UUID guestId, UUID accommodationId) {
        try {
            Guest guest = userRepository.findGuestByUUID(guestId);
            Accommodation accommodation = accommodationRepository.findAccommodationById(accommodationId);

            guest.addFavoriteAccommodation(accommodation);
            userRepository.save(guest);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean removeFavouriteAccommodation(UUID guestId, UUID accommodationId) {
        try {
            Guest guest = userRepository.findGuestByUUID(guestId);
            Accommodation accommodation = accommodationRepository.findAccommodationById(accommodationId);

            guest.removeFavoriteAccommodation(accommodation);
            userRepository.save(guest);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isFavouriteAccommodation(UUID guestId, UUID accommodationId) {
        return accommodationRepository.isAccommodationInFavorites(guestId, accommodationId);
    }

    public Collection<AccommodationSummaryResponse> getFavouriteAccommodations(UUID guestId) {
        Collection<Accommodation> accommodations = accommodationRepository.getFavouriteAccommodations(guestId);
        return mapToSummaryResponse(accommodations.stream().toList());
    }

    private List<AccommodationSummaryResponse> mapToSummaryResponse(List<Accommodation> accommodations) { //dodato za searchAccommodations zajedno sa calculateAverageRating
        return accommodations.stream()
                .map(accommodation -> {
                    // Calculate average rating
                    double averageRating = calculateAverageRating(accommodation);
                    try {
                        return new AccommodationSummaryResponse(
                                accommodation.getId(),
                                accommodation.getName(),
                                accommodation.getPhotos().get(0).getEncodedImage(),
                                accommodation.getDescription(),
                                //accommodation.getPrice(),
                                accommodation.getPricelist().getDailyPrice(),
                                averageRating,
                                accommodation.getOnHoldStatus()
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private double calculateAverageRating(Accommodation accommodation) {
//        List<AccommodationReview> reviews = accommodation.getReviews();
//        List<Double> ratings = new ArrayList<Double>(); //accommodation.getRatings();
//
//        for (AccommodationReview review: reviews) {
//            ratings.add(review.getRating());
//        }
//
//        if (ratings.isEmpty()) {
//            return 0.0; // any default value
//        }
//
//        //int sum = ratings.stream().mapToInt(AccommodationRating::getRating).sum();
//        double sum = 0.0;
//        for (Double rating: ratings) {
//            sum += rating;
//        }
//        return (double) sum / ratings.size();
        return 0.0; //nesto ovde ne radi - popraviti
    }

    @Override
    public Collection<AccommodationSummaryResponse> filterAccommodations(String city, LocalDate startDate, LocalDate endDate, int guestNumber, List<String> amenities, AccommodationType accommodationType, double minPrice, double maxPrice) {
        List<Accommodation> filteredAccommodations = accommodationRepository.filterAccommodations(city, guestNumber, startDate, endDate,/* amenities,*/ accommodationType, minPrice, maxPrice);

        return mapToSummaryResponse(filteredAccommodations);
    }

    @Override
    public ReservationSummaryCollectionResponse getGuestReservations(UUID guestId,int page, int numberOfElements) throws IOException {
        long totalNumberOfAccommodations = accommodationRepository.countAllGuestReservations(guestId);
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Reservation> fullList = new ArrayList<Reservation>(accommodationRepository.findAllGuestReservations(guestId,pageRequest).getContent());
        ArrayList<ReservationRequest> summary = new ArrayList<>();
        for (Reservation reservation : fullList){
            summary.add(new ReservationRequest(reservation.getId() , reservation.getGuest().getId(), reservation.getHost().getId(),
                    reservation.getAccommodation().getId(), reservation.getReservationStatus(), reservation.getReservedDate(), reservation.getPrice()));
        }
        return new ReservationSummaryCollectionResponse(summary,totalNumberOfAccommodations);
    }
}
