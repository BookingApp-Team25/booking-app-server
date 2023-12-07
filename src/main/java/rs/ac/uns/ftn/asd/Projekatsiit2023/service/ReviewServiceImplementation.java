package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImplementation implements ReviewService{
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private HostReviewRepository hostReviewRepository;
    @Override
    public ReviewResponse getReviewById(int reviewId) {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllReportedReviews() {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllAccommodationReviews(UUID accommodationId) {
        Collection<Review> reviews= reviewRepository.findByAccommodationId(accommodationId);
        Collection<ReviewResponse> reviewResponses=new ArrayList<ReviewResponse>();
        for (Review review: reviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getComment(),review.getRating(),review.getGuest().getUsername(),review.getAccommodation().getName(),ReviewType.AccommodationReview);
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }

    @Override
    public Collection<ReviewResponse> getAllHostReviews(UUID hostId) {
        Collection<HostReview> hostReviews= hostReviewRepository.findByHostId(hostId);
        Collection<ReviewResponse> reviewResponses=new ArrayList<ReviewResponse>();
        for (HostReview review: hostReviews) {
            ReviewResponse reviewResponse=new ReviewResponse(review.getComment(),review.getRating(),review.getGuest().getUsername(),review.getHost().getUsername(),ReviewType.HostReview);
            reviewResponses.add(reviewResponse);
        }
        return reviewResponses;
    }

    @Override
    public MessageResponse createReview(ReviewRequest reviewRequest) {
        Guest guest= guestRepository.getReferenceById(reviewRequest.getGuestId());
        if(reviewRequest.getType().equals(ReviewType.AccommodationReview)){
            Review review=new Review(reviewRequest.getComment(),reviewRequest.getRating());
            Accommodation accommodation=accommodationRepository.getReferenceById(reviewRequest.getReviewedEntity());
            review.setGuest(guest);
            review.setAccommodation(accommodation);
            reviewRepository.save(review);
        } else {
            HostReview hostReview=new HostReview(reviewRequest.getComment(),reviewRequest.getRating());
            Host host=hostRepository.getReferenceById(reviewRequest.getReviewedEntity());
            hostReview.setHost(host);
            hostReview.setGuest(guest);
            hostReviewRepository.save(hostReview);
        }
        return new MessageResponse(true,"Review created");
    }

    @Override
    public MessageResponse reportReview(int reviewId) {
        return null;
    }

    @Override
    public Boolean deleteReview(int id) {
        return null;
    }
}
