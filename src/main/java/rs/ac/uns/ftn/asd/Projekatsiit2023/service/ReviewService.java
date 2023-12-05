package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;

import java.util.Collection;

public interface ReviewService {
    ReviewResponse getReviewById(int reviewId);
    Collection<ReviewResponse> getAllAccommodationReviews(int accommodationId);
    Collection<ReviewResponse> getAllHostReviews(int hostId);
    Collection<ReviewResponse> getAllReportedReviews();
    ReviewResponse createReview(ReviewRequest reviewRequest);
    MessageResponse reportReview(int reviewId);
    Boolean deleteReview(int id);


}
