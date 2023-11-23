package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationUpdate;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
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
    private AccommodationService accommodationService;
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

    @GetMapping(value = "/viewAllAccommodations")
    public ResponseEntity<Collection<AccommodationResponse>> getAllAccommodations(){
        Collection<AccommodationResponse> accommodations = accommodationService.getAllAccommodations();
        return  ResponseEntity.ok(accommodations);
    }

    @GetMapping(value = "/viewAccommodation/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getAccommodation(@PathVariable("accommodationId") int accommodationId){
        AccommodationResponse accommodation = accommodationService.getAccommodation(accommodationId);
        if(accommodation == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodation);
    }

    @GetMapping(value = "/searchAccommodations")
    public ResponseEntity<Collection<AccommodationResponse>> searchAccommodations(@RequestBody AccommodationSearchRequest accommodationSearchRequest){
        Collection<AccommodationResponse> accommodations = accommodationService.searchAccommodations(accommodationSearchRequest);
        if(accommodations == null){
            return new ResponseEntity<Collection<AccommodationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping(value = "/searchAccommodationsFiltered")
    public ResponseEntity<Collection<AccommodationResponse>> searchAccommodationsFiltered(@RequestBody AccommodationFilteredSearchRequest accommodationFilteredSearchRequest){
        Collection<AccommodationResponse> accommodations = accommodationService.searchAccommodationsFiltered(accommodationFilteredSearchRequest);
        if(accommodations == null){
            return new ResponseEntity<Collection<AccommodationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodations);
    }

    @PutMapping(value = "{adminId}/editAccount")
    public ResponseEntity<Boolean> editAccount(@PathVariable("adminId") int adminId, @RequestBody AccountEditRequest accountEditRequest){
        Boolean status = adminService.editAccount(adminId,accountEditRequest);
        return ResponseEntity.ok(status);
    }
}
