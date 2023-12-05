package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;

import java.util.Collection;

@Service
public class ReviewServiceImplementation implements ReviewService{
    @Override
    public ReviewResponse getReviewById(int reviewId) {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllReportedReviews() {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllAccommodationReviews(int accommodationId) {
        return null;
    }

    @Override
    public Collection<ReviewResponse> getAllHostReviews(int hostId) {
        return null;
    }

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        return null;
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
