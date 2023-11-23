package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.HostService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReservationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private HostService hostService;
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private ReservationService reservationService;
    @PostMapping(value = "/createAccommodation")
    public ResponseEntity<AccommodationResponse> createAccommodation(@RequestBody AccommodationRequest accommodationRequest) {
        AccommodationResponse createdAccommodationResponse = accommodationService.createAccommodation(accommodationRequest);
        return new ResponseEntity<AccommodationResponse>(createdAccommodationResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{hostId}/viewAccommodations")
    public ResponseEntity<Collection<AccommodationResponse>> getHostAccommodations(@PathVariable("hostId") int hostId){
        Collection<AccommodationResponse> accommodations = accommodationService.getHostAccommodations(hostId);

        return  ResponseEntity.ok(accommodations);

    }
    @GetMapping(value = "/viewAccommodations/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getHostAccommodation(@PathVariable("accommodationId") int accommodationId){
        AccommodationResponse accommodationResponse = accommodationService.getAccommodation(accommodationId);
        if(accommodationResponse == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationResponse);
    }
    @PutMapping(value = "/viewAccommodations/editAccommodation/{id}")
    public  ResponseEntity<AccommodationResponse> editAccommodation(@PathVariable("id") int id , @RequestBody AccommodationRequest accommodationRequest){
        AccommodationResponse accommodationResponse = hostService.sendEditRequest(id);
        if(accommodationResponse == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationResponse);
    }
    @GetMapping(value = "{hostId}/viewReservationsFiltered")
    public ResponseEntity<Collection<ReservationResponse>> getFilteredHostReservations(@PathVariable("hostId") int hostId,
    @RequestParam(required = false) DatePeriod reservationPeriod,
    @RequestParam(required = false) String reservationName,
    @RequestParam(required = false)ReservationStatus reservationStatus){
        Collection<ReservationResponse> reservations = reservationService.getFilteredHostReservations(hostId,reservationPeriod,reservationName,reservationStatus);
        if(reservations == null){
            return new ResponseEntity<Collection<ReservationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);

    }
    @GetMapping(value = "{hostId}/viewReservations")
    public ResponseEntity<Collection<ReservationResponse>> getHostReservations(@PathVariable("hostId") int hostId){
        Collection<ReservationResponse> reservations = reservationService.getAllHostReservations(hostId);
        if(reservations == null){
            return new ResponseEntity<Collection<ReservationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);
    }
    @PutMapping(value= "/acceptReservation/{reservationId}")
    public ResponseEntity<ReservationResponse> acceptReservation(@PathVariable("reservationId") int reservationId){
        ReservationResponse reservationResponse = reservationService.acceptReservation(reservationId);
        return ResponseEntity.ok(reservationResponse);
    }
    @GetMapping(value = "/generateLogs")
    public ResponseEntity<Collection<AccommodationLogDataResponse>> generateLogs(@RequestParam(required = false) DatePeriod datePeriod){
        Collection<AccommodationLogDataResponse>  logs = hostService.getLogsForPeriod(datePeriod);
        return ResponseEntity.ok(logs);
    }
    @PostMapping(value = "/reportReview/{reviewId}")
    public ResponseEntity<ReviewResponse> reportReview(@PathVariable("reviewId") int reviewId){
        ReviewResponse reviewResponse = hostService.reportReview(reviewId);
        return ResponseEntity.ok(reviewResponse);
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

    @PutMapping(value = "{hostId}/editAccount")
    public ResponseEntity<Boolean> editAccount(@PathVariable("hostId") int hostId, @RequestBody AccountEditRequest accountEditRequest){
        Boolean status = hostService.editAccount(hostId,accountEditRequest);
        return ResponseEntity.ok(status);
    }
}
