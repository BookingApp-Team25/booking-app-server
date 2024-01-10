package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewService;

import java.util.Collection;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasAuthority('ROLE_Guest')")
    @PostMapping
    public ResponseEntity<MessageResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
        MessageResponse messageResponse = reviewService.createReview(reviewRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Guest','ROLE_Admin')")
    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("reviewId") String id,@RequestParam Boolean flag){
        Boolean reviewResponse = reviewService.deleteReview(id,flag);
        if(reviewResponse == false){
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

    @PreAuthorize("hasAuthority('ROLE_Host')")
    @PostMapping(value = "/report/{reviewId}")
    public ResponseEntity<MessageResponse> reportReview(@PathVariable("reviewId") String reviewId,@RequestParam Boolean flag){
        MessageResponse reviewResponse = reviewService.reportReview(reviewId,flag);
        if(reviewResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping(value="/{reviewedEntity}")
    public ResponseEntity<Collection<ReviewResponse>> getAllReviews(@PathVariable("reviewedEntity") String reviewedEntity,@RequestParam Boolean flag){
        Collection<ReviewResponse> reviewResponses;
        if(flag) {
            reviewResponses = reviewService.getAllAccommodationReviews(UUID.fromString(reviewedEntity));
        }
        else {
            reviewResponses = reviewService.getAllHostReviews(UUID.fromString(reviewedEntity));
        }
        return ResponseEntity.ok(reviewResponses);
    }

    @GetMapping(value = "check/{username}/{accommodationId}")
    public ResponseEntity<Boolean> checkPermission(
            @PathVariable("username") String username,
            @PathVariable("accommodationId") String accommodationId) {
        Boolean response=reviewService.checkPermission(username,accommodationId);
        return ResponseEntity.ok(response);
    }
}
