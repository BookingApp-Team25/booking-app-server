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

    @GetMapping(value = "/log")
    public ResponseEntity<Collection<AccommodationLogDataResponse>> generateLogs(@RequestParam(required = false) DatePeriod datePeriod){
        Collection<AccommodationLogDataResponse>  logs = hostService.getLogsForPeriod(datePeriod);
        return ResponseEntity.ok(logs);
    }

}
