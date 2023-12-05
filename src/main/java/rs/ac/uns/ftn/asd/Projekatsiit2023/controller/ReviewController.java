package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewService;

import java.util.Collection;

@RestController
@RequestMapping("api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping //trebalo bi da pokriva i host review i accommodation review
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
        ReviewResponse reviewResponse = reviewService.createReview(reviewRequest);
        return new ResponseEntity<ReviewResponse>(reviewResponse, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("reviewId") int id){
        Boolean reviewResponse = reviewService.deleteReview(id);
        if(reviewResponse == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponse);
    }
    @GetMapping(value = "/reported-reviews")
    public ResponseEntity<Collection<ReviewResponse>> getAllReportedReviews(){
        Collection<ReviewResponse> reviewResponses = reviewService.getAllReportedReviews();
        if(reviewResponses == null){
            return new ResponseEntity<Collection<ReviewResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponses);
    }
    @PostMapping(value = "/report/{reviewId}")
    public ResponseEntity<MessageResponse> reportReview(@PathVariable("reviewId") int reviewId){
        MessageResponse reviewResponse = reviewService.reportReview(reviewId);
        if(reviewResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponse);
    }
}
