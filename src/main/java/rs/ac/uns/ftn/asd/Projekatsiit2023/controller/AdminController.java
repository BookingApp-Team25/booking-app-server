package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AdminService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewService;

import java.util.Collection;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AccommodationUpdateService accommodationUpdateService;
    @Autowired
    private AccommodationService accommodationService;
    @GetMapping(value = "/getAllAccommodationUpdates")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodationUpdates(){
        Collection<AccommodationSummaryResponse> accommodationUpdateResponses=accommodationUpdateService.getAllAccommodationUpdates();
        if(accommodationUpdateResponses == null){
            return ResponseEntity.ok(accommodationUpdateResponses);
        }
        return ResponseEntity.ok(accommodationUpdateResponses);
    }
    @GetMapping(value = "/getAllAccommodationUpdates/{accommodationId}")
    public ResponseEntity<AccommodationUpdateResponse> getAccommodationUpdate(@PathVariable("accommodationId") int id){
        AccommodationUpdateResponse accommodationUpdateResponse =accommodationUpdateService.getAccommodationUpdate(id);
        if(accommodationUpdateResponse == null){
            return ResponseEntity.ok(accommodationUpdateResponse);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }


    @PutMapping(value = "/getAllAccommodationUpdates/resolveAccommodationUpdate/{accommodationUpdateId}")
    public ResponseEntity<MessageResponse> resolveAccommodationUpdate(@PathVariable("accommodationUpdateId") int id, @RequestParam int flag){
        MessageResponse accommodationUpdateResponse = adminService.resolveAccommodationUpdate(id,flag);
        if(accommodationUpdateResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping(value = "/deleteReview/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("reviewId") int id){
        Boolean reviewResponse = reviewService.deleteReview(id);
        if(reviewResponse == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Boolean> blockUser(@PathVariable("userId") int id){
        Boolean blockedUser=adminService.blockUser(id);
        if(blockedUser == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(blockedUser);
    }

    @GetMapping(value = "/viewAllAccommodations")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodations(){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getAllAccommodations();
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
    public ResponseEntity<Collection<AccommodationSummaryResponse>> searchAccommodations(
            @RequestParam String city,
            @RequestParam DatePeriod datePeriod,
            @RequestParam int guestNumber){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.searchAccommodations(city, datePeriod,guestNumber);
        if(accommodations == null){
            return new ResponseEntity<Collection<AccommodationSummaryResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping(value = "/searchAccommodationsFiltered")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> searchAccommodationsFiltered(
            @RequestParam String city,
            @RequestParam DatePeriod datePeriod,
            @RequestParam int guestNumber,
            @RequestBody AccommodationFilteredSearchRequest accommodationFilteredSearchRequest
    ){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.searchAccommodationsFiltered(city,datePeriod,guestNumber,accommodationFilteredSearchRequest);
        if(accommodations == null){
            return new ResponseEntity<Collection<AccommodationSummaryResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodations);
    }

    @PutMapping(value = "{adminId}/editAccount")
    public ResponseEntity<Boolean> editAccount(@PathVariable("adminId") int adminId, @RequestBody AccountEditRequest accountEditRequest){
        Boolean status = adminService.editAccount(adminId,accountEditRequest);
        return ResponseEntity.ok(status);
    }
}
