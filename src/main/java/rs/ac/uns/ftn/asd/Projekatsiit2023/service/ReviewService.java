package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;

import java.util.Collection;
import java.util.UUID;

public interface ReviewService {
    ReviewResponse getReviewById(int reviewId);
    Collection<ReviewResponse> getAllAccommodationReviews(UUID accommodationId);
    Collection<ReviewResponse> getAllHostReviews(UUID hostId);
    Collection<ReviewResponse> getAllReportedReviews();
    MessageResponse createReview(ReviewRequest reviewRequest);
    MessageResponse reportReview(String reviewId,Boolean flag);
    Boolean deleteReview(String id,Boolean flag);
    Boolean checkPermission(String username,String accommodationId);
}
