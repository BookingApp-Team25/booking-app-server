package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;

import java.util.Collection;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/accommodation-request")
public class AccommodationUpdateController {
    @Autowired
    private AccommodationUpdateService accommodationUpdateService;
    @PostMapping(value = "/{accommodationId}")
    @PreAuthorize("hasAuthority('ROLE_Host')")
    public  ResponseEntity<MessageResponse> editAccommodationRequest(@PathVariable("accommodationId") UUID id, @RequestBody AccommodationRequest accommodationRequest){
        MessageResponse accommodationResponse = accommodationUpdateService.createEditRequest(id,accommodationRequest);
        if(accommodationResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationResponse);
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_Host')")
    public ResponseEntity<MessageResponse> createAccommodationRequest(@RequestBody AccommodationRequest accommodationRequest) {
        MessageResponse response = accommodationUpdateService.createAccommodationUpdate(accommodationRequest);
        if (response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public ResponseEntity<Collection<AccommodationUpdateSummaryResponse>> getAllAccommodationUpdates(){
        Collection<AccommodationUpdateSummaryResponse> accommodationUpdateResponses=accommodationUpdateService.getAllAccommodationUpdates();
        if(accommodationUpdateResponses == null){
            return ResponseEntity.ok(accommodationUpdateResponses);
        }
        return ResponseEntity.ok(accommodationUpdateResponses);
    }

    @GetMapping(value = "/{update-id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public ResponseEntity<AccommodationUpdateResponse> getAccommodationUpdate(@PathVariable("update-id") UUID id){
        AccommodationUpdateResponse accommodationUpdateResponse =accommodationUpdateService.getAccommodationUpdate(id);
        if(accommodationUpdateResponse == null){
            return ResponseEntity.ok(accommodationUpdateResponse);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }

    @PutMapping(value = "/{accommodation-update-id}")
    @PreAuthorize("hasAuthority('ROLE_Admin')")
    public ResponseEntity<MessageResponse> resolveAccommodationUpdate(@PathVariable("accommodation-update-id") UUID id, @RequestParam int flag){
        MessageResponse accommodationUpdateResponse = accommodationUpdateService.resolveAccommodationUpdate(id,flag);
        if(accommodationUpdateResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }
}
