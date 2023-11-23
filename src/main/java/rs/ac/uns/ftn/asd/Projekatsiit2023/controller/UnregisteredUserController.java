package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
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

    @PutMapping(value = "/register")
    public ResponseEntity<Boolean> registered(@RequestBody RegistrationRequest registrationRequest){
        Boolean registrationResponse = unregisteredUserService.register(registrationRequest);
        if(registrationResponse == null){
            return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(registrationResponse);
    }

    @GetMapping(value = "/viewAllAccommodations")
    public ResponseEntity<Collection<AccommodationResponse>> getAllAccommodations(){
        Collection<AccommodationResponse> accommodations = accommodationService.getAllAccommodations();
        return  ResponseEntity.ok(accommodations);
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

    @GetMapping(value = "/viewAccommodation/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getAccommodation(@PathVariable("accommodationId") int accommodationId){
        AccommodationResponse accommodation = accommodationService.getAccommodation(accommodationId);
        if(accommodation == null){
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodation);
    }

}
