package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.AccommodationService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.UserServiceImplementation;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("api/accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserServiceImplementation userService;
    @GetMapping(value = "/test")
    public String test(){
       return "application is working";
   }
    @PostMapping(value = "/create")
    public ResponseEntity<String> createAccommodation (@RequestBody Accommodation accommodation){
        String answer = accommodationService.createAccommodation(accommodation);
        return ResponseEntity.ok(answer);
    }
    @PreAuthorize("hasAuthority('ROLE_Host')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<AccommodationSummaryCollectionResponse> getHostAccommodations(@PathVariable("hostId") UUID hostId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        AccommodationSummaryCollectionResponse accommodations = accommodationService.getHostAccommodations(hostId,page,numberOfElements);
        return  ResponseEntity.ok(accommodations);

    }

    @GetMapping("/approved")
    public ResponseEntity<AccommodationSummaryCollectionResponse> getAllApprovedAccommodations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        AccommodationSummaryCollectionResponse accommodations = accommodationService.getAllApprovedAccommodations(page,numberOfElements);
        return  ResponseEntity.ok(accommodations);
    }

    @GetMapping()
    public ResponseEntity<Collection<AccommodationSummaryResponse>> getAllAccommodations() throws IOException {
        Collection<AccommodationSummaryResponse> accommodations = accommodationService.getAllAccommodations();
        return  ResponseEntity.ok(accommodations);
    }
    @PostMapping(value = "details/{accommodationId}")
    public ResponseEntity<AccommodationResponse> getAccommodation(@PathVariable("accommodationId")  UUID accommodationId) throws IOException {
        AccommodationResponse accommodation = accommodationService.getAccommodation(accommodationId);
        if(accommodation == null) {
            return new ResponseEntity<AccommodationResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accommodation);
    }
    @GetMapping(value = "/results")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> searchAccommodations(
            @RequestParam String city,
            //@RequestParam DatePeriod datePeriod,
            @RequestParam String dateStart,
            @RequestParam String dateEnd,
            @RequestParam int guestNumber) {

        OffsetDateTime offsetDateTimeStart = OffsetDateTime.parse(dateStart, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime offsetDateTimeEnd = OffsetDateTime.parse(dateEnd, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        LocalDate localDateStart = offsetDateTimeStart.toLocalDate();
        LocalDate localDateEnd = offsetDateTimeEnd.toLocalDate();

        Collection<AccommodationSummaryResponse> accommodations = accommodationService.searchAccommodations(city, localDateStart, localDateEnd, guestNumber);
//        if(accommodations.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping(value = "/filtered")
    public ResponseEntity<Collection<AccommodationSummaryResponse>> searchAccommodationsFiltered(
            @RequestParam String city,
            @RequestParam String dateStart,
            @RequestParam String dateEnd,
            @RequestParam int guestNumber,
            @RequestParam(required = false) String amenities,
            @RequestParam(required = false) String accommodationType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        OffsetDateTime offsetDateTimeStart = OffsetDateTime.parse(dateStart, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime offsetDateTimeEnd = OffsetDateTime.parse(dateEnd, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        LocalDate localDateStart = offsetDateTimeStart.toLocalDate();
        LocalDate localDateEnd = offsetDateTimeEnd.toLocalDate();

        List<String> amenitiesList = new ArrayList<String>();
        if (amenities != null) {
            amenitiesList = List.of(amenities.split(","));
        }

        double minPriceValue = (minPrice != null) ? minPrice : 0.0;
        double maxPriceValue = (maxPrice != null) ? maxPrice : 0.0;

        Collection<AccommodationSummaryResponse> accommodations = accommodationService.filterAccommodations(city, localDateStart, localDateEnd, guestNumber, amenitiesList, AccommodationType.valueOf(accommodationType), minPriceValue, maxPriceValue);

//        if (accommodations.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        return ResponseEntity.ok(accommodations);
    }

    @PreAuthorize("hasAuthority('ROLE_Guest')")
    @PutMapping(value = "/favorite/{accommodationId}")
    public ResponseEntity<Boolean> addFavoriteAccommodation(@PathVariable("accommodationId") int accommodationId) {//, @RequestBody AccommodationRequest accommodationRequest) {
        Boolean isAdded = accommodationService.addFavoriteAccommodation(accommodationId);//, accommodationRequest);

        if (isAdded != null) {
            return ResponseEntity.ok(isAdded);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/data/{hostId}") //morao sam u ovaj controller staviti jer nije drugde zbog corsa hteo nzm zasto cak i kad se doda cors da treba da radi on ne radi
    public ResponseEntity<HostData> findHostById(@PathVariable("hostId") String hostIdString){
        UUID hostId = UUID.fromString(hostIdString);
        return ResponseEntity.ok(userService.getHostById(hostId));
    }

    @PreAuthorize("hasAuthority('ROLE_Guest')")
    @GetMapping(value = "/guest/{guestId}")
    public ResponseEntity<ReservationSummaryCollectionResponse> getGuestReservations(@PathVariable("guestId") UUID guestId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        ReservationSummaryCollectionResponse reservations = accommodationService.getGuestReservations(guestId,page,numberOfElements);
        return  ResponseEntity.ok(reservations);
    }
}
