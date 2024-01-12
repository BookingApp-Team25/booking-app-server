package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private HostService hostService;

    @Autowired
    private GuestServiceImplementation guestService;

    @Autowired
    private UserServiceImplementation userService;

    @GetMapping(value = "/log")
    public ResponseEntity<Collection<AccommodationLogDataResponse>> generateLogs(@RequestParam(required = false) DatePeriod datePeriod){
        Collection<AccommodationLogDataResponse>  logs = hostService.getLogsForPeriod(datePeriod);
        return ResponseEntity.ok(logs);
    }

//    @GetMapping(value = "/hosts")
//    public ResponseEntity allHosts(){
//        return ResponseEntity.ok(hostService.findAll());
//    }

    @GetMapping(value = "/guests")
    public ResponseEntity<Collection<Guest>> allGuests(){
        return ResponseEntity.ok(guestService.findAll());
    }

    @GetMapping(value = "/users")
    public ResponseEntity<Collection<User>> allUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

}
