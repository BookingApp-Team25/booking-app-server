package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationUpdateResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationUpdate;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AdminService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AccommodationUpdateService accommodationUpdateService;
    @GetMapping(value = "/getAllAccommodationUpdates")
    public ResponseEntity<Collection<AccommodationUpdateResponse>> getAllAccommodationUpdates(){
        Collection<AccommodationUpdateResponse> accommodationUpdateResponses=accommodationUpdateService.getAllAccommodationUpdates();
        if(accommodationUpdateResponses == null){
            return ResponseEntity.ok(accommodationUpdateResponses);
        }
        return ResponseEntity.ok(accommodationUpdateResponses);
    }

    @PutMapping(value = "/resolveAccommodationUpdate/{accommodationUpdateId}")
    public ResponseEntity<AccommodationUpdateResponse> resolveAccommodationUpdate(@PathVariable("accommodationUpdateId") int id, @RequestParam int flag){
        AccommodationUpdateResponse accommodationUpdateResponse = adminService.resolveAccommodationUpdate(id,flag);
        if(accommodationUpdateResponse == null){
            return new ResponseEntity<AccommodationUpdateResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }

    @GetMapping(value = "/getAllReportedReviews")
    public ResponseEntity<Collection<ReviewResponse>> getAllReportedReviews(){
        Collection<ReviewResponse> reviewResponses = reviewService.getAllReportedReviews();
        if(reviewResponses == null){
            return new ResponseEntity<Collection<ReviewResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponses);
    }

    @PutMapping(value = "/deleteReview/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") int id){
        ReviewResponse reviewResponse = reviewService.deleteReview(id);
        if(reviewResponse == null){
            return new ResponseEntity<ReviewResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping(value = "/getAllReportedUsers")
    public ResponseEntity<Collection<ReportedUserResponse>> getAllReportedUsers(){
        Collection<ReportedUserResponse> reportedUsers = adminService.getAllReportedUsers();
        if(reportedUsers == null){
            return new ResponseEntity<Collection<ReportedUserResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reportedUsers);
    }

    @PutMapping(value = "/blockUser/{userId}")
    public ResponseEntity<UUID> blockUser(@PathVariable("userId") int id){
        UUID blockedUser=adminService.blockUser(id);
        if(blockedUser == null){
            return new ResponseEntity<UUID>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(blockedUser);
    }
}
