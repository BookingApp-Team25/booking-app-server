package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationUpdateService;

import java.util.Collection;

@RestController
@RequestMapping("api/accommodation-request")
public class AccommodationUpdateController {
    @Autowired
    private AccommodationUpdateService accommodationUpdateService;
    @PostMapping(value = "/{accommodationId}")
    public  ResponseEntity<MessageResponse> createEditRequest(@PathVariable("accommodationId") int id , @RequestBody AccommodationUpdateRequest accommodationUpdateRequest){
        MessageResponse accommodationResponse = accommodationUpdateService.createEditRequest(id);
        if(accommodationResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationResponse);
    }
    @PostMapping()
    public ResponseEntity<MessageResponse> createAccommodationRequest(@RequestBody AccommodationUpdateRequest accommodationUpdateRequest) {
        MessageResponse response = accommodationUpdateService.createAccommodationUpdate(accommodationUpdateRequest);
        if (response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodationUpdates(){
        Collection<AccommodationSummaryResponse> accommodationUpdateResponses=accommodationUpdateService.getAllAccommodationUpdates();
        if(accommodationUpdateResponses == null){
            return ResponseEntity.ok(accommodationUpdateResponses);
        }
        return ResponseEntity.ok(accommodationUpdateResponses);
    }
    @GetMapping(value = "/{accommodation-id}")
    public ResponseEntity<AccommodationUpdateResponse> getAccommodationUpdate(@PathVariable("accommodationId") int id){
        AccommodationUpdateResponse accommodationUpdateResponse =accommodationUpdateService.getAccommodationUpdate(id);
        if(accommodationUpdateResponse == null){
            return ResponseEntity.ok(accommodationUpdateResponse);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }
    @PutMapping(value = "/{accommodation-update-id}")
    public ResponseEntity<MessageResponse> resolveAccommodationUpdate(@PathVariable("accommodationUpdateId") int id, @RequestParam int flag){
        MessageResponse accommodationUpdateResponse = accommodationUpdateService.resolveAccommodationUpdate(id,flag);
        if(accommodationUpdateResponse == null){
            return new ResponseEntity<MessageResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodationUpdateResponse);
    }
}
