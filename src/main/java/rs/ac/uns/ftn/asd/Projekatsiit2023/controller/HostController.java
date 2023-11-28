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
    public ResponseEntity<String> createAccommodation(@RequestBody AccommodationRequest accommodationRequest) {
        String response = accommodationService.createAccommodation(accommodationRequest);
        if (response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok("Succesfuly created accommodation");
    }

    @GetMapping(value = "/{hostId}/viewAccommodations")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getHostAccommodations(@PathVariable("hostId") int hostId){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getHostAccommodations(hostId);
        return  ResponseEntity.ok(accommodations);

    }
    @GetMapping(value = "/viewAccommodations/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getHostAccommodation(@PathVariable("accommodationId") int accommodationId){
        AccommodationResponse accommodationResponse = accommodationService.getAccommodation(accommodationId);
        if(accommodationResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(accommodationResponse);
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
    public ResponseEntity<String> acceptReservation(@PathVariable("reservationId") int reservationId){
        String reservationResponse = reservationService.acceptReservation(reservationId);
        if(reservationResponse == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservationResponse);
    }
    @GetMapping(value = "/generateLogs")
    public ResponseEntity<Collection<AccommodationLogDataResponse>> generateLogs(@RequestParam(required = false) DatePeriod datePeriod){
        Collection<AccommodationLogDataResponse>  logs = hostService.getLogsForPeriod(datePeriod);
        return ResponseEntity.ok(logs);
    }
    @PostMapping(value = "/reportReview/{reviewId}")
    public ResponseEntity<String> reportReview(@PathVariable("reviewId") int reviewId){
        String reviewResponse = hostService.reportReview(reviewId);
        if(reviewResponse == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reviewResponse);
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

    @PutMapping(value = "{hostId}/editAccount")
    public ResponseEntity<Boolean> editAccount(@PathVariable("hostId") int hostId, @RequestBody AccountEditRequest accountEditRequest){
        Boolean status = hostService.editAccount(hostId,accountEditRequest);
        return ResponseEntity.ok(status);
    }
}
