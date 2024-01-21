package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImplementation implements ReviewService{
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostReviewRepository hostReviewRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Override
    public ReviewResponse getReviewById(int reviewId) {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllReportedReviews() {
        Collection<AccommodationReview> reportedAccommodationReviews = reviewRepository.getAllReportedAccommodationReviews();
        Collection<HostReview> reportedHostReviews = reviewRepository.getAllReportedHostReviews();

        Collection<ReviewResponse> reviewResponses = new ArrayList<ReviewResponse>();

        for (AccommodationReview review: reportedAccommodationReviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getId(),review.getComment(),review.getRating(),review.getGuest().getFirstName()
                    ,review.getAccommodation().getName(),ReviewType.AccommodationReview,review.getDate(),review.getGuest().getUsername(),review.isReported());
            reviewResponses.add(reviewResponse);
        }

        for (HostReview review: reportedHostReviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getId(),review.getComment(),review.getRating(),
                    review.getGuest().getFirstName(),review.getHost().getUsername(),ReviewType.HostReview,review.getDate(),review.getGuest().getUsername(),review.isReported());
            reviewResponses.add(reviewResponse);
        }

        return reviewResponses;
    }

    @Override
    public Collection<ReviewResponse> getAllAccommodationReviews(UUID accommodationId) {
        Accommodation accommodation=accommodationRepository.getReferenceById(accommodationId);
        Collection<ReviewResponse> reviewResponses=new ArrayList<ReviewResponse>();
        Collection<AccommodationReview> reviews=accommodation.getReviews();
        for (AccommodationReview review: reviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getId(),review.getComment(),review.getRating(),review.getGuest().getFirstName()
                    ,review.getAccommodation().getName(),ReviewType.AccommodationReview,review.getDate(),review.getGuest().getUsername(),review.isReported());
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }

    @Override
    public Collection<ReviewResponse> getAllHostReviews(UUID hostId) {
        Collection<HostReview> hostReviews= hostReviewRepository.findByHostId(hostId);
        Collection<ReviewResponse> reviewResponses=new ArrayList<ReviewResponse>();
        for (HostReview review: hostReviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getId(),review.getComment(),review.getRating(),
                    review.getGuest().getFirstName(),review.getHost().getUsername(),ReviewType.HostReview,review.getDate(),review.getGuest().getUsername(),review.isReported());
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }

    @Override
    public MessageResponse createReview(ReviewRequest reviewRequest) {
        Optional<User> ret= userRepository.findByUsername(reviewRequest.getGuestUsername());
        Guest guest= (Guest) ret.get();
        if(reviewRequest.getType().equals(ReviewType.AccommodationReview)){
            AccommodationReview review=new AccommodationReview(reviewRequest.getComment(),reviewRequest.getRating());
            Accommodation accommodation=accommodationRepository.getReferenceById(UUID.fromString(reviewRequest.getReviewedEntity()));
            review.setGuest(guest);
            review.setAccommodation(accommodation);
            accommodation.addReview(review);
            accommodation.updateRating();
            accommodationRepository.save(accommodation);
        } else {
            HostReview hostReview=new HostReview(reviewRequest.getComment(),reviewRequest.getRating());
            Optional<User> hret= userRepository.findByUsername(reviewRequest.getReviewedEntity());
            hostReview.setGuest(guest);
            if(hret.isPresent()){
                Host host= (Host) hret.get();
                hostReview.setHost(host);
                host.addReview(hostReview);
//                host.updateRating();
                userRepository.save(host);
            }
        }
        return new MessageResponse(true,"Review created");
    }

    @Override
    public MessageResponse reportReview(String reviewId,Boolean flag) {
        if(flag){
        AccommodationReview accommodationReview=reviewRepository.getReferenceById(UUID.fromString(reviewId));
        if(accommodationReview!=null) {
            accommodationReview.setReported(true);
            reviewRepository.save(accommodationReview);
            return new MessageResponse(true,"Review reported");
        }}else{
        HostReview hostReview=hostReviewRepository.getReferenceById(UUID.fromString((reviewId)));
        if(hostReview!=null){
            hostReview.setReported(true);
            hostReviewRepository.save(hostReview);
            return new MessageResponse(true,"Review reported");
        }}
        return new MessageResponse(false,"Unexpected error");
    }

    @Override
    public Boolean deleteReview(String id,Boolean flag) {
        if(flag) {
            AccommodationReview accommodationReview = reviewRepository.getReferenceById(UUID.fromString(id));
            if (accommodationReview != null) {
                Accommodation accommodation=accommodationReview.getAccommodation();
                accommodation.removeReview(accommodationReview);
                accommodation.updateRating();
                accommodationRepository.save(accommodation);
                return true;
            }
        }else {
            HostReview hostReview = hostReviewRepository.getReferenceById(UUID.fromString((id)));
            if (hostReview != null) {
                Host host=hostReview.getHost();
                host.removeReview(hostReview);
//                host.updateRating();
                userRepository.save(host);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean checkPermission(String username,String accommodationId) {
        Optional<User> ret=userRepository.findByUsername(username);
        if(ret.isPresent()){
            if(ret.get() instanceof Guest) {
                Guest guest = (Guest) ret.get();
                UUID guestId = guest.getId();
                Collection<Reservation> reservations = reservationRepository.findAllByGuestAndAccommodationId(guestId, UUID.fromString(accommodationId));
                if (reservations.isEmpty())
                    return false;
                for (Reservation r : reservations) {
                    if (r.isFinished() && r.getReservedDate().getEndDate().plusDays(7).isBefore(LocalDate.now())) {
                        return true;
                    }
                }
                return false;
            } else if (ret.get() instanceof Host) {
                Host host=(Host) ret.get();
                Accommodation accommodation=accommodationRepository.getReferenceById(UUID.fromString(accommodationId));
                if(host.getId()==accommodation.getHost().getId()){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
