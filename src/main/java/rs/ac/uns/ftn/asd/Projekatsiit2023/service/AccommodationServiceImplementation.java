package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

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
    @Override
    public String createAccommodation(Accommodation accommodation){
        accommodationRepository.save(accommodation);
        return "Accommodation saved";
    }
    @Override
    public List<AccommodationSummaryResponse> getAllAccommodations(){
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAll());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
            summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                    accommodation.getPhotos().get(0),accommodation.getDescription(),accommodation.getPrice(),
                    5,accommodation.getOnHoldStatus()));
        }
       return summary;
    }

    @Override
    public AccommodationResponse getAccommodation(UUID accommodationId) {
        Accommodation accommodation = accommodationRepository.getReferenceById(accommodationId);
        return new AccommodationResponse(accommodation.getId(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.getAmenities(),
                accommodation.getPhotos(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests(),
                accommodation.getType(),
                accommodation.getAvailabilityDatePeriods(),
                accommodation.getPrice(),
                accommodation.getPricelist(),
                accommodation.getDaysBefore(),
                accommodation.getPolicy(),
                accommodation.getOnHoldStatus(),accommodation.getHost().getId(),accommodation.getHost().getUsername());
    }

    @Override
    public AccommodationSummaryCollectionResponse getAllApprovedAccommodations(int page, int numberOfElements) {
        long totalNumberOfAccommodations = accommodationRepository.count();
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAll(pageRequest).getContent());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
            if(accommodation.getOnHoldStatus() == AccommodationOnHoldStatus.APPROVED){
                summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                        accommodation.getPhotos().get(0),accommodation.getDescription(),accommodation.getPricelist().getDailyPrice(),
                        5,accommodation.getOnHoldStatus()));
            }
        }
        return new AccommodationSummaryCollectionResponse(summary,totalNumberOfAccommodations);
    }


    @Override
    public AccommodationSummaryCollectionResponse getHostAccommodations(UUID hostId,int page, int numberOfElements){
        long totalNumberOfAccommodations = accommodationRepository.countAllHostAccommodations(hostId);
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAllHostAccommodations(hostId,pageRequest).getContent());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
                summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                        accommodation.getPhotos().get(0),accommodation.getDescription(),accommodation.getPrice(),
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
    public boolean addFavoriteAccommodation(int accommodationId) { return false; } //mozda treba menjati return tip

    private List<AccommodationSummaryResponse> mapToSummaryResponse(List<Accommodation> accommodations) { //dodato za searchAccommodations zajedno sa calculateAverageRating
        return accommodations.stream()
                .map(accommodation -> {
                    // Calculate average rating
                    double averageRating = calculateAverageRating(accommodation);
                    return new AccommodationSummaryResponse(
                            accommodation.getId(),
                            accommodation.getName(),
                            accommodation.getPhotos().get(0),
                            accommodation.getDescription(),
                            accommodation.getPrice(),
                            averageRating,
                            accommodation.getOnHoldStatus()
                    );
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
    public Collection<AccommodationSummaryResponse> filterAccommodations(String city, LocalDate startDate, LocalDate endDate, int guestNumber, List<String> amenities, String accommodationType, double minPrice, double maxPrice) {
        List<Accommodation> filteredAccommodations = accommodationRepository.filterAccommodations(city, guestNumber, startDate, endDate,/* amenities,*/ accommodationType, minPrice, maxPrice);

        return mapToSummaryResponse(filteredAccommodations);
    }

}
