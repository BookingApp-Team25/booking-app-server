package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AuthorizationService;

import java.util.Collection;

@RestController
@RequestMapping("api/unregistered-user")
public class RegisterController {
    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> registered(@RequestBody RegistrationRequest registrationRequest){
        Boolean registrationResponse = authorizationService.register(registrationRequest);
        if(!registrationResponse){
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(registrationResponse);
    }


}
