package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private HostService hostService;

    @Autowired
    private GuestServiceImplementation guestService;

    @Autowired
    private UserServiceImplementation userService;

    @GetMapping(value = "{hostUsername}/log")
    public ResponseEntity<AccommodationLogCollection> generateLogs(@RequestParam(required = true) String startDateStr,@RequestParam(required = true) String endDateStr, @PathVariable("hostUsername")String hostUsername) throws IOException {

        ZonedDateTime startDateTime = ZonedDateTime.parse(startDateStr, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime endDateTime = ZonedDateTime.parse(endDateStr, DateTimeFormatter.ISO_DATE_TIME);

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        DatePeriod datePeriod = new DatePeriod(startDate,endDate);
        AccommodationLogCollection  logs = hostService.getLogsForPeriod(datePeriod,hostUsername);
        return ResponseEntity.ok(logs);
    }
    @GetMapping(value = "{accommodationId}/annual-log")
    public ResponseEntity<AccommodationMonthlyLogCollection> generateAnnualLog(@PathVariable("accommodationId") UUID accommodationId) throws IOException {
        AccommodationMonthlyLogCollection annualLog = hostService.getAnnualLog(accommodationId);
        return ResponseEntity.ok(annualLog);
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
