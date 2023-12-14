package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationFilteredSearchRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationSummaryResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;

import java.util.Collection;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @PostMapping()
    public ResponseEntity<String> createAccommodation (@RequestBody Accommodation accommodation){
        String answer = accommodationService.createAccommodation(accommodation);
        return ResponseEntity.ok(answer);
    }
    @GetMapping(value = "/{hostId}")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getHostAccommodations(@PathVariable("hostId") int hostId){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getHostAccommodations(hostId);
        return  ResponseEntity.ok(accommodations);

    }

    @GetMapping()
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodations(){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getAllAccommodations();
        return  ResponseEntity.ok(accommodations);
    }
    @GetMapping(value = "/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getAccommodation(@PathVariable("accommodationId")  UUID accommodationId){
        AccommodationResponse accommodation = accommodationService.getAccommodation(accommodationId);
        if(accommodation == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodation);
    }
    @GetMapping(value = "/results")
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
    @GetMapping(value = "/filtered")
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
    @PutMapping(value = "/favorite/{accommodationId}")
    public ResponseEntity<Boolean> addFavoriteAccommodation(@PathVariable("accommodationId") int accommodationId) {//, @RequestBody AccommodationRequest accommodationRequest) {
        Boolean isAdded = accommodationService.addFavoriteAccommodation(accommodationId);//, accommodationRequest);

        if (isAdded != null) {
            return ResponseEntity.ok(isAdded);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
