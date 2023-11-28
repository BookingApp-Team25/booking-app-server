package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UnregisteredUserService;

import java.util.Collection;

@RestController
@RequestMapping("api/unregisteredUser")
public class UnregisteredUserController {
    @Autowired
    private UnregisteredUserService unregisteredUserService;
    @Autowired
    private AccommodationService accommodationService;
    @GetMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = unregisteredUserService.login(loginRequest);
        if(loginResponse == null){
            return new ResponseEntity<LoginResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> registered(@RequestBody RegistrationRequest registrationRequest){
        Boolean registrationResponse = unregisteredUserService.register(registrationRequest);
        if(!registrationResponse){
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(registrationResponse);
    }

    @GetMapping(value = "/viewAllAccommodations")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodations(){
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getAllAccommodations();
        return  ResponseEntity.ok(accommodations);
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

    @GetMapping(value = "/viewAccommodation/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getAccommodation(@PathVariable("accommodationId") int accommodationId){
        AccommodationResponse accommodation = accommodationService.getAccommodation(accommodationId);
        if(accommodation == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodation);
    }

}
